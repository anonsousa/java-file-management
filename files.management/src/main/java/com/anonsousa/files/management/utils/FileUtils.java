package com.anonsousa.files.management.utils;

import com.anonsousa.files.management.exceptions.compression.CompressionException;
import com.anonsousa.files.management.exceptions.compression.DecompressionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static byte[] compressData(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[4 * 1024];

            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        }
        catch (IOException exception) {
            logger.error("Error on compress data, size: " + data.length, exception);
            throw new CompressionException(exception);
        }
        finally {
            deflater.end();
        }
    }

    public static byte[] decompressData(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[4 * 1024];

            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        }
        catch (IOException | DataFormatException exception) {
            logger.error("Error on decompress data, size: " + data.length, exception);
            throw new DecompressionException(exception);
        }
        finally {
            inflater.end();
        }
    }
}
