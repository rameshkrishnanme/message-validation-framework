package com.app.util.message.validator.manage;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import com.github.cliftonlabs.json_simple.Jsoner;

public class JSONOrderSerialzer {

    public static String toJson(Map<String, Object> map) {
        final StringWriter writable = new StringWriter();
        try {
            toJson(writable, map);
        }
        catch (final IOException caught) {
            /* See java.io.StringWriter. */
        }
        return writable.toString();
    }


    public static void toJson(final Writer writable, Map<String, Object> map) throws IOException {
        /* Writes the map in JSON object format. */
        boolean isFirstEntry = true;
        final Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        writable.write('{');
        while (entries.hasNext()) {
            if (isFirstEntry) {
                isFirstEntry = false;
            }
            else {
                writable.write(',');
            }
            final Map.Entry<String, Object> entry = entries.next();
            writable.write(Jsoner.serialize(entry.getKey()));
            writable.write(':');
            writable.write(Jsoner.serialize(entry.getValue()));
        }
        writable.write('}');
    }
}
