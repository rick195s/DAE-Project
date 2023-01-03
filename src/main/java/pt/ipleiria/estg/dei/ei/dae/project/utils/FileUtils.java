package pt.ipleiria.estg.dei.ei.dae.project.utils;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public String upload(String dirpath, String filename, InputPart inputPart) throws IOException {
        MultivaluedMap<String, String> headers = inputPart.getHeaders();

        String uploaddir = System.getProperty("user.home") + File.separator + "uploads" + File.separator + dirpath;


        // convert the uploaded file to inputstream
        InputStream inputStream = inputPart.getBody(InputStream.class, null);

        byte[] bytes = IOUtils.toByteArray(inputStream);

        mkdirIfNotExists(uploaddir);

        String filepath =  uploaddir + java.io.File.separator + filename;
        writeFile(bytes, filepath);

        return filepath;
    }

    private void mkdirIfNotExists(String path) {
        java.io.File file = new java.io.File(path);

        if (! file.exists()) {
            file.mkdirs();
        }
    }

    public String getFilename(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }

        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        var file = new java.io.File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

        System.out.println("Written: " + filename);
    }
}
