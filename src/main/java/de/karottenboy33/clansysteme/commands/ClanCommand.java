package de.karottenboy33.clansysteme.commands;

import de.karottenboy33.clansysteme.ClanSysteme;
import de.karottenboy33.clansysteme.mysql.ClanManager;
import de.karottenboy33.clansysteme.utils.BungeeNameFetcher;
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
                    /*if (args[0].equalsIgnoreCase("list")){
                        proxiedPlayer.sendMessage((BaseComponent) ClanManager.getAllClanMemberNames(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                    }*/
                    /*if (args[0].equalsIgnoreCase("Stats")){
                        String ownernameid = ClanManager.getClanOwner(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                        String ownername = BungeeNameFetcher.getName(ownernameid);
                        String vizeid = ClanManager.getClanVizeID(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                        String vizename = BungeeNameFetcher.getName(vizeid);
                        if (!ownername.equals(" ") && !vizename.equals(" ")){
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Name: "+ ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Owner: "+ ownername);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Vize: "+ vizename);
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Name: "+ ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())));
                        }


                    }*/
                    if (args[0].equalsIgnoreCase("leave")) {
                        String clanid = ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId());
                        proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist dem Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " verlassen.");
                        ClanManager.setPlayerClanID(proxiedPlayer.getUniqueId(), "0");
                        ClanManager.setClanMember(clanid, ClanManager.getClanMember(clanid) - 1);
                        if (ClanManager.getClanMember(clanid) == 0 || (ClanManager.getClanOwner(clanid).equals(String.valueOf(proxiedPlayer.getUniqueId())))){
                            ClanManager.deleteClan(clanid);
                        }
                    }
                    if (args[0].equalsIgnoreCase("vize")) {
                        if (args.length == 2){
                            if (proxiedPlayer.getUniqueId().toString().equalsIgnoreCase(ClanManager.getClanOwner(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()))))){
                                ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                                if (proxiedTarget != null){
                                    ClanManager.setClanVizeID(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())), proxiedTarget.getUniqueId());
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Du hast den Clan Member "+ proxiedTarget.getName() + " befördert.");
                                } else {
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist nicht online.");
                                }
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("kick")){
                        if (args.length == 2){
                            if (proxiedPlayer.getUniqueId().toString().equalsIgnoreCase(ClanManager.getClanOwner(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())))) || proxiedPlayer.getUniqueId().toString().equalsIgnoreCase(ClanManager.getClanVizeID(Objects.requireNonNull(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId()))))){
                                ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                                if (proxiedTarget != null){
                                    String clanid = ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId());
                                    ClanManager.setPlayerClanID(proxiedTarget.getUniqueId(), "0");
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Du hast den Clan Member "+ proxiedTarget.getName() + " gekickt.");
                                    ClanManager.setClanMember(clanid, ClanManager.getClanMember(clanid) - 1);
                                } else {
                                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Der Spieler ist nicht online.");
                                }
                            }else {
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + "Dazu hast du keine rechte.");
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("invite")){
                        if (args.length == 2){
                            ProxiedPlayer proxiedTarget = ProxyServer.getInstance().getPlayer(args[1]);
                            if (proxiedTarget != null){
                                if (ClanManager.getPlayerClanID(proxiedTarget.getUniqueId()).equalsIgnoreCase("0")){
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
                    if (args[0].equalsIgnoreCase("delete")) {
                        String clanid = ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId());
                        if ((ClanManager.getClanOwner(clanid).equals(String.valueOf(proxiedPlayer.getUniqueId())))){
                            ClanManager.deleteClan(clanid);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan " + ClanManager.getClanName(ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId())) + " gelöcht.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist nicht der Besitzer dieses Clans.");
                        }
                    }
                } else {
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du kannst folgende befehle nutzen: ");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan leave "+ ChatColor.of("#fcba03") + "» Nutze diesen um den Clan zu verlassen.");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan invite "+ ChatColor.of("#fcba03") + "» Nutze diesen um neue leute einzuladen.");
                    //proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist im clan: ");
                }
            } else {
                if (args.length >= 1){
                    if (args[0].equalsIgnoreCase("accept")){
                        if (clanrequest.containsKey(proxiedPlayer.getUniqueId())){
                            String clanid = clanrequest.get(proxiedPlayer.getUniqueId());
                            if (ClanManager.getClanMember(clanid) + 1 <= ClanManager.getClanSize(clanid) || ClanManager.getClanIsPublic(clanid) == 1){
                                ClanManager.setPlayerClanID(proxiedPlayer.getUniqueId(), clanid);
                                clanrequest.remove(proxiedPlayer.getUniqueId(), clanid);
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du bist dem Clan "+ ClanManager.getClanName(clanid) + " beigetretten.");
                                ClanManager.setClanMember(clanid, ClanManager.getClanMember(clanid) + 1);
                            } else {
                                proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Der Clan "+ ClanManager.getClanName(clanid) + " ist leider schon voll.");
                            }

                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast keine Einladung erhalten☹");
                        }
                    }
                    if (args[0].equalsIgnoreCase("deny")){
                        if (clanrequest.containsKey(proxiedPlayer.getUniqueId())){
                            String clanid = clanrequest.get(proxiedPlayer.getUniqueId());
                            clanrequest.remove(proxiedPlayer.getUniqueId(), clanid);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den "+ ClanManager.getClanName(clanid) + " abgelehnt.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast keine Einladung erhalten☹");
                        }
                    }
                    if (args[0].equalsIgnoreCase("join")){
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

                    if (args[0].equalsIgnoreCase("create")){
                        if (args.length >= 3){
                            ClanManager.createClan(args[1], args[2], proxiedPlayer.getUniqueId(), 25);
                            String clanid = ClanManager.getPlayerClanID(proxiedPlayer.getUniqueId());
                            ClanManager.setClanMember(clanid, ClanManager.getClanMember(clanid) + 1);
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du hast den Clan: "+ ChatColor.of("#0398fc") + args[1] + ChatColor.of("#fcba03") + " erstellt.");
                        } else {
                            proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " /clan create <Name> <Tag>");
                        }
                    }
                } else {
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#fcba03") + " Du kannst folgende befehle nutzen: ");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan create "+ ChatColor.of("#fcba03") + "» Nutze diesen um einen Clan zu erstellen.");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan join "+ ChatColor.of("#fcba03") + "» Nutze diesen um einen public clan zu joinen.");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan accept "+ ChatColor.of("#fcba03") + "» Nutze diesen um eine Einladungs anfrage anzunehmen.");
                    proxiedPlayer.sendMessage(ClanSysteme.prefix + ChatColor.of("#0398fc") + "/clan deny "+ ChatColor.of("#fcba03") + "» Nutze diesen um eine Einladungs anfrage abzulehnen.");
                }
            }
        }

    }
}
