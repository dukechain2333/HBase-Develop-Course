package TermProject;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class CreateTable {
    Admin admin;
    Configuration conf;
    Connection conn;

    public CreateTable() throws IOException {
        this.conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        this.conn = ConnectionFactory.createConnection(this.conf);
        this.admin = conn.getAdmin();
    }

    public void createPlayerInfo() throws IOException {
        String tableName = "PlayerInfo";
        if (!this.admin.isTableAvailable(TableName.valueOf(tableName))) {
            HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
            ht.addFamily(new HColumnDescriptor("Followings").setMaxVersions(1000));
            this.admin.createTable(ht);
        }
        System.out.println("PlayerInfo created successfully");
    }

    public void createGameInfo() throws IOException {
        String tableName = "GameInfo";
        if (!this.admin.isTableAvailable(TableName.valueOf(tableName))) {
            HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
            ht.addFamily(new HColumnDescriptor("Followers").setMaxVersions(1000));
            this.admin.createTable(ht);
        }
        System.out.println("GameInfo created successfully");
    }

    public void createPlayingInfo() throws IOException {
        String tableName = "PlayingInfo";
        if (!this.admin.isTableAvailable(TableName.valueOf(tableName))) {
            HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
            ht.addFamily(new HColumnDescriptor("PlayingGames").setMaxVersions(1000));
            this.admin.createTable(ht);
        }
        System.out.println("PlayingInfo created successfully");
    }

    public void closeConn() throws IOException {
        this.conn.close();
        this.admin.close();
    }
}
