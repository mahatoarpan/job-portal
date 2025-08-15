package com.test.jobs.controller;

import com.test.jobs.entity.Job;
import com.test.jobs.service.JobService;
import com.test.jobs.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping()
    public ResponseEntity<List<JobDTO>> findAll() {
        List<JobDTO> jobs = jobService.findAll();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable("jobId") Long jobId) {
        JobDTO jobDTO =  jobService.findJobById(jobId);
        if (jobDTO != null) {
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job Created Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJobById(@PathVariable("jobId") Long jobId) {
        boolean removed = jobService.deleteJobById(jobId);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJobById(@PathVariable("jobId") Long jobId, @RequestBody Job job) {
        boolean updated = jobService.updateJobById(jobId, job);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
