/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.util;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileUtils {
	
	public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	@Value("${api.encryptfile}")
	private String uidFile;
	
	@Value("${api.checksumfile}")
	private String chksumFile;
	
	public void saveEncryptUidToFile(String content) {

		logger.info("Content to write :{}",content);
	    byte data[] = content.getBytes();
	    Path p = Paths.get(uidFile);
	    logger.info("Encrypted uid save at {}",p);
	    try (OutputStream out = new BufferedOutputStream(
	      Files.newOutputStream(p, CREATE, APPEND))) {
	      out.write(data, 0, data.length);
	      
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}
	
	
	public void saveCheckSumToFile(String content) {

		logger.info("Content to write :{}",content);
	    byte data[] = content.getBytes();
	    Path p = Paths.get(chksumFile);
	    logger.info("Checksum for encrypted uid save at {}",p);
	    try (OutputStream out = new BufferedOutputStream(
	      Files.newOutputStream(p, CREATE, TRUNCATE_EXISTING))) {
	      out.write(data, 0, data.length);
	      
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}

	

	   public  String checksum() throws IOException, NoSuchAlgorithmException {
			//calculate checksum(MD5) for the file
		    MessageDigest md = MessageDigest.getInstance("MD5");
			logger.info("Calculating check sum for file ["+ uidFile+"]");

	        // DigestInputStream is better, but you also can hash file like this.
	        try (InputStream fis = new FileInputStream(uidFile)) {
	            byte[] buffer = new byte[1024];
	            int nread;
	            while ((nread = fis.read(buffer)) != -1) {
	                md.update(buffer, 0, nread);
	            }
	        }

	        // bytes to hex
	        StringBuilder result = new StringBuilder();
	        for (byte b : md.digest()) {
	            result.append(String.format("%02x", b));
	        }
	        return result.toString();

	    }
	

}
