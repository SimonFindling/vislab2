package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("fibonacci")
public class Fibonacci {

	private static int current = 1;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("next")
	public Integer next() {
		int tmp = compute(current);
		current++;
		return tmp;
	}
	
	@GET
	@Path("restore")
	@Produces(MediaType.TEXT_PLAIN)
	public String restore() {
		current = 1;
		return "restored";
	}
	
	@GET
	@Path("getfibonacci")
	@Produces(MediaType.TEXT_PLAIN)
	public Integer getFibonacci(@QueryParam("n") int n) {
		return compute(n);
	}

	private int compute(int n) {
		if (n <= 0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return compute(n - 2) + compute(n - 1);
	}
}
