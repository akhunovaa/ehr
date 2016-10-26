package cz.atria.lsd.util.entity;

/**
 * Created by Alexander on 09.03.14.
 */
public class ReferralProtocol
{
    private int id;

    private String path;
    private String templatePath;
    private Integer referralType;
    private Integer referralId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Integer getReferralType() {
        return referralType;
    }

    public void setReferralType(Integer referralType) {
        this.referralType = referralType;
    }

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }
}
