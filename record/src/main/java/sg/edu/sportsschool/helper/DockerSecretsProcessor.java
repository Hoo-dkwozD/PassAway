package sg.edu.sportsschool.Helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class DockerSecretsProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) throws RuntimeException {
        Resource resource = new FileSystemResource("/usr/src/app/.env");

        if (resource.exists()) {
            try {
                String password = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

                Properties properties = new Properties();
                properties.put("sql-password", password);
                properties.put("sql-host", "host.docker.internal");
                environment.getPropertySources().addLast(new PropertiesPropertySource("DBProperties", properties));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Properties properties = new Properties();
            properties.put("sql-password", "root");
            properties.put("sql-host", "localhost");
            environment.getPropertySources().addLast(new PropertiesPropertySource("DBProperties", properties));
        }

        // String privateKeyPath = getClass().getClassLoader().getResource("private.key").getPath();
        // Resource privateKey = new FileSystemResource(privateKeyPath);
        // String publicKeyPath = getClass().getClassLoader().getResource("public.key").getPath();
        // Resource publicKey = new FileSystemResource(publicKeyPath);

        // if (!privateKey.exists() || !publicKey.exists()) {
        //     throw new RuntimeException("No signing keys found for authentication. ");
        // }
    }
}
