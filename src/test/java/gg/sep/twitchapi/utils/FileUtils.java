package gg.sep.twitchapi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import gg.sep.twitchapi.model.users.TestTwitchUser;

/**
 * Test Utilities class for working with files.
 */
public final class FileUtils {

    private FileUtils() { }

    /**
     * Returns the file contents of the specified file path within the resources directory.
     * @param resourcePath Relative path within the resources directory.
     * @return String contents of the file.
     */
    public static String getResourceFileContents(final String resourcePath) {
        final ClassLoader loader = TestTwitchUser.class.getClassLoader();
        try {
            final File file = new File(loader.getResource(resourcePath).getFile());
            final FileInputStream stream =  new FileInputStream(file);
            return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
