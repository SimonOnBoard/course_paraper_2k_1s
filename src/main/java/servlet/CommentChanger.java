package servlet;

import dao.CommentDaoImpl;
import dao.interfaces.CommentDao;
import model.Comment;
import service.CommentLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletionException;

@WebServlet("/changeComment")
public class CommentChanger extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long id = Long.valueOf(req.getParameter("comment_id"));
        String path = req.getParameter("path");
        String text = req.getParameter("text");
        commentLoader.save(id,text);
        resp.sendRedirect(path);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] strings = req.getHeader("referer").split("/");
        Long id = Long.valueOf(req.getParameter("comment_id"));
        Optional<Comment> comment = commentDao.find(id);
        if(comment.isPresent()){
            req.setAttribute("path",strings[strings.length - 1]);
            req.setAttribute("comment",comment.get());
            req.getRequestDispatcher("/WEB-INF/templates/change_comment.ftl").forward(req, resp);
        }
        else{
            throw new IllegalStateException("Запрошен для изменения несуществующий комментарий");
        }
    }

    private CommentLoader commentLoader;
    private CommentDao commentDao;
    @Override
    public void init() throws ServletException {
        this.commentLoader = (CommentLoader) this.getServletContext().getAttribute("commentLoader");
        if(commentLoader == null){
            commentLoader = new CommentLoader();
        }
        this.commentDao = new CommentDaoImpl();
    }
}
