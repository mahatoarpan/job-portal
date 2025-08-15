package com.test.jobs.service;

import com.test.jobs.entity.Job;
import com.test.jobs.dto.JobDTO;

import java.util.List;

public interface JobService {

    List<JobDTO> findAll();

    JobDTO findJobById(Long jobId);

    void createJob(Job job);

    boolean deleteJobById(Long jobId);

    boolean updateJobById(Long jobId, Job updatedJob);
}
