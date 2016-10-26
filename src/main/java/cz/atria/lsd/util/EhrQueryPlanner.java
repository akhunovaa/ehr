package cz.atria.lsd.util;

import cz.atria.lsd.util.entity.EhrProtocolQuery;
import cz.atria.lsd.util.entity.ReferralProtocol;
import cz.atria.lsd.util.entity.ServiceProtocol;
import cz.atria.lsd.util.service.EhrProtocolQueryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class EhrQueryPlanner
{
    public static void main(String... args)
    {
        String queryIdString = args[0];
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        EhrProtocolQueryService ehrProtocolQueryService = context.getBean(EhrProtocolQueryService.class);
        EhrProtocolQuery query = ehrProtocolQueryService.getEhrProtocolQuery(Integer.parseInt(queryIdString));
        System.out.println("got query: " + query.getId());

        List<ReferralProtocol> referralProtocols = ehrProtocolQueryService
                .getReferralProtocols(query.getReferralTypeId(), query.getTemplatePath());
        int index = 0;
        for (ReferralProtocol referralProtocol : referralProtocols)
        {
            System.out.println("Execution for referral protocol " + referralProtocol.getId() + " started");
            ehrProtocolQueryService.sendQueryExecuteRequest(referralProtocol, query);
            System.out.println("Execution for referral protocol " + referralProtocol.getId() +
                    " finished. Done " + (++index) + " of " + referralProtocols.size());
        }

        List<ServiceProtocol> serviceProtocols = ehrProtocolQueryService.getServiceProtocols(query.getServiceId(),
                query.getServicePrototypeId(), query.getTemplatePath());
        index = 0;
        for (ServiceProtocol serviceProtocol : serviceProtocols)
        {
            System.out.println("Execution for service protocol " + serviceProtocol.getId() + " started");
            ehrProtocolQueryService.sendQueryExecuteRequest(serviceProtocol, query);
            System.out.println("Execution for service protocol " + serviceProtocol.getId() +
                    " finished. Done " + (++index) + " of " + serviceProtocols.size());
        }
    }
}
