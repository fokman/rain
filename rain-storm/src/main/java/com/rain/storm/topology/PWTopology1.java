package com.rain.storm.topology;

import com.rain.storm.bolt.PrintBolt;
import com.rain.storm.bolt.WriteBolt;
import com.rain.storm.spout.PWSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * 拓扑图1，定义topology结构
 */
public class PWTopology1 {
    public static void main(String[] args) throws InterruptedException {
        Config cfg = new Config();
        cfg.setNumWorkers(2); //代表有几个jvm来处理
        cfg.setDebug(false); //调试模式

        TopologyBuilder builder = new TopologyBuilder(); //用来组织spout,bolt的结构
        builder.setSpout("spout", new PWSpout());
        builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("spout"); // 在spout执行完成后执行print-bolt
        builder.setBolt("write-bolt", new WriteBolt()).shuffleGrouping("print-bolt"); // 在print-bolt执行完成后执行write-bolt


        //1. 本地执行模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("top1", cfg, builder.createTopology()); //提交流程图
        Thread.sleep(10000);
        cluster.killTopology("top1");
        cluster.shutdown();
    }
}
