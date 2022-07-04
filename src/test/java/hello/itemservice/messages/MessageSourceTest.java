package hello.itemservice.messages;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void greetingMessage() {
        String result = ms.getMessage("greeting", null, null);
        System.out.println("result = " + result);
        assertThat(result).isEqualTo("반갑습니다");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void defaultMessage() {
        String defaultMessage = "defaultMessage";
        String result = ms.getMessage("no_code", null, defaultMessage, null);
        assertThat(result).isEqualTo(defaultMessage);
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("greeting.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("반갑습니다 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("greeting", null, null)).isEqualTo("반갑습니다");
        assertThat(ms.getMessage("greeting", null, Locale.KOREA)).isEqualTo("반갑습니다");
    }

    @Test
    void enLand() {
        assertThat(ms.getMessage("greeting", null, Locale.ENGLISH)).isEqualTo("greeting");
    }
}
