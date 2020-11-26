package pl.politechnika.goalreacher.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomInvitationsSerializer extends StdSerializer<AppUser>
{
    protected CustomInvitationsSerializer(Class<AppUser> t)
    {
        super(t);
    }

    public CustomInvitationsSerializer()
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
        serialized.setInvitesGot(appUser.getInvitesGot());
        serialized.setInvitesSent(appUser.getInvitesSent());

        List<UserGroup> groups = new ArrayList<>();
        for(UserGroup g : appUser.getGroups())
        {
            g.setUser(null);
            g.getGroup().setUsers(null);
            groups.add(g);
        }

        serialized.setGroups(groups);

        jsonGenerator.writeObject(serialized);
    }
}
