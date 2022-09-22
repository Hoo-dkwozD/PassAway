package sg.edu.sportsschool.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import sg.edu.sportsschool.record.Sample.SampleRepository;

@SpringBootApplication
@ConfigurationPropertiesScan("sg.edu.sportsschool.helper")
public class RecordApplication {

	@Autowired
	SampleRepository sampleRepository;

	public static void main(String[] args) {
		SpringApplication.run(RecordApplication.class, args);
	}

}
