package ishizuka_haruna.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ishizuka_haruna.beans.Branch;
import ishizuka_haruna.beans.Message;
import ishizuka_haruna.beans.Position;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.beans.UserComment;
import ishizuka_haruna.beans.UserMessage;
import ishizuka_haruna.service.BranchService;
import ishizuka_haruna.service.MessageService;
import ishizuka_haruna.service.PositionService;
import ishizuka_haruna.service.UserCommentService;
import ishizuka_haruna.service.UserMessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		User user = (User)request.getSession().getAttribute("loginUser");

		boolean isShowMessageForm;
		if(user != null){
			isShowMessageForm = true;
		}else{
			isShowMessageForm = false;
		}

		request .setCharacterEncoding("UTF-8");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String selectCategory = request.getParameter("category");


		if(!StringUtils.isEmpty(start)){
			request.setAttribute("start", start);
		}else{
			start = "2017/09/04";
		}

		if(!StringUtils.isEmpty(end)){
			request.setAttribute("end", end);
		}else{
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        end = sdf.format(cal.getTime());
		}

		if(StringUtils.isEmpty(selectCategory) == true){
			selectCategory = null;
		}


		List<Message> categorys = new MessageService().getCategory();

		List<UserMessage> messages = new UserMessageService().getUserMessage(start,end,selectCategory);
		List<UserComment> userComment = new UserCommentService().getUserComment();
		List<Branch> branches = new BranchService().getBranch();
		List<Position> positions = new PositionService().getPosition();

		request.setAttribute("positions", positions);
		request.setAttribute("loginuser", user);
		request.setAttribute("branches", branches);
		request.setAttribute("messages", messages);
		request.setAttribute("comments", userComment);
		request.setAttribute("isShowMessageFrom", isShowMessageForm);
		request.setAttribute("categorys", categorys);
		request.setAttribute("selectCategory", selectCategory);




		request.getRequestDispatcher("top.jsp").forward(request, response);
	}


}
