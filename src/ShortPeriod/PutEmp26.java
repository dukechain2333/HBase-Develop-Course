package ShortPeriod;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

public class PutEmp26 {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        Connection conn = ConnectionFactory.createConnection(conf);

        TableName tableName = TableName.valueOf("emp26");
        Table table = conn.getTable(tableName);

        String[] cols = new String[]{
                "info:name",
                "info:job",
                "info::mgr",
                "info:hiredata",
                "money:sal",
                "money:credit",
                "info:deptno"
        };
        String file = "Data/emp.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line;
        Put put;
        while ((line = br.readLine()) != null) {
            String[] ls = line.split(",");
            put = new Put(ls[0].getBytes());
            for (int i = 1; i < ls.length; i++) {
                if (!Objects.equals(ls[i], "")) {
                    put.addColumn(cols[i - 1].split(":")[0].getBytes(), cols[i - 1].split(":")[1].getBytes(), ls[i].getBytes());
                }
            }
            table.put(put);
        }

        table.close();
        conn.close();

    }
}
