package at.questionbank.qustion_bank;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class QustionBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(QustionBankApplication.class, args);
		MongoClient mongoClient = MongoClients.create(
				MongoClientSettings.builder()
						.applyToClusterSettings(builder ->
								builder.hosts(Arrays.asList(
										new ServerAddress("172.18.0.5", 27017),
										new ServerAddress("172.18.0.2", 27017),
										new ServerAddress("172.18.0.3", 27017))))
						.build());

	}
}
