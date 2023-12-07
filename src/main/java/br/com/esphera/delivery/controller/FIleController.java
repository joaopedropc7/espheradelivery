package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.exceptions.MyFileNotFoundException;
import br.com.esphera.delivery.exceptions.UnsupportedMediaTypeException;
import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.DTOS.responseDtos.UploadFileResponse;
import br.com.esphera.delivery.models.Enums.EntityRequestImage;
import br.com.esphera.delivery.models.FileEntity;
import br.com.esphera.delivery.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "Files", description = "Files API")
@RestController
@RequestMapping("/api/file")
public class FIleController {

    private Logger logger = Logger.getLogger(FIleController.class.getName());

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/uploadFile/{EntityRequestImage}/{idEntity}")
    @Operation(summary = "Upload image entity", description = "Upload image entity",
            tags = {"Files"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = FileEntity.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file, @PathVariable(value = "EntityRequestImage") EntityRequestImage entityRequestImage, @PathVariable(value = "idEntity") Integer idEntity) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") && !fileExtension.equals("png")) {
            throw new UnsupportedMediaTypeException("Apenas arquivos JPEG, PNG ou JPG são suportados.");
        }
        String fileName = fileStorageService.storeFile(file, idEntity, entityRequestImage);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/requestProductImage/" + entityRequestImage + "/" + idEntity)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/requestProductImage/{EntityRequestImage}/{entityId}")
    @Operation(summary = "Request image passing entityId", description = "Request image passing entityId",
            tags = {"Files"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = FileEntity.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "EntityRequestImage")EntityRequestImage entityRequestImage ,@PathVariable(value = "entityId") Integer entityId) {
        FileEntity fileEntity = fileStorageService.loadFileAsResource(entityId, entityRequestImage);
        Resource resource = new ByteArrayResource(fileEntity.getData());
        System.out.println(resource.getFilename());
        String contentType = fileEntity.getFileType();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
