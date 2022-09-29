package netty.protocol;


import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    <T> T deserializer(Class<T> clazz, byte[] bytes);

    <T> byte[] serializer(T object);

    enum Algorithm implements Serializer {
        Java{
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败", e);
                }
            }

            @Override
            public <T> byte[] serializer(T object) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败", e);
                }
            }
        },
        Json{
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serializer(T object) {
                String json = new Gson().toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }
    }

}
