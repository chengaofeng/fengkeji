package org.fengkeji.weixin.jdbc.impl;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.fengkeji.weixin.jdbc.JdbcDao;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * @author Hironobu Isoda
 * @since 1.0
 * 
 */
@Repository
public class JdbcDaoImpl extends SqlMapClientDaoSupport  implements JdbcDao {
    /**
     * ログインスタンス
     */
    private static Logger log = Logger.getLogger(JdbcDaoImpl.class);

    /**
     * 参照系SQLを実行し、結果を指定されたオブジェクトとして返却する。
     * SQLの結果オブジェクトと、指定された型が違った場合は、例外を発生させる。
     *
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param clazz
     *            返却値のクラス
     * @return SQLの実行結果
     */
    @Override
    public <E> E executeForObject(String sqlID,
                                   Object bindParams,
                                   Class<E> clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }

        // SqlMapClientTemplateの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：値の取得
        Object obj = sqlMapTemp.queryForObject(sqlID, bindParams);
        if (log.isDebugEnabled() && obj != null) {
            log.debug("Return type:" + obj.getClass().getName());
        }

        E rObj = null;
        try {
            if (clazz != null && obj != null) {
                rObj = clazz.cast(obj);
            }
        } catch (ClassCastException e) {
            log.error(e.getClass());
            throw new ClassCastException();
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
        }

        return rObj;
    }

    /**
     * 参照系SQLを実行し、Mapとして返却する。
     *
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> executeForMap(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMap Start.");
        }

        Map<String, Object> rObj = this.executeForObject(sqlID, bindParams,
                Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMap End.");
        }

        return rObj;
    }

    /**
     * 参照系SQLを実行し、結果を指定されたオブジェクトの配列として返却する。
     * SQLの結果オブジェクトと、指定された型が違った場合は、例外を発生させる。
     * 結果0件時は空配列が返却される。
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param clazz
     *            返却値のクラス
     * @return SQLの実行結果
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class<E> clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error("clazz is null");
            throw new IllegalStateException();
        }

        // SqlMapClientの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：値の取得
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams);

        // 配列に変換
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error("ArrayStoreException");
            throw e;
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * 参照系SQLを実行し、Mapの配列として返却する。
     * Map配列の変換に失敗した場合は、例外を発生させる。
     * 結果0件時は空配列が返却される。
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    @SuppressWarnings("unchecked")
	@Override
    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams,
                Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * 参照系SQLを実行し、結果を指定されたオブジェクトの配列として返却する。
     * 返却される結果は、引数にて指定されたインデックスから指定された件数である。
     * SQLの結果オブジェクトと、指定された型が違った場合は、例外を発生させる。
     * 結果0件時は空配列が返却される。
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param clazz
     *            返却値のクラス
     * @param beginIndex
     *            取得する開始インデックス
     * @param maxCount
     *            取得する件数
     * @return SQLの実行結果
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class<E> clazz, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error("clazz is null");
            throw new IllegalStateException("clazz is null");
        }

        // SqlMapClientの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：値の取得
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams, beginIndex,
                maxCount);

        // 配列に変換
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error(e.getClass());
            throw  e;
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * 参照系SQLを実行し、Mapの配列として返却する。
     * 返却される結果は、引数にて指定されたインデックスから指定された件数である。
     * Map配列の変換に失敗した場合は、例外を発生させる。
     * 結果0件時は空配列が返却される。
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param beginIndex
     *            取得する開始インデックス
     * @param maxCount
     *            取得する件数
     * @return SQLの実行結果
     */
    @SuppressWarnings("unchecked")
	@Override
    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams,
                Map.class, beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * 参照系SQLを実行し、結果を指定されたオブジェクトのListとして返却する。
     * 結果0件時は空リストが返却される。
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果リスト
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        // SqlMapClientの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：値の取得
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }

    /**
     * 参照系SQLを実行し、MapのListとして返却する。
     * 結果0件時は空リストが返却される。
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    @Override
    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }

    /**
     * 参照系SQLを実行し、結果を指定されたオブジェクトのListとして返却する。
     * 返却される結果は、引数にて指定されたインデックスから指定された件数である。
     * 結果0件時は空リストが返却される。
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param beginIndex
     *            取得する開始インデックス
     * @param maxCount
     *            取得する件数
     * @return SQLの実行結果
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams,
            int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        // SqlMapClientの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：値の取得
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams, beginIndex,
                maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }

    /**
     * 参照系SQLを実行し、MapのListとして返却する。
     * 返却される結果は、引数にて指定されたインデックスから指定された件数である。
     * 結果0件時は空リストが返却される。
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @param beginIndex
     *            取得する開始インデックス
     * @param maxCount
     *            取得する件数
     * @return SQLの実行結果
     */
    @Override
    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams,
                beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }
    
    /**
     * バッチ実行用のSQL
     * @deprecated この変数は将来削除されます
     */
    @Deprecated
    protected final ThreadLocal<List<WxSqlHolder>> batchSqls
        = new ThreadLocal<List<WxSqlHolder>>();

    /**
     * 引数sqlIDで指定されたSQLを実行して、結果件数を返却する。
     * 実行するSQLは「insert, update delete」の3種類とする。
     *
     * @param sqlID 実行するSQLのID
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果件数を返却
     */
    @Override
    public int execute(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("execute Start.");
        }

        //SqlMapClientの取得
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQLの実行：データ追加。
        int row = sqlMapTemp.update(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("execute End. success count:" + row);
        }
        return row;
    }

    /**
     * バッチ追加メソッド。
     * 引数のSQLをスレッドローカルに保持する。
     * 複数のリクエストをまたいでSQLを保持することはできない。
     * 追加後に、<code>UpdateDAO#executeBatch()</code>で、一括実行を行う。
     *
     * <b>注意：</b>このメソッドを使用すると、バッチ更新対象のSQLが
     * クリアされない可能性がある。{@link #executeBatch(List)}を使用すること。
     *
     * @param sqlID 実行するSQLのID
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @deprecated addBatchの代わりに{@link #executeBatch(List)}
     * を使用すること
     */
    @Override
    @Deprecated
    public void addBatch(final String sqlID, final Object bindParams) {
        // スレッドローカルの取得
        List<WxSqlHolder> sqlHolders = batchSqls.get();
        if (sqlHolders == null) {
            sqlHolders = new ArrayList<WxSqlHolder>();
            batchSqls.set(sqlHolders);
            if (log.isDebugEnabled()) {
                log.debug("Create new SqlHolder in ThreadLocal.");
            }
        }

        // バッチ用のSQLをスレッドローカルに保持する
        sqlHolders.add(new WxSqlHolder(sqlID, bindParams));
        if (log.isDebugEnabled()) {
            log.debug("Add batch sql.  SQL_ID='" + sqlID
                    + "' Parameters:" + bindParams);
        }
    }

    /**
     * バッチ処理の実行メソッド。
     * <code>{@link #addBatch(String, Object)}</code>で追加されたSQLを
     * 一括実行する。バッチ実行後はSQLをクリアする。
     * <code>{@link #addBatch(String, Object)}</code>でSQLを追加していない場合、
     * 実行時例外が発生する。
     *
     * <b>注意：</b>このメソッドを使用すると、バッチ更新対象のSQLが
     * クリアされない可能性がある。{@link #executeBatch(List)}を使用すること。
     *
     * @return SQLの実行結果
     * @deprecated addBatchの代わりに{@link #executeBatch(List)}
     * を使用すること
     */
    @Override
    @Deprecated
    public int executeBatch() {
        // スレッドローカルからSQLを取り出す
        final List<WxSqlHolder> sqlHolders = batchSqls.get();

        // SQLバッチ実行
        Integer result = 0;
        try {
            result = (Integer) getSqlMapClientTemplate().execute(
                    new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor executor)
                      throws SQLException {
                    StringBuilder logStr = new StringBuilder();
                    if (log.isDebugEnabled()) {
                        log.debug("Batch SQL count:" + sqlHolders.size());
                    }
                    executor.startBatch();
                    for (WxSqlHolder sqlHolder : sqlHolders) {
                        executor.update(
                            sqlHolder.getSqlID(), sqlHolder.getBindParams());

                        if (log.isDebugEnabled()) {
                            logStr.setLength(0);
                            logStr.append("Call update sql. - SQL_ID:'");
                            logStr.append(sqlHolder.getSqlID());
                            logStr.append("' Parameters:");
                            logStr.append(sqlHolder.getBindParams());
                            log.debug(logStr.toString());
                        }
                    }
                    return executor.executeBatch();
                }
            });
        } finally {
            // スレッドローカルからSQLを削除
            batchSqls.remove();
            if (log.isDebugEnabled()) {
                log.debug("Remove SqlHolder in ThreadLocal.");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("ExecuteBatch complete. Result:" + result);
        }
        return result.intValue();
    }

    /**
     * バッチ処理の実行メソッド。<br/>
     *
     * 引数の{@link WxSqlHolder}のリストで指定されたすべてのSQLを実行する。<br/>
     *
     * <br/>
     * <b>注意点</b><br/>
     * executeBatchはiBATISのバッチ実行機能を使用している。
     * executeBatchの戻り値は、実行したSQLによる変更行数が返却するが、
     * java.sql.PreparedStatementを使用しているため、
     * ドライバにより正確な行数が取得できないケースがある。<br/>
     * 変更行数が正確に取得できないドライバを使用する場合、
     * 変更行数がトランザクションに影響を与える業務では
     * (変更行数が0件の場合エラー処理をするケース等)、
     * バッチ更新は使用しないこと。<br/>
     * 参考資料）<br/>
     * <a href="http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf#page=450"
     * target="_blank">
     * http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf</a>
     * <br/>
     * 450ページ「標準バッチ処理のOracle 実装の更新件数」を参照のこと。<br/>
     *
     * @param sqlHolders バッチ更新対象のsqlId、パラメータを格納した
     * SqlHolderインスタンスのリスト
     * @return SQLの実行結果件数
     */
    @Override
    public int executeBatch( final List<WxSqlHolder> sqlHolders) {
        // SQLバッチ実行
        Integer result = 0;
        result = (Integer) getSqlMapClientTemplate().execute(
                new SqlMapClientCallback() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor)
                  throws SQLException {
                StringBuilder logStr = new StringBuilder();
                if (log.isDebugEnabled()) {
                    log.debug("Batch SQL count:" + sqlHolders.size());
                }
                executor.startBatch();
                for (WxSqlHolder sqlHolder : sqlHolders) {
                    executor.update(
                        sqlHolder.getSqlID(), sqlHolder.getBindParams());

                    if (log.isDebugEnabled()) {
                        logStr.setLength(0);
                        logStr.append("Call update sql. - SQL_ID:'");
                        logStr.append(sqlHolder.getSqlID());
                        logStr.append("' Parameters:");
                        logStr.append(sqlHolder.getBindParams());
                        log.debug(logStr.toString());
                    }
                }
                return executor.executeBatch();
            }
        });

        if (log.isDebugEnabled()) {
            log.debug("ExecuteBatch complete. Result:" + result);
        }
        return result.intValue();
    }
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;
    
    @PostConstruct        
    public void initSqlMapClient(){
     super.setSqlMapClient(sqlMapClient);    
    }
}
