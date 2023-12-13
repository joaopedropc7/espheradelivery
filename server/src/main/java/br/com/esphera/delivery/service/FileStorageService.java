package br.com.esphera.delivery.service;

import br.com.esphera.delivery.config.FileStorageConfig;
import br.com.esphera.delivery.exceptions.FileStorageException;
import br.com.esphera.delivery.exceptions.MyFileNotFoundException;
import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.Enums.EntityRequestImage;
import br.com.esphera.delivery.models.FileEntity;
import br.com.esphera.delivery.repository.FileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TokenService tokenService;

    public String storeFile(MultipartFile file, Integer id, EntityRequestImage entityRequestImage, Integer companyId){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + fileName);
            }
            FileEntity fileEntity = new FileEntity();
            fileEntity.setNameFile(fileName);
            fileEntity.setFileType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileRepository.save(fileEntity);

            if (entityRequestImage == EntityRequestImage.PRODUCT) {
                productService.setImageProduct(id, fileEntity, companyId);
            } else if (entityRequestImage == EntityRequestImage.COMPANYBANNER) {
                companyService.setBannerImageCompany(fileEntity ,id);
            } else if (entityRequestImage == EntityRequestImage.COMPANYLOGO) {
                companyService.setLogoImageCompany(fileEntity ,id);
            }

            return fileName;
        }catch (Exception e){
            throw new FileStorageException("Não foi possivel armazenar o arquivo " + fileName + ". Por favor tente novamente!", e);
        }
    }


    public Resource loadProductImage(Integer productId) {
        try {
            FileEntity fileEntity = productService.getImageProduct(productId);
            return new ByteArrayResource(fileEntity.getData());
        } catch (Exception e) {
            throw new MyFileNotFoundException("Arquivo não encontrado ");
        }
    }

    public Resource loadBannerCompany(Integer companyId) {
        try {
            FileEntity fileEntity = companyService.getBannerImageCompany(companyId);
            return new ByteArrayResource(fileEntity.getData());
        } catch (Exception e) {
            throw new MyFileNotFoundException("Arquivo não encontrado ");
        }
    }

    public FileEntity loadCompanyLogo(Integer idCompany) {
        try {
            FileEntity fileEntity = companyService.getLogoImageCompany(idCompany);
            return fileEntity;
        } catch (Exception e) {
            throw new MyFileNotFoundException("Arquivo não encontrado ");
        }
    }

}
