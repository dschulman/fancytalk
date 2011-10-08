package fancytalk;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.extjwnl.JWNLException;

public class FancytalkServlet extends HttpServlet {
    private Fancytalker talker;

    public void init() throws ServletException {
	try {
	    talker = new Fancytalker();
	} catch (Exception e) {
	    if (e instanceof ServletException)
		throw (ServletException) e;
	    else
		throw new ServletException(e);
	}
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	String in = req.getParameter("input");
	PrintWriter out = resp.getWriter();
	if (in != null) {
	    try {
		out.println(talker.apply(in));
	    } catch (JWNLException e) {
		throw new ServletException(e);
	    }
	}
	out.flush();
	out.close();
    }
}
