package com.app.util.message.validator.core.grok;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.util.message.validator.domain.StandAlone;
import com.app.util.message.validator.domain.repository.StandAloneRepository;

import local.io.krakens.grok.api.GrokCompiler;

public class GrokCompilerProvider {

    private static GrokCompiler grokCompiler;
    
    public GrokCompilerProvider() {
        
    }
    
    public GrokCompilerProvider(StandAloneRepository standAloneRepository) {
        prepareCompiler(standAloneRepository);
    }

    private static void prepareCompiler(StandAloneRepository standAloneRepository) {
        grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        loadStandAlonePattern(grokCompiler, standAloneRepository);
    }

    public static void loadStandAlonePattern(GrokCompiler grokCompiler, StandAloneRepository standAloneRepository) {
        final List<StandAlone> standAlones = standAloneRepository.findAll();
        for (StandAlone standAlone : standAlones) {
            grokCompiler.register(standAlone.getName(), standAlone.getPattern());
        }
    }

    public static GrokCompiler getGrokCompiler(StandAloneRepository standAloneRepository) {
        if (grokCompiler == null) {
            prepareCompiler(standAloneRepository);
        }
        return grokCompiler;
    }

    public void setGrokCompiler(GrokCompiler grokCompilerParam) {
        grokCompiler = grokCompilerParam;
    }

    public static void clear() {
        grokCompiler = null;
    }
    
    
    

}
