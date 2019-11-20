package filter;


import dao.oldDaoWithoutInterfaces.UsersAuthRepository;
import dao.oldDaoWithoutInterfaces.UsersRepository;
import model.User;
import model.UserAuthParametr;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"/home/*","/newPost/*","/showPost/*"}, filterName="filter1")
public class AuthCookieFilter implements Filter {
    private UsersAuthRepository authRepository;
    private UsersRepository usersRepository;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.authRepository = new UsersAuthRepository();
        this.usersRepository = new UsersRepository();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession session = request.getSession();

        this.checkCookies(request.getCookies(),session,response, request);

        filterChain.doFilter(request,response);
    }
    private void checkCookies(Cookie[] cookies, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("abrakadabra")) {
                    Optional<UserAuthParametr> parametr = authRepository.findByPatametr(cookie.getValue());
                    if (parametr.isPresent()) {
                        Optional<User> user = usersRepository.findById(parametr.get().getOwnerId());
                        if (user.isPresent()) {
                            if(session.getAttribute("user") == null) {
                                session.setAttribute("user", user.get().getId());
                            }
                            break;
                            //request.getServletContext().getRequestDispatcher("/home").forward(request,response);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
