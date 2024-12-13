package iuh.fit.edu;

import iuh.fit.edu.backend.repositories.AddressRepository;
import iuh.fit.edu.backend.repositories.CandidateRepository;
import iuh.fit.edu.backend.repositories.CompanyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week05Application {

    public static void main(String[] args) {
        SpringApplication.run(Week05Application.class, args);
    }

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CompanyRespository companyRespository;

//    @Bean
//    CommandLineRunner initData() {
//        return args -> {
//            Random rnd = new Random();
//            for (int i = 1; i < 1000; i++) {
//                Address add = new Address(rnd.nextInt(1, 1000) + "", "Quang Trung", "HCM",
//                        rnd.nextInt(70000, 80000) + "", CountryCode.VN);
//                addressRepository.save(add);
//                Candidate can = new Candidate("Name #" + i,
//                        LocalDate.of(1998, rnd.nextInt(1, 13), rnd.nextInt(1, 29)),
//                        add,
//                        rnd.nextLong(1111111111L, 9999999999L) + "",
//                        "email_" + i + "@gmail.com");
//                candidateRepository.save(can);
//                System.out.println("Added: " + can);
//            }
//        };
//    }

}
