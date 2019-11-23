package dao.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import org.elasticsearch.client.Client;

import java.util.List;

public interface PostRepository extends CrudDao<Post> {
    public List<Post> findAllByCategory(String category,Long offset);
    public void saveToElastic(Post model, Client client, ObjectMapper objectMapper);
}
