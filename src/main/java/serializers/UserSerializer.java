package serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.Post;
import model.User;

import java.io.IOException;
import java.sql.Timestamp;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", user.getId().longValue());
        jgen.writeStringField("nick", user.getNick());
        jgen.writeStringField("email", user.getEmail());
        jgen.writeStringField("birth",user.getBirth_date().toString());
        jgen.writeStringField("photoPath",user.getPhotoPath());
        jgen.writeEndObject();
    }
}
