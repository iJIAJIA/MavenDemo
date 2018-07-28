package bridgemethod;

import org.springframework.validation.annotation.Validated;

public class BridgeMethodImpl implements IBridgeMethodTest<String> {
	
	@Override
	@Validated
	public String doSomething(String t) {
		return "doSomething";
	}
	
}
