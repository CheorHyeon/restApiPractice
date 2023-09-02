package com.example.restapipractice.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Ut {
	public static class json {

		public static Object toStr(Map<String, Object> map) {
			try {
				return new ObjectMapper().writeValueAsString(map);
			} catch (JsonProcessingException e) {
				return null;
			}
		}

		public static Map<String, Object> toMap(String jsonStr) {
			try {
				return new ObjectMapper().readValue(jsonStr, LinkedHashMap.class);
			} catch (JsonProcessingException e) {
				return null;
			}
		}
	}
}