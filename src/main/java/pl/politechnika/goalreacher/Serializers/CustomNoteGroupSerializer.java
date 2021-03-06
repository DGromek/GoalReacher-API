package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.AppGroup;

import java.io.IOException;

public class CustomNoteGroupSerializer extends StdSerializer<AppGroup>
{
    public CustomNoteGroupSerializer(Class<AppGroup> t)
    {
        super(t);
    }

    public CustomNoteGroupSerializer()
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
        serialised.setGuid(appGroup.getGuid());
        serialised.setUsers(appGroup.getUsers());
        serialised.setNotes(null);

        jsonGenerator.writeObject(serialised);
    }
}
