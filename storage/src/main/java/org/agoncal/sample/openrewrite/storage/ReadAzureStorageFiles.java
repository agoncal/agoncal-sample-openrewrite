package org.agoncal.sample.openrewrite.storage;

import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareFileClientBuilder;

import java.io.File;
import java.io.IOException;

public class ReadAzureStorageFiles {

    public static final String STORAGE_ACCOUNT_CONNECTION_STRING = System.getenv("STORAGE_ACCOUNT_CONNECTION_STRING");
    public static final String SHARE_NAME = System.getenv("SHARE_NAME");

    public static void main(String[] args) throws IOException {

        String[] fileNames = {
                "bio-duke-ellington.txt",
                "bio-ella-fitzgerald.txt",
                "bio-louis-armstrong.txt"
        };

        for (String fileName : fileNames) {
            ShareFileClient srcFileClient = new ShareFileClientBuilder().connectionString(STORAGE_ACCOUNT_CONNECTION_STRING).shareName(SHARE_NAME)
                    .resourcePath(fileName).buildFileClient();

            String downloadPath = "./tmp/download-" + fileName;
            File downloadFile = new File(downloadPath);
            downloadFile.delete();

            srcFileClient.downloadToFile(downloadPath);
        }
    }
}