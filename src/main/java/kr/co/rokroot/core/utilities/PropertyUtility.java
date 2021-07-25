/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.utilities;

import kr.co.rokroot.core.exceptions.DemoException;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtility {

	protected static InputStream is;

	/*public static Properties getYaml(String property) {

	}*/

	public static Properties getProperties(String property) {
		Properties prop = new Properties();
		try {
			is = PropertyUtility.class.getClassLoader().getResourceAsStream("props/" + property);
			prop.load(is);
		} catch (Exception e) {
			throw new DemoException("Property file not found", e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				throw new DemoException("input stream error occurred", e);
			}
		}

		return prop;
	}
}
