// 패키지 이름
package site.shcrm.shcrm_backend;

// 임포트한 라이브러리 목록
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 컨트롤러 애너테이션
// 'Hello' 클래스는 컨트롤러의 기능을 가짐
@Controller
public class Hello {
    // 리퀘스트 매핑 애너테이션
    // localhost:8080/hello의 URL 요청시 hello 메소드를 실행
    @RequestMapping("/hello")
    // 리스폰스바디 애너테이션
    // hello 메소드의 응답 결과 반환
    @ResponseBody
    public String hello() {
        return "Hello World! !@!";
    }
}

/////////////////////////////////////////////////////////