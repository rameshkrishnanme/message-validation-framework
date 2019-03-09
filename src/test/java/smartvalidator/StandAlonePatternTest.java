package smartvalidator;

import java.util.Map;

import org.junit.Test;

import com.app.util.message.validator.core.grok.GrokCompilerProvider;

import local.io.krakens.grok.api.Grok;
import local.io.krakens.grok.api.GrokCompiler;
import local.io.krakens.grok.api.Match;

public class StandAlonePatternTest {

    public void testBasic() {

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        grokCompiler.register("AIRLINE", "[A-Z]{2,3}");
        grokCompiler.register("FLIGHTNUMBER", "[0-9]{2,4}");
        grokCompiler.register("SUFFIX", "[A-Z]{1}");

        grokCompiler.register("FLIGHTIDENTIFIER", "%{AIRLINE:airline}%{FLIGHTNUMBER:flightnumber}%{SUFFIX:suffix}");

        final Grok compile = grokCompiler
            .compile("%{FLIGHTIDENTIFIER:flightidentifier1}%{FLIGHTIDENTIFIER:flightidentifier2}");

        final String text = "MAS123AAI123G";
        final Match match = compile.match(text);
        final Map<String, Object> capture = match.capture();
        System.out.println("------------" + capture);

    }
    
    
    public void testFlightIdentifier() {

        final GrokCompiler grokCompiler = GrokMessagePatternExecuterTest.testGrokComplier();

        final Grok compile = grokCompiler
            .compile("%{FLIGHTIDENTIFIER:flightidentifier}");

        final String text = "MA123";
        final Match match = compile.match(text);
        final Map<String, Object> capture = match.capture();
        System.out.println("------------" + capture);

    }
   
   
    public void testSeatLine() {

        final GrokCompiler grokCompiler = GrokMessagePatternExecuterTest.testGrokComplier();
        
        final Grok compile = grokCompiler
            .compile("%{NIL}|%{SEATNUM} ?(%{AIRPORT} %{PAXNAME})?");

        final String text = "02C DEN OHARA/KENDRAMS";   
        final Match match = compile.match(text);
        final Map<String, Object> capture = match.capture();
        System.out.println("------------" + capture);

    }
   
    @Test
    public void testAirport() {
        final GrokCompiler grokCompiler = GrokMessagePatternExecuterTest.testGrokComplier();
        grokCompiler.register("AIRPORT", "[A-Z]{3}");
        
        final Grok compile = grokCompiler.compile("%{AIRPORT:airport}");
        final Match match = compile.match("DEL");
        final Map<String, Object> capture = match.capture();

        System.out.println("1------------" + capture);

    }


}
