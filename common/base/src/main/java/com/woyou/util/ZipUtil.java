package com.woyou.util;

import android.text.TextUtils;

import com.woyou.AppContextHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created by ligs on 11/16/16.
 */
public class ZipUtil {
    private static final String SCHEME_ZIP = "zip";
    private static final String SCHEME_ASSETS = "assets";
    private static final String SUFFIX_ZIP = ".zip";
    private static final String SUFFIX_APK= ".apk";

    /**
     * 从assets或者local文件中读取
     *
     * @param path 支持 assets:// 或 zip://
     * @return
     */
    public static InputStream read(Path path) {
        InputStream in = null;

        Iterator<String> iterator = path.iterator();
        Path subPath = new Path(path.getScheme());
        boolean hasZip = false;

        try {
            while (iterator.hasNext()) {
                String sub = iterator.next();
                subPath.join(sub);

                if (sub.endsWith(SUFFIX_ZIP)||sub.endsWith(SUFFIX_APK)) {
                    hasZip = true;
                    break;
                }
            }

            in = readWithPath(subPath);
            if (hasZip) {
                in = readFromZip(in, iterator);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

    static InputStream readFromZip(InputStream in, Iterator<String> iterator) throws IOException {
        Path subZip = new Path("zip://");
        while (iterator.hasNext()) {
            subZip.join(iterator.next());
        }
        return readFromZip(in, subZip);
    }


    /**
     * 从zip中读取
     *
     * @param in   zip的输入流,有可能是文件夹，有可能是Zip流
     * @param path 要读取zip内的路径，如zip://ab/cd/ef
     * @return
     */
    public static InputStream readFromZip(InputStream in, Path path) throws IOException {

        if (TextUtils.isEmpty(path.getFullPath())) {
            return in;
        }

        //change to ZipInputStream
        ZipInputStream zipInput = new ZipInputStream(in);
        in = zipInput;

        Path subPath = new Path(path.getScheme());
        Iterator<String> iterator = path.iterator();
        while (iterator.hasNext()) {
            String sub = iterator.next();
            subPath.join(sub);

            if (sub.endsWith(SUFFIX_ZIP)) {
                break;
            }
        }

        ZipEntry entry;
        while ((entry = zipInput.getNextEntry()) != null) {
            if (subPath.getFullPath().endsWith(entry.getName())) {
                if (iterator.hasNext()) {
                    in = readFromZip(zipInput, iterator);
                }
                break;
            }
        }

        return in;
    }

    static InputStream readWithPath(Path path) throws IOException {

        InputStream in;
        String fullPath = path.getFullPath();
        if (path.getScheme().startsWith(SCHEME_ASSETS)) {
            in = AppContextHelper.appContext().getAssets().open(fullPath.substring(1, fullPath.length()));
        } else {
            in = new FileInputStream(fullPath);
        }

        return in;
    }

    /**
     * 从zipPath中读取zip内置的路径
     *
     * @param zipPath zip文件位置
     * @param path    zip文件内位置
     * @return
     */
    public static InputStream readFromZip(Path zipPath, Path path) {

        return null;
    }

    /**
     * 带scheme或不带的使用
     *
     * @param path
     * @return
     */
    public static InputStream readLocalFile(Path path) {

        String fullPath = path.getFullPath();

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            StreamUtil.close(fileInputStream);
            fileInputStream = null;
        }

        return fileInputStream;
    }

}


