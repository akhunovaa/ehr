package cz.atria.lsd.util.entity;

/**
 * Created by Alexander on 09.03.14.
 */
public class EhrProtocolQuery
{
    private int id;

    private Integer serviceId;

    private Integer servicePrototypeId;

    private Integer referralTypeId;

    private String templatePath;

    public Integer getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(Integer serviceId)
    {
        this.serviceId = serviceId;
    }

    public Integer getServicePrototypeId()
    {
        return servicePrototypeId;
    }

    public void setServicePrototypeId(Integer servicePrototypeId) {
        this.servicePrototypeId = servicePrototypeId;
    }

    public Integer getReferralTypeId() {
        return referralTypeId;
    }

    public void setReferralTypeId(Integer referralTypeId) {
        this.referralTypeId = referralTypeId;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
