package com.example.onlinestoreofsocks.controller;

import com.example.onlinestoreofsocks.service.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@RestController
@RequestMapping("/files")
@Tag(name = "Импорт, экспорт файлов", description = "Импорт и экспорт файлов")
public class FileController {
    private final FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/export/socks")
    @Operation(summary = "Экспорт носков",
            description = "Экспорт всех носков на складе")
    public ResponseEntity<InputStreamResource> downloadFileSocks() throws FileNotFoundException {
        File file = fileService.getDataFileSocks();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentType(MediaType.APPLICATION_JSON).
                    contentLength(file.length()).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks.json\"").
                    body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/export/defect")
    @Operation(summary = "Экспорт бракованных носков",
            description = "Экспорт всех бракованных носков на складе")
    public ResponseEntity<InputStreamResource> downloadFileDefect() throws FileNotFoundException {
        File file = fileService.getDataFileDefect();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentType(MediaType.APPLICATION_JSON).
                    contentLength(file.length()).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Defect.json\"").
                    body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/socks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт носков")
    public ResponseEntity<Void> upLoadDataFileSocks(@RequestParam MultipartFile file) {
        fileService.cleanFileSocks();
        File dataFileSocks = fileService.getDataFileSocks();

        try (FileOutputStream fos = new FileOutputStream(dataFileSocks)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/import/defect", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт бракованных носков")
    public ResponseEntity<Void> upLoadDataFileDefect(@RequestParam MultipartFile file) {
        fileService.cleanFileDefect();
        File dataFileDefect = fileService.getDataFileDefect();

        try (FileOutputStream fos = new FileOutputStream(dataFileDefect)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
