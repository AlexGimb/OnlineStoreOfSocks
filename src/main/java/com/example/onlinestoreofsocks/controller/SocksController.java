package com.example.onlinestoreofsocks.controller;

import com.example.onlinestoreofsocks.model.Socks;
import com.example.onlinestoreofsocks.service.Color;
import com.example.onlinestoreofsocks.service.Size;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.onlinestoreofsocks.service.SocksServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated()
@RequestMapping("/socks")
public class SocksController {
    private final SocksServiceImpl socksService;
    public SocksController(SocksServiceImpl socksService) {
        this.socksService = socksService;
    }

    @GetMapping
    public List<Socks> getAllRecipeBooks() {
        return this.socksService.getAllSocks();
    }

    @GetMapping("/search/min/{min}")
    @Operation(summary = "Поиск носков с долей хлопка менее ",
            description = "Поиск носков с долей хлопка больше чем cottonMin")
    public Socks searchSocksCottonMin(@RequestParam Color color, @RequestParam Size size, @PathVariable("min") int cottonMin) {
        return this.socksService.searchSocksCottonMin(color, size, cottonMin);
    }

    @GetMapping("/search/max/{max}")
    @Operation(summary = "Поиск носков с долей хлопка более ",
            description = "Поиск носков с долей хлопка меньше чем cottonMax")
    public Socks searchSocksCottonMax(@RequestParam Color color, @RequestParam Size size, @PathVariable("max") int cottonMax) {
        return this.socksService.searchSocksCottonMax(color, size, cottonMax);
    }

    @PostMapping
    public Socks addSocks(@RequestBody @Valid Socks socks) {
        return this.socksService.addSocks(socks);
    }

    @PutMapping("{quantity}")
    public Socks updateSocks(@RequestBody Socks socks, @PathVariable("quantity") int quantity) {
        return this.socksService.updateSocks(socks, quantity);
    }

    @DeleteMapping("{quantity}")
    public Socks removeSocks(@RequestBody Socks socks, @PathVariable("quantity") int quantity) {
        return this.socksService.removeSocks(socks, quantity);
    }


}
