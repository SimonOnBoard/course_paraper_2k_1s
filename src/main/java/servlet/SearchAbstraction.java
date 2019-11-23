package servlet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.PostRepository;
import dao.PostRepositoryImpl;
import model.Category;
import model.Post;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        req.setAttribute("categories",enumValues);
        req.getRequestDispatcher("/WEB-INF/templates/main.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String string = req.getParameter("query");
        if(string != null){
            String value = req.getParameter("categories");
            List<Post> posts = postRepository.findAllByCategory(value, 0L );
            Map<String,Object> data = new HashMap<>();
            data.put("posts",posts);
            String json = objectMapper.writeValueAsString(data);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
        else{
            String value = req.getParameter("categories");
            List<Post> posts = postRepository.findAllByCategory(value, 0L );
            Map<String,Object> data = new HashMap<>();
            data.put("posts",posts);
            String json = objectMapper.writeValueAsString(data);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }
    private PostRepository postRepository;
    private ObjectMapper objectMapper;
    private TransportClient client;
    @Override
    public void init() throws ServletException {
        try {
            this.client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
           throw new IllegalStateException(e);
        }
        this.postRepository = new PostRepositoryImpl();
        //маппер плохо работает с многопоточностью, поэтому внутри
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }
}
