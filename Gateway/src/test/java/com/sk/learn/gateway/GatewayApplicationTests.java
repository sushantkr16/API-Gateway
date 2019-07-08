package com.sk.learn.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RunWith(SpringRunner.class)
public class GatewayApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void encodeStringToBase64() {
		String namePassword = "learning_thursday_zuul";


			// Encode
			String encodedString = Base64.getEncoder().encodeToString(namePassword.getBytes());
			System.out.println("encodedString : " + encodedString);

			// Decode
			byte[] decodedBytes = Base64.getDecoder().decode("bGVhcm5pbmdfdGh1cnNkYXlfenV1bA==");
			String decodedString = new String(decodedBytes);
			System.out.println("decodedString : "+ decodedString);


		String namePassword1 = "learning_thursday_api_zuul";
		// Encode
		String encodedString1 = Base64.getEncoder().encodeToString(namePassword1.getBytes());
		System.out.println("encodedString1 : " + encodedString1);

		// Decode
		byte[] decodedBytes1 = Base64.getDecoder().decode("bGVhcm5pbmdfdGh1cnNkYXlfYXBpX3p1dWw=");
		String decodedString1 = new String(decodedBytes1);
		System.out.println("decodedString1 : "+ decodedString1);



	}

}
