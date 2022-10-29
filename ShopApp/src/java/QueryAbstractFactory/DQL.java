package QueryAbstractFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DQL {
	void getDQL();

	void doDQLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
