package ishizuka_haruna.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ishizuka_haruna.service.UserService;

@WebServlet(urlPatterns = {"/userStop"})
public class UserStopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		int isWorking = Integer.parseInt(request.getParameter("is_working"));

		new UserService().stop(id, isWorking);

		response.sendRedirect("management");
	}
}
