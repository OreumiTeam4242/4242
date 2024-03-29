package team.ftft.project4242;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import team.ftft.project4242.commons.utils.file.AwsProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {AwsProperties.class})
public class Project4242Application {

    public static void main(String[] args) {
        SpringApplication.run(Project4242Application.class, args);
    }

}
