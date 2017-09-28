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

import ishizuka_haruna.beans.Branch;
import ishizuka_haruna.beans.Position;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.exception.NoRowsUpdatedRuntimeException;
import ishizuka_haruna.service.BranchService;
import ishizuka_haruna.service.PositionService;
import ishizuka_haruna.service.UserService;

@WebServlet(urlPatterns = {"/settings"})
public class SettingsUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<Branch> branches = new BranchService().getBranch();
	List<Position> positions = new PositionService().getPosition();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		HttpSession session = request.getSession();


		String userid = request.getParameter("id");

		if(StringUtils.isEmpty(userid) || userid.length() >= 10){
			session.setAttribute("errorMessages", "URLが不正です");
			response.sendRedirect("management");

		}else if(!userid.matches("^([1-9]\\d*)$")){
			session.setAttribute("errorMessages", "URLが不正です");
			response.sendRedirect("management");

		}else{
			int id  = Integer.parseInt(userid);
			User editUser = new UserService().getUser(id);

			if(editUser == null){
				session.setAttribute("errorMessages", "ユーザーが存在しません");
				response.sendRedirect("management");
			}else{
				request.setAttribute("branches", branches);
				request.setAttribute("positions", positions);
				request.setAttribute("User", editUser);
				request.getRequestDispatcher("settings.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		List<String> messages = new ArrayList<String>();
		User editUser = getEditUser(request);

		String password = request.getParameter("password");


		request.setAttribute("editUser",editUser);

		if(isValid(request,messages) == true){
			try{
				new UserService().update(editUser,password);

			}catch(NoRowsUpdatedRuntimeException e){

				request.setAttribute("errorMessages", messages);
				response.sendRedirect("settings");
			}

			response.sendRedirect("management");

		}else{
			request.setAttribute("User", editUser);
			request.setAttribute("errorMessages", messages);
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);

			request.getRequestDispatcher("/settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request) throws IOException, ServletException {

		User editUser = new User();
		int id  = Integer.parseInt(request.getParameter("id"));
		editUser.setId(id);
		editUser.setAccount(request.getParameter("account"));
		editUser.setName(request.getParameter("name"));
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		editUser.setBranch_id(branchId);
		int positionId = Integer.parseInt(request.getParameter("position_id"));
		editUser.setPosition_id(positionId);

		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		int id  = Integer.parseInt(request.getParameter("id"));
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		int positionId = Integer.parseInt(request.getParameter("position_id"));
		User user = new UserService().getUser(id);


		if(id != user.getId() && account.equals(user.getAccount())){
			messages.add("ユーザーIDが重複しています");
		}

		if(StringUtils.isEmpty(account) == true){
			messages.add("ユーザーIDを入力してください");
		}

		if (!StringUtils.isEmpty(account) == true && !account.matches("^[0-9a-zA-Z]{6,20}$")) {
			messages.add("ユーザーIDは6文字以上、20文字以内の半角英数で設定してください");
		}

		if(!password.equals(checkPassword)){
			messages.add("パスワードが一致していません");
		}

		if(StringUtils.isEmpty(name) == true){
			messages.add("ユーザー名を入力してください");
		}

		if(10 < name.length()){
			messages.add("ユーザー名は10文字以内で入力してください");
		}

		if(branchId != 1 && (positionId == 1 || positionId == 2)){
			messages.add("支店・役職の組み合わせが不正です");
		}

		if(branchId == 1 && (positionId == 3 || positionId == 4)){
			messages.add("支店・役職の組み合わせが不正です");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

}
