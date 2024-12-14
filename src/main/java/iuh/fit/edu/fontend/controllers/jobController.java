package iuh.fit.edu.fontend.controllers;


import iuh.fit.edu.backend.enums.skillLevel;
import iuh.fit.edu.backend.enums.skillType;
import iuh.fit.edu.backend.ids.JobSkillId;
import iuh.fit.edu.backend.models.*;
import iuh.fit.edu.backend.repositories.*;
import iuh.fit.edu.backend.services.CandidateService;
import iuh.fit.edu.backend.services.CompanyService;
import iuh.fit.edu.backend.services.EmailService;
import iuh.fit.edu.backend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jobs")
public class jobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRespository companyRespository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @GetMapping(value = {"", "/"})
    public String showJobListPaging(Model model, @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    Optional<String> search) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        Page<Job> jobPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            jobPage = jobService.findByKey(search.get(), currentPage - 1, pageSize, "company", "asc");
            model.addAttribute("search", search.get());
        } else {
            jobPage = jobService.findAll(currentPage - 1, pageSize, "company", "asc");
        }
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "jobs/list";
    }


    @GetMapping("/show_add_form/{id}")
    public ModelAndView showAddForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("jobs/add");
        Job job = new Job();
        mav.addObject("company", companyRespository.findById(id).get());
        mav.addObject("skills", skillRepository.findAll());
        mav.addObject("skillLevels", skillLevel.values());
        mav.addObject("job", job);
        return mav;
    }

    @PostMapping("/save/{id}")
    public String saveJob(@PathVariable("id") Long companyId,
                          @ModelAttribute("job") Job job,
                          @RequestParam("skills") List<Long> skills,
                          @RequestParam("skillLevels") List<skillLevel> skillLevels,
                          @RequestParam("moreInfos") List<String> moreInfos,
                          BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        try {
            job.getJobSkills().clear();
            Optional<Company> companyOptional = companyRespository.findById(companyId);
            Job job1 = new Job();
            if (companyOptional.isPresent()) {
                job1.setJobSkills(job.getJobSkills());
                job1.setCompany(companyOptional.get());
                job1.setJobName(job.getJobName());
                job1.setJobDesc(job.getJobDesc());
                jobRepository.save(job1);
                for (int i = 0; i < skills.size(); i++) {
                    if (skills.get(i) != null) {
                        JobSkill jobSkill = new JobSkill();
                        Skill skill = skillRepository.findById(skills.get(i)).orElse(null);
                        if (skill != null) {
                            saveAndUpdate(i, skill, jobSkill, job1, skillLevels, moreInfos);
                        }
                    }
                }
            }
            jobSkillRepository.saveAll(job1.getJobSkills());
            redirectAttributes.addFlashAttribute("message", "Job saved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save job: " + e.getMessage());
        }
        return "redirect:/companies/" + companyId;
    }

    @GetMapping("/show_candidate_matching/{id}/{jobID}")
    public String showCandidateMatching(Model model, @PathVariable("id") Long id,
                                        @PathVariable("jobID") Long jobID,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        Optional<String> search) {

        Company company = companyRespository.findById(id).get();
        model.addAttribute("company", company);
        model.addAttribute("job", jobRepository.findById(jobID).get());

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Candidate> candidatePage;
        if (search.isPresent()) {
            candidatePage = candidateService.findMatchingCandidatesByKey(jobID, search.get(), currentPage - 1, pageSize, "id", "asc");
        } else {
            candidatePage = candidateService.findMatchingCandidates(jobID, currentPage - 1, pageSize, "id", "asc");
        }

        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "jobs/CandidateMatching";
    }

    @PostMapping("/{id}/{jobId}/{candidateId}/send-email")
    public String sendEmail(@PathVariable("jobId") Long jobId, @PathVariable("candidateId") Long candidateId,
                            @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Job job = jobService.findById(jobId);
            Candidate candidate = candidateRepository.findById(candidateId).get();
            String emailResult = emailService.sendEmail(candidate, job);
            if (emailResult.equals("Email sent successfully.")) {
                redirectAttributes.addFlashAttribute("message", emailResult);
            } else {
                redirectAttributes.addFlashAttribute("error", emailResult);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send email: " + e.getMessage());
        }
        return "redirect:/jobs/show_candidate_matching/" + id + "/" + jobId;
    }

    @GetMapping("/{id}")
    public String showJobDetail(@PathVariable("id") Long id, Model model) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            String comId = job.get().getCompany().getId().toString();
            Page<Job> jobPage = jobService.companyJobs(Long.parseLong(comId), 0, 5, "id", "asc");

            List<Job> filteredJobs = jobPage.getContent().stream()
                    .filter(j -> !j.getId().equals(id))
                    .collect(Collectors.toList());

            model.addAttribute("job", job.get());
            model.addAttribute("otherJobs", filteredJobs);
            return "jobs/details";
        }
        return "redirect:/jobs";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("jobs/edit");
        Job job = jobRepository.findById(id).get();
        mav.addObject("job", job);
        mav.addObject("skills", skillRepository.findAll());
        mav.addObject("skillLevels", skillLevel.values());
        return mav;
    }

    @PostMapping("/update")
    public String updateJob(@ModelAttribute("job") Job job,
                            @RequestParam("skills") List<Long> skills,
                            @RequestParam("skillLevels") List<skillLevel> skillLevels,
                            @RequestParam("moreInfos") List<String> moreInfos,
                            @RequestParam("compId") Long compId,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<JobSkill> jobSkills = jobRepository.findById(job.getId()).get().getJobSkills();
            List<JobSkill> delete = new ArrayList<>(jobSkills);
            job.setCompany(companyRespository.findById(compId).get());
            jobRepository.save(job);
            for (int i = 0; i < skills.size(); i++) {
                if (skills.get(i) != null) {
                    JobSkill jobSkill = new JobSkill();
                    Skill skill = skillRepository.findById(skills.get(i)).orElse(null);
                    if (skill != null) {
                        saveAndUpdate(i, skill, jobSkill, job, skillLevels, moreInfos);
                    }
                }
            }
            for (JobSkill jobSkill : job.getJobSkills()) {
                delete.removeIf(skill -> skill.getId().getSkillId().equals(jobSkill.getId().getSkillId()));
            }
            jobSkillRepository.deleteAll(delete);
            jobSkillRepository.saveAll(job.getJobSkills());

            redirectAttributes.addFlashAttribute("message", "Job updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update job: " + e.getMessage());
        }
        return "redirect:/companies/" + compId;
    }

    public void saveAndUpdate(int i, Skill skill, JobSkill jobSkill, Job job1, List<skillLevel> skillLevels, List<String> moreInfos) {
        jobSkill.setSkill(skill);
        jobSkill.setSkillLevel(skillLevels.get(i));
        jobSkill.setMoreInfos(moreInfos.get(i));
        jobSkill.setJob(job1);
        JobSkillId jobSkillId = new JobSkillId();
        jobSkillId.setJobId(job1.getId());
        jobSkillId.setSkillId(skill.getId());
        jobSkill.setId(jobSkillId);
        job1.getJobSkills().add(jobSkill);
    }
}
