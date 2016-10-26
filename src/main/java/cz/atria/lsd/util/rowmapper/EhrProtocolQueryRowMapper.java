package cz.atria.lsd.util.rowmapper;

import cz.atria.lsd.util.entity.EhrProtocolQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 19.03.14.
 */
public class EhrProtocolQueryRowMapper extends CommonRowMapper<EhrProtocolQuery>
{
    @Override
    public EhrProtocolQuery mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        EhrProtocolQuery query = new EhrProtocolQuery();
        query.setReferralTypeId(getInteger(rs, "referral_type_id"));
        query.setServiceId(getInteger(rs, "service_id"));
        query.setServicePrototypeId(getInteger(rs, "srv_prototype_id"));
        query.setTemplatePath(getString(rs, "template_path"));
        if ((query.getTemplatePath() != null) && query.getTemplatePath().trim().isEmpty())
        {
            query.setTemplatePath(null);
        }
        query.setId(getInteger(rs, "id"));
        return query;
    }
}
