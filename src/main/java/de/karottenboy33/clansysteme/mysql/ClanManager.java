package de.karottenboy33.clansysteme.mysql;


import de.karottenboy33.clansysteme.utils.BungeeNameFetcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ClanManager {
    public static boolean userPlayedBefore(UUID uuid) {
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clanuser` WHERE `uuid` = ?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(uuid));
            }
        });
        try {
            if(set.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean IsClanExist(String ClanID) {
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid` = ?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(ClanID));
            }
        });
        try {
            if(set.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createUser(UUID uuid) {
        if(!userPlayedBefore(uuid)) {
            ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `clanuser` (`uuid`, `clanid`) VALUES (?,?)", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(uuid));
                    put(2, String.valueOf(0));
                }
            });
        }
    }
    public static void createClan(String ClanName, UUID ownerid) {
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        String generatedString = new String(array);
        if (!IsClanExist(generatedString)){
            ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `clan` (`clanid`, `name`, `ownerid`) VALUES (?,?,?)", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(generatedString));
                    put(2, ClanName);
                    put(3, String.valueOf(ownerid));
                }
            });
            setPlayerClanID(ownerid, generatedString);
        } else {
            byte[] array2 = new byte[20];
            new Random().nextBytes(array2);
            String generatedString2 = new String(array2);
            if (!IsClanExist(generatedString2)){
                ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `clan` (`clanid`, `name`, `ownerid`) VALUES (?,?,?)", new HashMap<Integer, String>(){
                    {
                        put(1, String.valueOf(generatedString2));
                        put(2, ClanName);
                        put(3, String.valueOf(ownerid));
                    }
                });
                setPlayerClanID(ownerid, generatedString2);  
            }
        }
    }

    //Backend Player
    public static String getPlayerClanID(UUID uuid){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clanuser` WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, uuid.toString());
            }
        });
        try {
            while (set.next()){
                return set.getString("clanid");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }

    public static void setPlayerClanID(UUID uuid, String ClanId){
        if (IsClanExist(ClanId)){
            ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clanuser` SET `clanid`=? WHERE uuid=?", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(ClanId));
                    put(2, String.valueOf(uuid));
                }

            });
        } else if (ClanId == "0")  {
              ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clanuser` SET `clanid`=? WHERE uuid=?", new HashMap<Integer, String>(){
                  {
                      put(1, String.valueOf(ClanId));
                      put(2, String.valueOf(uuid));
                  }

              });
        }
    }


    //Backend Clan
    public static String getClanName(String ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getString("name");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    public static String getClanOwner(String ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getString("ownerid");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    /*public static String getAllClanMemberUUI(String ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clanuser` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID);
            }
        });
        try {
            while (set.next()){
                return set.getString("uuid");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    public static List< String > getAllClanMemberNames(String ClanID){
        String uuids = getAllClanMemberUUI(ClanID);
        if (uuids != 0){
            List<String> names = Collections.singletonList(BungeeNameFetcher.getName(String.valueOf(uuids)));
            return names;
        }
        return null;
    }*/
    public static void setClanName(String ClanID, String name){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `name`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(name));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanOwner(String ClanID, UUID ownerid){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `ownerid`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(ownerid));
                put(2, ClanID.toString());

            }
        });
    }
}
