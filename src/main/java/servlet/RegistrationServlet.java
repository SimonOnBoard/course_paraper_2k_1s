package servlet;

import dao.oldDaoWithoutInterfaces.UsersRepository;
import model.User;
import service.RegistrationValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
@MultipartConfig
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cur_date", new Date(System.currentTimeMillis()));
        req.getRequestDispatcher("/WEB-INF/templates/registration.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Date birth = Date.valueOf(req.getParameter("birth"));
        String nick = req.getParameter("user_name");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String mail = req.getParameter("email");
        List<String> errors = registrationValidator.validate(mail, password, nick, name);
        if (errors.isEmpty()) {
            Part p = req.getPart("photo");
            String localdir = "avatars";
            String pathDir = getServletContext().getRealPath("") + File.separator + localdir;
            File dir = new File(pathDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String[] filename_data = p.getSubmittedFileName().split("\\.");
            String filename = Math.random() + "." + filename_data[filename_data.length - 1];
            String fullpath = pathDir + File.separator + filename;
            p.write(fullpath);
            String photoPath = ("/" + localdir + "/" + filename);
            usersRepository.save(new User(name, password, nick, mail, birth,
                    LocalDateTime.now(), (Long)0L, photoPath));
            resp.sendRedirect("/login");
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("cur_date", birth);
            req.getRequestDispatcher("/WEB-INF/templates/registration.ftl").forward(req, resp);
        }
    }

    private RegistrationValidator registrationValidator;
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        this.registrationValidator = new RegistrationValidator();
        this.usersRepository = new UsersRepository();
    }
}
