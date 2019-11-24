package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.PostRepository;
import dao.PostRepositoryImpl;
import model.Category;
import model.Post;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        Post post = preparePostToSave(req,resp, session);

        req.setAttribute("id", post.getId());
        req.getServletContext().getRequestDispatcher("/showPost").forward(req,resp);
    }

    private Post preparePostToSave(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException, ServletException {
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
        post.setPhotoPath("/" + localdir + "/" + filename);
        postRepository.save(post);
        //Я решил не пытаться дальше с нереляционной базой данных, потому что
        // все виды нужных для сайта реквестов я попробовал
        // но оставшихся требований больше по баллам и эта фича не стоит того
        //думаю отхожу к Якупову на курс по нереляционным и будет веселее

//        postRepository.saveToElastic(post, (Client) this.getServletContext().getAttribute("client"),objectMapper);
        return post;
    }
    private PostRepository postRepository;
    private ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        postRepository = new PostRepositoryImpl();
        this.objectMapper = new ObjectMapper();
        try {
            this.getServletContext().setAttribute("client",new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300)));
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }
}
