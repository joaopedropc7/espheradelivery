package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.exceptions.MyFileNotFoundException;
import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.DTOS.responseDtos.UploadFileResponse;
import br.com.esphera.delivery.models.FileEntity;
import br.com.esphera.delivery.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

@Tag(name = "File", description = "File API")
@RestController
@RequestMapping("/api/file")
public class FIleController {

    private Logger logger = Logger.getLogger(FIleController.class.getName());

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/uploadFile/{idProduct}")
    @Operation(summary = "Upload image product", description = "Upload image product",
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
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file) {
        logger.info("Armazenando arquivo em disco...");
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/requestProductImage/{productId}")
    @Operation(summary = "Request image product passing productId", description = "Request image product passing productId",
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
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "productId") Integer productId, HttpServletRequest request) {
        logger.info("Lendo arquivo em disco...");

        FileEntity fileEntity = fileStorageService.loadFileAsResource(productId);
        Resource resource = new ByteArrayResource(fileEntity.getData());
        System.out.println(resource.getFilename());
        String contentType = fileEntity.getFileType();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
