package jp.co.kke.sendgrid.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SendGridのEvent Webhookデータのモデル.
 * 
 * @author kikuta
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String sg_message_id;
    private String email;
    private String event;
    private String timestamp;
    
    public Date retriveTimestamp() {
        return new Date(Long.parseLong(timestamp) * 1000);
    }
}
