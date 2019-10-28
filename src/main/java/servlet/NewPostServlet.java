package servlet;

import dao.PostRepository;
import dao.PostRepositoryImpl;
import model.Category;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@MultipartConfig
@WebServlet("/newPost")
public class NewPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Enum> enumValues = Arrays.asList(Category.values());
        req.setAttribute("categories",enumValues);
        req.getRequestDispatcher("/WEB-INF/templates/postForm.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        preparePostToSave(req,resp, session);
    }

    private void preparePostToSave(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Post post = new Post();
        post.setPublication(LocalDateTime.now());
        post.setAuth_id((Long) session.getAttribute("user"));
        post.setName(req.getParameter("name"));
        post.setText(req.getParameter("text"));
        post.setCategory(Category.valueOf(req.getParameter("categories")));
        String checkBox = req.getParameter("showAuth");
        post.setShowAuthor(false);
        if(checkBox != null){
            post.setShowAuthor(true);
        }
        Part p = req.getPart("photo");
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
        post.setPhotopath("/" + localdir + "/" + filename);
        postRepository.save(post);
    }
    private PostRepository postRepository;
    @Override
    public void init() throws ServletException {
        postRepository = new PostRepositoryImpl();
    }
}
