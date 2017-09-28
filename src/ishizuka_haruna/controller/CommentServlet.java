package ishizuka_haruna.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import ishizuka_haruna.beans.Comment;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.service.CommentService;

@WebServlet(urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if(isValid(request, messages) == true){
			User user = (User) session.getAttribute("loginUser");

			Comment comment = new Comment();

			comment.setUser_id(user.getId());
			comment.setMessage_id(Integer.parseInt(request.getParameter("id")));
			comment.setText(request.getParameter("text"));
			comment.setBranch_id(user.getBranch_id());
			comment.setPosition_id(user.getPosition_id());

			new CommentService().register(comment);

			response.sendRedirect("./");
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
		}

	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String comment = request.getParameter("text");


		if(StringUtils.isEmpty(comment) == true || StringUtils.isBlank(comment) == true){
			messages.add("コメントを入力してください。");
		}

		if(!StringUtils.isEmpty(comment) == true && 500 < comment.length()){
			messages.add("コメントは500字以内で入力してください");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}


	}

}
