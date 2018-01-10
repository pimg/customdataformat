package nl.rubix.eos.camel.customdataformat;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by pim on 1/10/18.
 */
public class StringDataformatTest extends CamelTestSupport {

  @Override protected RoutesBuilder[] createRouteBuilders() throws Exception {
    RoutesBuilder StringDataFormatRouteBuilder = new RouteBuilder() {
      @Override public void configure() throws Exception {
        from("direct:unmarshalBody")
          .log("original body: ${body}")
          .unmarshal(new StringDataformat())
          .log("the body contains: ${body}")
          .log("the headers contain: $simple{headers}")
          .to("mock:outBody");
      }
    };

    RoutesBuilder MessageRouteBuilder = new RouteBuilder() {
      @Override public void configure() throws Exception {
        from("direct:unmarshalHeaders")
          .log("original body: ${body}")
          .unmarshal(new MessageDataFormat())
          .log("the body contains: ${body}")
          .log("the headers contain: $simple{headers}")
          .to("mock:outHeaders");
      }
    };

    return new RoutesBuilder[]{StringDataFormatRouteBuilder, MessageRouteBuilder};
  }

  @Test
  public void testUnmarshalBody() throws Exception {
    MockEndpoint mockOut = getMockEndpoint("mock:outBody");
    mockOut.setExpectedMessageCount(1);

    template.sendBody("direct:unmarshalBody", "Hello world");

    assertMockEndpointsSatisfied();
    Exchange response = mockOut.getExchanges().get(0);
    assertEquals("The exchange body contains: dlrow olleH", "dlrow olleH", response.getIn().getBody(String.class));
  }

  @Test
  public void testUnmarshalHeader() throws Exception {
    MockEndpoint mockOut = getMockEndpoint("mock:outHeaders");
    mockOut.setExpectedMessageCount(1);

    template.sendBody("direct:unmarshalHeaders", "Hello world");

    assertMockEndpointsSatisfied();
    Exchange response = mockOut.getExchanges().get(0);
    assertEquals("The exchange body should contain: dlrow olleH", "dlrow olleH", response.getIn().getBody(String.class));
    assertEquals("The header MyAwesomeHeader should contain: dlrow olleH", "dlrow olleH", response.getIn().getHeader("MyAwesomeHeader", String.class));
  }
}
