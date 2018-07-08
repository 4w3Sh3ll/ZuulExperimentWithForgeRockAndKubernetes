package gateway.sample.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class GatewayParametrizationSupport {
	
	@Autowired
	RestTemplate template;
	
	public String getEnvironmentVariableValue(String searchKey) {
		return System.getenv(searchKey);
	}
	
	
	public HttpStatus pingClusterAddress(String url) {
		return template.getForEntity(url, String.class).getStatusCode();
	}
	
}
