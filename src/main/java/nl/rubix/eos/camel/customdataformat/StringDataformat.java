package nl.rubix.eos.camel.customdataformat;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pim on 1/10/18.
 */
public class StringDataformat implements DataFormat {

  @Override
  public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {

  }

  @Override
  public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
    String originalBody = exchange.getIn().getBody(String.class);
    String reversedBody = new StringBuilder(originalBody).reverse().toString();

    //The statement below does nothing
    exchange.getIn().setHeader("MyAwesomeHeader", reversedBody);

    return reversedBody;
  }
}
