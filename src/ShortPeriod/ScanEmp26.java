package ShortPeriod;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

public class ScanEmp26 {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        Connection conn = ConnectionFactory.createConnection(conf);

        TableName tableName = TableName.valueOf("emp26");
        Table table = conn.getTable(tableName);

        Scan scan = new Scan();
        scan.setMaxVersions();
        scan.setStartRow("7500".getBytes());
        ResultScanner scanner = table.getScanner(scan);
        Result rs;
        while ((rs = scanner.next()) != null) {
            for (Cell cell : rs.rawCells()) {
                System.out.println(new String(CellUtil.cloneRow(cell)) + ":" +
                        new String(CellUtil.cloneFamily(cell)) + ":" +
                        new String(CellUtil.cloneQualifier(cell)) + ":" +
                        new String(CellUtil.cloneValue(cell)));
            }
        }
    }
}
