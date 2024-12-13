package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Company;
import iuh.fit.edu.backend.repositories.CompanyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class CompanyService {

    @Autowired
    private CompanyRespository companyRespository;

    public Page<Company> findAll(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return companyRespository.findAll(pageable);
    }

    public Page<Company> findByKey(String key, int page, int size, String sortBy, String sortDirection) {
        Page<Company> companyPage = new PageImpl<>(new ArrayList<>());
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if (key.isEmpty()) {
            return companyPage;
        } else {
            companyPage = companyRespository.findByJob(key, pageable);
            if (companyPage == null || companyPage.isEmpty()) {
                companyPage = companyRespository.findByKey(key, pageable);
            }
        }

        return companyPage;

    }
}
