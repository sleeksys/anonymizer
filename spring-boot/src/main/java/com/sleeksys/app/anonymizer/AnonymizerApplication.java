package com.sleeksys.app.anonymizer;

import com.sleeksys.app.anonymizer.service.Impl.CellService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableJpaRepositories
public class AnonymizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymizerApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CellService cellService){
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
        };
    }
    @Bean
    BCryptPasswordEncoder getBPCE(){
        return new BCryptPasswordEncoder();
    }
}
