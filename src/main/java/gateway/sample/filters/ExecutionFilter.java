package gateway.sample.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import gateway.sample.config.GatewayLoggingFactory;
import gateway.sample.messanging.MessagingService;
import gateway.sample.util.StaticValues;

@Component
public class ExecutionFilter extends ZuulFilter {
	
	@Autowired
	MessagingService messagingService;
	
	//inject the zuul value to demo for using the environment to parametrize services inside the cluster
	//this can be extended to any similar cases... like mapping an entire set of microservices inside the cluster if no service discovery is required/wanted
	@Value("$(zuul.routes.login.url)")
    private String loginURL;
	
	public Object run() throws ZuulException {
		
		GatewayLoggingFactory.GatewayLogger().info("A request was detected. Running normal token checks.");
		//log the token to see what`s coming on the other side
		GatewayLoggingFactory.GatewayLogger().info( System.lineSeparator() +System.lineSeparator() +
				RequestContext.getCurrentContext().getRequest().getHeader(StaticValues.FORGEROCK_TOKEN_NAME.toString()) 
				+System.lineSeparator() +System.lineSeparator());
		
		try {
			//check the token state to be valid (or not)
			if(messagingService.checkTokenState(RequestContext.getCurrentContext().getRequest().getHeader(StaticValues.FORGEROCK_TOKEN_NAME.toString()))) {
				//if true, do nothing, let Zuul run the routing service
				return null;
			} 
			else {
				//redirect to a login page
				//...or you can do whatever you want
				RequestContext.getCurrentContext().setResponseStatusCode(401);
				RequestContext.getCurrentContext().getResponse().sendRedirect( loginURL );
			}
		}
		catch (Exception x) {
			GatewayLoggingFactory.GatewayLogger().info(x.toString());
		}
		
		
		
		return null;
	}

	public boolean shouldFilter() {
		
		if(RequestContext.getCurrentContext().get("proxy").equals("login")) {
			return false;
		}
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "route";
	}

}
