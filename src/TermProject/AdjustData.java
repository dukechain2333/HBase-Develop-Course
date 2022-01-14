package TermProject;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class AdjustData {
    Configuration conf;
    Connection conn;

    public AdjustData() throws IOException {
        this.conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        this.conn = ConnectionFactory.createConnection(this.conf);
    }

    public void putData(String tableName, String rowKey, String columnFamily, String columnName, String value) throws IOException {
        Table table = this.conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));
        table.put(put);
        table.close();
    }

    public void closeConn() throws IOException {
        this.conn.close();
    }
}
