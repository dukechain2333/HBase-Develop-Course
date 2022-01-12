package ShortPeriod;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class CreateEmp26 {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        Connection conn = ConnectionFactory.createConnection(conf);

        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("emp_test");

        HTableDescriptor ht = new HTableDescriptor(tableName);
        ht.addFamily(new HColumnDescriptor("info"));
        ht.addFamily(new HColumnDescriptor("money").setMaxVersions(12));

        admin.createTable(ht);
    }
}
