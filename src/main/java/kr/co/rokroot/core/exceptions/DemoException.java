/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.exceptions;

import kr.co.rokroot.core.type.ResultType;
import lombok.Getter;

@Getter
public class DemoException extends RuntimeException {

	private ResultType errorCode = ResultType.ERROR;

	public DemoException(String message, Exception e) {
		super(message, e);
	}

	public DemoException(ResultType errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}

	public DemoException(ResultType errorCode, String message, Exception e) {
		super(message, e);
		this.errorCode = errorCode;
	}
}