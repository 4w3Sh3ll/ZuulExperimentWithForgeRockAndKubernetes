package gateway.sample.messanging;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessagingService {
	
	<T> T getMessage();

	<T> T getMessage(String baseUrl, String path);

	String getTokenId() throws JsonProcessingException, IOException;

	Boolean checkTokenState(String tokenId);
}
