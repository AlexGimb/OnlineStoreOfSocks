package com.example.onlinestoreofsocks.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${socks.of.data.file}")
    private String dataFileSocks;
    @Value("${defect.of.data.file}")
    private String dataFileDefectSocks;

    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "temp", suffix);
        } catch (IOException e) {
            throw new FileSocksException("Ошибка создания временного файла");
        }
    }

    @Override
    public boolean saveFileSocks(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileSocks), json);
            return true;
        } catch (IOException e) {
            throw new FileSocksException("Ошибка сохранения файла рецепта");
        }

    }

    @Override
    public boolean saveFileDefect(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileDefectSocks), json);
            return true;
        } catch (IOException e) {
            throw new FileSocksException("Ошибка сохранения файла ингредиента");
        }
    }

    @Override
    public String readFromFileSocks() {
        try {
            if (dataFileSocks.isEmpty() || dataFileSocks.isBlank() || dataFileSocks == null) {
                System.out.println("Файл пустой");
            } else {
                return Files.readString(Path.of(dataFilePath, dataFileSocks));
            }
        } catch (IOException e) {
            throw new FileSocksException("Ошибка чтения файла рецептов");
        }
        return readFromFileSocks();
    }

    @Override
    public String readFromFileDefect() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileDefectSocks));
        } catch (IOException e) {
            throw new FileSocksException("Ошибка чтения файла ингредиентов");
        }
    }

    @Override
    public boolean cleanFileSocks() {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, dataFileSocks));
            Files.createFile(Path.of(dataFilePath, dataFileSocks));
            return true;
        } catch (IOException e) {
            throw new FileSocksException("Ошибка очистки файла");
        }

    }

    @Override
    public boolean cleanFileDefect() {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, dataFileDefectSocks));
            Files.createFile(Path.of(dataFilePath, dataFileDefectSocks));
            return true;
        } catch (IOException e) {
            throw new FileSocksException("Ошибка очистки файла");
        }
    }

    @Override
    public File getDataFileSocks() {
        return new File(dataFilePath + "/" + dataFileSocks);
    }

    @Override
    public File getDataFileDefect() {
        return new File(dataFilePath + "/" + dataFileDefectSocks);
    }
}
