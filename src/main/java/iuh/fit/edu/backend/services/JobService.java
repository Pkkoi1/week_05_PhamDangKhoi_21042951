package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.CandidateSkill;
import iuh.fit.edu.backend.models.Job;
import iuh.fit.edu.backend.models.Skill;
import iuh.fit.edu.backend.repositories.CandidateRepository;
import iuh.fit.edu.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public Page<Job> findAll(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobRepository.findAll(pageable);
    }

    public Page<Job> findByKey(String key, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobRepository.listJob(key, pageable);
    }

    public Job findById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.orElse(null);
    }

    public Page<Job> companyJobs(Long key, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobRepository.findCompanyJobs(key, pageable);
    }


    public List<Job> findJobsForCandidate(Long candidateId) {
        Optional<Candidate> candidate = candidateRepository.findById(candidateId);
        return candidate.get()
                .getCandidateSkills().stream()
                .map(
                        candidateSkill ->
                                jobRepository.findJobsBySkillLevelAndSkillName(
                                        candidateSkill.getSkillLevel(), candidateSkill.getSkill().getSkillName()
                                )
                )
                .flatMap(List::stream)
                .toList();

    }

}
