package sg.edu.sportsschool.Sample;

import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.Sample.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SampleController {
    private final SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping(path = "/api/task")
    public ResponseEntity<JSONBody> getTask() {
        return sampleService.getTask();
    }

    @PostMapping(path = "/api/task")
    public ResponseEntity<JSONBody> addTask(@RequestBody Task task) {
        return sampleService.addTask(task);
    }

    @DeleteMapping(path = "/api/task/{id}")
    public ResponseEntity<JSONBody> removeTask(@PathVariable("id") long id) {
        return sampleService.removeTask(id);
    }
}
