package de.karottenboy33.clansysteme.commands;

import de.karottenboy33.clansysteme.ClanSysteme;
import de.karottenboy33.clansysteme.mysql.ClanManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ClanCommand extends Command {

    public ClanCommand() {
        super("clan", "", "c");
    }
    public HashMap< UUID, String> clanrequest = new HashMap();
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if (!ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()).equals("0")){
                if (args.length >= 1){
                    /*if (args[0].equals("list")){
                        proxiedPlayer.sendMessage((BaseComponent) ClanManager.getAllClanMemberNames(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    }*/
                    if (args[0].equals("Stats")){
                        String ownername = ProxyServer.getInstance().getPlayer(ClanManager.getClanOwner(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()))).getDisplayName();
                        proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Name: "+ ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                        proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Owner: "+ ownername);
                    }
                    if (args[0].equals("leave")) {
                        proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist dem Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " verlassen.");
                        ClanManager.setPlayerClanID(proxiedPlayer.getUniqueId(), "0");
                    }
                    if (args[0].equals("vize")) {
                        if (args.length == 2){
                            if (proxiedPlayer.getUniqueId().toString().equals(ClanManager.getClanOwner(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()))))){
                                ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                                if (proxiedTarget != null){
                                    ClanManager.setClanVizeID(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()), proxiedTarget.getUniqueId());
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Du hast den Clan Member "+ proxiedTarget.getName() + " befördert.");
                                } else {
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist nicht online.");
                                }
                            }
                        }
                    }
                    if (args[0].equals("kick")){
                        if (args.length == 2){
                            if (proxiedPlayer.getUniqueId().toString().equals(ClanManager.getClanOwner(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())))) || proxiedPlayer.getUniqueId().toString().equals(ClanManager.getClanVizeID(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()))))){
                                ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                                if (proxiedTarget != null){
                                    ClanManager.setPlayerClanID(proxiedTarget.getUniqueId(), "0");
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Du hast den Clan Member "+ proxiedTarget.getName() + " gekickt.");
                                } else {
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist nicht online.");
                                }
                            }else {
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Dazu hast du keine rechte.");
                            }
                        }
                    }
                    if (args[0].equals("invite")){
                        if (args.length == 2){
                            ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                            if (proxiedTarget != null){
                                if (ClanManager.getPlayerClanID(proxiedTarget.getUniqueId()).equals("0")){
                                    TextComponent txt = new TextComponent();
                                    txt.setText(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du Wurdest in den Clan "+ ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " eingeladen.");

                                    TextComponent selectAccept = new TextComponent(ClanSysteme.prefix + ChatColor.of("#fcba03") + "§7[§2Anehmen§7] ");
                                    selectAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept"));
                                    selectAccept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§2aceptieren")));

                                    TextComponent selectDeny = new TextComponent("§7[§3Ablehnen§7] ");
                                    selectDeny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan deny"));
                                    selectDeny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§4ablehnen")));

                                    TextComponent message = new TextComponent();
                                    message.addExtra(txt);
                                    message.addExtra(" ");
                                    message.addExtra(selectAccept);
                                    message.addExtra(" ");
                                    message.addExtra(selectDeny);
                                    proxiedTarget.sendMessage(message);
                                    clanrequest.put(proxiedTarget.getUniqueId(), ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()));
                                } else {
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist bereits in einem Clan.");
                                }
                            }else {
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist nicht online.");
                            }
                        }
                        //proxiedPlayer.sendMessage((BaseComponent) ClanManager.getAllClanMemberNames(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    }
                } else {
                    //proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist im clan: " +ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist im clan: ");
                }
            } else {
                if (args.length >= 1){
                    if (args[0].equals("accept")){
                        if (clanrequest.containsKey(proxiedPlayer.getUniqueId())){
                            String clanid = clanrequest.get(proxiedPlayer.getUniqueId());
                            ClanManager.setPlayerClanID(proxiedPlayer.getUniqueId(), clanid);
                            clanrequest.remove(proxiedPlayer.getUniqueId(), clanid);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist dem Clan "+ ClanManager.getClanName(clanid) + " beigetretten.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast keine Einladung erhalten☹");
                        }
                    }
                    if (args[0].equals("deny")){
                        if (clanrequest.containsKey(proxiedPlayer.getUniqueId())){
                            String clanid = clanrequest.get(proxiedPlayer.getUniqueId());
                            clanrequest.remove(proxiedPlayer.getUniqueId(), clanid);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den "+ ClanManager.getClanName(clanid) + " abgelehnt.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast keine Einladung erhalten☹");
                        }
                    }
                    if (args[0].equals("join")){
                        if (args.length == 2){
                            String ClanName = args[1];
                            String Clanid = ClanManager.getClanID(ClanName);
                            if (Clanid != null){
                                if (ClanManager.getClanIsPublic(Clanid) == 1){
                                    ClanManager.setPlayerClanID(proxiedPlayer.getUniqueId(), Clanid);
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist dem Clan "+ ClanName + " beigetretten.");
                                }
                            } else {
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Clan exestiert nicht.");
                            }

                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " /clan join <Name>");
                        }
                    }

                    if (args[0].equals("create")){
                        if (args.length >= 3){
                            ClanManager.createClan(args[1], args[2], proxiedPlayer.getUniqueId(), 25);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan: "+ ChatColor.of("#0398fc") + args[1] + ChatColor.of("#fcba03") + " erstellt.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " /clan create <Name> <Tag>");
                        }
                    }
                }
            }
        }

    }
}
