package if4031.client.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfiguration implements ClientConfiguration {
    private final Properties prop;

    public PropertyConfiguration(String propertiesFilename) throws IOException {
        prop = new Properties();
        InputStream inputStream = getClass().getResourceAsStream(propertiesFilename);

        if (inputStream != null) {
            prop.load(inputStream);

        } else {
            throw new FileNotFoundException("property file '" + propertiesFilename + "' not found in the classpath");
        }
    }

    @Override
    public String getString(String propertyName) {
        return prop.getProperty(propertyName);
    }

    @Override
    public int getInt(String propertyName) {
        return Integer.parseInt(prop.getProperty(propertyName));
    }
}
