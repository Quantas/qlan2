package com.quantasnet.qlan2.steam;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by andrewlandsverk on 4/15/15.
 */
@Component
public class SteamAPI {

    private static final Logger LOG = LoggerFactory.getLogger(SteamAPI.class);

    private static final String URL_PREFIX = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=";

    @Value("${steam.api.key}")
    private String steamApiKey;

    /*public SteamServer getSourceServer(final Server server) {
        try {
            final SourceServer sourceServer = new SourceServer(server.getHostname(), server.getPort());

            final Map<String, Object> serverInfo = sourceServer.getServerInfo();
            final int ping = sourceServer.getPing();

            final SteamServer steamServer = new SteamServer();
            steamServer.setPing(ping);
            steamServer.setCurrentPlayers((byte) serverInfo.get("numberOfPlayers"));
            steamServer.setMaxPlayers((byte) serverInfo.get("maxPlayers"));
            steamServer.setName((String) serverInfo.get("serverName"));
            return steamServer;

        } catch (Exception e) {
            LOG.error("Error in SteamAPI - {}", e.getMessage());
        }

        return null;
    }*/

    public SteamProfile getProfileForId(final long id) {
        try {
            final URL url = new URL(URL_PREFIX + steamApiKey + "&steamids=" + id);
            final URLConnection urlc = url.openConnection();
            final String responseJson = IOUtils.toString(urlc.getInputStream(), "UTF-8");

            final JSONObject wrapper = new JSONObject(responseJson);
            final JSONObject response = wrapper.getJSONObject("response");
            final JSONArray players = response.getJSONArray("players");
            final JSONObject player = players.getJSONObject(0);

            if (null != player) {
                final SteamProfile profile = new SteamProfile();

                profile.setOnlineState(getIntValueFromJson(player, "personastate"));
                profile.setImageUrl(getValueFromJson(player, "avatarfull"));
                profile.setNickname(getValueFromJson(player, "personaname"));
                profile.setRealName(getValueFromJson(player, "realname"));
                profile.setGameName(getValueFromJson(player, "gameextrainfo"));
                profile.setSteamId64(id);

                return profile;
            }
        } catch(final Exception e) {
            LOG.error("Error retrieving Steam Profile", e);
        }
        return null;
    }

    private String getValueFromJson(final JSONObject player, final String target) {
        try {
           return player.getString(target);
        } catch(final JSONException e) {
           return null;
        }
    }

    private int getIntValueFromJson(final JSONObject player, final String target) {
        try {
            return player.getInt(target);
        } catch(final JSONException e) {
            return 0;
        }
    }
}
