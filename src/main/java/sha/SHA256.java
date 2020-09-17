package sha;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static void main(String[] args) {
        SHA256 sha256 = new SHA256();
        String msg = "{\n" +
                "  \"secret\": \"123456\",\n" +
                "  \"ref\": \"refs/heads/master\",\n" +
                "  \"before\": \"62d128da4b55c47b1e703b5e60b5dfa46233d093\",\n" +
                "  \"after\": \"741e370ddca6fc13fcd4937781890c7532dd34fe\",\n" +
                "  \"compare_url\": \"http://10.0.3.43:3000/SDD_Lib/WebhookOnGiea/compare/62d128da4b55c47b1e703b5e60b5dfa46233d093...741e370ddca6fc13fcd4937781890c7532dd34fe\",\n" +
                "  \"commits\": [\n" +
                "    {\n" +
                "      \"id\": \"741e370ddca6fc13fcd4937781890c7532dd34fe\",\n" +
                "      \"message\": \"增加 SHA256 加密\\n\",\n" +
                "      \"url\": \"http://10.0.3.43:3000/SDD_Lib/WebhookOnGiea/commit/741e370ddca6fc13fcd4937781890c7532dd34fe\",\n" +
                "      \"author\": {\n" +
                "        \"name\": \"tw009077\",\n" +
                "        \"email\": \"james_song@usiglobal.com\",\n" +
                "        \"username\": \"tw009077\"\n" +
                "      },\n" +
                "      \"committer\": {\n" +
                "        \"name\": \"tw009077\",\n" +
                "        \"email\": \"james_song@usiglobal.com\",\n" +
                "        \"username\": \"tw009077\"\n" +
                "      },\n" +
                "      \"verification\": null,\n" +
                "      \"timestamp\": \"2020-09-16T15:41:17+08:00\",\n" +
                "      \"added\": [],\n" +
                "      \"removed\": [],\n" +
                "      \"modified\": [\n" +
                "        \"src/main/java/org/james/HookRest.java\",\n" +
                "        \"src/main/java/sha/SHA256.java\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"head_commit\": null,\n" +
                "  \"repository\": {\n" +
                "    \"id\": 384,\n" +
                "    \"owner\": {\n" +
                "      \"id\": 73,\n" +
                "      \"login\": \"SDD_Lib\",\n" +
                "      \"full_name\": \"\",\n" +
                "      \"email\": \"\",\n" +
                "      \"avatar_url\": \"http://10.0.3.43:3000/user/avatar/SDD_Lib/-1\",\n" +
                "      \"language\": \"\",\n" +
                "      \"is_admin\": false,\n" +
                "      \"last_login\": \"1970-01-01T08:00:00+08:00\",\n" +
                "      \"created\": \"2020-06-18T13:58:21+08:00\",\n" +
                "      \"username\": \"SDD_Lib\"\n" +
                "    },\n" +
                "    \"name\": \"WebhookOnGiea\",\n" +
                "    \"full_name\": \"SDD_Lib/WebhookOnGiea\",\n" +
                "    \"description\": \"\",\n" +
                "    \"empty\": false,\n" +
                "    \"private\": false,\n" +
                "    \"fork\": false,\n" +
                "    \"template\": false,\n" +
                "    \"parent\": null,\n" +
                "    \"mirror\": false,\n" +
                "    \"size\": 54,\n" +
                "    \"html_url\": \"http://10.0.3.43:3000/SDD_Lib/WebhookOnGiea\",\n" +
                "    \"ssh_url\": \"Administrator@localhost:SDD_Lib/WebhookOnGiea.git\",\n" +
                "    \"clone_url\": \"http://10.0.3.43:3000/SDD_Lib/WebhookOnGiea.git\",\n" +
                "    \"original_url\": \"\",\n" +
                "    \"website\": \"\",\n" +
                "    \"stars_count\": 0,\n" +
                "    \"forks_count\": 0,\n" +
                "    \"watchers_count\": 31,\n" +
                "    \"open_issues_count\": 0,\n" +
                "    \"open_pr_counter\": 0,\n" +
                "    \"release_counter\": 0,\n" +
                "    \"default_branch\": \"master\",\n" +
                "    \"archived\": false,\n" +
                "    \"created_at\": \"2020-09-15T13:39:58+08:00\",\n" +
                "    \"updated_at\": \"2020-09-16T15:41:33+08:00\",\n" +
                "    \"permissions\": {\n" +
                "      \"admin\": true,\n" +
                "      \"push\": true,\n" +
                "      \"pull\": true\n" +
                "    },\n" +
                "    \"has_issues\": true,\n" +
                "    \"internal_tracker\": {\n" +
                "      \"enable_time_tracker\": true,\n" +
                "      \"allow_only_contributors_to_track_time\": true,\n" +
                "      \"enable_issue_dependencies\": true\n" +
                "    },\n" +
                "    \"has_wiki\": true,\n" +
                "    \"has_pull_requests\": true,\n" +
                "    \"ignore_whitespace_conflicts\": false,\n" +
                "    \"allow_merge_commits\": true,\n" +
                "    \"allow_rebase\": true,\n" +
                "    \"allow_rebase_explicit\": true,\n" +
                "    \"allow_squash_merge\": true,\n" +
                "    \"avatar_url\": \"\"\n" +
                "  },\n" +
                "  \"pusher\": {\n" +
                "    \"id\": 59,\n" +
                "    \"login\": \"tw009077\",\n" +
                "    \"full_name\": \"James_Song\",\n" +
                "    \"email\": \"james_song@usiglobal.com\",\n" +
                "    \"avatar_url\": \"http://10.0.3.43:3000/user/avatar/tw009077/-1\",\n" +
                "    \"language\": \"zh-TW\",\n" +
                "    \"is_admin\": true,\n" +
                "    \"last_login\": \"2020-09-15T13:13:34+08:00\",\n" +
                "    \"created\": \"2020-05-20T15:02:29+08:00\",\n" +
                "    \"username\": \"tw009077\"\n" +
                "  },\n" +
                "  \"sender\": {\n" +
                "    \"id\": 59,\n" +
                "    \"login\": \"tw009077\",\n" +
                "    \"full_name\": \"James_Song\",\n" +
                "    \"email\": \"james_song@usiglobal.com\",\n" +
                "    \"avatar_url\": \"http://10.0.3.43:3000/user/avatar/tw009077/-1\",\n" +
                "    \"language\": \"zh-TW\",\n" +
                "    \"is_admin\": true,\n" +
                "    \"last_login\": \"2020-09-15T13:13:34+08:00\",\n" +
                "    \"created\": \"2020-05-20T15:02:29+08:00\",\n" +
                "    \"username\": \"tw009077\"\n" +
                "  }\n" +
                "}";
        String result = sha256.encode(msg);
        System.out.println(result);
//ce166865e2125e187fa898da9e85b0dbcafdf9d9c664ed804ba43a146c3199f2
    }


    public String encode(String message) {
        try {
            String secret = "123456";// 加密使用的key

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = byteArrayToHexString(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
            return hash;
        }
        catch (Exception e){
            System.out.println("Error");
            return "Error";
        }

    }
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
