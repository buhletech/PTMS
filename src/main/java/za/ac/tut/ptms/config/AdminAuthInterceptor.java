package za.ac.tut.ptms.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();

        // Admin login page is always public
        if (uri.equals("/admin/login") || uri.equals("/admin/login/")) {
            return true;
        }

        HttpSession session  = request.getSession(false);
        boolean     loggedIn = session != null
                && session.getAttribute("adminUser") != null;

        if (loggedIn) return true;

        response.sendRedirect(request.getContextPath() + "/admin/login");
        return false;
    }
}