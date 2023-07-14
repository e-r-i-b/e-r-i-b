package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.log.operations.LogParametersInfoBuilder;
import com.rssl.phizic.messaging.TranslitMode;

import java.math.BigDecimal;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeImpl implements Employee
{
    private Long id;
    private Long externalId;

    private BankLogin login;
    private String    firstName;
    private String    surName;
    private String    patrName;
    private String    info        = "";
    private String    email       = "";
    private String    mobilePhone = "";
    private TranslitMode SMSFormat = TranslitMode.DEFAULT;
	private Long      departmentId;
	private BigDecimal      loanOfficeId;
	private boolean CAAdmin = false;
	private boolean vspEmployee = false;
	private boolean allDepartments;
	private String managerId;
	private String managerPhone;
	private String managerEMail;
	private String managerLeadEMail;
	private Long channelId;
	private String sudirLogin;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public BankLogin getLogin()
    {
        return login;
    }

    public void setLogin(BankLogin login)
    {
        this.login = login;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

	public Long getExternalId()
	{
		return externalId;
	}

	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

    public String getSurName()
    {
        return surName;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public String getPatrName()
    {
        return patrName;
    }

    public void setPatrName(String patrName)
    {
        this.patrName = patrName;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public TranslitMode getSMSFormat() {
        return SMSFormat;
    }

    public void setSMSFormat(TranslitMode SMSFormat) {
        this.SMSFormat = SMSFormat;
    }

    public String getFullName()
	{
		StringBuffer buf = new StringBuffer();

		if (getSurName() != null)
		{
			buf.append(getSurName());
		}

		if (getFirstName() != null)
		{
			if(buf.length() > 0) buf.append(" ");
			buf.append(getFirstName());
		}

		if (getPatrName() != null)
		{
			if (buf.length() > 0) buf.append(" ");
			buf.append(getPatrName());
		}

		return buf.toString();
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String toString()
	{
		return getFullName();
	}

	public BigDecimal getLoanOfficeId()
	{
		return loanOfficeId;
	}

	public void setLoanOfficeId(BigDecimal loanOfficeId)
	{
		this.loanOfficeId = loanOfficeId;
	}

	public StringBuilder getLogEmployeeInfo()  throws BusinessException
	{
		LogParametersInfoBuilder stringBuilder = new LogParametersInfoBuilder();
		return stringBuilder.getEmployeeLogInfo(this);
	}

	public boolean isCAAdmin()
	{
		return CAAdmin;
	}

	public void setCAAdmin(boolean CAAdmin)
	{
		this.CAAdmin = CAAdmin;
	}

	public boolean isVSPEmployee()
	{
		return vspEmployee;
	}

	public void setVSPEmployee(boolean vspEmployee)
	{
		this.vspEmployee = vspEmployee;
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	public String getManagerPhone()
	{
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone)
	{
		this.managerPhone = managerPhone;
	}

	public String getManagerEMail()
	{
		return managerEMail;
	}

	public void setManagerEMail(String managerEMail)
	{
		this.managerEMail = managerEMail;
	}

	public String getManagerLeadEMail()
	{
		return managerLeadEMail;
	}

	public void setManagerLeadEMail(String managerLeadEMail)
	{
		this.managerLeadEMail = managerLeadEMail;
	}

	public Long getChannelId()
	{
		return channelId;
	}

	public String getSUDIRLogin()
	{
		return sudirLogin;
	}

	public void setSUDIRLogin(String sudirLogin)
	{
		this.sudirLogin = sudirLogin;
	}

	public void setChannelId(Long channelId)
	{
		this.channelId = channelId;
	}
}
