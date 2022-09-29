package netty.config;

import netty.protocol.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @className: Config
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/29 4:40
 * @version: 1.0
 */
public abstract class Config {
    static Properties properties;
    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort() {
        String value = properties.getProperty("Server.port");
        if (value == null) {
            return 8080;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm() {
        String value = properties.getProperty("serializer.algorithm");
        if (value == null) {
            return Serializer.Algorithm.Java;
        } else {
            return Serializer.Algorithm.valueOf(value);
        }
    }
}
