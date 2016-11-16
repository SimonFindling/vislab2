package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hska.iwi.vislab.lab1.example.ws.FibonacciService;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.net.URL;

public class HelloWorldTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and
		// Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testSayHello() {
		String responseMsg = target.path("helloworld").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		assertEquals("Hallo Welt!", responseMsg);
	}

	@Test
	public void testGetFibonacci() {

		// call the service 25 times
		int max = 25;
		int result = 1;
		for (int i = 1; i <= max; i++) {
			if (i > 1)
				result = target.path("fibonacci").path("getfibonacci").queryParam("n", i).request()
						.accept(MediaType.TEXT_PLAIN).get(int.class);
		}
		// test the 25th f*-nr
		assertEquals(result, 75025);
	}

	@Test
	public void testNextFibonacci() throws Exception {
		// create a client stub for the service just based on the URL

		int current = 0;
		int tmp = 0;
		target.path("fibonacci").path("restore").request().accept(MediaType.TEXT_PLAIN).get();
		for (int i = 0; i < 25; i++) {
			tmp = target.path("fibonacci").path("getfibonacci").queryParam("n", i).request(MediaType.TEXT_PLAIN)
					.get(int.class);
			current = target.path("fibonacci").path("next").request(MediaType.TEXT_PLAIN).get(int.class);
			System.out.println("current=" + current + ", temp=" + tmp);

			assertEquals(current, tmp);
		}
	}

	@Test
	public void testRestoreFibonacci() throws Exception {
		int test = 0;
		target.path("fibonacci").path("restore").request().accept(MediaType.TEXT_PLAIN).get();
		int a = target.path("fibonacci").path("getfibonacci").queryParam("n", test).request(MediaType.TEXT_PLAIN)
				.get(int.class);

		int b = target.path("fibonacci").path("next").request(MediaType.TEXT_PLAIN).get(int.class);
		assertEquals(b, a);
	}
}
