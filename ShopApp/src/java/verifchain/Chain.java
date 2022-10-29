package verifchain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Register;

public class Chain {
	Processor vchain;

	public Chain() {
		buildChain();
	}

	private void buildChain() {
		vchain = new AuthProcessor(new ValProcessor(null));
	}

	public void process(Type type, Register login,HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException {
		vchain.process(type,login, req, res, chain);
	}

}
