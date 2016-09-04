package com.skjanyou.recycle.pojo;

import java.util.HashMap;
import java.util.Map;

public class ImageCache<K,V> {
	private static final int MAX_SIZE = 1024;
	private Map<K,V> cache = new HashMap<K, V>();

	@SuppressWarnings("rawtypes")
	public ImageCache put(K k, V v){
		if(cache.size() >= MAX_SIZE){
			throw new RuntimeException("达到存储上限");
		}
		cache.put(k, v);
		return this;
	}
	public V get(K k){
		return cache.get(k);
	}
}
