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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestListResponse<T extends Serializable> extends AbstractRestResponse implements Serializable {

	public static <T extends Serializable> RestListResponse<T> create(T clazz) {
		return new RestListResponse<T>().add(clazz);
	}

	@SafeVarargs
	public static <T extends Serializable> RestListResponse<T> create(T... clazz) {
		return new RestListResponse<T>().add(clazz);
	}

	public static <T extends Serializable> RestListResponse<T> create(List<T> clazz) {
		return new RestListResponse<T>().add(clazz);
	}

	public RestListResponse<T> resultCnt(Integer resultCnt) {
		this.resultCnt = resultCnt;
		if (this.resultCnt == null) {
			this.resultCnt = 0;
		}
		return this;
	}

	private List<T> data = new ArrayList<>();

	private RestListResponse<T> add(T clazz) {
		if (clazz == null) {
			return this;
		}
		this.data.add(clazz);
		return this;
	}

	@SafeVarargs
	private final RestListResponse<T> add(T... ts) {
		if (ts == null || ts.length == 0) {
			return this;
		}
		for (T t : ts) {
			add(t);
		}
		return this;
	}

	private RestListResponse<T> add(List<T> list) {
		if (list == null) {
			return this;
		}
		this.data.addAll(list);
		return this;
	}

	@Override
	public boolean hasData() {
		return this.data != null && !this.data.isEmpty();
	}
}