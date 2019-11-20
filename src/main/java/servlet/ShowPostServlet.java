package servlet;

import dao.interfaces.PostRepository;
import dao.PostRepositoryImpl;
import dto.UserDto;
import javafx.util.Pair;
import model.Comment;
import model.Post;
import service.CommentLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/showPost/*")
public class ShowPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String n1 = req.getParameter("id");
        Long id = Long.parseLong(n1);
        Optional<Post> post = postRepository.find(id);
        if(post.isPresent()){
            List<Pair<Comment, UserDto>> comments = commentLoader.getComments(id);
            req.setAttribute("comments",comments);
            req.setAttribute("post",post.get());
            req.getServletContext().getRequestDispatcher("/WEB-INF/templates/showPost.ftl").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = (Long) req.getAttribute("id");
        Optional<Post> post = postRepository.find(id);
        if(post.isPresent()){
            req.setAttribute("post",post.get());
            req.getServletContext().getRequestDispatcher("/WEB-INF/templates/showPost.ftl").forward(req,resp);
        }
    }
    private PostRepository postRepository;
    private CommentLoader commentLoader;
    @Override
    public void init() throws ServletException {
        this.commentLoader = new CommentLoader();
        this.postRepository = new PostRepositoryImpl();
        this.getServletContext().setAttribute("commentLoader",commentLoader);
    }
}
