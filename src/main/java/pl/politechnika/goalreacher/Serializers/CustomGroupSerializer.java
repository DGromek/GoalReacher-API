package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.UserGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomGroupSerializer extends StdSerializer<List<UserGroup>>
{
    public CustomGroupSerializer()
    {
        this(null);
    }

    public CustomGroupSerializer(Class<List<UserGroup>> t)
    {
        super(t);
    }

    @Override
    public void serialize(List<UserGroup> group_s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        List<UserGroup> groups = new ArrayList<>();
        for (UserGroup g : group_s)
        {
            g.setGroup(null);
            g.getUser().setGroups(null);
            groups.add(g);
        }
        jsonGenerator.writeObject(groups);
    }
}
