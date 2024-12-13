package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.enums.skillLevel;
import iuh.fit.edu.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;


public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT c FROM Candidate c inner join c.candidateSkills sk WHERE " +
            "UPPER(sk.skill.skillName) LIKE UPPER(?1) " +
            "ORDER BY sk.skillLevel")
    Page<Candidate> findBySkill(String skillName, Pageable pageable);

    @Query("SELECT c FROM Candidate c  WHERE " +
            "UPPER(c.fullName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.phone) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.email) LIKE CONCAT('%', UPPER(?1), '%') ")
    Page<Candidate> findByKey(String ket, Pageable pageable);

    @Query("""
            select c from Candidate c inner join c.candidateSkills candidateSkills
            where candidateSkills.skillLevel <= ?1 and candidateSkills.skill.skillName = ?2
            """)
    List<Candidate> findMatchingCandidates(skillLevel skillLevel, String skillName);

    @Query("""
            select c from Candidate c inner join c.candidateSkills candidateSkills
            where candidateSkills.skillLevel <= ?1 and candidateSkills.skill.skillName = ?2
            and (UPPER(c.fullName) LIKE CONCAT('%', UPPER(?3), '%') 
            OR UPPER(c.phone) LIKE CONCAT('%', UPPER(?3), '%') 
            OR UPPER(c.email) LIKE CONCAT('%', UPPER(?3), '%'))
            """)
    List<Candidate> findMatchingCandidatesByKey(skillLevel skillLevel, String skillName, String key);
}
