package gateway.sample.util;

import org.springframework.stereotype.Component;

import gateway.sample.config.GatewayLoggingFactory;

@Component
public class AddressBuilder {
	
	/**
	 * Builds the required address for the cluster service, based on name, kubernetes master address and service names.
	 * Kubernetes has many ways to access a service, including your very own configured proxy (INGRESS controller), 
	 * to show the difference between ways of doing things:
	 * 1. Using parametrization with the environment... or
	 * 2. Using some Kubernetes specific method implementation
	 * */
	public String buildClusterAddress(String clusterRootContext, String namespace, String serviceName, String port) {
		//http://kubernetes_master_address/api/v1/namespaces/namespace_name/services/service_name[:port_name]/proxy
		if(namespace ==null || clusterRootContext==null) {
			GatewayLoggingFactory.GatewayLogger().info(" No namespace available. Most likely a label addressing is being used!");
			return "http://"+serviceName;
		}
		else {
			return clusterRootContext+"/api/v1/namespaces/"+namespace+"services/"+serviceName+port+"/proxy";
		}
	}
	
	
	
}
