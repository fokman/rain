package com.rain.storm.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class WriteBolt extends BaseBasicBolt {
    private static final Logger logger = LoggerFactory.getLogger(WriteBolt.class);

    private FileWriter writer;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String text = tuple.getStringByField("write");
        try {
            if (writer == null) {
                logger.info("current os :{}", System.getProperty("os.name"));
                switch (System.getProperty("os.name")) {
                    case "Windows 10":
                        writer = new FileWriter("c:\\project\\" + this);
                        break;
                    case "Windows 8.1":
                        writer = new FileWriter("c:\\project\\" + this);
                        break;
                    case "Windows 7":
                        writer = new FileWriter("c:\\project\\" + this);
                        break;
                    case "Linux":
                        writer = new FileWriter("c:\\project\\" + this);
                        break;
                }
            }
            logger.info("写入文件----------------");
            writer.write(text);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
