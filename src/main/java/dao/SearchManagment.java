package dao;

import model.Post;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;

public class SearchManagment {
    public synchronized List<Post> findPosts(String[] params, TransportClient client){
        return null;
    }
}
