package gateway.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayLoggingFactory {

	private static final Logger GatewayLogger = LoggerFactory.getLogger(GatewayLoggingFactory.class);
	
	public static final Logger GatewayLogger() {
		return GatewayLogger;
	}
	
}
