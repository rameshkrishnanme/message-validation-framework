package com.app.util.message.validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.util.message.validator.core.grok.GrokCompilerProvider;
import com.app.util.message.validator.manage.SortMapByKeyOrValue;
import com.app.util.message.validator.pattern.pojo.MessagePattern;

import local.io.krakens.grok.api.Grok;
import local.io.krakens.grok.api.GrokCompiler;
import local.io.krakens.grok.api.Match;

public class GrokMatchDiscoverExecuter extends AbstractPatternExecuter {


    
	public String proposedMessagePattern(String message) {
	    
	    final GrokCompiler grokCompiler = getGrokCompiler();
	    final Map<String, String> patternDefinitions = grokCompiler.getPatternDefinitions();
	    
	    List<String> patterns = new ArrayList<String>();
	    
	    final String[] msgContent = message.split(MessagePattern.SEPARATOR_PATTERN);
	    
	    for (String text : msgContent) {
            
	        Map<String, Integer> patternScore = new LinkedHashMap<String, Integer>();
	        
	        
	        final Set<Entry<String, String>> entrySet = patternDefinitions.entrySet();
	        for (Entry<String, String> entry : entrySet) {
                final String key = entry.getKey();
                
                
                Grok grok = grokCompiler.compile("%{" + key + "}");
                final Match match = grok.match(text);
                final Map<String, Object> capture = match.capture();
                
                
                Integer score = calculateScore(capture);
                
                if (score > 0) {
                    patternScore.put(key, score);
                }
            }
	        
	        final String bestPattern = deduceBestPattern(patternScore, text);
	        patterns.add("%{"+bestPattern+"}");
        }
	    
	    String proposedPattern = patterns.stream().collect(Collectors.joining(System.lineSeparator()));
	    return proposedPattern;
	}

    public GrokCompiler getGrokCompiler() {
        return GrokCompilerProvider.getGrokCompiler(standAloneRepository);
    }

    private String deduceBestPattern(Map<String, Integer> patternScore, String text) {
        if(patternScore.isEmpty()) {
            return text;
        }
        
        final Map<String, Integer> sortByValue = SortMapByKeyOrValue.sortByValue(false, patternScore);
        final Optional<Entry<String, Integer>> findFirst = sortByValue.entrySet().stream().findFirst();
        return findFirst.get().getKey();
    }

    private Integer calculateScore(Map<String, Object> capture) {
        final Set<Entry<String, Object>> entrySet = capture.entrySet();
        final long score = entrySet.stream().filter(i -> i.getValue() !=null).count();
        return new Integer(String.valueOf(score));
    }


}
