package ru.yandex.clickhouse.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.yandex.clickhouse.response.ClickHouseResponse;
import ru.yandex.clickhouse.response.ClickHouseResponseGsonDeserializer;

public class JsonStreamUtilsLegacy {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ClickHouseResponse.class,
            new ClickHouseResponseGsonDeserializer()).create();

    public static <T> T readObject(InputStream input, Class<T> clazz) throws IOException {
        return readObject(input, StandardCharsets.UTF_8, clazz);
    }

    public static <T> T readObject(InputStream input, Charset charset, Class<T> clazz) throws IOException {
        return readObject(new InputStreamReader(input, charset), clazz);
    }

    public static <T> T readObject(Reader reader, Class<T> clazz) throws IOException {
        return gson.fromJson(reader, clazz);
    }

    public static <T> T readObject(String json, Class<T> clazz) throws IOException {
        return gson.fromJson(json, clazz);
    }

    public static <T> void writeObject(OutputStream output, T object) throws IOException {
        writeObject(output, StandardCharsets.UTF_8, object);
    }

    public static <T> void writeObject(OutputStream output, Charset charset, T object) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output, charset);
        writeObject(writer, object);
        writer.flush();
    }

    public static <T> void writeObject(Writer writer, T object) throws IOException {
        gson.toJson(object, object == null ? Object.class : object.getClass(), gson.newJsonWriter(writer));
    }

    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    private JsonStreamUtilsLegacy() {
    }
}
