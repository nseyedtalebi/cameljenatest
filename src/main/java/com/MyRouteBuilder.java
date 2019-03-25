package com;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        from("rabbitmq://"+/*server ip*/+"/"+/*queue name*/"?username="+/*username*/"&password="+/*password*/"&autoDelete=false&exchangeType=fanout&durable=false")
                .bean(new Testbed(),"doStuff");
    }

}
