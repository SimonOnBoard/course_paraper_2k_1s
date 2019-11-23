package servlet;

import dao.PostRepositoryImpl;
import dao.interfaces.PostRepository;
import model.Category;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
@MultipartConfig
@WebServlet("/changePost/*")
public class PostChangerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("post_id"));
        String[] strings = req.getHeader("referer").split("/");
        Optional<Post> post = postDao.find(id);
        if (post.isPresent()) {
            Post post1 = post.get();
            req.setAttribute("post", post1);
            req.setAttribute("path",strings[strings.length - 1]);
            ArrayList<Category> enumValues = new ArrayList<>(Arrays.asList(Category.values()));
            enumValues.remove(post1.getCategory());
            req.setAttribute("categoryA",post1.getCategory());
            req.setAttribute("categories",enumValues);
            req.getServletContext().getRequestDispatcher("/WEB-INF/templates/changePost.ftl").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        preparePostAndSave(req,resp, session);
        String path = req.getParameter("path");
        resp.sendRedirect("/" + path);
    }

    private void preparePostAndSave(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException, ServletException {
        Long id = Long.valueOf(req.getParameter("post_id"));
        Optional<Post> post = postDao.find(id);
        //There is some kind of ТТУК, сервисы мы пока красиво не пишем))
        if(post.isPresent()){
            Post post1 = post.get();
            post1.setName(req.getParameter("name"));
            post1.setText(req.getParameter("text"));
            String checkBox = req.getParameter("showAuth");
            post1.setShowAuthor(false);
            if(checkBox != null){
                post1.setShowAuthor(true);
            }
            Part p = req.getPart("photo");
            String photoPath = post1.getPhotoPath();
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
            post1.setPhotoPath(photoPath);
            postDao.update(post1);
        }
        else{
            throw new IllegalStateException("Пробуем сохранить пост, которого нет в бд");
        }
    }

    private PostRepository postDao;
    @Override
    public void init() throws ServletException {
        this.postDao = new PostRepositoryImpl();
    }
}
