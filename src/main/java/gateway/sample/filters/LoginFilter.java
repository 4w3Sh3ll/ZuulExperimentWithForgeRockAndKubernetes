package gateway.sample.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import gateway.sample.config.GatewayLoggingFactory;
import gateway.sample.messanging.MessagingService;
import gateway.sample.util.StaticValues;

@Component
public class LoginFilter extends ZuulFilter{
	
	private RequestContext ctx;
	
	@Autowired
	MessagingService messagingService;
	
	//Use these variables if you want to get values from the environment instead of some hardcoded stuff


	@Override
	public Object run() throws ZuulException {
		GatewayLoggingFactory.GatewayLogger().info("Executing gateway filters... " );
		
		try {
			//when this filter gets executed, then a header is added with a tokenId released by FogeRock
			//this token must be persisted and used for each call to the cluster services
			RequestContext.getCurrentContext().getResponse().addHeader(StaticValues.FORGEROCK_TOKEN_NAME.toString(), messagingService.getTokenId());	
		}
		catch (Exception x) {
			//log the error
			GatewayLoggingFactory.GatewayLogger().error(System.lineSeparator()+System.lineSeparator()+x.toString()+System.lineSeparator()+System.lineSeparator());
		}
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		ctx = RequestContext.getCurrentContext();
		//this filter should execute only on GET:/login calls
		//this is because a session token should be provided only when a login request is processed
		if ( ctx.get("proxy").equals("login")) {
			GatewayLoggingFactory.GatewayLogger().info(" Someone requested a login! " + "Origin: " + ctx.getRequest().getRemoteAddr() );
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		return "route";
	}

}
