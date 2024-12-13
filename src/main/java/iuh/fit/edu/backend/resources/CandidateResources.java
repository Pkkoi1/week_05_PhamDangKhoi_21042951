package iuh.fit.edu.backend.resources;

import iuh.fit.edu.backend.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/vi")
public class CandidateResources {

    @Autowired
    private CandidateService candidateService;

}
