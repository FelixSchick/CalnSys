package de.karottenboy33.clansysteme.mysql;


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
    public static boolean IsClanExist(UUID ClanID) {
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
                    put(2, String.valueOf(UUID.fromString("00000000-0000-0000-0000-000000000000")));
                }
            });
        }
    }
    public static void createClan(String ClanName,String ClanTag, UUID ownerid, int size) {
       UUID clanid = UUID.randomUUID();
        if (!IsClanExist(clanid)){
            ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `clan` (`clanid`, `name`, `tag`, `ownerid`, `vizeid`, `size`, `ispublic`) VALUES (?,?,?,?,?,?,?)", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(clanid));
                    put(2, ClanName);
                    put(3, ClanTag);
                    put(4, String.valueOf(ownerid));
                    put(5, String.valueOf(0));
                    put(6, String.valueOf(size));
                    put(7, String.valueOf(0));
                }
            });
            setPlayerClanID(ownerid, clanid);
        } else {
            UUID clanid2 = UUID.randomUUID();
            if (!IsClanExist(clanid)){
                ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `clan` (`clanid`, `name`,`tag`, `ownerid`, `vizeid`, `size`, `ispublic`) VALUES (?,?,?)", new HashMap<Integer, String>(){
                    {
                        put(1, String.valueOf(clanid2));
                        put(2, ClanName);
                        put(3, ClanTag);
                        put(4, String.valueOf(ownerid));
                        put(5, String.valueOf(0));
                        put(6, String.valueOf(size));
                        put(7, String.valueOf(false));
                    }
                });
                setPlayerClanID(ownerid,clanid2);
            }
        }
    }

    public static void deleteClan(UUID Clanid) {
        if(IsClanExist(Clanid)) {
            ResultSet set = MySQL.getInstance().executeQuery("DELETE FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(Clanid));
                }
            });
        }
    }

    //Backend Player
    public static UUID getPlayerClanID(UUID uuid){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clanuser` WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, uuid.toString());
            }
        });
        try {
            while (set.next()){
                return UUID.fromString(set.getString("clanid"));
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    public static String getAllClanMembers(UUID clanid){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clanuser` WHERE clanid=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(clanid));
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

    public static void setPlayerClanID(UUID uuid, UUID ClanId){
        if (IsClanExist(ClanId)){
            ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clanuser` SET `clanid`=? WHERE uuid=?", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(ClanId));
                    put(2, String.valueOf(uuid));
                }

            });
        } else if (ClanId.equals("0"))  {
              ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clanuser` SET `clanid`=? WHERE uuid=?", new HashMap<Integer, String>(){
                  {
                      put(1, String.valueOf(ClanId));
                      put(2, String.valueOf(uuid));
                  }

              });
        }
    }


    //Backend Clan
    public static String getClanName(UUID ClanID){
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
    public static String getClanOwner(UUID ClanID){
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
    public static String getClanTag(UUID ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getString("tag");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    public static String getClanVizeID(UUID ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getString("vizeid");
            }
            return null;
        } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }
    public static int getClanSize(UUID ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getInt("size");
            }
            return 0;
        } catch (SQLException e){
            e.getErrorCode();
            return 0;
        }
    }
    public static int getClanIsPublic(UUID ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getInt("ispublic");
            }
            return 0;
        } catch (SQLException e){
            e.getErrorCode();
            return 0;
        }
    }
    public static int getClanMember(UUID ClanID){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanID.toString());
            }
        });
        try {
            while (set.next()){
                return set.getInt("member");
            }
            return 0;
        } catch (SQLException e){
            e.getErrorCode();
            return 0;
        }
    }

    public static UUID getClanID(String ClanName){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `clan` WHERE `name`=?", new HashMap<Integer, String>(){
            {
                put(1, ClanName.toString());
            }
        });
        try {
            while (set.next()){
                return UUID.fromString(set.getString("clanid"));
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
    public static void setClanName(UUID ClanID, String name){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `name`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(name));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanOwner(UUID ClanID, UUID ownerid){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `ownerid`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(ownerid));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanTag(UUID ClanID, String ClanTag){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `tag`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(ClanTag));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanMember(UUID ClanID, int member){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `member`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(member));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanVizeID(UUID ClanID, UUID vizeid){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `vizeid`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(vizeid));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanSize(UUID ClanID, int Size){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `size`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(Size));
                put(2, ClanID.toString());

            }
        });
    }
    public static void setClanIsPublic(UUID ClanID, int ispublic){

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `clan` SET `ispublic`=? WHERE `clanid`=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(ispublic));
                put(2, ClanID.toString());

            }
        });
    }
}
