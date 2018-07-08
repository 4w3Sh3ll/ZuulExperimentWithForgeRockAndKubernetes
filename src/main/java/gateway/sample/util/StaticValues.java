package gateway.sample.util;

public enum StaticValues {
	FORGEROCK_ROOT_CONTEXT("http://localhost:8080"),
	FORGEROCK_TOKEN_GET_CONTEXT_PATH("/openam/json/authenticate"),
	FORGEROCK_REALM("myrealm"),
	FORGEROCK_TOKEN_NAME("tokenId"),
	FORGEROCK_USERNAME_HEADER_TOKEN("X-OpenAM-Username"),
	FORGEROCK_PASSWORD_HEADER_TOKEN("X-OpenAM-Password"),
	FORGEROCK_TOKEN_CHECK_CONTEXT_PATH("/openam/json/myrealm/sessions?_action=validate"),
	ENV_FORGEROCK_ROOT_CONTEXT("FORGEROCK_REALM"),
	ENV_ADDRESS("http://localhost:8080"),
	ENV_MASTER_ADDRESS("KUBERNETES_MASTER_ADDRESS"),
	ENV_SERVICE_ADDRESS("SERVICE1_ADDRESS");
	
	private final String val;

	StaticValues(final String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
	
}
