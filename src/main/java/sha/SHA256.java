package sha;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256 {

    public String decode(String message) {
        try {
            String secret = "secret";// 加密使用的key
//            String message = "Message";// 需要加密的字符串（本项目是 "{uuid}_{timestamp}" ）

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = Base64.getDecoder().decode(sha256_HMAC.doFinal(message.getBytes())); // 重点
            System.out.println(hash);
            return new String(hash);
        } catch (Exception e) {
            System.out.println("Error");
            return "Error";
        }
    }

}
