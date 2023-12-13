package br.com.esphera.delivery.components;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class CompanyComponent {

    @Autowired
    private CompanyRepository companyRepository;

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void paymentCompanyVerify(){
        List<CompanyModel> companyModels = companyRepository.findAll();
        System.out.println("Verificando pagamentos..." + " Este metodo esta rodando as" + LocalDateTime.now());
        for (CompanyModel companyModel : companyModels) {
            LocalDate dateLastPayment = companyModel.getDateLastPayment();
            if (dateLastPayment == null || dateLastPayment.plusDays(31).isBefore(LocalDate.now())){
                companyModel.setSignatureActive(false);
            }
            companyRepository.save(companyModel);
        }
    }

}
