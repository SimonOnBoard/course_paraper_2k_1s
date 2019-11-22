package serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.Comment;

import java.io.IOException;
import java.sql.Timestamp;

public class CommentSerializer extends StdSerializer<Comment> {

    public CommentSerializer() {
        this(null);
    }

    public CommentSerializer(Class<Comment> t) {
        super(t);
    }

    @Override
    public void serialize(Comment comment, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", comment.getId().longValue());
        jgen.writeStringField("text", comment.getText());
        jgen.writeNumberField("post_id",comment.getPostId().longValue());
        jgen.writeStringField("time", comment.getDate().toString());
        jgen.writeNumberField("ownerId", comment.getOwnerId().longValue());
        jgen.writeEndObject();
    }
}