package com.rain.common.cache.util;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;

public class SerializationUtils {
	private static final Logger LOGGER = Logger.getLogger(SerializationUtils.class);
    
    public  static byte[] serialize (Object obj) {        
        byte[] bytes = null;        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();        
        try {          
            ObjectOutputStream oos = new ObjectOutputStream(bos);           
            oos.writeObject(obj);          
            oos.flush();           
            bytes = bos.toByteArray ();        
            oos.close();           
            bos.close();          
        } catch (IOException ex) {
            LOGGER.error("toByteArrayError", ex);
        }        
        return bytes;      
    }     
         
        
    public static  Object deserialize (byte[] bytes) {        
        Object obj = null;   
        if ((bytes==null) || (bytes.length<=0)) {
        	return obj;
        }
        try {          
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);          
            ObjectInputStream ois = new ObjectInputStream (bis);          
            obj = ois.readObject();        
            ois.close();     
            bis.close();     
        } catch (IOException ex) {    
            LOGGER.error("toObjectError", ex);
        } catch (ClassNotFoundException ex) {          
            LOGGER.error("toObjectError", ex);
        }        
        return obj;      
    }     
}
