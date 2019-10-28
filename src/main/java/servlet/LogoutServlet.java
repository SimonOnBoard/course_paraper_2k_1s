package servlet;

import dao.UsersAuthRepository;
import dao.UsersRepository;
import model.User;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.print.attribute.standard.NumberUp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession();
        Long name = (Long) session.getAttribute("user");
        if(name != null){
            Optional<User> user = usersRepository.findById((Long) session.getAttribute("user"));
            if(user.isPresent()){
                usersAuthRepository.delete(user.get().getId());
            }
            else{
                throw new IllegalStateException("Пользователя нет в бд, ищи SQLexception или смотри упала ли БД");
            }
        }
        else{
            throw new IllegalStateException("Неавторизованый пользователь запросил логаут");
        }

        session.removeAttribute("user");
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession();
        Long name = (Long) session.getAttribute("user");
        if(name != null){
            Optional<User> user = usersRepository.findById((Long) session.getAttribute("user"));
            if(user.isPresent()){
                usersAuthRepository.delete(user.get().getId());
            }
            else{
                throw new IllegalStateException("Пользователя нет в бд, ищи SQLexception или смотри упала ли БД");
            }
        }
        else{
            throw new IllegalStateException("Неавторизованый пользователь запросил логаут");
        }

        session.removeAttribute("user");
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
    }

    private UsersRepository usersRepository;
    private UsersAuthRepository usersAuthRepository;
    @Override
    public void init() throws ServletException {
        usersRepository = new UsersRepository();
        usersAuthRepository = new UsersAuthRepository();
    }
}
