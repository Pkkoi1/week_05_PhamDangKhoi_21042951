package iuh.fit.edu.fontend.controllers;


import com.neovisionaries.i18n.CountryCode;
import iuh.fit.edu.backend.models.Company;
import iuh.fit.edu.backend.models.Job;
import iuh.fit.edu.backend.repositories.AddressRepository;
import iuh.fit.edu.backend.repositories.CompanyRespository;
import iuh.fit.edu.backend.services.CompanyService;
import iuh.fit.edu.backend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRespository companyRespository;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(value = {"", "/"})
    public String showListCompany(Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  Optional<String> search) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Company> companyPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            companyPage = companyService.findByKey(search.get(), currentPage - 1, pageSize, "id", "asc");
            model.addAttribute("search", search.get());
        } else {
            companyPage = companyService.findAll(currentPage - 1, pageSize, "id", "asc");
        }
        model.addAttribute("companyPage", companyPage);
        System.out.println(companyPage.getContent().size());
        int totalPages = companyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "companies/list";
    }

    @GetMapping("/show_edit_form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView mav = new ModelAndView();
        Optional<Company> company = companyRespository.findById(id);
        if (company.isPresent()) {
            Company company1 = company.get();
            mav.addObject("company", company1);
            List<CountryCode> sortedCountries = Arrays.stream(CountryCode.values())
                    .sorted(Comparator.comparing(CountryCode::getName))
                    .collect(Collectors.toList());
            mav.addObject("country", sortedCountries);
            mav.setViewName("companies/edit");
        }
        return mav;
    }

    @GetMapping("/show_add_form")
    public ModelAndView showAddForm() {
        ModelAndView mav = new ModelAndView();
        Company company = new Company();
        mav.addObject("company", company);
        List<CountryCode> sortedCountries = Arrays.stream(CountryCode.values())
                .sorted(Comparator.comparing(CountryCode::getName))
                .collect(Collectors.toList());
        mav.addObject("country", sortedCountries);
        mav.setViewName("companies/add");
        return mav;
    }

    @PostMapping("/update")
    public String updateCompany(@ModelAttribute("company") Company company, BindingResult result, Model model) {
        addressRepository.save(company.getAddress());
        companyRespository.save(company);
        return "redirect:/companies";
    }

    @PostMapping("/save")
    public String saveCompany(@ModelAttribute("company") Company company, BindingResult result, Model model) {
        addressRepository.save(company.getAddress());
        companyRespository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/{id}")
    public String showListCompany(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @PathVariable("id") Long id) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.companyJobs(id, currentPage - 1, pageSize, "id", "asc");
        Company company = companyRespository.findById(id).get();

        model.addAttribute("company", company);
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "companies/listJob";
    }
}
