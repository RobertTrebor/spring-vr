package de.lengsfeld.apps.vr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@Configuration
public class FileConfigurationProperties {

    protected final Log log = LogFactory.getLog(getClass());

    private String uploadDir;

    //@Bean
    //@ConfigurationProperties("file")
    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}

