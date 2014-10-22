package org.fengkeji.weixin.jdbc;

import java.util.List;
import java.util.Map;

import org.fengkeji.weixin.jdbc.impl.WxSqlHolder;

/**
 * @author Hironobu Isoda
 * @since 1.0
 * 
 */
public interface JdbcDao {

    /**
     * SQLの実行結果を指定された型にして返却する。
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
    <E> E executeForObject(String sqlID, Object bindParams, Class<E> clazz);

    /**
     * SQLの実行結果をMapに格納して返却する。
     *
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    Map<String, Object> executeForMap(String sqlID, Object bindParams);

    /**
     * SQLの実行結果を指定された型の配列にして返却する。 結果0件時は空配列が返却される。
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
    <E> E[] executeForObjectArray(String sqlID, Object bindParams, Class<E> clazz);

    /**
     * SQLの実行結果をMapの配列に格納して返却する。 結果0件時は空配列が返却される。
     *
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    Map<String, Object>[] executeForMapArray(String sqlID, Object bindParams);

    /**
     * SQLの実行結果を指定されたインデックスから指定された行数分、 指定された型の配列にして返却する。 結果0件時は空配列が返却される。
     *
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
    <E> E[] executeForObjectArray(String sqlID, Object bindParams, Class<E> clazz, int beginIndex, int maxCount);

    /**
     * SQLの実行結果を指定されたインデックスから指定された行数分、 Mapの配列にして返却する。 結果0件時は空配列が返却される。
     *
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
    Map<String, Object>[] executeForMapArray(String sqlID, Object bindParams, int beginIndex, int maxCount);

    /**
     * SQLの実行結果を指定された型のListで返却する。 結果0件時は空リストが返却される。
     *
     * @param <E>
     *            返却値の型
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    <E> List<E> executeForObjectList(String sqlID, Object bindParams);

    /**
     * SQLの実行結果をMapのListに格納して返却する。 結果0件時は空リストが返却される。
     *
     * @param sqlID
     *            実行するSQLのID
     * @param bindParams
     *            SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果
     */
    List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams);

    /**
     * SQLの実行結果を指定されたインデックスから指定された行数分、 指定された型のListで返却する。 結果0件時は空リストが返却される。
     *
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
    <E> List<E> executeForObjectList(String sqlID, Object bindParams, int beginIndex, int maxCount);

    /**
     * SQLの実行結果を指定されたインデックスから指定された行数分、 MapのListにして返却する。 結果0件時は空リストが返却される。
     *
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
    List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams, int beginIndex, int maxCount);



    /**
     * 引数sqlIDで指定されたSQLを実行して、結果件数を返却する。
     * 実行するSQLは「insert, update delete」の3種類とする。
     *
     * @param sqlID 実行するSQLのID
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @return SQLの実行結果件数を返却
     */
    int execute(String sqlID, Object bindParams);

    /**
     * バッチ追加メソッド。
     * バッチ処理として追加したいSQLのSQLIDとバインドパラメータを
     * 引数に渡す。
     *
     * @param sqlID 実行するSQLのID
     * @param bindParams SQLにバインドする値を格納したオブジェクト
     * @deprecated addBatchの代わりに{@link #executeBatch(List)}
     * を使用すること
     */
    @Deprecated
    void addBatch(String sqlID, Object bindParams);

    /**
     * バッチ処理の実行メソッド。
     *
     * @return SQLの実行結果
     * @deprecated executeBatchの代わりに{@link #executeBatch(List)}
     * を使用すること
     */
    @Deprecated
    int executeBatch();

    /**
     * バッチ更新処理を行うメソッド。<br/>
     * 引数の{@link WxSqlHolder}のリストで指定されたすべてのSQLを実行する。
     * DAOインスタンスに状態を持たせない為、バッチ更新対象のSQLはすべて
     * このメソッド内で実行まで完結する必要がある。
     *
     * @param sqlHolders バッチ更新対象のsqlId、パラメータを格納した
     * SqlHolderインスタンスのリスト
     * @return SQLの実行結果件数
     */
    int executeBatch(List<WxSqlHolder> sqlHolders);

}
