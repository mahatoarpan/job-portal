package com.test.companyms.service;

import com.test.companyms.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    boolean updateCompany(Long companyId, Company company);

    void createCompany(Company company);

    boolean deleteCompanyById(Long companyId);

    Company getCompanyById(Long companyId);
}
