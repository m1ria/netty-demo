package netty.protocol;

public interface Serializer {
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
