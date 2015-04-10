package com.quantasnet.qlan2.steam;

import java.io.Serializable;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public class SteamProfile implements Serializable {

    private static final long serialVersionUID = 3534647337121995251L;

    private String imageUrl;
    private int onlineState;
    private String realName;
    private String nickname;
    private long steamId64;
    private String gameName;

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getOnlineState() {
        return onlineState;
    }
    public void setOnlineState(int onlineState) {
        this.onlineState = onlineState;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public long getSteamId64() {
        return steamId64;
    }
    public void setSteamId64(long steamId64) {
        this.steamId64 = steamId64;
    }
    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
