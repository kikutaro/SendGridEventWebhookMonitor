package jp.co.kke.sendgrid.monitor;

import com.google.gson.Gson;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import jp.co.kke.sendgrid.model.Event;

/**
 * SendGridのメール送信状況をモニタリングする管理Bean.
 * 
 * @author kikuta
 */
@Path("webhook")
@Named
@ApplicationScoped
public class MonitoringBean implements Serializable {
    
    @Inject
    @Push(channel = "sendgrid")
    private PushContext push;

    @GET
    public String ping() {
       return "pong";
    }

    @POST
    public void post(String body) {
        Gson gson = new Gson();
        Event[] events = gson.fromJson(body, Event[].class);
        monitor(events);
    }

    public void startMonitor(){
        push.send("start monitor");
    }
 
    public void monitor(Event[] events) {
        //thread unsafe
        SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        Arrays.stream(events).forEach(e -> 
                push.send(
                        df.format(e.retriveTimestamp()) + "｜" +
                        e.getEmail() + "｜" +
                        e.getEvent() + "｜" +
                        e.getSg_message_id()));
    }
}