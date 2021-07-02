package de.karottenboy33.clansysteme.events;

import de.karottenboy33.clansysteme.ClanSysteme;
import de.karottenboy33.clansysteme.mysql.ClanManager;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxiedPlayerJoinEvent extends Event implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent event){
        event.getPlayer().sendMessage("test");
        ClanManager.createUser(event.getPlayer().getUniqueId());
    }
}
