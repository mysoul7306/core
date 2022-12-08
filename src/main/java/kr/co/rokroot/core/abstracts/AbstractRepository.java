/*
 * Author: rok_root
 * Created time: 2021. 07. 25
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.abstracts;

import kr.co.rokroot.core.exceptions.DemoException;
import kr.co.rokroot.core.types.QueryType;
import kr.co.rokroot.core.types.ResultType;
import kr.co.rokroot.core.utilities.ObjectUtility;
import kr.co.rokroot.core.wrappers.res.RestEmptyResponse;
import kr.co.rokroot.core.wrappers.res.RestListResponse;
import kr.co.rokroot.core.wrappers.req.RestRequest;
import kr.co.rokroot.core.wrappers.res.RestSingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractRepository extends DefaultTransactionDefinition {

	protected abstract SqlSessionTemplate getSqlSession();
	protected abstract DataSourceTransactionManager getTransaction();
	protected abstract String getRepository();

	private static final String SEPARATE_STATEMENT = ".";

	private transient TransactionStatus status;

	private interface Type<T> {
		T work();
	}

	private interface TypeList<T> {
		List<T> work(List<Map<String, Object>> typeList);
	}

	public <Q extends Serializable, S extends Serializable> RestSingleResponse<S> singleWrapper(String statement, RestRequest<Q> reqDTO, Type<S> type) {
		Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

		return this.singleWrapper(statement, map, type);
	}

	public <Q extends Serializable, S extends Serializable> RestListResponse<S> listWrapper(String statement, RestRequest<Q> reqDTO, TypeList<S> typeList) {
		Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

		return this.listWrapper(statement, map, typeList);
	}

	public <Q extends Serializable> RestEmptyResponse insertWrapper(String statement, RestRequest<Q> reqDTO) {
		Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

		return this.insertWrapper(statement, map);
	}

	public <Q extends Serializable> RestEmptyResponse updateWrapper(String statement, RestRequest<Q> reqDTO) {
		Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

		return this.updateWrapper(statement, map);
	}


	private <S extends Serializable> RestSingleResponse<S> singleWrapper(String statement, Map<String, Object> map, Type<S> type) {
		map = this.callDatabase(QueryType.SELECT_ONE, statement, map);

		S resDTO = map.get("result") == null? null : ObjectUtility.castDTO(type.work(), (Map<String, Object>) map.get("result"));
		map.clear();

		return this.setMessage(RestSingleResponse.create(resDTO).resultCnt(resDTO == null? 0 : 1));
	}

	private <S extends Serializable> RestListResponse<S> listWrapper(String statement, Map<String, Object> map, TypeList<S> typeList) {
		map = this.callDatabase(QueryType.SELECT_LIST, statement, map);

		List<S> resListDTO = typeList.work(((List<Map<String, Object>>) map.get("result")).stream().filter(Objects::nonNull).collect(Collectors.toList()));
		map.clear();

		return this.setMessage(RestListResponse.create(resListDTO).resultCnt(resListDTO.size()));
	}

	private RestEmptyResponse insertWrapper(String statement, Map<String, Object> map) {
		map = this.callDatabase(QueryType.INSERT, statement, map);

		Integer result = Integer.parseInt(map.get("result") == null || Integer.parseInt(map.get("result").toString()) < 0? "0" : map.get("result").toString());
		map.clear();

		return this.setMessage(RestEmptyResponse.create(result));
	}

	private RestEmptyResponse updateWrapper(String statement, Map<String, Object> map) {
		map = this.callDatabase(QueryType.UPDATE, statement, map);

		Integer result = Integer.parseInt(map.get("result") == null || Integer.parseInt(map.get("result").toString()) < 0? "0" : map.get("result").toString());
		map.clear();

		return this.setMessage(RestEmptyResponse.create(result));
	}

	private synchronized Map<String, Object> callDatabase(QueryType mapping, String statement, Map<String, Object> param) {
		statement = SEPARATE_STATEMENT + statement;

		long startTime = 0L;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			startTime = System.currentTimeMillis();
			this.start(statement);
			switch (mapping) {
				case SELECT_ONE:
					result.put("result", this.getSqlSession().selectOne(this.getRepository() + statement, param));
					break;
				case SELECT_LIST:
					result.put("result", this.getSqlSession().selectList(this.getRepository() + statement, param));
					break;
				case INSERT:
					result.put("result", this.getSqlSession().insert(this.getRepository() + statement, param));
					break;
				case UPDATE:
					result.put("result", this.getSqlSession().update(this.getRepository() + statement, param));
					break;
				case DELETE:
					result.put("result", this.getSqlSession().delete(this.getRepository() + statement, param));
					break;
			}
			this.commit();
		} catch (UncategorizedSQLException e) {
			throw new UncategorizedSQLException("Wrong statement called", statement, e.getSQLException());
		} catch (BadSqlGrammarException e) {
			throw new BadSqlGrammarException("API query error", statement, e.getSQLException());
		} catch (MyBatisSystemException e) {
			throw new MyBatisSystemException(e);
		} catch (TransactionException e) {
			throw new TransactionTimedOutException("Transaction timeout, Default timeout(ms) : " + this.getTransaction().getDefaultTimeout() * 1000);
		} catch (Exception e) {
			throw new DemoException("Undefined exception error", e);
		} finally {
			this.finish();
			param.clear();
			log.info("Open API - Database call : {}, Established time(ms) : {}", statement, System.currentTimeMillis() - startTime);
		}

		return result;
	}

	private void start(String statement) throws TransactionException {
		this.setName(statement);
		this.status = this.getTransaction().getTransaction(this);
	}

	private void commit() throws TransactionException {
		if (! this.status.isCompleted()) {
			this.getTransaction().commit(this.status);
		}
	}

	private void finish() throws TransactionException {
		if (! this.status.isCompleted()) {
			this.getTransaction().rollback(this.status);
		}
	}

	private <S extends AbstractRestResponse> S setMessage(S res) {
		if (res.hasData()) {
			res.setResultType(ResultType.OK);
			res.setResultMsg("SUCCESS");
		} else {
			res.setResultType(ResultType.ERROR);
			res.setResultMsg("NO DATA");
		}

		return res;
	}
}