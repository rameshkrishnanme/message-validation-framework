package smartvalidator;

import org.junit.Test;

import com.app.util.message.validator.GrokMatchDiscoverExecuter;
import com.app.util.message.validator.manage.UtilFile;

import local.io.krakens.grok.api.GrokCompiler;

public class GrokMatchDiscoverTest {

    @Test
    public void testBasic() {

        final String msg = UtilFile.readFileContents("/PIL.message");
        GrokMatchDiscoverExecuter grokMatchDiscoverExecuter = new GrokMatchDiscoverExecuter() {
            @Override
            public GrokCompiler getGrokCompiler() {
                return GrokMessagePatternExecuterTest.testGrokComplier();
            }

        };
        final String proposedMessagePattern = grokMatchDiscoverExecuter.proposedMessagePattern(msg);

        System.out.println(proposedMessagePattern);

    }

}
