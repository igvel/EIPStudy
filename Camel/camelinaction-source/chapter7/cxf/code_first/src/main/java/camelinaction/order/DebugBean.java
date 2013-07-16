package camelinaction.order;

import org.apache.camel.Exchange;

/**
 * DebugBean
 *
 */
public class DebugBean {

    public static String process(Exchange body) {
        System.out.println(body);
        return "New body!";
    }
}
