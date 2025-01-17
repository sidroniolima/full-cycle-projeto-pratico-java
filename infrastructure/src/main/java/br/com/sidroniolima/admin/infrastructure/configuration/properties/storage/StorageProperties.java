package br.com.sidroniolima.admin.infrastructure.configuration.properties.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class StorageProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(StorageProperties.class);

    private String filenamePattern;
    private String locationPattern;

    public StorageProperties() {
    }

    public String getFileNamePattern() {
        return filenamePattern;
    }

    public StorageProperties setFileNamePattern(String filenamePattern) {
        this.filenamePattern = filenamePattern;
        return this;
    }

    public String getLocationPattern() {
        return locationPattern;
    }

    public StorageProperties setLocationPattern(String locationPattern) {
        this.locationPattern = locationPattern;
        return this;
    }

    @Override
    public void afterPropertiesSet() {
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "StorageProperties{" +
                "filenamePattern='" + filenamePattern + '\'' +
                ", locationPattern='" + locationPattern + '\'' +
                '}';
    }
}
