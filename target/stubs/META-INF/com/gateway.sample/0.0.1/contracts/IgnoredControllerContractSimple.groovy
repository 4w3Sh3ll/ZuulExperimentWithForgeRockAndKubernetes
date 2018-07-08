import org.springframework.cloud.contract.spec.Contract

Contract.make {
	name: "SimpleContractNoPathParamsOnController"
    description "simple contract sample for an imported controller in a SpringBoot project"
    request {
        method GET()
        url("/hurrdurr/test")
    }
    response {
        status 200
    }
}
