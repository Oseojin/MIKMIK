package com.habu.testplugin.db;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.domain.user;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerScoreboardManager;
import com.habu.testplugin.manager.TitleNameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class player_db_connect
{
    private static player_db_connect instance;

    private player_db_connect()
    {

    }

    public static player_db_connect getInstance()
    {
        if(instance == null)
        {
            synchronized (player_db_connect.class)
            {
                instance = new player_db_connect();
            }
        }

        return instance;
    }

    private Connection connection;

    private String host = "localhost";
    private int port = 4507;
    private String database = "mikmik";
    //private String database = "mc_test";
    private String table = "playerdata";
    private String username = "root";
    private String password = "vu64kH!M*W";
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public static HashMap<UUID, user> userList = new HashMap<UUID, user>();

    public Connection open_Connection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                return null;
            }

            synchronized (this)
            {
                if (connection != null && !connection.isClosed())
                {
                    return null;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
                        this.username, this.password);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    public void SetName(Player player, String name)
    {
        String coloumName = "u_name";
        SetData(player, coloumName, name);
    }

    public String GetName(Player player)
    {
        return TestPlugin.User_List.get(player.getUniqueId().toString()).getU_name();
    }

    public void SetGold(Player player, Integer amount)
    {
        String coloumName = "u_gold";
        SetData(player, coloumName, amount);
    }

    public Integer GetGold(Player player)
    {
        return TestPlugin.User_List.get(player.getUniqueId().toString()).getU_gold();
    }

    public void AddGold(Player player, Integer amount)
    {
        String coloumName = "u_gold";
        amount += GetGold(player);
        SetData(player, coloumName, amount);
    }

    public void UseGold(Player player, Integer amount)
    {
        String coloumName = "u_gold";
        amount = GetGold(player) - amount;
        SetData(player, coloumName, amount);
    }
    public void SetJob(Player player, String jobName)
    {
        String coloumName = "u_job";
        SetData(player, coloumName, jobName);
    }

    public String GetJob(Player player)
    {
        return TestPlugin.User_List.get(player.getUniqueId().toString()).getU_job();
    }

    public void SetTitle(Player player, String title)
    {
        String coloumName = "u_title";
        SetData(player, coloumName, title);
        player.setPlayerListName(title + player.getName());
    }

    public String GetTitle(Player player)
    {
        return TestPlugin.User_List.get(player.getUniqueId().toString()).getU_title();
    }

    public int insertMember(Player player)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            if (rs.getInt(1) == 0)
            {
                try
                {
                    // PreparedStatement 초기화 (재사용을 위해)
                    pstmt.clearParameters();
                    sql = "INSERT INTO " + table.toUpperCase() + " (u_uuid, u_name, u_gold, u_job, u_title) VALUES (?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, player.getUniqueId().toString()); // uuid
                    pstmt.setString(2, player.getName()); // name
                    pstmt.setInt(3, 0); // gold
                    pstmt.setString(4, JobNameManager.JobLessName); // job
                    pstmt.setString(5, TitleNameManager.JobLess); // title
                    pstmt.executeUpdate();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    public user db_PlayerInfo(Player player)
    {
        UUID player_UUID = player.getUniqueId();
        user u = new user();
        Connection conn = null;
        // 예외 처리 안해주면 서버 터짐
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select u.u_no, u.u_uuid, u.u_name, u.u_gold, u.u_job, u.u_title from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player_UUID.toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수임 ㅇ)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            u.setU_no(rs.getInt("u_no"));
            u.setU_uuid(rs.getString("u_uuid"));
            u.setU_name(rs.getString("u_name"));
            u.setU_gold(rs.getInt("u_gold"));
            u.setU_job(rs.getString("u_job"));
            u.setU_title(rs.getString("u_title"));

            TestPlugin.User_List.put(rs.getString("u_uuid"), u);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                // 연결 된 DB 연결을 종료 해준다. 안해주면 여러개 쌓이면 DB 검색 안됨 (즉, commit과 같은 용도)
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return u;
    }

    private int SetData(Player player, String coloum, Integer content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = " + content + " WHERE u_uuid = \"" + player.getUniqueId() + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                db_PlayerInfo(player);
                PlayerScoreboardManager.reloadScboard(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    private int SetData(Player player, String coloum, String content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = \"" + content + "\" WHERE u_uuid = \"" + player.getUniqueId() + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                db_PlayerInfo(player);
                PlayerScoreboardManager.reloadScboard(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }
}