package org.rest.app.service;

import org.rest.app.database.DatabaseClass;
import org.rest.app.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProfileService {

    private Map<String, Profile> profiles = DatabaseClass.getProfiles();

    public ProfileService()
    {
        profiles.put("koushik", new Profile(1L, "koushik", "Koushik", "Kothagal"));
        profiles.put("rabeltre", new Profile(2L, "rabeltre", "Rafael", "Beltre"));
    }

    public  List<Profile> getProfiles()
    {
        return new ArrayList<Profile>(profiles.values());
    }

    public  Profile getProfile(String profileName)
    {
        return profiles.get(profileName);
    }
    public Profile addProfile(Profile profile)
    {
        profile.setId(profiles.size()+1);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile updateProfile(Profile profile) {
        if (profile.getProfileName() == null) {
            return null;
        }
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile removeProfile(String profileName) {
        return profiles.remove(profileName);
    }

}
