package nl.rubix.eos.camel.customdataformat;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pim on 1/10/18.
 */
public class MessageDataFormat implements DataFormat {
  @Override
  public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {

  }

  @Override
  public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
    //set the message to the original message of the Exchange. This preserves the body and all headers previously present on the exchange.
    Message response = exchange.getIn();

    String originalBody = exchange.getIn().getBody(String.class);
    String reversedBody = new StringBuilder(originalBody).reverse().toString();

    response.setBody(reversedBody, String.class);
    response.setHeader("MyAwesomeHeader", reversedBody);

    return response;
  }
}
