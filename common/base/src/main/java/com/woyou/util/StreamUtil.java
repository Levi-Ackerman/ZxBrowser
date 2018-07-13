package com.woyou.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class StreamUtil {

    private static final String TAG = StreamUtil.class.getSimpleName();

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static final void close(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 从输入流读取内容, 写入到输出流中. 此方法使用大小为8192字节的默认的缓冲区.
     *
     * @param in  输入流
     * @param out 输出流
     * @throws IOException 输入输出异常
     */
    public static void io(InputStream in, OutputStream out) throws IOException {
        io(in, out, -1);
    }

    /**
     * 从输入流读取内容, 写入到输出流中. 使用指定大小的缓冲区.
     *
     * @param in         输入流
     * @param out        输出流
     * @param bufferSize 缓冲区大小(字节数)
     * @throws IOException 输入输出异常
     */
    public static void io(InputStream in, OutputStream out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    /**
     * 从输入流读取内容, 写入到输出流中. 此方法使用大小为4096字符的默认的缓冲区.
     *
     * @param in  输入流
     * @param out 输出流
     * @throws IOException 输入输出异常
     */
    public static void io(Reader in, Writer out) throws IOException {
        io(in, out, -1);
    }

    /**
     * 从输入流读取内容, 写入到输出流中. 使用指定大小的缓冲区.
     *
     * @param in         输入流
     * @param out        输出流
     * @param bufferSize 缓冲区大小(字符数)
     * @throws IOException 输入输出异常
     */
    public static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        }

        char[] buffer = new char[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    /**
     * 将指定输入流的所有文本全部读出到一个字符串中.
     *
     * @param in 要读取的输入流
     * @return 从输入流中取得的文本
     * @throws IOException 输入输出异常
     */
    public static String readText(InputStream in) throws IOException {
        return readText(in, null, -1);
    }

    /**
     * 将指定输入流的所有文本全部读出到一个字符串中.
     *
     * @param in       要读取的输入流
     * @param encoding 文本编码方式
     * @return 从输入流中取得的文本
     * @throws IOException 输入输出异常
     */
    public static String readText(InputStream in, String encoding) throws IOException {
        return readText(in, encoding, -1);
    }

    /**
     * 将指定输入流的所有文本全部读出到一个字符串中.
     *
     * @param in         要读取的输入流
     * @param encoding   文本编码方式
     * @param bufferSize 缓冲区大小(字符数)
     * @return 从输入流中取得的文本
     * @throws IOException 输入输出异常
     */
    public static String readText(InputStream in, String encoding, int bufferSize) throws IOException {
        Reader reader = (encoding == null) ? new InputStreamReader(in) : new InputStreamReader(in, encoding);

        return readText(reader, bufferSize);
    }

    /**
     * 将指定<code>Reader</code>的所有文本全部读出到一个字符串中.
     *
     * @param reader 要读取的<code>Reader</code>
     * @return 从<code>Reader</code>中取得的文本
     * @throws IOException 输入输出异常
     */
    public static String readText(Reader reader) throws IOException {
        return readText(reader, -1);
    }

    /**
     * 将指定<code>Reader</code>的所有文本全部读出到一个字符串中.
     *
     * @param reader     要读取的<code>Reader</code>
     * @param bufferSize 缓冲区的大小(字符数)
     * @return 从<code>Reader</code>中取得的文本
     * @throws IOException 输入输出异常
     */
    public static String readText(Reader reader, int bufferSize) throws IOException {
        StringWriter writer = new StringWriter();

        io(reader, writer, bufferSize);
        return writer.toString();
    }
}
