package com.test.jobs.mapper;

import com.test.jobs.dto.JobDTO;
import com.test.jobs.entity.Job;
import com.test.jobs.external.Company;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);

        return jobDTO;
    }
}
