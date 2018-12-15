package com.raitahila.k.rnbmcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;
import org.bytedeco.javacpp.tesseract;
import org.springframework.stereotype.Component;

@Component
public class OCR {
    public String ocrFile(String path) throws Exception {
        //Modified from sample 
        //https://github.com/bytedeco/javacpp-presets/tree/master/tesseract
        
        BytePointer outText;

        tesseract.TessBaseAPI api = new tesseract.TessBaseAPI();
        // Initialize tesseract-ocr without specifying tessdata path
        if (api.Init(".", "fin") != 0) {
            throw new Exception("Could not initialize tesseract.");
        }

        // Open input image with leptonica library
        lept.PIX image = pixRead(path);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
        
        return string;
    }
    
    public String ocrBase64(String imageBase64) throws Exception {
        byte[] data = Base64.decodeBase64(imageBase64);
        File image;
        try {
            image = File.createTempFile("img", ".temp");
        } catch (IOException ex) {
            throw new Exception("Could not open temporary file");
        }
        try (OutputStream stream = new FileOutputStream(image)) {
            stream.write(data);
        } catch (IOException ex) {
            throw new Exception("Could not write temporary file");
        }
        return this.ocrFile(image.getAbsolutePath());
    }
}
