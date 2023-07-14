package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoAccountOwnerForm;
import com.rssl.phizic.gate.depo.DepositorAccount;
import com.rssl.phizic.gate.clients.Address;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

/**
 * @author lukina
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountOwnerFormImpl implements DepoAccountOwnerForm
{
	private String depoAccountNumber;
	private Long   loginId;
	private String INN;
	private String surName;
	private String firstName;
	private String partName;
	private Calendar birthday;
	private String birthPlace;
	private Address registrationAddress;
	private Address  residenceAddress;
	private Address  forPensionAddress;
	private Address  mailAddress;
	private Address  workAddress;
	private String regAddressCountry;
	private String  resAddressCountry;
	private String  forPensionAddressCountry;
	private String  mailAddressCountry;
	private String  workAddressCountry;
	private String idType;
	private String idSeries;
	private String idNum;
	private Calendar idIssueDate;
	private Calendar idExpDate;
	private String idIssuedBy;
	private String idIssuedCode;
	private String citizenship;
	private String additionalInfo;
	private String homeTel;
	private String workTel;
	private String mobileTel;
	private String phoneOperator;
	private String privateEmail;
	private String workEmail;
	private String fax;
	private String recIncomeMethod;
	private String recInstructionMethod;
	private String recInfoMethod;

	private Set<DepositorAccount> depositorAccounts = new HashSet<DepositorAccount>();


	private Calendar startDate;

	public Set<DepositorAccount> getDepositorAccounts()
	{
		return depositorAccounts;
	}

	public void setDepositorAccounts(Set<DepositorAccount> depositorAccounts)
	{
		this.depositorAccounts = depositorAccounts;
	}

	public String getDepoAccountNumber()
	{
		return depoAccountNumber;
	}

	public void setDepoAccountNumber(String depoAccountNumber)
	{
		this.depoAccountNumber = depoAccountNumber;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPartName()
	{
		return partName;
	}

	public void setPartName(String partName)
	{
		this.partName = partName;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public Address getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress(Address registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public Address getResidenceAddress()
	{
		return residenceAddress;
	}

	public void setResidenceAddress(Address residenceAddress)
	{
		this.residenceAddress = residenceAddress;
	}

	public Address getForPensionAddress()
	{
		return forPensionAddress;
	}

	public void setForPensionAddress(Address forPensionAddress)
	{
		this.forPensionAddress = forPensionAddress;
	}

	public Address getMailAddress()
	{
		return mailAddress;
	}

	public void setMailAddress(Address mailAddress)
	{
		this.mailAddress = mailAddress;
	}

	public Address getWorkAddress()
	{
		return workAddress;
	}

	public void setWorkAddress(Address workAddress)
	{
		this.workAddress = workAddress;
	}

	public String getRegAddressCountry()
	{
		return regAddressCountry;
	}

	public void setRegAddressCountry(String regAddressCountry)
	{
		this.regAddressCountry = regAddressCountry;
	}

	public String getResAddressCountry()
	{
		return resAddressCountry;
	}

	public void setResAddressCountry(String resAddressCountry)
	{
		this.resAddressCountry = resAddressCountry;
	}

	public String getForPensionAddressCountry()
	{
		return forPensionAddressCountry;
	}

	public void setForPensionAddressCountry(String forPensionAddressCountry)
	{
		this.forPensionAddressCountry = forPensionAddressCountry;
	}

	public String getMailAddressCountry()
	{
		return mailAddressCountry;
	}

	public void setMailAddressCountry(String mailAddressCountry)
	{
		this.mailAddressCountry = mailAddressCountry;
	}

	public String getWorkAddressCountry()
	{
		return workAddressCountry;
	}

	public void setWorkAddressCountry(String workAddressCountry)
	{
		this.workAddressCountry = workAddressCountry;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

	public Calendar getIdIssueDate()
	{
		return idIssueDate;
	}

	public void setIdIssueDate(Calendar idIssueDate)
	{
		this.idIssueDate = idIssueDate;
	}

	public Calendar getIdExpDate()
	{
		return idExpDate;
	}

	public void setIdExpDate(Calendar idExpDate)
	{
		this.idExpDate = idExpDate;
	}

	public String getIdIssuedBy()
	{
		return idIssuedBy;
	}

	public void setIdIssuedBy(String idIssuedBy)
	{
		this.idIssuedBy = idIssuedBy;
	}

	public String getIdIssuedCode()
	{
		return idIssuedCode;
	}

	public void setIdIssuedCode(String idIssuedCode)
	{
		this.idIssuedCode = idIssuedCode;
	}

	public String getCitizenship()
	{
		return citizenship;
	}

	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

	public String getHomeTel()
	{
		return homeTel;
	}

	public void setHomeTel(String homeTel)
	{
		this.homeTel = homeTel;
	}

	public String getWorkTel()
	{
		return workTel;
	}

	public void setWorkTel(String workTel)
	{
		this.workTel = workTel;
	}

	public String getMobileTel()
	{
		return mobileTel;
	}

	public void setPhoneOperator(String phoneOperator)
	{
		this.phoneOperator = phoneOperator;
	}

	public String getPhoneOperator()
	{
		return phoneOperator;
	}

	public void setMobileTel(String mobileTel)
	{
		this.mobileTel = mobileTel;
	}

	public String getPrivateEmail()
	{
		return privateEmail;
	}

	public void setPrivateEmail(String privateEmail)
	{
		this.privateEmail = privateEmail;
	}

	public String getWorkEmail()
	{
		return workEmail;
	}

	public void setWorkEmail(String workEmail)
	{
		this.workEmail = workEmail;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getRecIncomeMethod()
	{
		return recIncomeMethod;
	}

	public void setRecIncomeMethod(String recIncomeMethod)
	{
		this.recIncomeMethod = recIncomeMethod;
	}

	public String getRecInstructionMethod()
	{
		return recInstructionMethod;
	}

	public void setRecInstructionMethod(String recInstructionMethod)
	{
		this.recInstructionMethod = recInstructionMethod;
	}

	public String getRecInfoMethod()
	{
		return recInfoMethod;
	}

	public void setRecInfoMethod(String recInfoMethod)
	{
		this.recInfoMethod = recInfoMethod;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}
}
