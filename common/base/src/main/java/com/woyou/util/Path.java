package com.woyou.util;

import android.text.TextUtils;

import java.util.Iterator;

/**
 * 路径类
 * 可以接受带scheme与不带的
 * eg.
 * scheme://XXX/xxx
 * /XXX/xxx
 * XXX/xxx
 * Created by ligs on 8/31/16.
 */
public class Path implements Iterable<String> {
    private static final int DEFAULT_SIZE = 16;
    private static final String SCHEME_SUFFIX = "://";

    private char mSpeartor = '/';
    private String mScheme = null;
    private StringBuilder mPathContainer;
    private int mParentIndex = 0;//当前最后分隔符的位置

    public Path() {
        mPathContainer = new StringBuilder();
    }

    public Path(String path) {
        if (!TextUtils.isEmpty(path)) {
            mPathContainer = new StringBuilder(path.length() + DEFAULT_SIZE);
            join(path);
        }
    }

    public Path getParent() {
        ///避免多线程问题
        return new Path(toString().substring(0, mParentIndex));
    }

    public Path join(String path) {
        if (null == mScheme) {
            mScheme = getScheme(path);
            path = path.substring(mScheme.length(), path.length());
        }

        if (path != null && path.length() > 0) {
            //以斜杠开头则需要添加到container中

            String speartor = String.valueOf(mSpeartor);
            //            if (path.startsWith(speartor))
            //                insertSeparator();

            path = trim(path);

            String[] parts = path.split(speartor);
            for (String part : parts) {
                append(part);
            }
        }
        return this;
    }

    public Path clone() {
        return new Path(toString());
    }

    private void append(String str) {
        //        if ((mPathContainer.length() > 0) ||
        //                (/*mPathContainer.length() == 0 && */!TextUtils.isEmpty(str)/* && mSpeartor == str.charAt(0)*/)) {
        //            insertSeparator();
        //        }
        insertSeparator();
        mPathContainer.append(str);
    }

    //过滤前面多余斜杠和后面的斜杠，中间多余斜杠
    private String trim(String path) {

        int start = 0;
        int end = path.length() - 1;

        if (end < 0) {
            return "";
        }

        char c = '\0';
        while (start <= end && (((c = path.charAt(start)) == mSpeartor) || c == ' ')) {
            start++;
        }

        while (end >= start && (((c = path.charAt(end)) == mSpeartor) || c == ' ')) {
            end--;
        }

        //不可使用缓存，trim有可能多线程，保证无副作用
        StringBuilder sb = new StringBuilder(end - start + 1);
        //        if (path.charAt(0) == mSpeartor)
        //            sb.append(mSpeartor);

        int index = start;
        while (start <= end) {
            c = path.charAt(start);
            if (c != mSpeartor) {
                if (index != start) {
                    sb.append(mSpeartor);
                }
                sb.append(c);
                index = ++start;
            } else {
                start++;
            }
        }

        return sb.toString();
    }

    private void insertSeparator() {
        mPathContainer.append(mSpeartor);
        mParentIndex = mScheme.length() + mPathContainer.length();
    }

    public String toString() {
        return mScheme + mPathContainer.toString();
    }

    /**
     * 获取路径（不带scheme）
     *
     * @return
     */
    public String getFullPath() {
        return mPathContainer.toString();
    }

    private String getScheme(String fullPath) {
        String scheme = "";
        if (!TextUtils.isEmpty(fullPath)) {
            int index = judgeScheme((fullPath));
            if (index >= 0) {
                scheme = fullPath.substring(0, index) + SCHEME_SUFFIX;
            }
        }
        return scheme;
    }

    private int judgeScheme(String path) {
        int j;

        for (j = 0; j < path.length(); j++) {
            char c = path.charAt(j);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                if (StringUtil.match(path, j, SCHEME_SUFFIX)) {
                    return j;
                }
            }
        }
        return -1;
    }

    public String getScheme() {
        return mScheme;
    }

    //迭代路径
    @Override
    public Iterator<String> iterator() {
        return new PathIterator(this);
    }

    private class PathIterator implements Iterator<String> {

        private String[] container = {};
        //每一个都会以/开始
        private int index = 1;

        PathIterator(Path path) {
            if (path != null) {
                container = path.mPathContainer.toString().split(String.valueOf(mSpeartor));
            }
        }

        @Override
        public boolean hasNext() {
            return index < container.length;
        }

        @Override
        public String next() {
            return container[index++];
        }

        @Override
        public void remove() {
            //DO NOTHING
        }
    }
}
