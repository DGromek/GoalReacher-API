package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomInvitationsUserSerializer extends StdSerializer<AppUser>
{
    protected CustomInvitationsUserSerializer(Class<AppUser> t)
    {
        super(t);
    }

    public CustomInvitationsUserSerializer()
    {
        this(null);
    }

    @Override
    public void serialize(AppUser appUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        AppUser serialized = new AppUser();

        serialized.setEmail(appUser.getEmail());
        serialized.setFirstName(appUser.getFirstName());
        serialized.setLastName(appUser.getLastName());

        jsonGenerator.writeObject(serialized);
    }
}
