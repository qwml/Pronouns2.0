package dev.jay.pronouns.commands.managers;

import dev.jay.pronouns.Pronouns;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class dbq {


    private final Pronouns plugin;
    public dbq(Pronouns plugin){
        this.plugin = plugin;
    }

    public void createTable() throws SQLException {
        PreparedStatement table = plugin.con.GetDb().prepareStatement("CREATE TABLE IF NOT EXISTS pronouns(playerUUID varchar, pronounsSet varchar, PRIMARY KEY(playerUUID))");
        table.executeUpdate();
        System.out.println("Table created.");
    }

    public void createPlayer(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        if (!doesPlayerExist(player)){
            PreparedStatement createPlayer = plugin.con.GetDb().prepareStatement("INSERT INTO pronouns(playerUUID, pronounsSet) VALUES (?,?)");
            createPlayer.setString(1, uuid.toString());
            createPlayer.setString(2, "N/A");
            createPlayer.executeUpdate();
            System.out.println("Player created.");
        }
    }


    public boolean doesPlayerExist(Player player) throws SQLException{
        UUID uuid = player.getUniqueId();
        PreparedStatement ps1 = plugin.con.GetDb().prepareStatement("SELECT * FROM pronouns WHERE playerUUID=?");
        ps1.setString(1, String.valueOf(uuid));
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()){
            return true;
        }else{
            return false;
        }
    }
}
