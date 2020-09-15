package first;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "myFirstWebhook", urlPatterns = "/servlet/push")
public class HookServlet extends HttpServlet {


    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null) {
            wholeStr += str;
        }
        System.out.println(wholeStr);
        response.getWriter().println(wholeStr);
    }
}
