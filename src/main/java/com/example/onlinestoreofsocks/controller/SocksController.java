package com.example.onlinestoreofsocks.controller;

import com.example.onlinestoreofsocks.model.Socks;
import com.example.onlinestoreofsocks.model.Color;
import com.example.onlinestoreofsocks.model.Size;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.onlinestoreofsocks.service.SocksServiceImpl;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@Validated()
@RequestMapping("api/socks")
public class SocksController {
    private final SocksServiceImpl socksService;
    public SocksController(SocksServiceImpl socksService) {
        this.socksService = socksService;
    }

    @GetMapping
    @Operation(summary = "Список всех носков",
            description = "Список всех носков на складе")
    public List<Socks> getAllSocks() {
        return this.socksService.getAllSocks();
    }

    @GetMapping("/defect")
    @Operation(summary = "Список всех бракованных носков",
            description = "Список всех бракованных носков на складе")
    public List<Socks> getAllDefectiveSocks() {
        return this.socksService.getAllDefectiveSocks();
    }

    @GetMapping("/search/min")
    @Operation(summary = "Поиск носков на складе")
    public Socks searchSocksCottonMin(@RequestParam(name = "Цвет") Color color,
                                      @RequestParam(name = "Размер") Size size,
                                      @RequestParam(name = "Минимальное содержание Хлопка") int cottonMin,
                                      @RequestParam(name = "Максимальное содержание Хлопка") int cottonMax) {
        return this.socksService.searchSocks(color, size, cottonMin, cottonMax);
    }

    @PostMapping
    @Operation(summary = "Добавление новых носков на склад")
    public Socks addSocks(@RequestBody @Valid Socks socks) {
        return this.socksService.addSocks(socks);
    }

    @PutMapping("{quantity}")
    @Operation(summary = "Отпуск носков со склада")
    public Socks updateSocks(@RequestBody Socks socks, @PathVariable("quantity") int quantity) {
        return this.socksService.updateSocks(socks, quantity);
    }

    @DeleteMapping("{quantity}")
    @Operation(summary = "Списание бракованных носков")
    public Socks removeSocks(@RequestBody Socks socks, @PathVariable("quantity") int quantity) {
        return this.socksService.removeSocks(socks, quantity);
    }
    @GetMapping("/export")
    @Operation(summary = "Экспорт носков",
            description = "Скачивание данных по всем носкам которые есть на складе")
    public ResponseEntity<Object> downloadAllSocks() {
        try {
            Path path = socksService.createSocksPathReport();
            if (Files.size(path) == 0) {
                return ResponseEntity.notFound().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok().
                    contentType(MediaType.TEXT_PLAIN).
                    contentLength(Files.size(path)).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"AllSocks.txt\"").
                    body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
