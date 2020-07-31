package com.sleeksys.app.anonymizer;

import com.sleeksys.app.anonymizer.repository.CellRepository;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import com.sleeksys.app.anonymizer.security.entities.AppRole;
import com.sleeksys.app.anonymizer.security.services.AccountService;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;


@SpringBootApplication
@EnableJpaRepositories
@AllArgsConstructor
public class AnonymizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymizerApplication.class, args);
    }

    private LabelRepository labelRepository;
    private CellRepository cellRepository;
    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
/*            String excelFilePath = "C:\\projets spring\\anonymizer\\spring-boot\\src\\main\\java\\com\\sleeksys\\app\\anonymizer\\TestData.xlsx";
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            System.out.println("Hallo");
            //List<Cell> cells = cellService.extractCellsFromLabel(inputStream,2, 1L);

            List<Label> labels = cellService.extractLabelsFromExcelSheet(inputStream, 1L);

            for (Label label:labels) {
                System.out.println("ID:"+" "+label.getCellId()+" "+"value" + " " + label.getText());
                for (Cell cell:label.getCells()) {
                    System.out.println("Value of Cell:" + " " + cell.getText());
                }
            }*/
            // Save les roles
            accountService.saveAppRole(new AppRole(null, "USER"));
            accountService.saveAppRole(new AppRole(null, "ADMIN"));

            Stream.of("user1", "user2", "user3", "admin").forEach(u ->{
                accountService.saveUser(u, "1234", "1234");
            });
            accountService.addRoleToUser("admin", "ADMIN");

           // labelRepository.deleteAll();
         //   cellRepository.deleteAll();
        };
    }
    @Bean
    BCryptPasswordEncoder getBPCE(){
        return new BCryptPasswordEncoder();
    }
}
