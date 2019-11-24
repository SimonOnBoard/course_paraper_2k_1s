package servlet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.PostRepository;
import dao.PostRepositoryImpl;
import model.Category;
import model.Post;
import model.User;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/mainSearch")
public class SearchAbstraction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Enum> enumValues = Arrays.asList(Category.values());
        List<Post> posts = postRepository.findAllByCategory("Post", 0L );
        req.setAttribute("categories",enumValues);
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("/WEB-INF/templates/main.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        if(!"".equals(query)){
            Map<String,Object> data = new HashMap<>();
            List<?> objects = searchService.searchByInput(req);
            if(objects.size() != 0){
                if(objects.get(0) instanceof User){
                    data.put("users", objects);
                    data.put("type", "user");
                }
                else{
                    data.put("posts",objects);
                    data.put("type", "post");
                }
            }
            else{
                data.put("posts",objects);
                data.put("type", "post");
            }
            String json = objectMapper.writeValueAsString(data);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
        else{
            String value = req.getParameter("categories");
            Map<String,Object> data = new HashMap<>();
            String entity = req.getParameter("entity");
            if(entity.equals("post")) {
                List<Post> posts = postRepository.findAllByCategory(value, 0L);
                data.put("posts", posts);
                data.put("type", "post");
            }
            else{
                List<User> users = searchService.findAllUsers();
                data.put("users", users);
                data.put("type", "user");
            }
            String json = objectMapper.writeValueAsString(data);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }
    private PostRepository postRepository;
    private ObjectMapper objectMapper;
    private SearchService searchService;
    @Override
    public void init() throws ServletException {
        this.searchService = new SearchService();
        this.postRepository = new PostRepositoryImpl();
        //маппер плохо работает с многопоточностью, поэтому внутри
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }
}
