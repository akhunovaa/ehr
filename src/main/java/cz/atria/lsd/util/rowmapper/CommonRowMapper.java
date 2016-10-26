package cz.atria.lsd.util.rowmapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 19.03.14.
 */
public abstract class CommonRowMapper<T> implements RowMapper<T>
{
    protected Integer getInteger(ResultSet resultSet, String columnName) throws SQLException
    {
        Integer result = resultSet.getInt(columnName);
        if (resultSet.wasNull())
        {
            result = null;
        }
        return result;
    }

    protected String getString(ResultSet resultSet, String columnName) throws SQLException
    {
        String result = resultSet.getString(columnName);
        if (resultSet.wasNull())
        {
            result = null;
        }
        return result;
    }
}
