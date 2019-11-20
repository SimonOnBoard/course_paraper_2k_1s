package dao.interfaces;

import model.Comment;

import java.util.List;

public interface CommentDao extends CrudDao<Comment>{
    List<Comment> findAllByOwnerId(Long id);
    List<Comment> findAllByPostId(Long id);
}
