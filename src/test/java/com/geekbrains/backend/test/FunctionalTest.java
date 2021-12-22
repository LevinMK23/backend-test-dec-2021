package com.geekbrains.backend.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class FunctionalTest {


    public static Properties readProperties() throws Exception {
        Properties properties = new Properties();
        Files.readAllLines(Paths.get(
                FunctionalTest.class.getResource("test.properties").toURI()))
                .forEach(str -> {
                    String[] props = str.split("=");
                    properties.setProperty(props[0].trim(), props[1].trim());
                });
        return properties;
    }

    public String getStringResource(String name) throws IOException {
        String dir = getClass().getSimpleName();
        InputStream is = getClass().getResource(dir + "/" + name)
                .openStream();
        StringBuilder s = new StringBuilder();
        while (is.available() > 0) {
            s.append((char) is.read());
        }
        return s.toString();
    }

    public File getFileResource(String name) {
        String dir = getClass().getSimpleName();
        return new File(getClass().getResource(dir + "/" + name).getFile());
    }

}
