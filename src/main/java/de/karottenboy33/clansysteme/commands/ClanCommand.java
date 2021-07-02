package de.karottenboy33.clansysteme.commands;

import de.karottenboy33.clansysteme.ClanSysteme;
import de.karottenboy33.clansysteme.mysql.ClanManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ClanCommand extends Command {

    public ClanCommand() {
        super("clan", "", "c");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if (ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()).toString() != "0"){
                if (args.length >= 1){
                    if (args[0].equals("list")){
                        proxiedPlayer.sendMessage((BaseComponent) ClanManager.getAllClanMemberNames(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    }
                } else {
                    //proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist im clan: "+ ChatColor.of("#0398fc") +ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist im clan: ");
                }
            } else {
                if (args.length == 2){
                    if (args[0].equals("create")){
                        ClanManager.createClan(args[1], proxiedPlayer.getUniqueId());
                        proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan: "+ ChatColor.of("#0398fc") + args[1] + ChatColor.of("#fcba03") + " erstellt.");
                    }
                } else {
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Erstelle mit /clan create <Name> einen clan");
                }
            }
        }

    }
}
