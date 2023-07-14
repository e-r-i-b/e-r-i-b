package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.PolicyDetails;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceAppImpl implements InsuranceApp
{
	private String  id;
	private String  reference;
	private String  businessProcess;
	private String  productType;
	private String  status;
	private String  company;
	private String  program;
	private String  SNILS;
	private String  additionalInfo;
	private String  risk;
	private Calendar startDate;
	private Calendar endDate;
	private Money amount;
	private PolicyDetails policyDetails;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getReference()
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	public String getBusinessProcess()
	{
		return businessProcess;
	}

	public void setBusinessProcess(String businessProcess)
	{
		this.businessProcess = businessProcess;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCompany()
	{
		return company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public String getProgram()
	{
		return program;
	}

	public void setProgram(String program)
	{
		this.program = program;
	}

	public String getSNILS()
	{
		return SNILS;
	}

	public void setSNILS(String SNILS)
	{
		this.SNILS = SNILS;
	}

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

	public String getRisk()
	{
		return risk;
	}

	public void setRisk(String risk)
	{
		this.risk = risk;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public PolicyDetails getPolicyDetails()
	{
		return policyDetails;
	}

	public void setPolicyDetails(PolicyDetails policyDetails)
	{
		this.policyDetails = policyDetails;
	}
}
