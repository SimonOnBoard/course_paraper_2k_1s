package dao;

import model.Category;
import model.Post;

import java.util.List;

public interface PostRepository extends CrudDao<Post> {
    public List<Post> findAllByCategory(String category,Long offset);
}