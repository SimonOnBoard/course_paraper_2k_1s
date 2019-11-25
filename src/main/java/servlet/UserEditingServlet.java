package servlet;

import dao.oldDaoWithoutInterfaces.UsersRepository;
import model.User;
import service.LoginValidator;
import service.PasswordEncripter;
import service.RegistrationValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@MultipartConfig
@WebServlet("/changeProfile/*")
public class UserEditingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("user_id"));
        String[] strings = req.getHeader("referer").split("/");
        Optional<User> user = usersRepository.findByIdForRegistration(id);
        if (user.isPresent()) {
            User user1 = user.get();
            HttpSession session = req.getSession();
            List<String> errors = (List<String>) session.getAttribute("errors");
            if(errors != null){
                session.removeAttribute("errors");
                req.setAttribute("errors", errors);
            }
            req.setAttribute("user", user1);
            req.setAttribute("path",strings[strings.length - 1]);
            req.getServletContext().getRequestDispatcher("/WEB-INF/templates/changeProfile.ftl").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long id = Long.valueOf(req.getParameter("user_id"));
        Optional<User> user = usersRepository.findById(id);
        List<String> errors = new ArrayList<>();
        if(user.isPresent()){
            User user1 = user.get();
            errors.addAll(LoginValidator.validate(user1.getName(),req.getParameter("old_password"),user));
            if(checkErrors(resp,session,errors,id)){
                return;
            }
            errors.addAll(validator.validate(req.getParameter("email"),req.getParameter("password"),
                    req.getParameter("nick"),"loremipsum",user1.getBirth_date().toString()));
            if(checkErrors(resp,session,errors,id)){
                return;
            }
            user1.setEmail(req.getParameter("email"));
            user1.setNick(req.getParameter("nick"));
            user1.setPassword(PasswordEncripter.getEncryptedString(req.getParameter("password")));
            Part p = req.getPart("photo");
            String photoPath = user1.getPhotoPath();
            if (p.getSize() != 0) {
                String localdir = "" + session.getAttribute("user");
                String pathDir = getServletContext().getRealPath("") + File.separator + localdir;
                File dir = new File(pathDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String[] filename_data = p.getSubmittedFileName().split("\\.");
                String filename = Math.random() + "." + filename_data[filename_data.length - 1];
                String fullpath = pathDir + File.separator + filename;
                p.write(fullpath);
                photoPath = ("/" + localdir + "/" + filename);
            }
            user1.setPhotoPath(photoPath);
            usersRepository.update(user1);
            String path = req.getParameter("path");
            resp.sendRedirect("/home");
        }
        else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean checkErrors(HttpServletResponse resp, HttpSession session, List<String> errors, Long id) throws IOException {
        if(errors.size()!=0){
            session.setAttribute("errors",errors);
            resp.sendRedirect("/changeProfile?user_id=" + id);
            return true;
        }
        return false;
    }


    private UsersRepository usersRepository;
    private RegistrationValidator validator;
    @Override
    public void init() throws ServletException {
        this.usersRepository = new UsersRepository();
        this.validator = new RegistrationValidator();
    }
}
