package org.james;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.james.model.ContentObject;
import org.james.persistance.IntoDb;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import sha.SHA256;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping(path = "/hook")
public class HookRest {

    private DataSource dataSource;
    private IntoDb intoDb;

    @PostMapping(value = "/push")
    public String doThings(@RequestBody String text, @RequestHeader Map<String, String> headers){
        String signature = "no data";
        String giteaEvent = "nuknown event";
        if (headers.get("x-gitea-signature") != null){
            signature = headers.get("x-gitea-signature");
        }
        if (headers.get("x-gitea-event") != null){
            giteaEvent = headers.get("x-gitea-event");
        }
        System.out.println("GITEA_SIGNATURE:" + signature) ;
        System.out.println("gitea-event:" + giteaEvent) ;
        System.out.println(text);
        SHA256 sha256 = new SHA256();
        if (signature != null && signature.equals(sha256.encode(text)) ){
            System.out.println("PASS");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ContentObject contentObject = null;
        try {
            contentObject = objectMapper.readValue(text, ContentObject.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (contentObject != null) {
            if (contentObject.getRepository() != null) {
                if (contentObject.getRepository().getFullName() != null) {
                    System.out.println("Full Name:" + contentObject.getRepository().getFullName());
                }else{
                    System.out.printf("contentObject.getRepository().getFullName() IS NULL!");
                }
            }else{
                System.out.printf("contentObject.getRepository() IS NULL!");
            }
        }else{
            System.out.println("contentObject is NULL!");
        }

        return text;
    }

    @PostMapping(value = "/dbtest")
    public String insertDBTest(){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        int c = jt.update("insert into git_added (content) values ('Ffald"+ System.currentTimeMillis() + "')");
        return String.valueOf(c);
    }

    @PostMapping(value = "/default")
    public String doDefault(@RequestBody String text, @RequestHeader Map<String, String> headers){
        int result = 0;
        String strResult = "0";
        String signature = "no data";
        String giteaEvent = "nuknown event";
        String giteaDelivery = "";
        if (headers.get("x-gitea-signature") != null){
            signature = headers.get("x-gitea-signature");
        }
        if (headers.get("x-gitea-event") != null){
            giteaEvent = headers.get("x-gitea-event");
        }
        if (headers.get("x-github-delivery") != null){
            giteaDelivery = headers.get("x-github-delivery");
        }
        System.out.println("GITEA_SIGNATURE:" + signature) ;
        System.out.println("gitea-event:" + giteaEvent) ;
        System.out.println(text);
        SHA256 sha256 = new SHA256();
        if (signature != null && signature.equals(sha256.encode(text)) ){
            System.out.println("PASS");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ContentObject contentObject = null;
        try {
            contentObject = objectMapper.readValue(text, ContentObject.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (contentObject != null) {
            if (contentObject.getRepository() != null) {
                if (contentObject.getRepository().getFullName() != null) {
                    System.out.println("Full Name:" + contentObject.getRepository().getFullName());
                }else{
                    System.out.printf("contentObject.getRepository().getFullName() IS NULL!");
                }
            }else{
                System.out.printf("contentObject.getRepository() IS NULL!");
            }
//            IntoDb intoDb = new IntoDb();
            intoDb.setGiteaEvent(giteaEvent);
            intoDb.setGiteaDelivery(giteaDelivery);
            long resultL = intoDb.insert(contentObject);
            strResult = String.valueOf(resultL);
//            strResult = intoDb.insert2(contentObject);

        }else{
            System.out.println("contentObject is NULL!");
        }

        return strResult ;//String.valueOf(result);
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public IntoDb getIntoDb() {
        return intoDb;
    }

    @Autowired
    public void setIntoDb(IntoDb intoDb) {
        this.intoDb = intoDb;
    }
}

/*

host
user-agent
content-length
content-type
x-github-delivery
x-github-event
x-gitea-delivery
x-gitea-event
x-gitea-signature
x-gogs-delivery
x-gogs-event
x-gogs-signature
accept-encoding

*/


/*

GITEA_SIGNATURE:e1ed765a7ce7407f96bdd9c9115ec917b433cf1328cd2fa5300542b22cdd6687
        gitea-event:push

*/
