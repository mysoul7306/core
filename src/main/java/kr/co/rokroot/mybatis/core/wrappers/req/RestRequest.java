/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.mybatis.core.wrappers.req;

import kr.co.rokroot.mybatis.core.abstracts.AbstractRestRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestRequest<T extends Serializable> extends AbstractRestRequest implements Serializable {

	public static <T extends Serializable> RestRequest<T> create() {
		return new RestRequest<>();
	}

	public static <T extends Serializable> RestRequest<T> create(T clazz) {
		return new RestRequest<T>().add(clazz);
	}

	private T data;

	private RestRequest<T> add(T clazz) {
		if (clazz == null) {
			return this;
		}
		this.data = clazz;
		return this;
	}

	@Override
	public boolean hasData() {
		return this.data != null;
	}
}