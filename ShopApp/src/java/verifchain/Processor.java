package verifchain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Register;

abstract class Processor {
	private Processor nextProcessor;

	public Processor(Processor nextProcessor) {
		this.nextProcessor = nextProcessor;
	}

	public void process(Type type, Register login,HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException {
		if (nextProcessor != null)
			nextProcessor.process(type, login,req, res, chain);

	}
}