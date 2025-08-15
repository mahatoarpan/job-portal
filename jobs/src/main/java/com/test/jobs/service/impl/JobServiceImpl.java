package com.test.jobs.service.impl;

import com.test.jobs.clients.CompanyClient;
import com.test.jobs.entity.Job;
import com.test.jobs.external.Review;
import com.test.jobs.mapper.JobMapper;
import com.test.jobs.repository.JobRepository;
import com.test.jobs.service.JobService;
import com.test.jobs.dto.JobDTO;
import com.test.jobs.external.Company;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO findJobById(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);

        if (job != null) {
            return convertToDto(job);
        }

        return null;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJobById(Long jobId) {
        if (jobRepository.existsById(jobId)){
            jobRepository.deleteById(jobId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJobById(Long jobId, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription( updatedJob.getDescription() );
            job.setMinSalary( updatedJob.getMinSalary());
            job.setMaxSalary( updatedJob.getMaxSalary());
            job.setLocation( updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    private JobDTO convertToDto(Job job) {
        JobDTO jobDTO = new JobDTO();
        Company company = companyClient.getCompany(job.getCompanyId());
//        Company company = restTemplate.getForObject("http://COMPANY-SERVICE/companies/" + job.getCompanyId(), Company.class);

//        ResponseEntity<List<Review>> reviewResponse =  restTemplate.exchange(
//                "http://REVIEW-SERVICE/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//
//        List<Review> reviews = reviewResponse.getBody();
        return JobMapper.mapToJobWithCompanyDto(job, company);
    }
}
