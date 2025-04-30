package com.moujitx.myblog.server.common;

/**
 * Function:
 * Author: MOUJITX
 * Date: 2024/3/11 22:23
 */

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 仅用于向形如{a:'a1',b:'b1',c:'c1'}的转换
 *
 */

@Slf4j
@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JSONObjectTypeHandler implements TypeHandler<JSONObject> {

    @Override
    public void setParameter(PreparedStatement ps, int i, JSONObject parameter,
            JdbcType jdbcType) throws SQLException {
        String value = parameter.toString();
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public JSONObject getResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return result == null ? null : JSONUtil.parseObj(result);
    }

    @Override
    public JSONObject getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return result == null ? null : JSONUtil.parseObj(result);
    }

    @Override
    public JSONObject getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return result == null ? null : JSONUtil.parseObj(result);
    }
}
