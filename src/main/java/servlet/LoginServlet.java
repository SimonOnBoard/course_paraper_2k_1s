package servlet;

import com.google.gson.Gson;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import dao.UsersAuthRepository;
import dao.UsersRepository;
import model.User;
import model.UserAuthParametr;
import service.LoginValidator;
import service.PasswordEncripter;
import service.StringEncriptor;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.websocket.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsersRepository usersRepository;
    private UsersAuthRepository usersAuthRepository;
    @Override
    public void init() throws ServletException {
        usersRepository = new UsersRepository();
        usersAuthRepository = new UsersAuthRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> user = usersRepository.find(name);
        HttpSession session = req.getSession();
        List<String> errors= LoginValidator.validate(name,password,user);
        Map<String, Object> data = new HashMap<>();
        if(errors.size() == 0){
            session.setAttribute("user",user.get().getId());
            String addToCookie = StringEncriptor.getEncryptedString(name + password);
            this.saveAuthParam(user.get(),addToCookie);
            Cookie authParam = this.getAuthCookie(addToCookie);
            resp.addCookie(authParam);
            data.put("redirect","/home");
        }
        else{
            data.put("errors", errors);
        }
        String json = new Gson().toJson(data);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private void saveAuthParam(User user, String addToCookie) {
        this.usersAuthRepository.save(new UserAuthParametr(addToCookie, user.getId()));
    }

    private Cookie getAuthCookie(String value) {
        Cookie cookie = new Cookie("abrakadabra", value);
        cookie.setMaxAge(-1);
        return cookie;
    }

}
