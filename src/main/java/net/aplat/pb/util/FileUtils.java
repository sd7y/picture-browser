package net.aplat.pb.util;

import net.aplat.pb.exception.IllegalFileAccessError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileUtils {

    public static File getFile(String root, String path) throws IOException, IllegalFileAccessError {
        String normalizedPath = Paths.get(path).normalize().toString();

        File file = new File(root, normalizedPath);

        if (file.getCanonicalFile().toPath().startsWith(new File(root).getCanonicalPath())) {
            return file;
        } else {
            throw new IllegalFileAccessError("The path " + path + " is not a valid path.");
        }
    }
}
