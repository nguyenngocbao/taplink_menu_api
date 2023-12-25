package taplink.network.menu.api.commons.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import taplink.network.menu.api.commons.constants.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUtils {

    public static String getFileExtensionWithTimestamp(String fileName) {
        // Define a regular expression pattern to match the image name and file extension
        Pattern pattern = Pattern.compile("(.+?)(\\.[^.]+)-\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.matches()) {
            return matcher.group(2).substring(1);
        } else {
            return null;
        }
    }

    public String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return ""; // No file extension found
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}