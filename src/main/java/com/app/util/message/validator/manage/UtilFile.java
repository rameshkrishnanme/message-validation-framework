package com.app.util.message.validator.manage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class UtilFile {
    public static Boolean createDirectory(String filepath) throws IOException {
        File directory = new File(filepath);
        FileUtils.forceMkdir(directory);
        return Boolean.TRUE;
    }

    public static Boolean deleteDirectory(String filepath) throws IOException {
        File directory = new File(filepath);
        if (directory.exists()) {
            FileUtils.deleteDirectory(directory);
        }
        return Boolean.TRUE;
    }

    public static Boolean copyFileToDirectory(String filepath, String dirpath) throws IOException {
        File fpath = new File(filepath);
        File dpath = new File(dirpath);
        FileUtils.forceMkdir(dpath);
        String baseName = FilenameUtils.getBaseName(filepath) + "." + FilenameUtils.getExtension(filepath);
        String readFileContents = readFileContents(filepath);
        writeContentsToFile(dpath + "/" + baseName, readFileContents);
        return Boolean.TRUE;
    }

    public static Boolean copyFolderFS(String dest, String source) throws IOException {
        File fsource = new File(source);
        if (fsource.exists()) {
            createDirectory(dest);
            File fdest = new File(dest);

            FileUtils.copyDirectory(fsource, fdest);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean copyFolder(String dest, String source, String path) throws IOException {

        JarFile jarFile = new JarFile(path);
        String src = source.substring(1);
        Enumeration<JarEntry> enumw = jarFile.entries();

        while (enumw.hasMoreElements()) {
            JarEntry file = (JarEntry) enumw.nextElement();
            if (file.getName().startsWith(src)) {
                String pathname = dest + File.separator + StringUtils.replace(file.getName(), src, "").substring(1);

                File f = new File(pathname);

                if (file.isDirectory()) {
                    FileUtils.forceMkdir(f);
                }
                else {
                    InputStream is = jarFile.getInputStream(file);
                    FileUtils.forceMkdir(f.getParentFile());
                    FileOutputStream fos = new FileOutputStream(f);

                    while (is.available() > 0) {
                        fos.write(is.read());
                    }
                    fos.close();
                    is.close();
                }
            }
        }
        jarFile.close();
        return Boolean.TRUE;
    }

    public static Boolean writeContentsToFile(String filename, String contents) throws IOException {
        File fsource = new File(filename);
        FileUtils.writeStringToFile(fsource, contents);
        return Boolean.TRUE;
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String readFileContents(String filepath) {
        File fsource = new File(filepath);
        try {
            InputStream resourceAsStream = readAsInputStream(filepath);
            byte[] byteArray = IOUtils.toByteArray(resourceAsStream);
            return new String(byteArray);
        }
        catch (Exception e) {
            try {
                return FileUtils.readFileToString(fsource);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static InputStream readAsInputStream(String filepath) {
        InputStream resourceAsStream = UtilFile.class.getResourceAsStream(filepath);
        return resourceAsStream;
    }

    @SuppressWarnings("resource")
    public static boolean copyClassPathFile(String src, String dest) throws IOException {
        ReadableByteChannel inputChannel = null;
        FileChannel outputChannel = null;
        InputStream input = null;
        try {
            input = UtilFile.class.getResourceAsStream(src);
            inputChannel = Channels.newChannel(input);
            outputChannel = new FileOutputStream(dest).getChannel();

            outputChannel.transferFrom(inputChannel, 0, input.available());
        }
        finally {
            inputChannel.close();
            outputChannel.close();
            input.close();
        }
        return true;
    }

    private static String readInputStreamAsString(InputStream in) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }
        return buf.toString();
    }

    public static ArrayList<File> listFileInDirectory(String location) {
        ArrayList files = new ArrayList();
        File file = new File(location);
        if (file.exists()) {
            readDirectoryForFile(file, files);
        }

        return files;
    }

    public static List<File> listFileInDirectoryByExtn(String location, String extn) {
        List files = new ArrayList();
        File file = new File(location);
        if (file.exists()) {
            readDirectoryForFile(file, extn, files);
        }

        return files;
    }

    private static void readDirectoryForFile(File file, String extn, List files) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File f : listFiles)
                if (f.isDirectory()) {
                    readDirectoryForFile(f, extn, files);
                }
                else {
                    if (FilenameUtils.getExtension(f.getAbsolutePath()).equals(extn)) {
                        files.add(f);
                    }
                }
        }
    }

    private static void readDirectoryForFile(File file, List files) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File f : listFiles)
                if (f.isDirectory()) {
                    files.add(f);
                    readDirectoryForFile(f, files);
                }
                else {
                    files.add(f);
                }
        }
    }

    
    

    public static byte[] readFiletoByte(String filename) {
        try {
            final File file = new File(filename);
            byte[] byteArray = FileUtils.readFileToByteArray(file);
            return byteArray;
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        finally {
        }

        return null;
    }

}
