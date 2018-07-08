import org.springframework.cloud.contract.spec.Contract

Contract.make {
	name: "SimpleContractWithPathParamsOnController"
    description "simple contract sample for an imported controller in a SpringBoot project with parameters"
    request {
        method GET()
        url("/hurrdurr/test") {
        	queryParameters {
        		parameter("isItTrue", "true")
        	}
        }
    }
    response {
        body("true")
        status 200
    }
}
