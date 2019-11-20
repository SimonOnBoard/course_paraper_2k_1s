package service;

import dao.CommentDaoImpl;
import dao.interfaces.CommentDao;
import dao.oldDaoWithoutInterfaces.UsersRepository;
import dto.UserDto;
import javafx.util.Pair;
import model.Comment;
import model.User;

import java.util.*;

public class CommentLoader {
    private UsersRepository usersRepository;
    private CommentDao commentDao;

    public CommentLoader() {
        this.usersRepository = new UsersRepository();
        this.commentDao = new CommentDaoImpl();
    }

    public List<Pair<Comment, UserDto>> getComments(Long id) {
        List<Comment> comments = commentDao.findAllByPostId(id);
        Set<Long> userIds = new HashSet<>();
        comments.forEach(comment -> userIds.add(comment.getOwnerId()));
        Map<Long, UserDto> dtos = usersRepository.findAllDtoBySet(userIds);
        List<Pair<Comment, UserDto>> info = new ArrayList<>();
        comments.forEach(comment -> info.add(new Pair<>(comment, dtos.get(comment.getOwnerId()))));
        return info;
    }
}
