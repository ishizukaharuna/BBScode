package ishizuka_haruna.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ishizuka_haruna.beans.User;
import ishizuka_haruna.service.UserService;

@WebFilter("/*")
public class LoginFilter implements Filter{

		@Override
		public void destroy() {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void init(FilterConfig paramFilterConfig) throws ServletException {
			// TODO 自動生成されたメソッド・スタブ
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {

			String path = ((HttpServletRequest) request).getServletPath();
			User loginUser = (User)((HttpServletRequest) request).getSession().getAttribute("loginUser");
			List<String> messages = new ArrayList<String>();
			HttpSession session = ((HttpServletRequest) request).getSession();

				if(!path.equals("/login")){

					if(loginUser == null){
						messages.add( "ログインしてください");
						session.setAttribute("errorMessages", messages);
						((HttpServletResponse) response).sendRedirect("./login");

						return;
					}

					if(path.equals("/management") && loginUser.getPosition_id() != 1){
						messages.add("権限がありません");
						session.setAttribute("errorMessages", messages);
						((HttpServletResponse) response).sendRedirect("./");
						return;
					}

					if(path.equals("/signup") && loginUser.getPosition_id() != 1){
						messages.add("権限がありません");
						session.setAttribute("errorMessages", messages);
						((HttpServletResponse) response).sendRedirect("./");
						return;
					}

					if(path.equals("/settings") && loginUser.getPosition_id() != 1){
						messages.add("権限がありません");
						session.setAttribute("errorMessages", messages);
						((HttpServletResponse) response).sendRedirect("./");
						return;
					}



					User user = new UserService().getUser(loginUser.getId());
					if(user.getIs_working() == 1){
						messages.add( "ログインしてください");
						session.setAttribute("errorMessages", messages);
						((HttpServletResponse) response).sendRedirect("./login");
						return;
					}

				}


			chain.doFilter(request, response);
		}


}
