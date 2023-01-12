package com.example.onlinestoreofsocks.service;

import com.example.onlinestoreofsocks.model.Socks;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    public static List<Socks> socksList = new ArrayList<>();

    @Override
    public List<Socks> getAllSocks() {
        return socksList;
    }

    @Override
    public Socks addSocks(Socks socks) {
        if (socksList.contains(socks)) {
            for (Socks value : socksList) {
                if (value.equals(socks)){
                value.setQuantity(value.getQuantity() + socks.getQuantity());
                }
            }
        } else {
            socksList.add(socks);
        }
        return socks;
    }

    @Override
    public int searchSocksCottonMin(Color color, Size size, int cottonMin) {
        int searchSocks = 0;
        for (Socks socks : socksList) {
            if (socks.getColors().equals(color) && socks.getSize().equals(size) && socks.getCottonPart() >= cottonMin) {
                searchSocks = socks.getQuantity();
            } else {
                throw new SocksInternalServerErrorException("Товар не найден");
            }
        }
        return searchSocks;
    }

    @Override
    public int searchSocksCottonMax(Color color, Size size, int cottonMax) {
        int searchSocks = 0;
        for (Socks socks : socksList) {
            if (socks.getColors().equals(color) && socks.getSize().equals(size) && socks.getCottonPart() <= cottonMax) {
                searchSocks = socks.getQuantity();
            } else {
                throw new SocksInternalServerErrorException("Товар не найден");
            }
        }
        return searchSocks;
    }

    @Override
    public Socks updateSocks(Socks socks, int quantity) {
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }
        for (Socks value : socksList) {
            if (value.equals(socks) && value.getQuantity() >= quantity) {
                value.setQuantity(value.getQuantity() - quantity);
            } else {
                throw new SocksBadRequestException("Товара не достаточно на складе");
            }
        }
        return socks;
    }

    @Override
    public Socks removeSocks(Socks socks, int quantity) {
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }
        for (Socks value : socksList) {
            if (value.equals(socks) && value.getQuantity() >= quantity) {
                value.setQuantity(value.getQuantity() - quantity);
            } else {
                throw new SocksBadRequestException("Товара не достаточно на складе");
            }
        }
        return socks;
    }
}
