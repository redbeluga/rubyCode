package music;

import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String input[] = event.getMessage().getContentRaw().split("\\s+");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        if(!input[0].equalsIgnoreCase("-play"))
            return;

        final TextChannel channel = event.getChannel();

        if (input.length < 2) {
            channel.sendMessage("Correct usage is `-play <youtube link>`").queue();
            return;
        }

        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            JoinCommand.joinVC(event);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread.interrupt();
            }
            
            return;
        }
        else {
            InVc(event, memberVoiceState, channel, selfVoiceState, input);
            return;
        }
    }



    public void InVc(GuildMessageReceivedEvent event, GuildVoiceState memberVoiceState, TextChannel channel, GuildVoiceState selfVoiceState, String input[]) {
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        String link = String.join(" ", input[1]);

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }
        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
