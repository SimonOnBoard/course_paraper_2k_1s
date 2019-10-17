package servlet;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import dao.UsersRepository;
import model.User;
import service.PasswordEncripter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsersRepository usersRepository;
    @Override
    public void init() throws ServletException {
        usersRepository = new UsersRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = usersRepository.find(name).get();
        HttpSession session = req.getSession();
        if(checkLoginandPass(name,password,user)){
            session.setAttribute("user",name);
            resp.sendRedirect("/home");
        }
        else{
            req.getServletContext().getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req,resp);
        }
    }

    private boolean checkLoginandPass(String name, String password, User user) {
        password = PasswordEncripter.getPass(password);
        return (user.getName().equals(name) && user.getPassword().equals(password));
    }
}
