package jp.co.sss.spring_test.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		// セッションが存在しない or loginUserが未設定 → ログイン画面へリダイレクト
		if (session == null || session.getAttribute("loginUser") == null) {
			String uri = request.getRequestURI();
			// ログイン画面・登録画面・静的リソースは除外
			if (uri.contains("/user/userLogin") ||
				uri.contains("/user/userRegistration") ||
				uri.contains("/css/") ||
				uri.contains("/js/") ||
				uri.contains("/images/")) {
				return true; // 通過OK
			}
			// それ以外はログインページへリダイレクト
			response.sendRedirect(request.getContextPath() + "/user/userLogin");
			return false;
		}
		// ログイン済みならそのまま通過
		return true;
	}
}