package com.mmednet.main.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.DatabaseConnection;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonDaoImpl<T> implements CommonDao<T> {

	protected Dao<T, Serializable> dao;
	private DbHelper helper;

	@Override
	@SuppressWarnings("unchecked")
	public void init(Context context) {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
			helper = DbHelper.getInstance(context);
			dao = helper.getDao(clazz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Dao<T, Serializable> getDao() {
		return dao;
	}

	@Override
	public int execute(String sql, boolean isTransactional) {
		DatabaseConnection connection = null;
		Savepoint savePoint = null;
		try {
			if (isTransactional) {
				connection = dao.startThreadConnection();
				savePoint = connection.setSavePoint(null);
			}
			return dao.executeRaw(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.commit(savePoint);
					dao.endThreadConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public void insert(T t) {
		try {
			dao.createIfNotExists(t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String[]> query(String sql) {
		try {
			GenericRawResults<String[]> queryRaw = dao.queryRaw(sql);
			List<String[]> results = queryRaw.getResults();
			queryRaw.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] queryObject(String sql) {
		try {
			GenericRawResults<String[]> queryRaw = dao.queryRaw(sql);
			String[] results = queryRaw.getFirstResult();
			queryRaw.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T query(int id) {
		T t = null;
		try {
			t = dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public T queryObject(String columnName, String columenValue) {
		List<T> list = query(columnName, columenValue);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<T> query(String columnName, String columenValue) {
		QueryBuilder<T, Serializable> queryBuilder = dao.queryBuilder();
		List<T> list = new ArrayList<T>();
		try {
			queryBuilder.where().eq(columnName, columenValue);
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<T> query(String[] columnNames, Object[] columenValues) {
		if (columnNames.length != columenValues.length) {
			throw new InvalidParameterException("参数不匹配");
		}
		QueryBuilder<T, Serializable> queryBuilder = dao.queryBuilder();
		List<T> list = new ArrayList<T>();
		try {
			Where<T, Serializable> wheres = queryBuilder.where();
			for (int i = 0; i < columnNames.length; i++) {
				wheres.eq(columnNames[i], columenValues[i]);
			}
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<T> query(Map<String, Object> map) {
		QueryBuilder<T, Serializable> queryBuilder = dao.queryBuilder();
		List<T> list = new ArrayList<T>();
		try {
			if (!map.isEmpty()) {
				Where<T, Serializable> wheres = queryBuilder.where();
				Set<String> keys = map.keySet();
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.addAll(keys);
				for (int i = 0; i < arrayList.size(); i++) {
					if (i == 0) {
						wheres.eq(arrayList.get(i), map.get(arrayList.get(i)));
					} else {
						wheres.and().eq(arrayList.get(i),
								map.get(arrayList.get(i)));
					}
				}
			}
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}



	@Override
	public List<T> queryAll() {
		List<T> list = new ArrayList<T>();
		try {
			list = dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<T> query(String columnName, List<String> ids) {
		QueryBuilder<T, Serializable> queryBuilder = dao.queryBuilder();
		List<T> list = new ArrayList<T>();
		try {
			if (!ids.isEmpty()) {
				Where<T, Serializable> wheres = queryBuilder.where();
				wheres.in(columnName,ids);
			}
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int delete(T t) {
		try {
			return dao.delete(t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(Collection<T> collection) {
		try {
			return dao.delete(collection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteAll() {
		List<T> list = this.queryAll();
		try {
			return dao.delete(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(String[] columnNames, String[] columenValues) {
		List<T> list = query(columnNames, columenValues);
		if (null != list && !list.isEmpty()) {
			return delete(list);
		}
		return 0;
	}

	@Override
	public int deleteObject(String columnName, String columenValue) {
		T t = queryObject(columnName, columenValue);
		if (null != t) {
			return delete(t);
		}
		return 0;
	}

	@Override
	public int update(String sql) {
		try {
			return dao.updateRaw(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public int update(T t) {
		try {
			return dao.update(t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(T t, Serializable id) {
		try {
			return dao.updateId(t, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean isTableExsits() {
		try {
			return dao.isTableExists();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public long countOf() {
		try {
			return dao.countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * 按列and连接选择
	 * </p>
	 * 
	 * @author liuyujian
	 * @date 2015年11月16日 下午3:30:46
	 * @param columnNames
	 * @param columenValues
	 * @return
	 */
	protected List<T> queryObject(String[] columnNames, Object[] columenValues) {
		if (columnNames.length != columenValues.length) {
			throw new InvalidParameterException("参数不匹配");
		}
		QueryBuilder<T, Serializable> queryBuilder = dao.queryBuilder();
		List<T> list = new ArrayList<T>();
		try {
			Where<T, Serializable> wheres = queryBuilder.where();
			for (int i = 0; i < columnNames.length; i++) {
				if (i == 0) {
					wheres.eq(columnNames[i], columenValues[i]);
				} else {
					wheres.and().eq(columnNames[i], columenValues[i]);
				}
			}
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
