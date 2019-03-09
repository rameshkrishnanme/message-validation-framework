package com.app.util.message.validator;


import com.app.util.message.validator.core.GrokPattern;
import com.app.util.message.validator.core.GrokPatternTypeEnum;
import com.app.util.message.validator.core.RepeatType;
import com.github.cliftonlabs.json_simple.JsonObject;

public class RepeatPatternExecuter extends AbstractPatternExecuter {
    
    public Exception repeatException = null; 
    
    int messageSeqFinish = 0;
    int messageSeq = 0;

    public void execute(GrokPattern[] subGrokPatterns, String[] msgContent, int messageSeq, JsonObject jsonObject) {
        
        this.messageSeq = messageSeq;
        int start = 1;
        int end = subGrokPatterns.length - 1;

        while (true) {

            final RepeatType repeatType = repeatPatternExecute(subGrokPatterns, msgContent, jsonObject, start, end);
            
            if(repeatType == RepeatType.ERROR) {
                return;
            }
        }
    }

    private RepeatType repeatPatternExecute(GrokPattern[] subGrokPatterns, String[] msgContent, JsonObject jsonObject, 
        int start, int end) {
        
        for (int patternSeq = start; patternSeq < end; patternSeq++) {

             GrokPattern grokPattern = subGrokPatterns[patternSeq];

            if (grokPattern.getGrokPatternType() == GrokPatternTypeEnum.REPEAT_START) {

                GrokPattern[] subNewGrokPatterns = buildSubGrokPatterns(patternSeq, subGrokPatterns);

                RepeatPatternExecuter repeatPatternExecuter = new RepeatPatternExecuter();
                repeatPatternExecuter.execute(subNewGrokPatterns, msgContent, messageSeq, jsonObject);
                messageSeq = repeatPatternExecuter.getMessageSeqFinish();
                // Continue Pattern
                patternSeq = patternSeq + subNewGrokPatterns.length + 1;
                
                if (patternSeq == subGrokPatterns.length ) {
                    return RepeatType.FINISH;
                }
                grokPattern = subGrokPatterns[patternSeq];
            }
            
            final String message = msgContent[messageSeq];
            
            try {
                compileMessage(grokPattern, message, jsonObject, messageSeq);
                //jsonObject.put(String.valueOf(messageSeq), compileMessage);
            }
            catch (Exception e) {
                // jsonObject.put("error", e.getMessage());
                repeatException  = e;
                setMessageSeqFinish(messageSeq);
                return RepeatType.ERROR;
            }
            messageSeq++;
        }
        return RepeatType.FINISH;
    }

    public int getMessageSeqFinish() {
        return messageSeqFinish;
    }

    public void setMessageSeqFinish(int messageSeqFinish) {
        this.messageSeqFinish = messageSeqFinish;
    }
    
    
}
