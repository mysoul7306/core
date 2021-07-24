/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.abstracts;

import kr.co.rokroot.core.type.ResultType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractRestResponse {
	protected ResultType resultType = ResultType.OK;
	protected String resultMsg;
	protected Integer resultCnt;
	protected Date resTime = DateTime.now().toDate();

	public abstract boolean hasData();
}
