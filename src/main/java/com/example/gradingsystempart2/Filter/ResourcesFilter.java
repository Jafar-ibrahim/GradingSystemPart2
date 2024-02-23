package com.example.gradingsystempart2.Filter;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

public class ResourcesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();

        Boolean isLoggedIn = (Boolean) request.getSession().getAttribute("logged_in");
        String role = (String) request.getSession().getAttribute("role");

        if (isLoggedIn == null || !isLoggedIn) {
            response.sendRedirect(request.getContextPath()+"/views/index.jsp");
            return;
        } else {
            boolean isAdminResource = path.contains("admin");
            boolean isInstructorResource = path.contains("instructor");
            boolean isStudentResource = path.contains("student");
            if (((isAdminResource || isInstructorResource) && role.equals("student"))
                    ||((isStudentResource || isAdminResource) && role.equals("instructor"))) {
                request.setAttribute("error","Resource access denied");
                response.sendRedirect(request.getContextPath()+"/views/index.jsp");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
