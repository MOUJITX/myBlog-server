package com.moujitx.myblog.server.common;

/**
 * Function:
 * Author: MOUJITX
 * Date: 2024/3/11 22:23
 */

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 可用于向形如[{a:'a1',b:'b1',c:'c1'},{a:'a2',b:'b2',c:'c2'}]的转换
 * 也可用于['aa','bb','cc']的转换
 *
 */

@MappedTypes(JSONArray.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JSONArrayObjectTypeHandler extends BaseTypeHandler<JSONArray> {

    /**
     * 重写设置参数
     * 
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, String.valueOf(parameter.toString()));
    }

    /**
     * 根据列名，获取可以为空的结果
     * 
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String sqlJson = rs.getString(columnName);
        if (null != sqlJson) {
            return JSONUtil.parseArray(sqlJson);
        }
        return new JSONArray();
    }

    /**
     * 根据列索引，获取可以为空的结果
     * 
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String sqlJson = rs.getString(columnIndex);
        if (null != sqlJson) {
            return JSONUtil.parseArray(sqlJson);
        }
        return new JSONArray();
    }

    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String sqlJson = cs.getString(columnIndex);
        if (null != sqlJson) {
            return JSONUtil.parseArray(sqlJson);
        }
        return new JSONArray();
    }
}
