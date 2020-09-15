package org.james;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/hook")
public class HookRest {

    @PostMapping(value = "/push")
    public String doThings(@RequestBody String text){
        System.out.println(text);
        return text;
    }

}
