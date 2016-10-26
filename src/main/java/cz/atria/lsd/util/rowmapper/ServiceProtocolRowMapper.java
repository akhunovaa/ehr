package cz.atria.lsd.util.rowmapper;

import cz.atria.lsd.util.entity.ServiceProtocol;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 19.03.14.
 */
public class ServiceProtocolRowMapper extends CommonRowMapper<ServiceProtocol>
{
    @Override
    public ServiceProtocol mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        ServiceProtocol query = new ServiceProtocol();
        query.setId(getInteger(rs, "id"));
        query.setPath(getString(rs, "path"));
        query.setTemplatePath(getString(rs, "template_path"));
        query.setServiceId(getInteger(rs, "service_id"));
        return query;
    }
}
