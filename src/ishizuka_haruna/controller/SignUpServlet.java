package ishizuka_haruna.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ishizuka_haruna.beans.Branch;
import ishizuka_haruna.beans.Position;
import ishizuka_haruna.beans.User;
import ishizuka_haruna.service.BranchService;
import ishizuka_haruna.service.PositionService;
import ishizuka_haruna.service.UserService;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<Branch> branches = new BranchService().getBranch();
	List<Position> positions = new PositionService().getPosition();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException,ServletException{

		request.setAttribute("branches", branches);
		request.setAttribute("positions",positions);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException,ServletException{

		List<String> messages = new ArrayList<String>();
		User user = new User();



		user.setAccount(request.getParameter("account"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		if(request.getParameter("branch_id") != null && request.getParameter("branch_id") != ""){
			int branchId = Integer.parseInt(request.getParameter("branch_id"));
			user.setBranch_id(branchId);
		}

		if(request.getParameter("position_id") != null && !request.getParameter("position_id").isEmpty()){

			int positionId = Integer.parseInt(request.getParameter("position_id"));
			user.setPosition_id(positionId);
		}

		if(isValid(request, messages) == true){

			new UserService().register(user);
			response.sendRedirect("management");

		}else{
			request.setAttribute("errorMessages", messages);
			request.setAttribute("User", user);
			request.setAttribute("branches", branches);
			request.setAttribute("positions",positions);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String name = request.getParameter("name");
		String branchId = request.getParameter("branch_id");
		String positionId = request.getParameter("position_id");
		User userAccount = new UserService().getAccount(account);

		if(StringUtils.isEmpty(account) == true){
			messages.add("ユーザーIDを入力してください");
		}

		if (!StringUtils.isEmpty(account) == true && !account.matches("^[0-9a-zA-Z]{6,20}$")) {
			messages.add("ユーザーIDは6文字以上、10文字以内の半角英数で設定してください");
		}

		if(userAccount != null){
				messages.add("ユーザーIDが重複しています");
		}

		if(StringUtils.isEmpty(password) == true){
			messages.add("パスワードを入力してください");
		}

		if(StringUtils.isEmpty(checkPassword) == true){
			messages.add("確認用パスワードを入力してください");
		}

		if(!StringUtils.isEmpty(password) == true && !password.matches("^[0-9a-zA-Z!-/]{6,20}$")){
			messages.add("パスワードは記号を含む6文字から20文字で入力してください");
		}

		if(!StringUtils.isEmpty(checkPassword) == true && !password.equals(checkPassword)){
			messages.add("パスワードが一致していません");
		}

		if(StringUtils.isEmpty(name) == true){
			messages.add("ユーザー名を入力してください");
		}

		if(10 < name.length()){
			messages.add("ユーザー名は10文字以内で入力してください");
		}

		if(StringUtils.isEmpty(branchId) == true){
			messages.add("支店名を指定してください");
		}

		if(StringUtils.isEmpty(positionId) == true){
			messages.add("役職名を指定してください");
		}

		if(!branchId.equals("1") && (positionId.equals("1") || positionId.equals("2"))){
			messages.add("支店・役職の組み合わせが不正です");
		}

		if(branchId.equals("1") && (positionId.equals("3") || positionId.equals("4"))){
			messages.add("支店・役職の組み合わせが不正です");
		}


		if(messages.size() ==0){
			return true;
		}else{
		return false;
		}
	}



}
