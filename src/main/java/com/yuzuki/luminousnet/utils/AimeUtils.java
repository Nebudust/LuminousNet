package com.yuzuki.luminousnet.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AimeUtils
{
    public static Map<String, Object> getBaseInfo(ByteBuf in) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("gameId", in.toString(0x000a, 0x000e - 0x000a, StandardCharsets.US_ASCII));
        resultMap.put("keychipId", in.toString(0x0014, 0x001f - 0x0014, StandardCharsets.US_ASCII));
        System.out.println(in.toString(0x000a, 0x000e - 0x000a, StandardCharsets.US_ASCII) + " - " + in.toString(0x0014, 0x001f - 0x0014, StandardCharsets.US_ASCII));
        return resultMap;
    }

    public static byte[] InputStreamToByteArray(InputStream is)
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建输出流对象
            if (is.available() <= 0)
            {
                return new byte[0];
            }
            byte[] b = is.readAllBytes();
            byte[] array = bos.toByteArray();
            bos.close();
            return array;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ByteBuf Decrypt(ByteBuf src)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("Copyright(C)SEGA".getBytes(StandardCharsets.UTF_8), "AES"));
            return Unpooled.copiedBuffer(cipher.doFinal(ByteBufUtils.toBytes(src)));
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static ByteBuf Encrypt(byte[] src)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("Copyright(C)SEGA".getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] bytes = cipher.doFinal(src);
            return Unpooled.copiedBuffer(bytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
