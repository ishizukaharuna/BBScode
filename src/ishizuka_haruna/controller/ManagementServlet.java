package ishizuka_haruna.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ishizuka_haruna.beans.Branch;
import ishizuka_haruna.beans.Position;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.service.BranchService;
import ishizuka_haruna.service.ManagementService;
import ishizuka_haruna.service.PositionService;

@WebServlet(urlPatterns = { "/management" })
public class ManagementServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

		User user = (User) request.getSession().getAttribute("User");
		boolean isShowUser;
		if(user != null){
			isShowUser = true;
		}else{
			isShowUser = false;
		}

		List<Branch> branches = new BranchService().getBranch();
		List<User> managements = new ManagementService().getUser();
		List<Position> positions = new PositionService().getPosition();

		request.setAttribute("positions", positions);
		request.setAttribute("branches", branches);
		request.setAttribute("managements", managements);
		request.setAttribute("isShowUser", isShowUser);
		request.getRequestDispatcher("/management.jsp").forward(request, response);

	}

}
