package de.hska.iwi.vislab.lab1;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import static org.testng.Assert.*;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import de.hska.iwi.vislab.lab1.example.ws.FibonacciService;
import de.hska.iwi.vislab.lab1.example.ws.FibonacciServiceImpl;

public class FibonacciTest {

	static {
		// dump http on client
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		// dump http on server
		// System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump",
		// "true");
	}

	// endpoint address
	private static final String FIBONACCI_URL = "http://localhost:4434/fibonacciservice";

	// server endpoint
	private Endpoint ep;

	@BeforeTest
	public void initServer() {
		// start the server
		ep = Endpoint.publish(FIBONACCI_URL, new FibonacciServiceImpl());
	}

	@Test
	public void testGetFibonacci() throws Exception {
		// create a client stub for the service just based on the URL
		Service service = Service.create(new URL(FIBONACCI_URL + "?wsdl"),
				new QName("http://ws.example.lab1.vislab.iwi.hska.de/", "FibonacciServiceImplService"));

		// create a proxy object for the fibonacci service interface
		FibonacciService fibonacciService = service.getPort(FibonacciService.class);

		// call the service 25 times
		int max = 25;
		int result = 1;
		for (int i = 1; i <= max; i++) {
			if (i > 1)
				result = fibonacciService.getFibonacci(i);
		}
		// test the 25th f*-nr
		assertEquals(result, 75025);
	}

	@Test
	public void testNextFibonacci() throws Exception {
		// create a client stub for the service just based on the URL
		Service service = Service.create(new URL(FIBONACCI_URL + "?wsdl"),
				new QName("http://ws.example.lab1.vislab.iwi.hska.de/", "FibonacciServiceImplService"));

		// create a proxy object for the fibonacci service interface
		FibonacciService fibonacciService = service.getPort(FibonacciService.class);
		int current = 0;
		int tmp = 0;
		fibonacciService.restore();
		for (int i = 0; i < 25; i++) {
			tmp = fibonacciService.getFibonacci(i);
			current = fibonacciService.next();
			System.out.println("current=" + current + ", temp=" + tmp);

			assertEquals(current, tmp);
		}
	}

	@Test
	public void testRestoreFibonacci() throws Exception {
		// create a client stub for the service just based on the URL
		Service service = Service.create(new URL(FIBONACCI_URL + "?wsdl"),
				new QName("http://ws.example.lab1.vislab.iwi.hska.de/", "FibonacciServiceImplService"));

		// create a proxy object for the fibonacci service interface
		FibonacciService fibonacciService = service.getPort(FibonacciService.class);
		int test = 0;
		fibonacciService.restore();
		int a = fibonacciService.getFibonacci(test);
		int b = fibonacciService.next();
		assertEquals(b, a);
	}

	@AfterTest
	public void stopServer() {
		// stop the server
		ep.stop();
	}
}
