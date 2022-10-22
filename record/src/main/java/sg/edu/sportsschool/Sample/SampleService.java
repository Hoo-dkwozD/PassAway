package sg.edu.sportsschool.Sample;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.core.io.Resource;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.HttpStatusCodeException;
// import org.springframework.web.client.RestTemplate;

import sg.edu.sportsschool.helper.RecordProperties;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithMessage;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.Sample.Task;

import java.util.ArrayList;

@Service
public class SampleService {
    private final SampleRepository sampleRepository;
    private final RecordProperties recordProperties;

    @Autowired
    public SampleService(SampleRepository sampleRepository, RecordProperties recordProperties) {
        this.sampleRepository = sampleRepository;
        this.recordProperties = recordProperties;
    }

    public ResponseEntity<JSONBody> getTask() {
        try {
            ArrayList<Sample> data = new ArrayList<>();
            sampleRepository.findAll().forEach(data::add);

            JSONWithData<ArrayList<Sample>> results = new JSONWithData<ArrayList<Sample>>(200, data);
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.OK);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve task. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> addTask(Task task) {
        try {
            Sample sample = sampleRepository.save(new Sample(task.getName()));

            JSONWithData<Sample> results = new JSONWithData<Sample>(201, sample);
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.OK);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to create task. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> removeTask(long id) {
        try {
            sampleRepository.deleteById(id);

            JSONWithMessage results = new JSONWithMessage(200, "Task deleted successfully. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.OK);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to delete task. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }
}
