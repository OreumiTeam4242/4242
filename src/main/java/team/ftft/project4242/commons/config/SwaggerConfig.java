package team.ftft.project4242.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("4242 API") // API 제목
                .description("스터디/프로젝트 팀원 모집을 중점으로 하는 4242프로젝트의 API 명세서입니다. 모집글,신청글,스터디/프로젝트 팀 구성, 신고글에 관련한 CRUD 기능이 주 기능입니다.") // API 설명
                .version("1.0.0"); // API 버전
    }
}
