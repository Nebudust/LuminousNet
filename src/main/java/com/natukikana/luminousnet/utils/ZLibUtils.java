package com.natukikana.luminousnet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;


/**
 * zlib压缩工具类
 */
public class ZLibUtils {
    public static String decode(InputStream data){
        try{
            return new String(decompress(Base64.getDecoder().decode(new String(data.readAllBytes(),StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }catch (Exception e){
            return "杨睿你妈死了";
        }
    }
    public static byte[] encode(byte[] data){
        try
        {
            return compress(Base64.getEncoder().encode(data));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // 压缩直接数组
    private static byte[] compress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    // 解压缩 字节数组
    private static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream  = new ByteArrayOutputStream(data.length);
        try {
            byte[] result  = new byte[1024];
            while (!inflater.finished()) {
                int count  = inflater.inflate(result );
                outputStream .write(result , 0, count );
            }
            output = outputStream .toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                outputStream .close();
                inflater.end();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}
