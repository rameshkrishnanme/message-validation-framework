package com.app.util.message.validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.util.message.validator.core.GrokPattern;
import com.app.util.message.validator.core.GrokPatternTypeEnum;
import com.app.util.message.validator.core.grok.GrokCompilerProvider;
import com.app.util.message.validator.domain.repository.StandAloneRepository;
import com.app.util.message.validator.exception.NoMatchException;
import com.app.util.message.validator.exception.PartialMatchException;
import com.app.util.message.validator.pojo.Validate;
import com.github.cliftonlabs.json_simple.JsonObject;

import local.io.krakens.grok.api.Grok;
import local.io.krakens.grok.api.GrokCompiler;

public class AbstractPatternExecuter {
    
    private static final String XML_STR = "XML";

    @Autowired
    protected StandAloneRepository standAloneRepository;
    
    
    public void updateJSON(JsonObject jsonObject, int messageSeq, final Map<String, Object> unmodifiableMap) {
        jsonObject.put("L"+String.valueOf(messageSeq + 1), unmodifiableMap);
        //jsonObject.putAll(unmodifiableMap);
    }
    
    
    protected void responseFormat(Validate validate, String output) {
        try {
            if (XML_STR.equalsIgnoreCase(validate.getResponseFormat())) {
                JSONObject json = new JSONObject(output);
                validate.setTestResults(XML.toString(json, "MESSAGEDECODE"));
            }
            else {
                validate.setTestResults(output);
            }
        }
        catch (JSONException ignore) {
            // Not happens
        }
    }

    public Map<String, Object> buildErrorMap(Exception e) {
        final Map<String, Object> unmodifiableMap = Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("error", e.getMessage());
            }
        });
        return unmodifiableMap;
    }
    
    public Map<String, Object> buildErrorMap(String key, Object value, Map<String, Object> map) {
        final Map<String, Object> unmodifiableMap = Collections.unmodifiableMap(new HashMap<String, Object>(map) {
            {
                put(key, value);
            }
        });
        return unmodifiableMap;
    }
    

    public GrokPattern[] buildSubGrokPatterns(int currSeq, GrokPattern[] grokPatterns) {

        int count = 0;
        int startIndex = currSeq;
        int endIndex = currSeq;
        

        for (int patternSeq = currSeq; patternSeq < grokPatterns.length; patternSeq++) {
            final GrokPattern grokPattern = grokPatterns[patternSeq];

            if (grokPattern.getGrokPatternType() == GrokPatternTypeEnum.REPEAT_START) {
                 count++;
            }

            if (grokPattern.getGrokPatternType() == GrokPatternTypeEnum.REPEAT_END) {
                 count--;
               
            }
            if (count == 0) {
                endIndex = patternSeq;
                break;
            }
        }
        
        final GrokPattern[] grokPatternsSub = Arrays.copyOfRange(grokPatterns, startIndex, endIndex + 1);
        
        return grokPatternsSub;
    }

    public Map<String, Object> compileMessage(GrokPattern grokPattern, String messageLine, JsonObject jsonObject, int messageSeq) throws NoMatchException, PartialMatchException {
        final Grok compile = getGrokComplier().compile(grokPattern.getRegEx());
        Map<String, Object> capture = compile.matchAndCapture(messageLine, true);
        
        if (capture == null || capture.isEmpty()) {
            throw new NoMatchException();
        }
        
        StringBuilder sb = new StringBuilder();
        matchString(capture.values(), sb);
        
        updateJSON(jsonObject, messageSeq, capture);
        
        if (messageLine != null) {
            if (messageLine.replace(" ", "").length() > sb.toString().length()) {
                capture = buildErrorMap("ERROR", "Partial Match found", capture);
                updateJSON(jsonObject, messageSeq, capture);
                throw new PartialMatchException();
            }
        }
        return capture;
    }


    private void matchString(Collection<Object> collection, StringBuilder sb) {
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
            final Object next = iterator.next();
            if (next instanceof List) {
               List list = (List) next;
               matchString(list, sb);
               /* for (Object object : list) {
                    String type = (String) object;
                    sb.append(StringUtils.trim(type));
                }*/
            }
            else {
                String type = (String) next;
                sb.append(StringUtils.trim(type));
            }
        }
    }


    public GrokCompiler getGrokComplier() {
        return GrokCompilerProvider.getGrokCompiler(standAloneRepository);
    }

}
