package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.AppGroup;

import java.io.IOException;
import java.security.acl.Group;

public class CustomInvitationsGroupSerializer extends StdSerializer<AppGroup>
{
    public CustomInvitationsGroupSerializer(Class<AppGroup> t)
    {
        super(t);
    }

    public CustomInvitationsGroupSerializer()
    {
        this(null);
    }


    @Override
    public void serialize(AppGroup appGroup, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        AppGroup serialised = new AppGroup();

        serialised.setId(appGroup.getId());
        serialised.setName(appGroup.getName());
        serialised.setDescription(appGroup.getDescription());

        jsonGenerator.writeObject(serialised);
    }
}
