/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.utilities;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ObjectUtility {

	public static Map<String, Object> castMap(Object dto) {
		Map<String, Object> map = new HashMap<>();
		Field[] fields = dto.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				map.put(field.getName(), field.get(dto));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public static <T> T castDTO(T dto, Map<String, Object> map) {
		try {
			beanUtilsBean.populate(dto, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}


	protected static final ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
		@Override
		public Object convert(String value, Class clazz) {
			if (value == null) {
				return null;
			}
			if (clazz.isEnum()) {
				return Enum.valueOf(clazz, value);
			}
			return super.convert(value, clazz);
		}
	};

	protected static final BeanUtilsBean beanUtilsBean;
	static {
		DateTimeConverter dtConverter = new DateConverter();
		dtConverter.setPattern("yyyyMMdd");
		convertUtilsBean.deregister(Date.class);
		convertUtilsBean.register(dtConverter, Date.class);
		beanUtilsBean = new BeanUtilsBean(convertUtilsBean);
	}
}