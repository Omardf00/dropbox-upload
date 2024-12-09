package com.mega.uploads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import com.mega.uploads.model.UserInfo;

public class UploadsApplication {

	public static void main(String[] args) {
		UserInfo userInfo = readCSV();
		userInfo.setToken(DropboxTokenGenerator.getDBTOken(userInfo.getUrl(), userInfo.getProperties(), userInfo.getDriver()));
		System.out.println("URL: " + userInfo.getUrl());
		System.out.println("Properties: " + userInfo.getProperties());
		System.out.println("Driver Route: " + userInfo.getDriver());
		System.out.println("Token: " + userInfo.getToken());
		System.out.println("Dropbox Path: " + userInfo.getDropBoxPath());
		System.out.println("Local Path: " + userInfo.getLocalPath());
        String accessToken = userInfo.getToken();

        // Configuración del cliente de Dropbox
        DbxRequestConfig config = DbxRequestConfig.newBuilder("my-app").build();
        DbxClientV2 client = new DbxClientV2(config, accessToken);

        // Archivo local y ruta de Dropbox
        String localFileToUpload = userInfo.getLocalPath(); // Archivo para subir
        String dropboxPath = userInfo.getDropBoxPath(); // Destino en Dropbox
        String localFileToDownload = userInfo.getLocalPath(); // Ruta para descargar archivo
        
        // Asegúrate de que `dropboxPath` incluya el nombre del archivo
        if (!dropboxPath.endsWith("/")) {
            dropboxPath += "/";
        }
        dropboxPath += new File(userInfo.getLocalPath()).getName(); // Añade el nombre del archivo al final

        System.out.println("Ruta en Dropbox para subir: " + dropboxPath);

//        // Subir archivo
//        try (InputStream inputStream = new FileInputStream(localFileToUpload)) {
//            FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
//                    .withMode(WriteMode.OVERWRITE) // Sobrescribe si ya existe
//                    .uploadAndFinish(inputStream);
//            System.out.println("Archivo subido exitosamente a Dropbox: " + metadata.getPathLower());
//        } catch (Exception e) {
//            System.err.println("Error al subir archivo: " + e.getMessage());
//        }

        // Descargar archivo
        try (OutputStream outputStream = new FileOutputStream(localFileToDownload)) {
            Metadata metadata = client.files().downloadBuilder(dropboxPath)
                    .download(outputStream);
            System.out.println("Archivo descargado exitosamente desde Dropbox: " + metadata.getPathLower());
        } catch (Exception e) {
            System.err.println("Error al descargar archivo: " + e.getMessage());
        }
	}

	private static UserInfo readCSV() {
		//String filePath = "./src/main/resources/info.csv";
		String filePath = "./info.csv";
		UserInfo userInfo = new UserInfo();
		try {
			Reader reader = new FileReader(filePath);
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
			if (csvParser.getRecordNumber() > 1) {
				throw new IOException();
			}
			for (CSVRecord record : csvParser) {
				userInfo.setUrl(record.get("URL"));
				userInfo.setProperties(record.get("Properties"));
				userInfo.setDriver(record.get("Driver"));
				userInfo.setDropBoxPath(record.get("DropBox Path"));
				userInfo.setLocalPath(record.get("Local Path"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

}
