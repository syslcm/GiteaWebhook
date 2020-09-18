package org.james;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.james.model.TestObject;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }

    public void test(){
        String val = "{\"str\":\"this is a string\",\"bln\" : true,\"i\" : 99,\"list\" : [\"ele1\",\"ele2\"]}";
        ObjectMapper objectMapper = new ObjectMapper();
        TestObject testObject = null;
        try {
            testObject = objectMapper.readValue(val, TestObject.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.printf(testObject.getStr());

    }
}
//{"str":"this is a string","bln" : true,"i" : 99,"list" : ["ele1","ele2"]}
