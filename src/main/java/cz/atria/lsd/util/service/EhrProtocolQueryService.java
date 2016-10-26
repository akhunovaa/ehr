package cz.atria.lsd.util.service;

import cz.atria.common.spring.jdbc.CustomJdbcTemplate;
import cz.atria.lsd.util.entity.EhrProtocolQuery;
import cz.atria.lsd.util.entity.ReferralProtocol;
import cz.atria.lsd.util.entity.ServiceProtocol;
import cz.atria.lsd.util.entity.TaskState;
import cz.atria.lsd.util.rowmapper.EhrProtocolQueryRowMapper;
import cz.atria.lsd.util.rowmapper.ReferralProtocolRowMapper;
import cz.atria.lsd.util.rowmapper.ServiceProtocolRowMapper;
import org.springframework.jms.core.JmsTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by Alexander on 09.03.14.
 */
public class EhrProtocolQueryService
{
    private CustomJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public EhrProtocolQuery getEhrProtocolQuery(int queryId)
    {
        Object[] params = new Object[]{queryId};

        return jdbcTemplate.queryForObject("select * from ehr_protocol_query where id = ?", params,
                new EhrProtocolQueryRowMapper());
    }

    public List<ReferralProtocol> getReferralProtocols(Integer referralTypeId, String templatePath)
    {
        List<ReferralProtocol> referralProtocols = null;

        String y = "%" + templatePath.substring(4);
        if ((referralTypeId != null) && (templatePath == null))
        {
            referralProtocols = jdbcTemplate.query("select md_ref_ehr_protocol.id, md_ehr_protocol.path,\n" +
                    "md_ehr_protocol.template_path, md_referral.referral_type_id ref_type_id, md_referral.id ref_id from md_ref_ehr_protocol, md_referral, md_ehr_protocol\n" +
                    "where md_referral.id = md_ref_ehr_protocol.referral_id and\n" +
                    "md_ref_ehr_protocol.protocol_id = md_ehr_protocol.id and\n" +
                    "md_referral.referral_type_id = ?", new Object[]{ referralTypeId }, new ReferralProtocolRowMapper());
        }
        else if (referralTypeId != null)
        {
            referralProtocols = jdbcTemplate.query("select md_ref_ehr_protocol.id, md_ehr_protocol.path,\n" +
                    "md_ehr_protocol.template_path, md_referral.referral_type_id ref_type_id, md_referral.id ref_id from md_ref_ehr_protocol, md_referral, md_ehr_protocol\n" +
                    "where md_referral.id = md_ref_ehr_protocol.referral_id and\n" +
                    "md_ref_ehr_protocol.protocol_id = md_ehr_protocol.id and\n" +
                    "md_referral.referral_type_id = ? and md_ehr_protocol.template_path = ?",
                    new Object[]{ referralTypeId, templatePath }, new ReferralProtocolRowMapper());
        }

        else if (templatePath != null)
        {
            referralProtocols = jdbcTemplate.query("select md_ref_ehr_protocol.id, md_ehr_protocol.path,\n" +
                            "md_ehr_protocol.template_path, md_referral.referral_type_id ref_type_id, md_referral.id ref_id from md_ref_ehr_protocol, md_referral, md_ehr_protocol\n" +
                            "where md_referral.id = md_ref_ehr_protocol.referral_id and\n" +
                            "md_ref_ehr_protocol.protocol_id = md_ehr_protocol.id and\n" +
                            "md_ehr_protocol.template_path LIKE ?",
                    new Object[]{ y }, new ReferralProtocolRowMapper());
        }

        if (referralProtocols == null)
        {
            referralProtocols = Collections.emptyList();
        }

        return referralProtocols;
    }

    public List<ServiceProtocol> getServiceProtocols(Integer serviceId, Integer servicePrototypeId, String templatePath)
    {
        List<ServiceProtocol> serviceProtocols = null;

        if ((serviceId == null) && (templatePath == null) && (servicePrototypeId == null))
        {
            serviceProtocols = Collections.emptyList();
        }
        else
        {
            List params = new LinkedList();
            StringBuilder sql = new StringBuilder();
            sql.append("select md_srv_protocol.id, md_ehr_protocol.path, sr_service.prototype_id,\n" +
                    "                    md_ehr_protocol.template_path, sr_srv_rendered.service_id service_id, sr_srv_rendered.id srv_id from md_srv_protocol, sr_srv_rendered, md_ehr_protocol, sr_service\n" +
                    "                    where sr_srv_rendered.id = md_srv_protocol.srv_rendered_id and\n" +
                    "                    md_srv_protocol.protocol_id = md_ehr_protocol.id and\n" +
                    "                    sr_srv_rendered.service_id = sr_service.id\n");
            if (serviceId != null)
            {
                params.add(serviceId);
                sql.append("and sr_srv_rendered.service_id = ?");
            }
            if (servicePrototypeId != null)
            {
                params.add(servicePrototypeId);
                sql.append("and sr_service.prototype_id = ?");
            }
            if (templatePath != null)
            {
                params.add(templatePath);
                sql.append("and md_ehr_protocol.template_path = ?");
            }
            serviceProtocols = jdbcTemplate.query(sql.toString(), params.toArray(), new ServiceProtocolRowMapper());
        }

        if (serviceProtocols == null)
        {
            serviceProtocols = Collections.emptyList();
        }

        return serviceProtocols;
    }

    public void sendQueryExecuteRequest(ReferralProtocol referralProtocol, EhrProtocolQuery ehrProtocolQuery)
    {
        int taskId = createTask();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("taskId", taskId);
        parameters.put("referralProtocolId", referralProtocol.getId());
        parameters.put("queryIds", Collections.singletonList(ehrProtocolQuery.getId()));

        jmsTemplate.convertAndSend("saveProtocolEhr", parameters);
    }

    public void sendQueryExecuteRequest(ServiceProtocol serviceProtocol, EhrProtocolQuery ehrProtocolQuery)
    {
        int taskId = createTask();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("taskId", taskId);
        parameters.put("srvProtocolId", serviceProtocol.getId());
        parameters.put("queryIds", Collections.singletonList(ehrProtocolQuery.getId()));

        jmsTemplate.convertAndSend("saveProtocolEhr", parameters);
    }

    private int createTask()
    {
        return jdbcTemplate
                .updateReturningKeyInteger("insert into task (id, state) values (nextval('hibernate_sequence'), ?)",
                        "id", TaskState.SCHEDULED.ordinal());
    }


}
