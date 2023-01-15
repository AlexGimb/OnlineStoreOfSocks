package com.example.onlinestoreofsocks.service;

import com.example.onlinestoreofsocks.model.Color;
import com.example.onlinestoreofsocks.model.Size;
import com.example.onlinestoreofsocks.model.Socks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    public static List<Socks> socksList = new ArrayList<>();
    public static List<Socks> defectiveSocksList = new ArrayList<>();

    private final FileServiceImpl fileService;

    public SocksServiceImpl(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void init() {
        readFileSocks();
        readFileDefect();
    }

    @Override
    public List<Socks> getAllSocks() {
        return socksList;
    }
    @Override
    public List<Socks> getAllDefectiveSocks() {
        return defectiveSocksList;
    }

    @Override
    public Socks addSocks(Socks socks) {
        if (socksList.contains(socks)) {
            for (Socks value : socksList) {
                if (value.equals(socks)){
                value.setQuantity(value.getQuantity() + socks.getQuantity());
                break;
                }
            }
        } else {
            socksList.add(socks);
        }
        saveFileSocks();
        return socks;
    }

    @Override
    public Socks addDefectSocks(Socks socks, int quantity) {
        if (defectiveSocksList.contains(socks)) {
            for (Socks value : defectiveSocksList) {
                if (value.equals(socks)){
                    value.setQuantity(value.getQuantity() + quantity);
                    break;
                }
            }
        } else {
            socks.setQuantity(quantity);
            defectiveSocksList.add(socks);
        }
        saveFileDefect();
        return socks;
    }

    @Override
    public Socks searchSocks(Color color, Size size, int cottonMin, int cottonMax) {
        Socks searchSocks = null;
        boolean search = false;
        for (Socks socks : socksList) {
            if (socks.getColors().equals(color) && socks.getSize().equals(size) && socks.getCottonPart() >= cottonMin && socks.getCottonPart() <= cottonMax) {
                searchSocks = socks;
                search = true;
                break;
            }
        } if (!search) {
            throw new SocksInternalServerErrorException("Товар не найден");
        }

        return searchSocks;
    }

    @Override
    public Socks updateSocks(Socks socks, int quantity) {
        boolean search = false;
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }
        for (Socks value : socksList) {
            if (value.equals(socks) && value.getQuantity() >= quantity) {
                value.setQuantity(value.getQuantity() - quantity);
                search = true;
                break;
            }
        } if (!search) {
            throw new SocksBadRequestException("Товара не достаточно на складе");
        }
        saveFileSocks();
        return socks;
    }

    @Override
    public Socks removeSocks(Socks socks, int quantity) {
        boolean search = false;
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }
        for (Socks value : socksList) {
            if (value.equals(socks) && value.getQuantity() >= quantity) {
                value.setQuantity(value.getQuantity() - quantity);
                addDefectSocks(socks, quantity);
                search = true;
                break;
            }
        }
        if (!search) {
            throw new SocksBadRequestException("Товара не достаточно на складе");
        }
        saveFileSocks();
        return socks;
    }

    private void saveFileSocks() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksList);
            fileService.saveFileSocks(json);
        } catch (JsonProcessingException e) {
            throw new FileSocksException("Ошибка сохранения файла");
        }
    }

    public void readFileSocks() {
        try {
            String json = fileService.readFromFileSocks();
            socksList = new ObjectMapper().readValue(json, new TypeReference<List<Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileSocksException("Ошибка чтения файла");
        }
    }

    public Path createSocksPathReport() throws IOException {
        Path path = fileService.createTempFile("AllSocks");
        for (Socks sock : socksList) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append("Цвет: " + sock.getColors() + "\n" +
                        "Размер: " + sock.getSize() + "\n" +
                        "Содержание хлопка :" + sock.getCottonPart() + "\n" +
                        "Количество пар: " + sock.getQuantity() + "\n");
                writer.append("\n");
                writer.append("\n");
                writer.append("-----------------------------------------------------------------------");
                writer.append("\n");
                writer.append("\n");
            }
        }
        return path;
    }
    private void saveFileDefect() {
        try {
            String json = new ObjectMapper().writeValueAsString(defectiveSocksList);
            fileService.saveFileDefect(json);
        } catch (JsonProcessingException e) {
            throw new FileSocksException("Ошибка сохранения файла");
        }
    }

    public void readFileDefect() {
        try {
            String json = fileService.readFromFileDefect();
            defectiveSocksList = new ObjectMapper().readValue(json, new TypeReference<List<Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileSocksException("Ошибка чтения файла");
        }
    }
}
