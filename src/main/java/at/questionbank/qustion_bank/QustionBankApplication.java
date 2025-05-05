package at.questionbank.qustion_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class QustionBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(QustionBankApplication.class, args);
	}
}
