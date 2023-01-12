package com.example.onlinestoreofsocks.controller;

import com.example.onlinestoreofsocks.model.Socks;
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

    @PostMapping
    public Socks addSocks(@RequestBody @Valid Socks socks) {
        return this.socksService.addSocks(socks);
    }

    @PutMapping("{quantity}")
    public Socks updateSocks(@RequestBody Socks socks, @PathVariable("quantity") int quantity) {
        return this.socksService.updateSocks(socks, quantity);
    }
    @DeleteMapping
    public Socks removeSocks(@RequestBody Socks socks) {
        return this.socksService.removeSocks(socks);
    }

}
