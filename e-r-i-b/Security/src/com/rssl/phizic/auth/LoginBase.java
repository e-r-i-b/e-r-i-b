package com.rssl.phizic.auth;

import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class LoginBase implements CommonLogin
{
	private static final SecurityService securityService = new SecurityService();

    private Long id;
	private String userId;
	private long wrongLoginAttempts;
    private boolean deleted;
	private Calendar logonDate;
	private Calendar lastLogonDate;
    private String csaUserId;
	private String csaUserAlias;
	private String ipAddress;
	private String lastIpAddress;
	private String lastLogonCardNumber;
	private String lastLogonCardDepartment;
	private boolean mobileBankConnected;
    private boolean firstLogin;
	private String lastLogonCardOSB;
	private String lastLogonCardVSP;
	private String lastLogonCardTB;
	private String lastLogonType;
	private String lastLogonParameter;

    public String getLastIpAddress()
	{
		return lastIpAddress;
	}

	public void setLastIpAddress(String lastIpAddress)
	{
		this.lastIpAddress = lastIpAddress;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getCsaUserId()
	{
		return csaUserId;
	}

	public void setCsaUserId(String csaUserId)
	{
		this.csaUserId = csaUserId;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

	public long getWrongLoginAttempts()
	{
		return wrongLoginAttempts;
	}

	public void setWrongLoginAttempts(long wrongLoginAttempts)
	{
		this.wrongLoginAttempts = wrongLoginAttempts;
	}

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public boolean equals(Object o)
	{
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    final LoginBase login = (LoginBase) o;

	    if (!id.equals(login.id)) return false;
		if (userId != null ? !userId.equals(login.userId) : login.userId != null)
			return false;

	    return true;
	}

	public int hashCode()
     {
          int result;
          result = (id != null ? id.hashCode() : 0);
          result = 31 * result + (userId != null ? userId.hashCode() : 0);
          return result;
     }


	public List<LoginBlock> getBlocks()
	{
		List<LoginBlock> sd;
		return securityService.getBlocksForLogin(this, new GregorianCalendar().getTime(), null);
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	public Calendar getLogonDate()
	{
		return logonDate;
	}

	public void setLogonDate(Calendar logonDate)
	{
		this.logonDate = logonDate;
	}

	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}

	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}

	public String getLastLogonCardNumber()
	{
		return lastLogonCardNumber;
	}

	public void setLastLogonCardNumber(String lastLogonCardNumber)
	{
		this.lastLogonCardNumber = lastLogonCardNumber;
	}

	public String getLastLogonCardDepartment()
	{
		return lastLogonCardDepartment;
	}

	public void setLastLogonCardDepartment(String lastLogonCardDepartment)
	{
		this.lastLogonCardDepartment = lastLogonCardDepartment;
	}

	public boolean isMobileBankConnected()
	{
		return mobileBankConnected;
	}

	public void setMobileBankConnected(boolean mobileBankConnected)
	{
		this.mobileBankConnected = mobileBankConnected;
	}

    public void setFirstLogin(boolean firstLogin)
    {
       this.firstLogin = firstLogin;
    }

    public boolean isFirstLogin()
    {
        return firstLogin;
    }

	public String getLastLogonCardOSB()
	{
		return lastLogonCardOSB;
	}

	public void setLastLogonCardOSB(String lastLogonCardOSB)
	{
		this.lastLogonCardOSB = lastLogonCardOSB;
	}

	public String getLastLogonCardVSP()
	{
		return lastLogonCardVSP;
	}

	public void setLastLogonCardVSP(String lastLogonCardVSP)
	{
		this.lastLogonCardVSP = lastLogonCardVSP;
	}

	public String getLastLogonCardTB()
	{
		return lastLogonCardTB;
	}

	public void setLastLogonCardTB(String lastLogonCardTB)
	{
		this.lastLogonCardTB = lastLogonCardTB;
	}

	/**
	 * Алиас под которым зашел клиент.
	 * @return алиас.
	 */
	public String getCsaUserAlias()
	{
		return csaUserAlias;
	}

	public void setCsaUserAlias(String csaUserAlias)
	{
		this.csaUserAlias = csaUserAlias;
	}

	public String getLastLogonType()
	{
		return lastLogonType;
	}

	public void setLastLogonType(String lastLogonType)
	{
		this.lastLogonType = lastLogonType;
	}

	public String getLastLogonParameter()
	{
		return lastLogonParameter;
	}

	public void setLastLogonParameter(String lastLogonParameter)
	{
		this.lastLogonParameter = lastLogonParameter;
	}
}
