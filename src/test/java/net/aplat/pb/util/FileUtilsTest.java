package net.aplat.pb.util;

import net.aplat.pb.exception.IllegalFileAccessError;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {

    @Test
    void checkIfDirectoryTraversal() throws IOException, IllegalFileAccessError {
        assertEquals(new File("/aaa/abc").getAbsolutePath(), FileUtils.getFile("/aaa", "/abc").getAbsolutePath());
        assertEquals(new File("/aaa/abc").getAbsolutePath(), FileUtils.getFile("/aaa", "./abc").getAbsolutePath());
        assertEquals(new File("/aaa/abc/123").getAbsolutePath(), FileUtils.getFile("/aaa", "abc/123").getAbsolutePath());
        assertEquals(new File("/aaa/~/abc").getAbsolutePath(), FileUtils.getFile("/aaa", "~/abc").getAbsolutePath());
        assertThrows(IllegalFileAccessError.class, () -> FileUtils.getFile("/aaa", "../abc"));
        assertThrows(IllegalFileAccessError.class, () -> FileUtils.getFile("/aaa", "abc/../../../123"));
    }

}
