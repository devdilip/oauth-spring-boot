package com.authentication.oauth.common.utilities;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class AppUtil {

    /**
     * Method to create temporary documents
     * @param filePath
     * @param constant
     * @throws IOException
     */
    public static void createFile(String filePath, String constant) throws IOException{
        try(FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(constant);
        }
    }
}
