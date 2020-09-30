package org.james;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.james.model.ContentObject;
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

    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
        payload=%7B%0A++%22secret%22%3A+%22secret%22%2C%0A++%22ref%22%3A+%22refs%2Fheads%2Fmaster%22%2C%0A++%22before%22%3A+%22ee534b1ade43abd6bc3d2b5379c25a31608e5513%22%2C%0A++%22after%22%3A+%2262d128da4b55c47b1e703b5e60b5dfa46233d093%22%2C%0A++%22compare_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2FSDD_Lib%2FWebhookOnGiea%2Fcompare%2Fee534b1ade43abd6bc3d2b5379c25a31608e5513...62d128da4b55c47b1e703b5e60b5dfa46233d093%22%2C%0A++%22commits%22%3A+%5B%0A++++%7B%0A++++++%22id%22%3A+%2262d128da4b55c47b1e703b5e60b5dfa46233d093%22%2C%0A++++++%22message%22%3A+%22REST%2Fpush+rest+--+%C3%A7%C2%94%C2%B1+header+%C3%A5%C2%8F%C2%96+signature+%2C+event+%C3%A5%C2%80%C2%BC%5Cn%22%2C%0A++++++%22url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2FSDD_Lib%2FWebhookOnGiea%2Fcommit%2F62d128da4b55c47b1e703b5e60b5dfa46233d093%22%2C%0A++++++%22author%22%3A+%7B%0A++++++++%22name%22%3A+%22tw009077%22%2C%0A++++++++%22email%22%3A+%22james_song%40usiglobal.com%22%2C%0A++++++++%22username%22%3A+%22tw009077%22%0A++++++%7D%2C%0A++++++%22committer%22%3A+%7B%0A++++++++%22name%22%3A+%22tw009077%22%2C%0A++++++++%22email%22%3A+%22james_song%40usiglobal.com%22%2C%0A++++++++%22username%22%3A+%22tw009077%22%0A++++++%7D%2C%0A++++++%22verification%22%3A+null%2C%0A++++++%22timestamp%22%3A+%222020-09-15T18%3A15%3A51%2B08%3A00%22%2C%0A++++++%22added%22%3A+%5B%5D%2C%0A++++++%22removed%22%3A+%5B%5D%2C%0A++++++%22modified%22%3A+%5B%0A++++++++%22src%2Fmain%2Fjava%2Forg%2Fjames%2FHookRest.java%22%0A++++++%5D%0A++++%7D%0A++%5D%2C%0A++%22head_commit%22%3A+null%2C%0A++%22repository%22%3A+%7B%0A++++%22id%22%3A+384%2C%0A++++%22owner%22%3A+%7B%0A++++++%22id%22%3A+73%2C%0A++++++%22login%22%3A+%22SDD_Lib%22%2C%0A++++++%22full_name%22%3A+%22%22%2C%0A++++++%22email%22%3A+%22%22%2C%0A++++++%22avatar_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2Fuser%2Favatar%2FSDD_Lib%2F-1%22%2C%0A++++++%22language%22%3A+%22%22%2C%0A++++++%22is_admin%22%3A+false%2C%0A++++++%22last_login%22%3A+%221970-01-01T08%3A00%3A00%2B08%3A00%22%2C%0A++++++%22created%22%3A+%222020-06-18T13%3A58%3A21%2B08%3A00%22%2C%0A++++++%22username%22%3A+%22SDD_Lib%22%0A++++%7D%2C%0A++++%22name%22%3A+%22WebhookOnGiea%22%2C%0A++++%22full_name%22%3A+%22SDD_Lib%2FWebhookOnGiea%22%2C%0A++++%22description%22%3A+%22%22%2C%0A++++%22empty%22%3A+false%2C%0A++++%22private%22%3A+false%2C%0A++++%22fork%22%3A+false%2C%0A++++%22template%22%3A+false%2C%0A++++%22parent%22%3A+null%2C%0A++++%22mirror%22%3A+false%2C%0A++++%22size%22%3A+50%2C%0A++++%22html_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2FSDD_Lib%2FWebhookOnGiea%22%2C%0A++++%22ssh_url%22%3A+%22Administrator%40localhost%3ASDD_Lib%2FWebhookOnGiea.git%22%2C%0A++++%22clone_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2FSDD_Lib%2FWebhookOnGiea.git%22%2C%0A++++%22original_url%22%3A+%22%22%2C%0A++++%22website%22%3A+%22%22%2C%0A++++%22stars_count%22%3A+0%2C%0A++++%22forks_count%22%3A+0%2C%0A++++%22watchers_count%22%3A+31%2C%0A++++%22open_issues_count%22%3A+0%2C%0A++++%22open_pr_counter%22%3A+0%2C%0A++++%22release_counter%22%3A+0%2C%0A++++%22default_branch%22%3A+%22master%22%2C%0A++++%22archived%22%3A+false%2C%0A++++%22created_at%22%3A+%222020-09-15T13%3A39%3A58%2B08%3A00%22%2C%0A++++%22updated_at%22%3A+%222020-09-15T18%3A18%3A19%2B08%3A00%22%2C%0A++++%22permissions%22%3A+%7B%0A++++++%22admin%22%3A+true%2C%0A++++++%22push%22%3A+true%2C%0A++++++%22pull%22%3A+true%0A++++%7D%2C%0A++++%22has_issues%22%3A+true%2C%0A++++%22internal_tracker%22%3A+%7B%0A++++++%22enable_time_tracker%22%3A+true%2C%0A++++++%22allow_only_contributors_to_track_time%22%3A+true%2C%0A++++++%22enable_issue_dependencies%22%3A+true%0A++++%7D%2C%0A++++%22has_wiki%22%3A+true%2C%0A++++%22has_pull_requests%22%3A+true%2C%0A++++%22ignore_whitespace_conflicts%22%3A+false%2C%0A++++%22allow_merge_commits%22%3A+true%2C%0A++++%22allow_rebase%22%3A+true%2C%0A++++%22allow_rebase_explicit%22%3A+true%2C%0A++++%22allow_squash_merge%22%3A+true%2C%0A++++%22avatar_url%22%3A+%22%22%0A++%7D%2C%0A++%22pusher%22%3A+%7B%0A++++%22id%22%3A+59%2C%0A++++%22login%22%3A+%22tw009077%22%2C%0A++++%22full_name%22%3A+%22James_Song%22%2C%0A++++%22email%22%3A+%22james_song%40usiglobal.com%22%2C%0A++++%22avatar_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2Fuser%2Favatar%2Ftw009077%2F-1%22%2C%0A++++%22language%22%3A+%22zh-TW%22%2C%0A++++%22is_admin%22%3A+true%2C%0A++++%22last_login%22%3A+%222020-09-15T13%3A13%3A34%2B08%3A00%22%2C%0A++++%22created%22%3A+%222020-05-20T15%3A02%3A29%2B08%3A00%22%2C%0A++++%22username%22%3A+%22tw009077%22%0A++%7D%2C%0A++%22sender%22%3A+%7B%0A++++%22id%22%3A+59%2C%0A++++%22login%22%3A+%22tw009077%22%2C%0A++++%22full_name%22%3A+%22James_Song%22%2C%0A++++%22email%22%3A+%22james_song%40usiglobal.com%22%2C%0A++++%22avatar_url%22%3A+%22http%3A%2F%2F10.0.3.43%3A3000%2Fuser%2Favatar%2Ftw009077%2F-1%22%2C%0A++++%22language%22%3A+%22zh-TW%22%2C%0A++++%22is_admin%22%3A+true%2C%0A++++%22last_login%22%3A+%222020-09-15T13%3A13%3A34%2B08%3A00%22%2C%0A++++%22created%22%3A+%222020-05-20T15%3A02%3A29%2B08%3A00%22%2C%0A++++%22username%22%3A+%22tw009077%22%0A++%7D%0A%7D
*/
