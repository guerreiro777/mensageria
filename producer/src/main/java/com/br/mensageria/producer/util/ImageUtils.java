package com.br.mensageria.producer.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    private ImageUtils() {}

    public static String encodeImageToBase64(final String path, final String imageName) throws IOException {
        final String inputFile = path + imageName;
        byte[] fileContent = FileUtils.readFileToByteArray(new File(inputFile));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    public static Boolean deleteImage(final String path, final String imageName) throws IOException {
        final String inputFile = path + imageName;
        File f = new File(inputFile);
        return f.delete();
    }
}
