package gateway.sample.util;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageReaderUtils {
	
	public String readMessage (String message, String key) throws JsonProcessingException, IOException {
		ObjectMapper mpr = new ObjectMapper();
		return mpr.readTree(message).get(key).toString();
	}
	
	
}
