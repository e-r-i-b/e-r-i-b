package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.utils.PropertyHelper;

import java.util.Properties;

/**
 * @see java.security.Principal
 */
public class PrincipalImpl extends PrincipalBase implements UserPrincipal
{
    private CommonLogin  login;
	private AccessPolicy accessPolicy;
	private Properties   properties;
    private boolean isMobileLightScheme = false; //явл€етс€ ли схема доступа клиента в mAPI light-схемой
	private boolean coldPeriod = false;  //действет ли сейчас холодный период

	public PrincipalImpl(CommonLogin login)
	{
		this(login, null, null);
	}

	/**
	 * @param login логин
	 * @param policy тип доступа
	 */
    public PrincipalImpl(CommonLogin login, AccessPolicy policy, Properties properties)
    {
        super(login.getUserId());
        this.login = login;
	    this.accessPolicy = policy;
	    this.properties = properties == null ? null : PropertyHelper.clone(properties);
    }

    public CommonLogin getLogin()
    {
        return login;
    }


	public AccessType getAccessType()
	{
		return accessPolicy.getAccessType();
	}

	public AccessPolicy getAccessPolicy()
	{
		return accessPolicy;
	}

	public String getAccessProperty(String name)
	{
		return properties.getProperty(name);
	}

    public boolean isMobileLightScheme()
    {
        return isMobileLightScheme;
    }

    public void setMobileLightScheme(boolean mobileLightScheme)
    {
        isMobileLightScheme = mobileLightScheme;
    }

	public boolean isMobileLimitedScheme()
	{
		return AccessType.mobileLimited.equals(accessPolicy.getAccessType());
	}

	public void setColdPeriod(boolean coldPeriod)
	{
		this.coldPeriod = coldPeriod;
	}

	public boolean isColdPeriod()
	{
		return coldPeriod;
	}
}