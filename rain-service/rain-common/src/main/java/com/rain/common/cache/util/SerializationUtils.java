package com.rain.common.cache.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author kunliu
 */
@Slf4j
public class SerializationUtils {

    public static byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            @Cleanup ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            log.error("toByteArrayError", ex);
        }
        return bytes;
    }


    public static Object deserialize(byte[] bytes) {
        Object obj = null;
        if ((bytes == null) || (bytes.length <= 0)) {
            return obj;
        }
        try {
            @Cleanup ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            @Cleanup ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException ex) {
            log.error("toObjectError", ex);
        } catch (ClassNotFoundException ex) {
            log.error("toObjectError", ex);
        }
        return obj;
    }
}
