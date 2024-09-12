package org.app;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.spark.HBaseContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String folderPath = "training4/data/sample_text";
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        List<PageView> pgs = new ArrayList<>();
        readData(listOfFiles, pgs);

        SparkSession spark = SparkSession.builder()
                .appName("bt4.0")
                .master("yarn")
                .getOrCreate();

        Configuration hbaseConfig = HBaseConfiguration.create();
        hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
        hbaseConfig.set("hbase.zookeeper.property.clientPort", "2181");
        hbaseConfig.set("hbase.master", "localhost:16000");

        HbaseWay();
        SparkWay(spark, pgs);
    }

    private static void SparkWay(SparkSession spark, List<PageView> pgs) {
        try{
            JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
            StructType schema = newSchemaParquetsSpark();
            List<Row> rows = new ArrayList<>();
            for (PageView pv : pgs) {
                rows.add(RowFactory.create(
                        pv.hashCode(),
                        pv.getTimeCreate(),
                        pv.getCookieCreate(),
                        pv.getBrowserCode(),
                        pv.getBrowserVer(),
                        pv.getOsCode(),
                        pv.getOsVer(),
                        pv.getIp(),
                        pv.getLocId(),
                        pv.getDomain(),
                        pv.getSiteId(),
                        pv.getcId(),
                        pv.getPath(),
                        pv.getReferer(),
                        pv.getGuid(),
                        pv.getFlashVersion(),
                        pv.getJre(),
                        pv.getSr(),
                        pv.getSc(),
                        pv.getGeographic(),
                        pv.getCategory()
                ));
            }
            System.out.println(PageView.catalog());
            Dataset<Row> df = spark.createDataFrame(rows, schema);

            df.write()
                    .format("org.apache.hadoop.hbase.spark")
                    .option("hbase.table", "pageviewlog")
                    .option("hbase.zookeeper.quorum", "localhost")
                    .option("hbase.zookeeper.property.clientPort", "2181")
                    .option("hbase.master", "localhost:16000")
                    .options(Collections.singletonMap("catalog", PageView.catalog()))
                    .save();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            spark.stop();

        }
    }

    private static StructType newSchemaParquetsSpark() {
        return new StructType(new StructField[] {
                DataTypes.createStructField("key", DataTypes.IntegerType, false),
                DataTypes.createStructField("timeCreate", DataTypes.DateType , false),
                DataTypes.createStructField("cookieCreate", DataTypes.DateType, false),
                DataTypes.createStructField("browserCode", DataTypes.IntegerType, false),
                DataTypes.createStructField("browserVer", DataTypes.StringType, false),
                DataTypes.createStructField("osCode", DataTypes.IntegerType, false),
                DataTypes.createStructField("osVer", DataTypes.StringType, false),
                DataTypes.createStructField("ip", DataTypes.LongType, false),
                DataTypes.createStructField("locId", DataTypes.IntegerType, false),
                DataTypes.createStructField("domain", DataTypes.StringType, false),
                DataTypes.createStructField("siteId", DataTypes.IntegerType, false),
                DataTypes.createStructField("cId", DataTypes.IntegerType, false),
                DataTypes.createStructField("path", DataTypes.StringType, false),
                DataTypes.createStructField("referer", DataTypes.StringType, false),
                DataTypes.createStructField("guid", DataTypes.LongType, false),
                DataTypes.createStructField("flashVersion", DataTypes.StringType, false),
                DataTypes.createStructField("jre", DataTypes.StringType, false),
                DataTypes.createStructField("sr", DataTypes.StringType, false),
                DataTypes.createStructField("sc", DataTypes.StringType, false),
                DataTypes.createStructField("geographic", DataTypes.IntegerType, false),
                DataTypes.createStructField("category", DataTypes.StringType, false)
        });
    }

    private static void HbaseWay() {
        Configuration config = HBaseConfiguration.create();

        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(config);
            createTable(connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable(Connection connection) {
        try{
            Admin admin = connection.getAdmin();
            TableName tableName = TableName.valueOf("pageviewlog");
            ColumnFamilyDescriptor cf1 = ColumnFamilyDescriptorBuilder.newBuilder("hardware".getBytes()).build();
//            browserCode	browserVer	osCode	osVer	flashVersion	jre	sr	sc
            ColumnFamilyDescriptor cf2 = ColumnFamilyDescriptorBuilder.newBuilder("consumer".getBytes()).build();
//            cId	guid	ip	locId	geographic
            ColumnFamilyDescriptor cf3 = ColumnFamilyDescriptorBuilder.newBuilder("producer".getBytes()).build();
//            domain	path	referrer	siteId	category    timeCreate	cookieCreate
            TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableName)
                    .setColumnFamily(cf1)
                    .setColumnFamily(cf2)
                    .setColumnFamily(cf3)
                    .build();
            if (!admin.tableExists(tableName)) {
                admin.createTable(tableDescriptor);
                System.out.println("Table created.");
            } else {
                System.out.println("Table already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readData(File[] listOfFiles, List<PageView> pgs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(listOfFiles != null){
            for(File file : listOfFiles){
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine())!= null){
                        String[] values = line.split("\t");

                        // Chuyển đổi từ java.util.Date sang java.sql.Date
                        java.sql.Date sqlTimeCreate = new java.sql.Date(dateFormat.parse(values[0]).getTime());
                        java.sql.Date sqlCookieCreate = new java.sql.Date(dateFormat.parse(values[1]).getTime());

                        PageView pg = new PageView(
                                sqlTimeCreate,                  //timeCreate
                                sqlCookieCreate,                //cookieCreate
                                Integer.parseInt(values[2]),    //browserCode
                                values[3],                      //browserVer
                                Integer.parseInt(values[4]),    //osCode
                                values[5],                      //osVer
                                Long.parseLong(values[6]),      //ip
                                Integer.parseInt(values[7]),    //locId
                                values[8],                      //domain
                                Integer.parseInt(values[9]),    //siteId
                                Integer.parseInt(values[10]),   //cId
                                values[11],                     //path
                                values[12],                     //referer
                                Long.parseLong(values[13]),     //guid
                                values[14],                     //flashVersion
                                values[15],                     //jre
                                values[16],                     //sr
                                values[17],                     //sc
                                Integer.parseInt(values[18]),   //geographic
                                values[23]                      //category
                        );
                        pgs.add(pg);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("FileNotFoundException");
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    System.out.println("IOException");
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    System.out.println("ParseException");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}