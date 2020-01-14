package com.app.util.message.validator.pattern.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.app.util.message.validator.core.FieldRules;
import com.app.util.message.validator.core.GrokPattern;
import com.app.util.message.validator.core.GrokPatternTypeEnum;

public class MessagePattern {

  public static final String SEPARATOR_PATTERN = "\r?\n";

  private String uniqueMessageIdentifier;
  private List<GrokPattern> grokPatterns;
  private List<FieldRules> fieldRules;


  public String getUniqueMessageIdentifier() {
    return uniqueMessageIdentifier;
  }

  public void setUniqueMessageIdentifier(String uniqueMessageIdentifier) {
    this.uniqueMessageIdentifier = uniqueMessageIdentifier;
  }

  public List<GrokPattern> getGrokPatterns() {
    return grokPatterns;
  }

  public void setGrokPatterns(List<GrokPattern> grokPatterns) {
    this.grokPatterns = grokPatterns;
  }

  public List<FieldRules> getFieldRules() {
    return fieldRules;
  }

  public void setFieldRules(List<FieldRules> fieldRules) {
    this.fieldRules = fieldRules;
  }

  public static String returnGroup1(String pattern, String line) {
    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);
    // Now create matcher object.
    Matcher m = r.matcher(line);
    if (m.find()) {
      return m.group(1);
    }
    return null;
  }


  public static MessagePattern buildMessagePattern(String fileContents) {

    final String[] splitPattern = fileContents.split(SEPARATOR_PATTERN);

    MessagePattern messagePattern = new MessagePattern();

    List<GrokPattern> grokPatterns = new ArrayList<GrokPattern>();
    messagePattern.setGrokPatterns(grokPatterns);

    for (int i = 0; i < splitPattern.length; i++) {

      String line = splitPattern[i];
      if (GrokPatternTypeEnum.REPEAT_START.getReservedPattern().equals(line)) {
        grokPatterns.add(new GrokPattern(GrokPatternTypeEnum.REPEAT_START));
        continue;
      }
      if (GrokPatternTypeEnum.REPEAT_END.getReservedPattern().equals(line)) {
        grokPatterns.add(new GrokPattern(GrokPatternTypeEnum.REPEAT_END));
        continue;
      }
      if (line.startsWith(GrokPatternTypeEnum.OPT_START.getReservedPattern())) {
        
        line = line.replace("[OPT::]", "").replace("[::OPT]", "");
        GrokPattern grokPattern = new GrokPattern(line, GrokPatternTypeEnum.PATTERN);
        grokPattern.setOptional(true);
        grokPatterns.add(grokPattern);
        continue;
      }
      grokPatterns.add(new GrokPattern(line, GrokPatternTypeEnum.PATTERN));
    }

    messagePattern.setGrokPatterns(grokPatterns);

    return messagePattern;
  }

}
