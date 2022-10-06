package ktgkid.spring.mvc.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;


@Component("grcp")
public class RecaptchaUtils {

    public boolean checkCaptcha(String grcp) throws IOException, ParseException {  /* throws IOException, ParseException : 예외처리 */
        String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6Lcu81oiAAAAANrOPme6LvGKrnVf2F9p3F3F4qec&response=" + grcp;
        String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36";

        Logger log = LoggerFactory.getLogger(getClass());

        // httpclient 객체 생성
        CloseableHttpClient req = HttpClients.createDefault();

        // 요청할 URL 을 get 메서드로 정의
        HttpGet get = new HttpGet(VERIFY_URL + params);

        // 요청 header 정의
        get.addHeader("User-Agent", USER_AGENT);
        get.addHeader("Content-type", "application/json");

        // 설정된 정보로 실제 URL 요청 실행
        CloseableHttpResponse res = req.execute(get);

        // 실행 여부 확인 (응답코드 : 200-정상실행, 404~505-실행실패)
        /*System.out.println(res.getCode());*/

        // 응답 결과 확인
        String result = EntityUtils.toString(res.getEntity());
        /*System.out.println(result);*/

        // 결과 문자열을 JSON 객체로 변환
        // success 키의 값을 알아냄 : json 객체명.getXxx(키)
        JSONObject json = new JSONObject(result);
        boolean success = json.getBoolean("success");
        /*log.info(success);*/   // true 로 뜸.

        // httpclient 객체 닫기
        req.close();

        return success;
    }

}
