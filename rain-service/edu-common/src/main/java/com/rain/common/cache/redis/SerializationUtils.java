package com.rain.common.cache.redis;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class SerializationUtils {

    public static byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            byteArrayOutputStream.close();
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
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (IOException e) {
            log.error("toObjectError", e);
        } catch (ClassNotFoundException e) {
            log.error("toObjectError", e);
        }
        return obj;
    }
}
