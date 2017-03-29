package com.brandonmanson.services;

import com.brandonmanson.models.SlackRequest;
import io.keen.client.java.KeenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandonmanson on 3/29/17.
 */
@Service
public class AnalyticsReportingService {
    @Value("${keen.project.id}")
    private String projectId;

    @Value("${keen.write.key}")
    private String writeKey;

    @Value("${swaggy.dev.channel.id}")
    private String swaggyDevChannelId;

    public void track(SlackRequest request) {
        if (!request.getChannelId().equals(swaggyDevChannelId))
        {
            Map<String, Object> event = new HashMap<String, Object>();
            event.put("channel", request.getChannelId());
            KeenClient.client().addEvent("prod_specs", event);
        }
    }

}
