package com.mmednet.robotGuide.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommonDao<T> {

	/** 初始化 */
	public abstract void init(Context context);

	/** 获取Dao */
	public abstract Dao<T, Serializable> getDao();

	/** 指定sql语句是否开启事务操作数据库 */
	public abstract int execute(String sql, boolean isTransactional);

	/** 插入数据 */
	public abstract void insert(T t);

	/** 指定sql语句查询数据 */
	public abstract List<String[]> query(String sql);

	/** 指定sql语句查询第一条数据 */
	public abstract String[] queryObject(String sql);

	/** 指定id查询数据 */
	public abstract T query(int id);

	/** 根据条件查询第一条数据 */
	public abstract T queryObject(String columnName, String columenValue);

	/** 根据条件查询所有数据 */
	public abstract List<T> query(String columnName, String columenValue);

	/** 根据多个条件查询所有数据 */
	public abstract List<T> query(String[] columnNames, Object[] columenValues);

	/** 根据多个条件查询所有数据 */
	public abstract List<T> query(Map<String, Object> map);

	/** 查询表中所有数据 */
	public abstract List<T> queryAll();

	/*in语句查询所有数据*/
	public abstract List<T> query(String columnName, List<String> ids);

	/** 删除指定数据 */
	public abstract int delete(T t);

	/** 批量删除数据 */
	public abstract int delete(Collection<T> collection);

	/** 删除表中所有数据 */
	public abstract int deleteAll();

	/** 按条件批量删除 */
	public abstract int delete(String[] columnNames, String[] columenValues);

	/** 根据条件删除第一条数据 */
	public abstract int deleteObject(String columnName, String columenValue);

	/** 根据sql语句进行更新 */
	public abstract int update(String sql);

	/** 更新指定数据 */
	public abstract int update(T t);

	/** 更新指定id数据 */
	public abstract int update(T t, Serializable id);

	public abstract boolean isTableExsits();

	/** 统计数据条数 */
	public abstract long countOf();
}
