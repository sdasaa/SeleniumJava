package com.sj.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class ConfigurationUtilities {

    public static final Logger logger = LogManager.getLogger(ConfigurationUtilities.class);
    private static Properties properties;

    public static InputStream resourceLoader(String path) {
        logger.info(" Returning the input stream of file -> {}", path);
        try {
            InputStream iostream = ConfigurationUtilities.class.getClassLoader().getResourceAsStream(path);
            if (Objects.nonNull(iostream)) {
                logger.info(" File {} found in class path ", path);
                return iostream;
            }else {
                logger.info(" File {} NOT found in class path, creating new InputStream from path ", path);
                return Files.newInputStream(Path.of(path));
            }
        }catch (Exception e){
            logger.error(" Unable to return the input stream of the file -> {} ", path);
            e.printStackTrace();
        }
        return null;
    }

    public static void loadDefaultProperties(String path){
        logger.info(" Reading Default Properties for the execution from -> {}", path);
        InputStream iostream = resourceLoader(path);
        try {
            properties = new Properties();
            properties.load(iostream);
        }catch (Exception e){
            logger.error(" Exception in loading iostream !! " );
        }
        logger.info(" Checking if any default Properties are being overridden from cmd line ");
        for(String key : properties.stringPropertyNames()){
            if(System.getProperties().containsKey(key)){
                logger.info(" The default property -> {} is overridden from cmd line, updating it from -> {} to -> {} ",key ,properties.getProperty(key) ,System.getProperty(key));
                properties.setProperty(key, System.getProperty(key));
            }
            logger.info(" Default values for this execution is : {} {} ", key, properties.getProperty(key));
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}