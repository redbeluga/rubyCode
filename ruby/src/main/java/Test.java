import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Test extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String input[] = event.getMessage().getContentRaw().split("\\s+");

        if(!input[0].equalsIgnoreCase("-test"))
            return;
        event.getChannel().sendMessage("Present").queue();
    }
}
