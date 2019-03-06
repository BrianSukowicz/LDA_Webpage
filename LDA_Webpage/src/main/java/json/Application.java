package json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
	MyMethods methodCaller = new MyMethods(); //allows us to use methods from MyMethods
        methodCaller.createExcelWorkBook();
        SpringApplication.run(Application.class, args);
    }

}
