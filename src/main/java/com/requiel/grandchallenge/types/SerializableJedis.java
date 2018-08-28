package com.requiel.grandchallenge.types;

import redis.clients.jedis.Jedis;

import java.io.Serializable;

public class SerializableJedis extends Jedis implements Serializable {
    public static final long serialVersionUID = 1L;

    public SerializableJedis(String host, int i) {
        super(host, i);
    }
}
