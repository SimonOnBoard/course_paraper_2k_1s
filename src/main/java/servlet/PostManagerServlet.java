package servlet;

import dao.PostRepositoryImpl;
import dao.interfaces.PostRepository;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@WebServlet("/posts")
public class PostManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("user");
        List<Post> posts = postDao.findAllByAuthorId(id);
        req.setAttribute("posts",posts);
        req.getRequestDispatcher("/WEB-INF/templates/posts.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("post_id"));
        postDao.delete(id);
        resp.sendRedirect("/posts");
    }

    private PostRepository postDao;
    @Override
    public void init() throws ServletException {
        this.postDao = new PostRepositoryImpl();
    }
}
