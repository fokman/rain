package com.rain.cache.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "jedis-config")
public class JedisConfigProperties {
    @Value("${jedis-config.pool.max-active}")
    private Integer poolMaxActive;

    @Value("${jedis-config.pool.max-active}")
    private Integer poolMaxIdle;

    @Value("${jedis-config.pool.max-wait}")
    private Integer poolMaxWait;

    @Value("${jedis-config.pool.max-total}")
    private Integer poolMaxTotal;

    @Value("${jedis-config.pool.min-idle}")
    private Integer poolMinIdle;

    @Value("${jedis-config.cluster.nodes}")
    private String clusterNodes;

    @Value("${jedis-config.cluster.connection-timeout}")
    private Integer clusterConnectionTimeout;

    @Value("${jedis-config.cluster.so-timeout}")
    private Integer clusterSoTimeout;

    @Value("${jedis-config.cluster.max-redirections}")
    private Integer clusterMaxRedirections;

    public Integer getPoolMaxActive() {
        return poolMaxActive;
    }

    public void setPoolMaxActive(Integer poolMaxActive) {
        this.poolMaxActive = poolMaxActive;
    }

    public Integer getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(Integer poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public Integer getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(Integer poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    public Integer getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(Integer poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public Integer getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(Integer poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getClusterConnectionTimeout() {
        return clusterConnectionTimeout;
    }

    public void setClusterConnectionTimeout(Integer clusterConnectionTimeout) {
        this.clusterConnectionTimeout = clusterConnectionTimeout;
    }

    public Integer getClusterSoTimeout() {
        return clusterSoTimeout;
    }

    public void setClusterSoTimeout(Integer clusterSoTimeout) {
        this.clusterSoTimeout = clusterSoTimeout;
    }

    public Integer getClusterMaxRedirections() {
        return clusterMaxRedirections;
    }

    public void setClusterMaxRedirections(Integer clusterMaxRedirections) {
        this.clusterMaxRedirections = clusterMaxRedirections;
    }

}
