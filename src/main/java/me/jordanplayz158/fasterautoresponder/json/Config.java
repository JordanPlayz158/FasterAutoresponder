package me.jordanplayz158.fasterautoresponder.json;

import com.google.gson.JsonObject;
import me.jordanplayz158.fasterautoresponder.Main;
import me.jordanplayz158.utils.LoadJson;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;

public class Config {
    private JsonObject json;

    public Config() {
        this.json = LoadJson.linkedTreeMap(Main.getInstance().getConfigFile());
    }

    public JsonObject getJson() {
        return json;
    }

    public String getPrefix() {
        return json.get("prefix").getAsString();
    }

    public JsonObject getActivity() {
        return json.getAsJsonObject("activity");
    }

    public String getActivityName() {
        return getActivity().get("name").getAsString();
    }

    public Activity.ActivityType getActivityType() {
        return Activity.ActivityType.valueOf(getActivity().get("type").getAsString().toUpperCase());
    }

    public JsonObject getChannels() {
        return json.getAsJsonObject("channels");
    }

    public JsonObject getLogChannel() {
        return getChannels().get("log").getAsJsonObject();
    }

    public long getLogChannelId() {
        return getLogChannel().get("id").getAsLong();
    }

    public String getLogChannelOption() {
        return getLogChannel().get("option").getAsString();
    }

    public long getRulesChannel() {
        return getChannels().get("rules").getAsLong();
    }

    public JsonObject getWarn() {
        return json.getAsJsonObject("warn");
    }

    public String getDetectionMessage() {
        return getWarn().get("message").getAsString();
    }

    public String getStaffWarnMessage() {
        return getWarn().get("staffMessage").getAsString();
    }

    public long getWarnRole() {
        return getWarn().get("role").getAsLong();
    }

    public List<?> getCopypastas() {
        return LoadJson.array(Main.getInstance().getCopypastasFile(), "detectionStrings");
    }
}