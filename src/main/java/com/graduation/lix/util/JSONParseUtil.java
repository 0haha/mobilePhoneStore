package com.graduation.lix.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by hehe on 18-4-19.
 */
public class JSONParseUtil {

    public static <T> String objectToJSONStr(T obj){

        JSONObject jsonObject = null;
       try{
            jsonObject = JSONObject.fromObject(obj);

       }catch (Exception e){
           e.printStackTrace();
       }
       return jsonObject.toString();
    }

    public static <T> T JSONStrTOObject(String str,Class<T> cl) {
        JSONObject obj = new JSONObject().fromObject(str);
        T jb = (T)JSONObject.toBean(obj,cl);
        return jb;
    }

    public static <T> String arrToJSONStr(T obj){
        JSONArray jsonArray = null;
        try{
            jsonArray = JSONArray.fromObject(obj);
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
