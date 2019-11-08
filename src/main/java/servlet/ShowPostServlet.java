package servlet;

import dao.PostRepository;
import dao.PostRepositoryImpl;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/showPost/*")
public class ShowPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String n1 = req.getParameter("id");
        Long id = Long.parseLong(n1);
        Optional<Post> post = postRepository.find(id);
        if(post.isPresent()){
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
    @Override
    public void init() throws ServletException {
        this.postRepository = new PostRepositoryImpl();
    }
}
