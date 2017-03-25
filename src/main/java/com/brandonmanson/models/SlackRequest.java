package com.brandonmanson.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

/**
 * Created by brandonmanson on 3/16/17.
 */
@Component
public class SlackRequest {
    private String teamId;
    private String teamDomain;
    private String channelId;
    private String channelName;
    private String userId;
    private String userName;
    private String command;
    private String text;
    private String responseUrl;

    public SlackRequest(){};

    public SlackRequest(WebRequest request) {
        this.teamId = request.getParameter("team_id");
        this.teamDomain = request.getParameter("team_domain");
        this.channelId = request.getParameter("channel_id");
        this.channelName = request.getParameter("channel_name");
        this.userId = request.getParameter("user_id");
        this.userName = request.getParameter("user_name");
        this.command = request.getParameter("command");
        this.text = request.getParameter("text");
        this.responseUrl = request.getParameter("response_url");
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamDomain() {
        return teamDomain;
    }

    public void setTeamDomain(String teamDomain) {
        this.teamDomain = teamDomain;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String[] generateSwaggerValues() {
        String[] swaggerValues = text.split(" ");
        return swaggerValues;
    }

    @Override
    public String toString() {
        String slackRequest = "teamId: " + teamId
                + "\nteamDomain: " + teamDomain
                + "\nchannel_id: " + channelId
                + "\nchannelName: " + channelName
                + "\nuserId: " + userId
                + "\nuserName: " + userName
                + "\ncommand: " + command
                + "\ntext: " + text
                + "\nresponseUrl: " + responseUrl;
        return slackRequest;
    }
}
