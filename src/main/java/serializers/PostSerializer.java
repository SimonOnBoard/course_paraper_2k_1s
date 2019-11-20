package serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.Post;

import java.io.IOException;
import java.sql.Timestamp;

public class PostSerializer extends StdSerializer<Post> {

    public PostSerializer() {
        this(null);
    }

    public PostSerializer(Class<Post> t) {
        super(t);
    }

    @Override
    public void serialize(Post post, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", post.getId().intValue());
        jgen.writeStringField("name", post.getName());
        jgen.writeStringField("text", post.getText());
        jgen.writeStringField("category",post.getCategory().toString());
        jgen.writeStringField("photoPath",post.getPhotoPath());
        jgen.writeNumberField("time", Timestamp.valueOf(post.getPublication()).getTime());
        jgen.writeBooleanField("showAuthor", post.getShowAuthor().booleanValue());
        jgen.writeNumberField("auth_id", post.getAuth_id().longValue());
        jgen.writeEndObject();
    }
}
