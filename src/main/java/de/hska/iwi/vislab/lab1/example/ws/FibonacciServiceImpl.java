package de.hska.iwi.vislab.lab1.example.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.example.ws.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService {

	private int current = 1;

	@Override
	public int getFibonacci(int n) {
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

	@Override
	public int next() {
		int tmp = compute(current);
		current++;
		return tmp;
	}

	@Override
	public void restore() {
		this.current = 0;
	}
}
