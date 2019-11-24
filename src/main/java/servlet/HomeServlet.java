package servlet;

import dao.oldDaoWithoutInterfaces.UsersRepository;
import model.User;
import service.CommentLoader;
import service.TimeConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/home/*")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("user");
        String n1 = req.getParameter("id");
        User user = null;
        if(n1 != null){
            Optional<User> user1 = usersRepository.findById(Long.parseLong(n1));
            if(user1.isPresent()){
                req.setAttribute("viewerId",id);
                req.setAttribute("viewBirth","true");
                req.setAttribute("curr_user", user1.get());
                req.setAttribute("timeOnBoard", TimeConverter.getUsersTimeOnSite(user1.get()    .getRegiStrationDate()));
            }
            else{
                req.setAttribute("error_wrong_user","No user avaliable for this id");
            }
        }
        else{
            user = usersRepository.findById(id).get();
            //Изначально планировалась фича с показом даты рождения, но в итоге из-за сроков её пришлось убрать
            req.setAttribute("viewerId",id);
            req.setAttribute("viewBirth","true");
            req.setAttribute("curr_user", user);
            req.setAttribute("timeOnBoard", TimeConverter.getUsersTimeOnSite(user.getRegiStrationDate()));
        }
        req.getServletContext().getRequestDispatcher("/WEB-INF/templates/profile.ftl").forward(req,resp);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    private UsersRepository usersRepository;
    @Override
    public void init() throws ServletException {
        this.usersRepository = new UsersRepository();

        this.getServletContext().setAttribute("commentLoader",new CommentLoader());

    }
}
