package servlet;

import dao.PostRepository;
import dao.PostRepositoryImpl;
import model.Category;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/m")
public class SearchAbstraction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Enum> enumValues = Arrays.asList(Category.values());
        req.setAttribute("categories",enumValues);
        req.getRequestDispatcher("/WEB-INF/templates/main.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("categories");
        List<Post> posts = postRepository.findAllByCategory(value, 0L );
    }
    private PostRepository postRepository;
    @Override
    public void init() throws ServletException {
        this.postRepository = new PostRepositoryImpl();
    }
}
