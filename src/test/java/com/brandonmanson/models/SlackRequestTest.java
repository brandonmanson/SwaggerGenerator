package com.brandonmanson.models;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by brandonmanson on 3/17/17.
 */
@RunWith(SpringRunner.class)
public class SlackRequestTest extends TestCase {
    SlackRequest request = new SlackRequest();

    @Before
    public void setup() {
        request.setToken("g123a4567b89");
        request.setTeamId("test_team_id");
        request.setTeamDomain("test_team_domain.slack.com");
        request.setChannelId("test_channel_id");
        request.setUserId("123456");
        request.setUserName("test");
        request.setCommand("/test");
        request.setText("text 1");
        request.setResponseUrl("https://hooks.slack.com/commands/123456");
    }

    @Test
    public void generateSwaggerValuesShouldHaveCorrectMembers() {
        System.out.println("generateSwaggerValues() should have the correct members in the returned array");
        String[] returnedArray = request.generateSwaggerValues();
        String[] expectedArray = { "text", "1" };
        Assert.assertArrayEquals(expectedArray, returnedArray);
    }


}
