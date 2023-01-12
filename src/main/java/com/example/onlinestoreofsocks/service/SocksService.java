package com.example.onlinestoreofsocks.service;

import com.example.onlinestoreofsocks.model.Socks;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    Socks addSocks(Socks socks);

    Socks updateSocks(Socks socks, int quantity);

    Socks removeSocks(Socks socks);
}
