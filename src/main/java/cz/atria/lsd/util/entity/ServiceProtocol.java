package cz.atria.lsd.util.entity;

/**
 * Created by Alexander on 09.03.14.
 */
public class ServiceProtocol
{
    private int id;

    private String path;
    private String templatePath;
    private Integer serviceId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getTemplatePath()
    {
        return templatePath;
    }

    public void setTemplatePath(String templatePath)
    {
        this.templatePath = templatePath;
    }

    public Integer getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(Integer serviceId)
    {
        this.serviceId = serviceId;
    }
}
