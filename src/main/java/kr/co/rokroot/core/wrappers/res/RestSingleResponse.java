/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.wrappers.res;

import kr.co.rokroot.core.abstracts.AbstractRestResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestSingleResponse<T extends Serializable> extends AbstractRestResponse implements Serializable {

	public static <T extends Serializable> RestSingleResponse<T> create() {
		return new RestSingleResponse();
	}

	public static <T extends Serializable> RestSingleResponse<T> create(T clazz) {
		return new RestSingleResponse().add(clazz);
	}

	public RestSingleResponse<T> resultCnt(Integer resultCnt) {
		this.resultCnt = resultCnt;
		if (this.resultCnt == null) {
			this.resultCnt = 0;
		}
		return this;
	}

	private T data;

	public RestSingleResponse<T> add(T clazz) {
		if (clazz == null) {
			return this;
		}
		this.data = clazz;
		return this;
	}

	@Override
	public boolean hasData() {
		return data != null;
	}
}