package cz.atria.lsd.util.rowmapper;

import cz.atria.lsd.util.entity.ReferralProtocol;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 19.03.14.
 */
public class ReferralProtocolRowMapper extends CommonRowMapper<ReferralProtocol>
{
    @Override
    public ReferralProtocol mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        ReferralProtocol query = new ReferralProtocol();
        query.setId(getInteger(rs, "id"));
        query.setPath(getString(rs, "path"));
        query.setTemplatePath(getString(rs, "template_path"));
        query.setReferralType(getInteger(rs, "ref_type_id"));
        query.setReferralId(getInteger(rs, "ref_id"));
        return query;
    }
}
