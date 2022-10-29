package filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import verifchain.*;

import java.io.IOException;

import com.google.gson.Gson;

public class VerificationFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public VerificationFilter() {
		super();
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("VerificationFilter initialized");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if(req.getMethod().equalsIgnoreCase("options")) {
			System.out.println("hi");
			chain.doFilter(req, res);
			return;
		}
		//String verify = req.getParameter("verify");
		
		model.Register login = new Gson().fromJson(request.getReader(), model.Register.class);
		
		String verify=login.getVerify();
		
		Chain verifchain = new Chain();

		verifchain.process(new Type(verify), login,req, res, chain);

	}

	public void destroy() {
		System.out.println("DBEntryFilter destroyed");
	}
}
