package sample.contracts.cloud;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import gateway.sample.config.AppStart;
import gateway.sample.controller.IngnoredController;;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AppStart.class)
@DirtiesContext
@AutoConfigureMessageVerifier
public class CloudContractsSetup {
			
	 @Before
	  public void setup() {
		  StandaloneMockMvcBuilder standaloneMockMvcBuilder 
         = MockMvcBuilders.standaloneSetup(new IngnoredController());
       RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
	  }
	
	
}
