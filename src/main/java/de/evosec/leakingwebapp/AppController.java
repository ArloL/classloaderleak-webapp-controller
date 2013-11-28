package de.evosec.leakingwebapp;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	@RequestMapping("/leak")
	public ResponseEntity<String> initiateLeak() throws Exception {
		InputStream is = null;
		try {
			is = new ClassPathResource("app.truststore").getInputStream();
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(is, "changeit".toCharArray());
			System.out.println("Loaded KeyStore");
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
