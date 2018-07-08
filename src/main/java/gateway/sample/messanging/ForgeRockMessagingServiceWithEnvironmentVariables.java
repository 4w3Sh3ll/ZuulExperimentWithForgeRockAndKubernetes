package gateway.sample.messanging;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;

import gateway.sample.config.GatewayLoggingFactory;
import gateway.sample.util.MessageReaderUtils;
import gateway.sample.util.StaticValues;

@Service
@Qualifier("withEnvironmentVariables")
public class ForgeRockMessagingServiceWithEnvironmentVariables implements MessagingService {
		
	@Autowired
	MessageReaderUtils messageReadingUtils;
	
	/**
	 * Value is injected using  application.yml file
	 * In this case, it`s taking the environment variable defined in the application.yml file and 
	 * injects it here.
	 * The application.yml file mathces the deployment.yaml file, which generates the variables when using Helm to online the 
	 * environment
	 * */
	@Value("$(forgerock.url)")
	private String envForgeRockURI;
	
	@Value("$(forgerock.realm)")
	private String envForgeRockRealm;
	
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
			String forgeRockResponse = WebClient.builder().baseUrl(this.envForgeRockURI)
						.build().get().uri("/openam/json/"+this.envForgeRockRealm+"/authenticate").retrieve().bodyToMono(String.class).block();
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
		return messageReadingUtils.readMessage(this.getMessage(), StaticValues.FORGEROCK_TOKEN_NAME.toString());
	}
	
	@Override
	public Boolean checkTokenState(String tokenId) {
        //the incoming response from ForgeRock
		String response;
		
		try {
			//get the ForgeRock response based on given address
			response = WebClient.builder().baseUrl(this.envForgeRockRealm)
					.defaultHeader(StaticValues.FORGEROCK_TOKEN_NAME.toString(), tokenId)
						.build().get().uri("/openam/json/"+this.envForgeRockRealm+"/sessions?_action=validate").retrieve().bodyToMono(String.class).block();
			//log ForgeRock response
			GatewayLoggingFactory.GatewayLogger().info(System.lineSeparator()+System.lineSeparator()+ 
					" :::::::::::Incoming ForgeRock result: " +response+System.lineSeparator()+System.lineSeparator());
			
			//run the token check
			if(messageReadingUtils.readMessage(response, "valid").equals(Boolean.TRUE.toString())) {
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
