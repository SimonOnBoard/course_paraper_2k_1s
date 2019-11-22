package dao.interfaces;

import model.Comment;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

public interface CommentDao extends CrudDao<Comment>{
    List<Comment> findAllByOwnerId(Long id);
    List<Comment> findAllByPostId(Long id);

}
