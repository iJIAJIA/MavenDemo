package jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import jaxb.model.Finger;
import jaxb.model.Hand;

import org.junit.Test;

public class JaxbTest {
	
	@Test
	public void test() throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(Hand.class);
		Marshaller marshaller = context.createMarshaller();  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
        Hand hand = new Hand("big size", null);
        hand.setFingers(Collections.singletonList(new Finger("type")));
        StringWriter writer = new StringWriter();  
        marshaller.marshal(hand, writer);  
        String result = writer.toString();
        System.out.println(result);
        //xml 2 bean
        Unmarshaller unmarshaller = context.createUnmarshaller();  
        Hand t = (Hand) unmarshaller.unmarshal(new StringReader(result));  
        System.out.println(t);
	}
}
