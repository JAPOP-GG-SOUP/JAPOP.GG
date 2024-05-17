package soup.japopgg.searchSummoner;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getDateTimeFormat(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }
}
