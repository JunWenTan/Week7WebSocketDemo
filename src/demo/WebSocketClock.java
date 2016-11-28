package demo;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 153907N on 11/28/2016.
 */
public class WebSocketClock {
    static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:MM:SS");
    @OnOpen
    public void showTime(Session session) {
        System.out.println("Opened : " + session.getId());
        timer.scheduleAtFixedRate(
                () -> sendTime(session), 0, 1, TimeUnit.SECONDS);

    }

    private void sendTime(Session session){
        try {
            String data = "Time " + LocalTime.now().format(timeFormatter);
            session.getBasicRemote().sendText(data);
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
