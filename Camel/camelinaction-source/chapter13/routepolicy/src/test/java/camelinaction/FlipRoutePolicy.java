/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.impl.RoutePolicySupport;

/**
 * @version $Revision: 259 $
 */
public class FlipRoutePolicy extends RoutePolicySupport {

    private final String name1;
    private final String name2;

    /**
     * Flip the two routes
     *
     * @param name1 name of the first route
     * @param name2 name of the second route
     */
    public FlipRoutePolicy(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    @Override
    public void onExchangeDone(Route route, Exchange exchange) {
        // decide which route to stop and start
        // basically we should flip the two routes
        String stop = route.getId().equals(name1) ? name1 : name2;
        String start = route.getId().equals(name1) ? name2 : name1;

        CamelContext context = exchange.getContext();
        try {
            // force stopping this route while we are routing an Exchange
            // requires two steps:
            // 1) unregister from the inflight registry
            // 2) stop the route
            context.getInflightRepository().remove(exchange);
            context.stopRoute(stop);
            // then we can start the other route
            context.startRoute(start);
        } catch (Exception e) {
            // let the exception handle handle it, which is often just to log it
            getExceptionHandler().handleException("Error flipping routes", e);
        }
    }

}
