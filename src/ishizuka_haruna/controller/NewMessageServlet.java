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

import ishizuka_haruna.beans.Message;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.service.MessageService;

@WebServlet(urlPatterns = {"/newMessage"})
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	List<Message> categorys = new MessageService().getCategory();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{


		request.setAttribute("categorys", categorys);
		request.getRequestDispatcher("newMessage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();
		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setTitle(request.getParameter("title"));
		message.setText(request.getParameter("text"));
		message.setCategory(request.getParameter("category"));
		message.setUser_id(user.getId());
		message.setBranch_id(user.getBranch_id());
		message.setPosition_id(user.getPosition_id());

		if(isValid(request, messages) == true){
			new MessageService().register(message);

			response.sendRedirect("./");

		}else{
			request.setAttribute("errorMessages",messages);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/newMessage.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");


		if(StringUtils.isEmpty(title) == true || StringUtils.isBlank(title) == true){
			messages.add("件名を入力してください。");
		}

		if(!StringUtils.isEmpty(title) == true &&  30 < title.length()){
			messages.add("件名は30文字以内で入力してください");
		}

		if(StringUtils.isEmpty(category) == true || StringUtils.isBlank(category) == true){
			messages.add("カテゴリーを入力してください");
		}

		if(!StringUtils.isEmpty(category) == true &&  10 < category.length()){
			messages.add("カテゴリーは10文字以内で入力してください");
		}

		if(StringUtils.isEmpty(text) == true || StringUtils.isBlank(text) == true){
			messages.add("本文を入力してください");
		}

		if(!StringUtils.isEmpty(text) == true && 1000 < text.length()){
			messages.add("本文は1000文字以内で入力してください");
		}



		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

}
