package com.alphacode.alphacodeapi.service;

import java.io.File;

public interface S3Service {
    String uploadFile(File file);

    String uploadBytes(byte[] data, String key, String contentType);
}
