package dao.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import org.elasticsearch.client.Client;

import java.util.List;

public interface PostRepository extends CrudDao<Post> {
    public List<Post> findAllByCategory(String category,Long offset);
    public List<Post> findAllByAuthorId(Long id);
    public void saveToElastic(Post model, Client client, ObjectMapper objectMapper);
    public void updateInElastic(Post model, Client client, ObjectMapper objectMapper);
    public void deleteInElastic(Post model, Client client, ObjectMapper objectMapper);

    List<Post> findAllWithNameByQuery(String query);

    List<Post> findAllWithNameByQueryAndCategory(String query, String value);

    List<Post> findAllWithTextByQuery(String query);

    List<Post> findAllWithTextByQueryAndCategory(String query, String value);
}
