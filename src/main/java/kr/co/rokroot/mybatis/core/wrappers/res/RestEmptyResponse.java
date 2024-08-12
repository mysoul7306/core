/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.mybatis.core.wrappers.res;

import kr.co.rokroot.mybatis.core.abstracts.AbstractRestResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestEmptyResponse extends AbstractRestResponse implements Serializable {

	public static RestEmptyResponse create() {
		return new RestEmptyResponse();
	}

	public static RestEmptyResponse create(Integer result) {
		return new RestEmptyResponse().resultCnt(result);
	}

	public RestEmptyResponse resultCnt(Integer resultCnt) {
		this.resultCnt = resultCnt;
		if (resultCnt == null) {
			this.resultCnt = 0;
		}
		return this;
	}

	@Override
	public boolean hasData() {
		return this.resultCnt > 0;
	}
}