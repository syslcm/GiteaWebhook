package org.james;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(path = "/hook")
public class HookRest {

    @PostMapping(value = "/push")
    public String doThings(@RequestBody String text, @RequestHeader Map<String, String> headers){
        String signature = "no data";
        if (headers.get("HTTP_X_GITEA_SIGNATURE") != null){
            signature = headers.get("HTTP_X_GITEA_SIGNATURE");
        }else if (headers.get("X_GITEA_SIGNATURE") != null){
            signature = headers.get("X_GITEA_SIGNATURE");
        }
        System.out.println("GITEA_SIGNATURE:" + signature) ;
        System.out.println(text);
        return text;
    }

}
