package com.app.util.message.validator.manage;

public class ApplicationLogger {

    private static final String ENABLE_LOG = "ENABLE_LOG";

    // static File file = new File("D:/temp.text");

    public static void log(String log) {

        if (true || System.getProperty(ENABLE_LOG) != null) {
            System.out.println(log);

            /*
             * try { FileUtils.writeStringToFile(file, log, true); } catch
             * (IOException e) { // TODO Auto-generated catch block
             * e.printStackTrace(); }
             */
        }
    }

    public static void logException(Exception e) {
        // TODO Auto-generated method stub
        
        if (true || System.getProperty(ENABLE_LOG) != null) {
            System.out.println(e.getMessage()  + " : " + e.getStackTrace() );


        }
        
    }
}
