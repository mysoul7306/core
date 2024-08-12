/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.mybatis.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.rokroot.mybatis.core.exceptions.DemoException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ObjectUtility {

	private static final ObjectMapper mapper = new ObjectMapper();
	private ObjectUtility() {
		throw new DemoException("Don't create instance.", new RuntimeException());
	}

	public static Map<String, Object> castMap(Object dto) {
		Map<String, Object> map = new HashMap<>();
		Field[] fields = dto.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (Modifier.isStatic(field.getModifiers())) continue;
				map.put(field.getName(), field.get(dto));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public static <T> T castDTO(T dto, Map<String, Object> map) {
		try {
			mapper.convertValue(map, dto.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
}