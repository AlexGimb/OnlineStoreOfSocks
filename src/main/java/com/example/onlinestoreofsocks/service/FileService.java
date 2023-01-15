package com.example.onlinestoreofsocks.service;

import java.io.File;
import java.nio.file.Path;

public interface FileService {
    Path createTempFile(String suffix);

    boolean saveFileSocks(String json);

    boolean saveFileDefect(String json);

    String readFromFileSocks();

    String readFromFileDefect();

    boolean cleanFileSocks();

    boolean cleanFileDefect();

    File getDataFileSocks();

    File getDataFileDefect();
}
