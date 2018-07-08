package gateway.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IngnoredController {
	
	@GetMapping ("/hurrdurr/test")
	@ResponseBody
	public String getMeAStateString() {
		return "I`m a little Teapot!";
	}
	
	@GetMapping("/hurrdurr/test")
    public String isNumberPrime(@RequestParam("isItTrue") String val) {
        return val.equals("true") ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
}
	
}
