package filter;

import dao.UsersAuthRepository;
import dao.UsersRepository;
import model.User;
import model.UserAuthParametr;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import javax.xml.ws.ResponseWrapper;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns ={"/home","/logout","/newPost"}, filterName="filter2")
public class HomeFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession session = request.getSession();
            if (session == null || session.getAttribute("user") == null) {
                servletRequest.getServletContext().getRequestDispatcher("/login").forward(request, response);
            }
        filterChain.doFilter(request,response);
    }

    public void destroy() {

    }
}
