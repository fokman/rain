package com.rain.ice.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.rain.ice.utils.ClassUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Set;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    public static final Logger log = LoggerFactory.getLogger(DruidDataSourceFactory.class);

    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

    public static SqlSessionFactory sqlSessionFactory;

    public static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
//            reader = new InputStreamReader();
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            Configuration configuration = sqlSessionFactory.getConfiguration();
            Set<String> files = ClassUtils.getFiles("com.rain", ".xml");
            for (String resource : files) {
                if (resource.endsWith("Mapper.xml")) {
                    ErrorContext.instance().resource(resource);
                    InputStream inputStream = Resources.getResourceAsStream(resource);
                    XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource,
                            configuration.getSqlFragments());
                    mapperParser.parse();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("Error thrown while closing the reader: {}", e);
                }
            }
        }
    }
}
