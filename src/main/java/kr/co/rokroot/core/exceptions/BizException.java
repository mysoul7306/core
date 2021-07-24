/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.exceptions;

import kr.co.rokroot.core.type.ResultType;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

	private ResultType errorCode = ResultType.ERROR;

	public BizException(String message, Exception e) {
		super(message, e);
	}

	public BizException(ResultType errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}

	public BizException(ResultType errorCode, String message, Exception e) {
		super(message, e);
		this.errorCode = errorCode;
	}
}