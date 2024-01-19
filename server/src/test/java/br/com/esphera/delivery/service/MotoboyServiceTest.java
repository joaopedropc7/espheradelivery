package br.com.esphera.delivery.service;

import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import br.com.esphera.delivery.repository.MotoboyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotoboyServiceTest {

    @Mock
    private MotoboyRepository motoboyRepository;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private MotoboyService motoboyService;
 
    @Captor
    private ArgumentCaptor<MotoboysModel> motoboysModelArgumentCaptor;

    @Captor
    private ArgumentCaptor<CompanyModel> companyModelArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Nested
    class createMotoboy {

        @Test
        @DisplayName("Should create motoboy with success")
        void shouldCreateMotoboyWithSuccess() {
            ///Arrange
            CompanyModel company = new CompanyModel(1, "nomeFantasia", "razaoSocial", "cpf", "cnpj", "nameContact", "numberCompany1", "NumberCompany2", LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now(), true, "emailCompany", true, new ArrayList<>(), new ArrayList<ProductModel>(), 0.0, true, new FileEntity(), new FileEntity(), "logradouro", "cep", "complemento", "bairro", "localidade", "UF", "numberHouse", "idLocalCompanyMaps");
            MotoboysModel motoboy = new MotoboysModel(1, "nameMotoboy", "number", "email", company, 0, false);
            doReturn(company).when(companyService).getCompanyById(integerArgumentCaptor.capture());
            doReturn(motoboy).when(motoboyRepository).save(motoboysModelArgumentCaptor.capture());
            var input = new MotoboyRecord("nameMotoboy", "number", "email");



            //Act
            var output = motoboyService.createMotoboy(input, company.getId());


            //Assert
            assertFalse(output.getLinks().isEmpty());

            var motoboyCaptured = motoboysModelArgumentCaptor.getValue();

            assertEquals(input.nameMotoboy(), motoboyCaptured.getNameMotoboy());
            assertEquals(input.number(), motoboyCaptured.getNumber());
            assertEquals(input.email(), motoboyCaptured.getEmail());
            assertEquals(company, motoboyCaptured.getCompanyModel());
            assertEquals(0, motoboyCaptured.getQuantityDelivered());
            assertEquals(false, motoboyCaptured.getInactive());

            verify(motoboyRepository, times(1)).save(any(MotoboysModel.class));

        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new RuntimeException()).when(motoboyRepository).save(any());
            var input = new MotoboyRecord(
                    "nameMotoboy",
                    "number",
                    "email@email.com"
            );
            var company = new CompanyModel(
                    1,
                    "nomeFantasia",
                    "razaoSocial",
                    "cpf",
                    "cnpj",
                    "nameContact",
                    "numberCompany1",
                    "NumberCompany2",
                    LocalDate.now(),
                    LocalDate.now(),
                    LocalDate.now(),
                    LocalDate.now(),
                    true,
                    "emailCompany",
                    true,
                    new ArrayList<>(),
                    new ArrayList<ProductModel>(),
                    0.0,
                    true,
                    new FileEntity(),
                    new FileEntity(),
                    "logradouro",
                    "cep",
                    "complemento",
                    "bairro",
                    "localidade",
                    "UF",
                    "numberHouse",
                    "idLocalCompanyMaps"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> motoboyService.createMotoboy(input, company.getId()));
        }

    }

    @Nested
    class getMotoboyById{

        @Test
        @DisplayName("Should get motoboy by id with success")
        void shouldGetMotoboyByIdWithSuccess(){
            //Arrange
            var motoboy = new MotoboysModel(
                    1,
                    "nameMotoboy",
                    "number",
                    "email",
                    new CompanyModel(),
                    0,
                    false
            );
            doReturn(Optional.of(motoboy)).when(motoboyRepository).findById(integerArgumentCaptor.capture());

            //Act
            var output = motoboyService.findMotoboyById(motoboy.getId());

            //Assert
            assertNotNull(output);
            assertEquals(motoboy.getId(), output.getId());
        }

    }
}