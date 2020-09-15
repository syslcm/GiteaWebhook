package org.james;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(path = "/hook")
public class HookRest {

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
        return text;
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
        accept-encoding*/
