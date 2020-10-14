import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import music.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ruby
{
    public static void main(String[] args) throws LoginException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(Ruby::print, 5, TimeUnit.SECONDS);


        JDA jda = JDABuilder.createDefault("NzY1MjM1NzE4NjcwNTE2MjI1.X4R3TA.KE7Dfgwxyg_aAG_nsmqhE0YxaJc")
                .enableCache(CacheFlag.VOICE_STATE).build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.addEventListener(new JoinCommand());
        jda.addEventListener(new Test());
        jda.addEventListener(new PlayCommand());
        jda.getPresence().setActivity(Activity.playing("music"));

    }

    public static void print() {

    }
}