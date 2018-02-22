package com.woyou.util;

import android.webkit.MimeTypeMap;

/**
 * Created by lee on 18-2-22.
 */

public class MimeTypeUtil {
    public static String getMimeType(String url){
        String extension = MimeTypeMap.getFileExtensionFromUrl(url.toLowerCase());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mimeType;
    }
}
