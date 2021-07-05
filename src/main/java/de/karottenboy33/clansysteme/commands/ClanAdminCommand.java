package de.karottenboy33.clansysteme.commands;

import de.karottenboy33.clansysteme.ClanSysteme;
import de.karottenboy33.clansysteme.mysql.ClanManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ClanAdminCommand extends Command {
    public ClanAdminCommand() {
        super("clanadmin", "", "ca");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if (proxiedPlayer.hasPermission("clan.admin")){
                if (args.length >= 1){
                    if (args[0].equalsIgnoreCase("setPublic")) {
                        if (args.length == 2 ){
                            String clanName = args[1];
                            String clanid = ClanManager.getClanID(clanName);
                            if (ClanManager.IsClanExist(clanid)){
                                if (ClanManager.getClanIsPublic(clanid) == 1){
                                    ClanManager.setClanIsPublic(clanid, 0);
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " nicht Public gesetzt.");
                                }else {
                                    ClanManager.setClanIsPublic(clanid, 1);
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " Public gesetzt.");
                                }
                            }
                        }

                    }
                    if (args[0].equalsIgnoreCase("setSize")) {
                        if (args.length == 3) {
                            String clanName = args[1];
                            String clanid = ClanManager.getClanID(clanName);
                            int clansize = Integer.parseInt(args[2]);
                            if (ClanManager.IsClanExist(clanid)){
                                ClanManager.setClanSize(clanid, clansize);
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " auf die Clan größe "+clansize+" gesetzt.");
                            }
                        }
                    }
                } else {
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du kannst folgende befehle nutzen: ");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clanadmin setPublic "+ ChatColor.of("#fcba03") + "» Nutze diesen um den Clan public zu setzen.");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clanadmin setSize <Wert>"+ ChatColor.of("#fcba03") + "» Nutze diesen um den eine Clan größe zu ändern.");
                }
            } else {
                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Dazu hast du keine Rechte!");
            }
        }
    }
}
