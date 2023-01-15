package com.example.onlinestoreofsocks.service;

import com.example.onlinestoreofsocks.model.Color;
import com.example.onlinestoreofsocks.model.Size;
import com.example.onlinestoreofsocks.model.Socks;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    List<Socks> getAllDefectiveSocks();

    Socks addSocks(Socks socks);

    Socks addDefectSocks(Socks socks, int quantity);

    Socks searchSocks(Color color, Size size, int cottonMin, int cottonMax);

    Socks updateSocks(Socks socks, int quantity);

    Socks removeSocks(Socks socks, int quantity);
}
