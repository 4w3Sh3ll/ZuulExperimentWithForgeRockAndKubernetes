package gateway.sample.messanging;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;

import gateway.sample.config.GatewayLoggingFactory;
import gateway.sample.util.MessageReaderUtils;
import gateway.sample.util.StaticValues;

@Service
@Qualifier("withoutEnvironmentVariables")
public class ForgeRockMessagingService implements MessagingService {
	
	@Autowired
	MessageReaderUtils messageUtils;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getMessage(String baseUrl, String path) {
		
		try { 
			//get the ForgeRock response based on given address
			String forgeRockResponse = WebClient.builder().baseUrl(baseUrl)
						.build().get().uri(path).retrieve().bodyToMono(String.class).block();
			//log ForgeRock response
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +forgeRockResponse+System.lineSeparator()+System.lineSeparator());
			//return the result
			return forgeRockResponse;
		}
		catch (Exception x) {
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +x.toString()+System.lineSeparator()+System.lineSeparator());
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMessage() {

		try {
			//get the ForgeRock response based on given address
			String forgeRockResponse = WebClient.builder().baseUrl(StaticValues.FORGEROCK_REALM.toString())
						.build().get().uri(StaticValues.FORGEROCK_TOKEN_GET_CONTEXT_PATH.toString()).retrieve().bodyToMono(String.class).block();
			//log ForgeRock response
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +forgeRockResponse+System.lineSeparator()+System.lineSeparator());
			//return the result
			return forgeRockResponse;
		}
		catch (Exception x) {
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +x.toString()+System.lineSeparator()+System.lineSeparator());
		}
		
		return null;
	}

	//get the tokenId from the ForgeRock response
	@Override
	public String getTokenId() throws JsonProcessingException, IOException {
		return messageUtils.readMessage(this.getMessage(), StaticValues.FORGEROCK_TOKEN_NAME.toString());
	}
	
	@Override
	public Boolean checkTokenState(String tokenId) {
        //the incoming response from ForgeRock
		String response;
		
		try {
			//get the ForgeRock response based on given address
			response = WebClient.builder().baseUrl(StaticValues.FORGEROCK_REALM.toString())
					.defaultHeader(StaticValues.FORGEROCK_TOKEN_NAME.toString(), tokenId)
						.build().get().uri(StaticValues.FORGEROCK_TOKEN_GET_CONTEXT_PATH.toString()).retrieve().bodyToMono(String.class).block();
			//log ForgeRock response
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +response+System.lineSeparator()+System.lineSeparator());
			
			//run the token check
			if(messageUtils.readMessage(response, "valid").equals(Boolean.TRUE.toString())) {
				return true;
			}
			return false;
		}
		catch (Exception x) {
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +x.toString()+System.lineSeparator()+System.lineSeparator());
		}
		
		return false;
	}

}

