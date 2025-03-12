package edu.unisabana.dyas.samples.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CustomDateTypeHandler extends BaseTypeHandler<java.sql.Date> {

    // Definir formato
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, java.sql.Date parameter, JdbcType jdbcType) throws SQLException {
        // Convertir java.sql.Date a String (YYYY-MM-DD format)
        ps.setString(i, format.format(parameter));
    }

    @Override
    public java.sql.Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String date = rs.getString(columnName);
        if (date != null) {
            try {
                Date utilDate = format.parse(date);  // Convertir String a java.util.Date
                return new java.sql.Date(utilDate.getTime());  // Convertir a java.sql.Date
            } catch (Exception e) {
                throw new SQLException("Unable to parse date: " + date, e);
            }
        }
        return null;
    }

    @Override
    public java.sql.Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String date = rs.getString(columnIndex);
        if (date != null) {
            try {
                Date utilDate = format.parse(date);  // Convertir a java.util.Date
                return new java.sql.Date(utilDate.getTime());  // Convertir a java.sql.Date
            } catch (Exception e) {
                throw new SQLException("Unable to parse date: " + date, e);
            }
        }
        return null;
    }

    @Override
    public java.sql.Date getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String date = cs.getString(columnIndex);
        if (date != null) {
            try {
                Date utilDate = format.parse(date);  // Convertir a java.util.Date
                return new java.sql.Date(utilDate.getTime());  // Convertir a java.sql.Date
            } catch (Exception e) {
                throw new SQLException("Unable to parse date: " + date, e);
            }
        }
        return null;
    }
}
