package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.*;
import iuh.fit.edu.backend.repositories.CandidateRepository;
import iuh.fit.edu.backend.repositories.CompanyRespository;
import iuh.fit.edu.backend.repositories.JobRepository;
import iuh.fit.edu.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyRespository CompanyRespository;

    @Autowired
    private SkillRepository skillRepository;

    public Page<Candidate> findAll(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Candidate> list;
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, candidates.size());
            list = candidates.subList(startItem, toIndex);
        }
        Page<Candidate> candidatePage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), candidates.size());
        return candidatePage;
    }

    public Page<Candidate> findBySkill(String skill, int page, int size, String sortBy, String sortDirection) {
        String key = skill.toLowerCase();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Candidate> candidatePage = new PageImpl<>(new ArrayList<>());
        if (key.isEmpty()) {
            return candidatePage;
        } else {
            candidatePage = candidateRepository.findBySkill(key, pageable);
            if (candidatePage.isEmpty()) {
                candidatePage = candidateRepository.findByKey(key, pageable);
            }
        }

        return candidatePage;
    }

    public Page<Candidate> findMatchingCandidates(Long jobId, int page, int size, String sortBy, String sortDirection) {
        Job job = jobService.findById(jobId);
        List<Candidate> candidates = job.getJobSkills().stream()
                .map(jobSkill -> candidateRepository.findMatchingCandidates(jobSkill.getSkillLevel(), jobSkill.getSkill().getSkillName()))
                .flatMap(List::stream)
                .toList();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), candidates.size());
        return new PageImpl<>(candidates.subList(start, end), pageable, candidates.size());
    }

    public Page<Candidate> findMatchingCandidatesByKey(Long jobId, String key, int page, int size, String sortBy, String sortDirection) {
        Job job = jobService.findById(jobId);
        List<Candidate> candidates = job.getJobSkills().stream()
                .map(jobSkill -> candidateRepository.findMatchingCandidatesByKey(jobSkill.getSkillLevel(), jobSkill.getSkill().getSkillName(), key))
                .flatMap(List::stream)
                .toList();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), candidates.size());
        return new PageImpl<>(candidates.subList(start, end), pageable, candidates.size());
    }

    public List<String> suggestSkills(Long candidateID) {
        Optional<Candidate> candidate = candidateRepository.findById(candidateID);
        Set<String> skills = new HashSet<>();

        if (candidate.isPresent()) {
            List<CandidateSkill> candidateSkills = candidate.get().getCandidateSkills();
            for (CandidateSkill candidateSkill : candidateSkills) {
                List<Job> foundJobs = jobRepository.findBySkill(candidateSkill.getSkill().getSkillName());
                for (Job job : foundJobs) {
                    for (JobSkill jobSkill : job.getJobSkills()) {
                        skills.add(jobSkill.getSkill().getSkillName());
                    }
                }
            }
        }

        return new ArrayList<>(skills);
    }

    public List<Job> findJobBySuggestSkill(String skillName) {
        return jobRepository.findBySkill(skillName);
    }
}
