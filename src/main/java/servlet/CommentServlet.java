package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CommentDaoImpl;
import dao.interfaces.CommentDao;
import dao.oldDaoWithoutInterfaces.UsersRepository;
import dto.UserDto;
import model.Comment;
import model.User;
import service.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        Long postId = Long.parseLong(req.getParameter("post_id"));
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("user");
        Optional<UserDto> user = usersRepository.findDtoById(id);
        if (user.isPresent()) {
            Comment comment = new Comment(text, id, postId, LocalDateTime.now());
            commentDao.save(comment);
            //UserDto dto = (Commentreq.getServletContext().getAttribute("commentLoader").
        } else {
            throw new IllegalStateException("Пользователь, ненайденный в базе пытается сохранить коммент");
        }
    }

    private CommentDao commentDao;
    private UsersRepository usersRepository;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.commentDao = new CommentDaoImpl();
        this.usersRepository = new UsersRepository();
        this.objectMapper = new ObjectMapper();
    }
}
