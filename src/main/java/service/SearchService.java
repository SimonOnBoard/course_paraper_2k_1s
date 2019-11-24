package service;

import dao.PostRepositoryImpl;
import dao.interfaces.PostRepository;
import dao.oldDaoWithoutInterfaces.UsersRepository;
import model.Post;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchService {
    private PostRepository postRepository;
    private UsersRepository usersRepository;
    public SearchService() {
        this.postRepository = new PostRepositoryImpl();
        this.usersRepository = new UsersRepository();
    }

    public List<?> searchByInput(HttpServletRequest req) {
        String value = req.getParameter("categories");
        String query = req.getParameter("query");
        String object = req.getParameter("entity");
        String use_c = req.getParameter("use_c");
        String where = req.getParameter("where");
        if (object.equals("post")) {
            if (where.equals("name")) {
                if ("".equals(use_c)) {
                    List<Post> posts = postRepository.findAllWithNameByQuery(query);
                    return posts;
                } else {
                    List<Post> posts = postRepository.findAllWithNameByQueryAndCategory(query, value);
                    return posts;
                }
            } else {
                if ("".equals(use_c)) {
                    List<Post> posts = postRepository.findAllWithTextByQuery(query);
                    return posts;
                } else {
                    List<Post> posts = postRepository.findAllWithTextByQueryAndCategory(query, value);
                    return posts;
                }
            }
        } else {
            List<User> users = usersRepository.findInNickByQuery(query);
            return users;
        }
    }

    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }
}
