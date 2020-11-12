package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.UserGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomUserSerializer extends StdSerializer<List<UserGroup>>
{
    public CustomUserSerializer()
    {
        this(null);
    }

    public CustomUserSerializer(Class<List<UserGroup>> t)
    {
        super(t);
    }

    @Override
    public void serialize(List<UserGroup> user_s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        List<UserGroup> groups = new ArrayList<>();
        for (UserGroup g : user_s)
        {
            g.setUser(null);
            g.getGroup().setUsers(null);
            groups.add(g);
        }
        jsonGenerator.writeObject(groups);
    }
}
