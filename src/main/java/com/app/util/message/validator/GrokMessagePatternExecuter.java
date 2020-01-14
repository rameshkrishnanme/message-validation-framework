package com.app.util.message.validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.util.message.validator.core.GrokPattern;
import com.app.util.message.validator.core.GrokPatternTypeEnum;
import com.app.util.message.validator.domain.repository.MessagePatternRepository;
import com.app.util.message.validator.domain.repository.StandAloneRepository;
import com.app.util.message.validator.exception.NoMatchException;
import com.app.util.message.validator.exception.PartialMatchException;
import com.app.util.message.validator.manage.JSONOrderSerialzer;
import com.app.util.message.validator.manage.SortMapByKeyOrValue;
import com.app.util.message.validator.pattern.pojo.MessagePattern;
import com.app.util.message.validator.pattern.pojo.MessageValidResponse;
import com.app.util.message.validator.pojo.ResponseCode;
import com.app.util.message.validator.pojo.Validate;
import com.github.cliftonlabs.json_simple.JsonObject;

@Component
public class GrokMessagePatternExecuter extends AbstractPatternExecuter {
    
    @Autowired
    MessagePatternRepository messagePatternRepository;
    
    @Autowired
    protected StandAloneRepository standAloneRepository;
    
    
    public Validate validate(Validate validate) {
        final MessagePattern messagePattern = MessagePattern.buildMessagePattern(validate.getTestPattern());
        final MessageValidResponse validateMessage = validateMessage(validate.getTestMessage(), messagePattern);

        validate.setResponseCode(validateMessage.getStatus().getCode());
        validate.setResponseMsg(validateMessage.getStatus().getMessage());
        responseFormat(validate, validateMessage.getJsonPlainText());

        // if (validateMessage.getStatus() == ResponseCode.SUCCESS.getCode() ) {
        /*
         * } else { ObjectMapper mapper = new ObjectMapper(); String jsonString;
         * try { jsonString =
         * mapper.writeValueAsString(validateMessage.getErrorDetails());
         * responseFormat(validate, jsonString); } catch
         * (JsonProcessingException e) { // ignore } }
         */

        return validate;
    }

    public Validate validate(Validate validate, String uniqueMessageIdentifier) {
        
        MessagePattern messagePattern = loadMessagePattern(uniqueMessageIdentifier);
        final MessageValidResponse validateMessage = validateMessage(validate.getTestMessage(), messagePattern);
        
        validate.setResponseCode(validateMessage.getStatus().getCode());
        validate.setResponseMsg(validateMessage.getStatus().getMessage());
        responseFormat(validate, validateMessage.getJsonPlainText());
        return validate;
    }

    public MessageValidResponse validateMessage(String message, MessagePattern messagePattern) {

        MessageValidResponse messageValidResponse = new MessageValidResponse();

        final String[] msgContent = message.split(MessagePattern.SEPARATOR_PATTERN);
        int messageLength = msgContent.length;
        int patternLength = messagePattern.getGrokPatterns().size();

        GrokPattern[] grokPatterns = new GrokPattern[patternLength];
        messagePattern.getGrokPatterns().toArray(grokPatterns);

        String jsonObject = validatePatternByLine(grokPatterns, msgContent, messageLength, patternLength,
            messageValidResponse);

        populateResponse(messageValidResponse, jsonObject);

        return messageValidResponse;
    }

    private void populateResponse(MessageValidResponse messageValidResponse, String jsonObject) {
        messageValidResponse.setJsonPlainText(jsonObject);
        if (messageValidResponse.getStatus() == null) {
            messageValidResponse.setStatus(ResponseCode.SUCCESS);
        }
    }

    private String validatePatternByLine(GrokPattern[] grokPatterns, final String[] msgContent, int messageLength,
        int patternLength, MessageValidResponse messageValidResponse) {

        JsonObject jsonObject = new JsonObject(new LinkedHashMap<>());
        int patternSeq = 0;
        int messageSeq = 0;

        RepeatPatternExecuter repeatPatternExecuter = new RepeatPatternExecuter(standAloneRepository);
        for (patternSeq = 0; patternSeq < patternLength; patternSeq++) {

            GrokPattern grokPattern = grokPatterns[patternSeq];

            while (grokPattern.getGrokPatternType() == GrokPatternTypeEnum.REPEAT_START) {
            	GrokPattern[] subGrokPatterns = buildSubGrokPatterns(patternSeq, grokPatterns);

                
                repeatPatternExecuter.execute(subGrokPatterns, msgContent, messageSeq, jsonObject);
                messageSeq = repeatPatternExecuter.getMessageSeqFinish();

                patternSeq = patternSeq + subGrokPatterns.length;

                if (patternSeq == grokPatterns.length) {
                    break;
                }
                grokPattern = grokPatterns[patternSeq];
            }
            
			/*
			 * if (grokPattern.getGrokPatternType() == GrokPatternTypeEnum.REPEAT_START) {
			 * 
			 * GrokPattern[] subGrokPatterns = buildSubGrokPatterns(patternSeq,
			 * grokPatterns);
			 * 
			 * 
			 * repeatPatternExecuter.execute(subGrokPatterns, msgContent, messageSeq,
			 * jsonObject); messageSeq = repeatPatternExecuter.getMessageSeqFinish();
			 * 
			 * patternSeq = patternSeq + subGrokPatterns.length;
			 * 
			 * if (patternSeq == grokPatterns.length) { break; } grokPattern =
			 * grokPatterns[patternSeq]; }
			 */
            
            if (messageSeq == messageLength) {
            	break;
            }

            try {
            	
            	
            	final String message = msgContent[messageSeq];
                //final Map<String, Object> compileMessage = compileMessage(grokPattern, message);
                //jsonObject.put(String.valueOf(messageSeq), compileMessage);
                
                compileMessage(grokPattern, message, jsonObject, messageSeq, grokPatterns);
            }
            catch (NoMatchException e) {
                if (repeatPatternExecuter.repeatException != null 
                    && repeatPatternExecuter.repeatException instanceof IllegalArgumentException) {
                    final Map<String, Object> unmodifiableMap = buildErrorMap(repeatPatternExecuter.repeatException);
                    updateJSON(jsonObject, messageSeq, unmodifiableMap);
                    messageValidResponse.setStatus(ResponseCode.RUNTIME_ERROR);
                } else {
                    final Map<String, Object> unmodifiableMap = buildErrorMap(e);
                    updateJSON(jsonObject, messageSeq, unmodifiableMap);
                    messageValidResponse.setStatus(ResponseCode.NO_MATCH);
                }
            }
            catch (PartialMatchException e) {
                messageValidResponse.setStatus(ResponseCode.PARTIAL_MATCH);
            } 
            catch (IllegalArgumentException e) {
                final Map<String, Object> unmodifiableMap = buildErrorMap(e);
                updateJSON(jsonObject, messageSeq, unmodifiableMap);
                messageValidResponse.setStatus(ResponseCode.RUNTIME_ERROR);
            }
            catch (Exception e) {
                final Map<String, Object> unmodifiableMap = buildErrorMap(e);
                updateJSON(jsonObject, messageSeq, unmodifiableMap);
                messageValidResponse.setStatus(ResponseCode.NO_MATCH);
            }
            messageSeq++;
        }
        final Map<String, Object> sortByKey = SortMapByKeyOrValue.sortByKey(true, jsonObject);
        return JSONOrderSerialzer.toJson(sortByKey);
    }


    public MessagePattern loadMessagePattern(String uniqueMessageIdentifier) {
        final List<com.app.util.message.validator.domain.MessagePattern> messagePatterns = messagePatternRepository
            .findByIdentifier(uniqueMessageIdentifier);
        
        if (messagePatterns == null || messagePatterns.isEmpty()) {
            return null;
        }
        
        final com.app.util.message.validator.domain.MessagePattern messagePattern = messagePatterns.get(0);
        
        return MessagePattern.buildMessagePattern(messagePattern.getPattern());
    }

}
