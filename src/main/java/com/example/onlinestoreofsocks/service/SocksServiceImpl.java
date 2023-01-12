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
            for (int i = 0; i < socksList.size(); i++) {
                if (socksList.get(i).equals(socks));
                socksList.get(i).setQuantity(socksList.get(i).getQuantity() + socks.getQuantity());
            }
        } else {
            socksList.add(socks);
        }
        return socks;
    }
    @Override
    public Socks updateSocks(Socks socks, int quantity) {
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }
        for (int i = 0; i < socksList.size(); i++) {
            if (socksList.get(i).equals(socks) && socksList.get(i).getQuantity() >= quantity) {
                socksList.get(i).setQuantity(socksList.get(i).getQuantity() - quantity);
            } else {
                throw new SocksBadRequestException("Товара не достаточно на складе");
            }
        }
        return socks;
    }

    @Override
    public Socks removeSocks(Socks socks) {
        if (!socksList.contains(socks)) {
            throw new SocksBadRequestException("Товар не найден");
        }  socksList.remove(socks);
        return socks;
    }
}
