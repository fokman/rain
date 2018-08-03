package com.rain.storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 数据源入口，所有的数据都进入到这里面
 */
public class PWSpout extends BaseRichSpout {
    private static final Logger logger = LoggerFactory.getLogger(PWSpout.class);

    private SpoutOutputCollector collector;

    //数据源
    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "java");
        map.put(1, "php");
        map.put(2, "groovy");
        map.put(3, "python");
        map.put(4, "ruby");
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    /**
     * 轮询tuple
     */
    @Override
    public void nextTuple() {
        final Random random = new Random();
        int num = random.nextInt(5);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发射数据到拓扑里面定义的流向
        logger.info("next tuple value : {}", map.get(num));
        this.collector.emit(new Values(map.get(num)));
    }

    /**
     * 声明发送数据的Field
     *
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //声明发送数据的名字叫print
        outputFieldsDeclarer.declare(new Fields("print"));
    }

}
