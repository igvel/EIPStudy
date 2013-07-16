package camel;

import org.apache.camel.InOnly;

/**
 * TestBean
 *
 * @author Игорь Елькин (ielkin@nvision-group.com)
 */
public class TestBean {

    @InOnly
    public static String test(String body) {
        System.out.println("Incoming: " + body);
        return "Enhanced:" + body;
    }
}
