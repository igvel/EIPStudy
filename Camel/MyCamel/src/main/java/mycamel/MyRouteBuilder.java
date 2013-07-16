package mycamel;

import org.apache.camel.ExchangePattern;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends SpringRouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:src/data?noop=true")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK message")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others").stop()
            .end()
            .inOnly("bean:testBean")
            .log("Body ${body}");

    }

}
