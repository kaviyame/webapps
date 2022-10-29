package QueryAbstractFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DML {
	void getDML();

	void doDMLOperation(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
