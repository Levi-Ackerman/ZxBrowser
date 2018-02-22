package com.woyou.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lee on 18-2-22.
 */

public class VideoUtil {
    private static final List<String> VIDEO_EXT = Arrays.asList("mp4", "flv", "mpeg");
    public static boolean isVideoExtension(String extension){
        return VIDEO_EXT.contains(extension);
    }
}
