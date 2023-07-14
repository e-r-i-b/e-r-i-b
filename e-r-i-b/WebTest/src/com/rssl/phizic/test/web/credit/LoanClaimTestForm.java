package com.rssl.phizic.test.web.credit;

import org.apache.struts.action.ActionForm;

/**
 * Форма для теста смены статуса заявки
 */
public class LoanClaimTestForm extends ActionForm
{
	/**
	 * номер заявки
	 * Обязательное
	 */
	private String claimNumber;
	/**
	 * Статус заявки
	 * Обязательное
	 */
	private int claimStatus;

	/**
	 * блок с данными по Выданному кредиту
	 */
	private String issuedAmount;
	private String issuedInterestRate;
	private String issuedPeriod;

	/**
	 * блок с данными по Одобренному кредиту
	 */
	private String approvedAmount;
	private String approvedInterestRate;
	private String approvedPeriod;

	/**
	 * блок с данными по предварительно Одобренному кредиту
	 */
	private String preApprovedAmount;
	private String preApprovedInterestRate;
	private String preApprovedPeriod;

	private String needVisitOfficeReason;
	private String applicationNumber;
	private String fioKI;
	private String loginKI;
	private String fioTM;
	private String loginTM;
	private String department;
	private String channel;
	private String agreementDate;
	private String applicationType;
	private String acctIdType;
	private String cardNum;

	private String businessProcess;
	private String insuranceProgram;
	private boolean includeInsuranceFlag;
	private String insurancePremium;

	private String type;
	private String productCode;
	private String subProductCode;
	private String loanAmount;
	private String loanPeriod;
	private String currency;
	private String paymentType;

	private String surName;
	private String firstName;
	private String patrName;
	private String birthDay;
	private String citizen;
	private String documentSeries;
	private String documentNumber;
	private String passportIssueDate;
	private String passportIssueByCode;
	private String passportIssueBy;
	private boolean hasOldPassport;

	private String oldDocumentSeries;
	private String oldDocumentNumber;
	private String oldPassportIssueDate;
	private String oldPassportIssueBy;

	private boolean peapprovedFlag;

	/**
	 * extetnalUID из OFFLINE_DOC
	 */
	private String operUID;

	public String getClaimNumber()
	{
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber)
	{
		this.claimNumber = claimNumber;
	}

	public int getClaimStatus()
	{
		return claimStatus;
	}

	public void setClaimStatus(int claimStatus)
	{
		this.claimStatus = claimStatus;
	}

	public String getApprovedAmount()
	{
		return approvedAmount;
	}

	public void setApprovedAmount(String approvedAmount)
	{
		this.approvedAmount = approvedAmount;
	}

	public String getApprovedInterestRate()
	{
		return approvedInterestRate;
	}

	public void setApprovedInterestRate(String approvedInterestRate)
	{
		this.approvedInterestRate = approvedInterestRate;
	}

	public String getApprovedPeriod()
	{
		return approvedPeriod;
	}

	public void setApprovedPeriod(String approvedPeriod)
	{
		this.approvedPeriod = approvedPeriod;
	}

	public String getIssuedAmount()
	{
		return issuedAmount;
	}

	public void setIssuedAmount(String issuedAmount)
	{
		this.issuedAmount = issuedAmount;
	}

	public String getIssuedInterestRate()
	{
		return issuedInterestRate;
	}

	public void setIssuedInterestRate(String issuedInterestRate)
	{
		this.issuedInterestRate = issuedInterestRate;
	}

	public String getIssuedPeriod()
	{
		return issuedPeriod;
	}

	public void setIssuedPeriod(String issuedPeriod)
	{
		this.issuedPeriod = issuedPeriod;
	}

	public String getPreApprovedAmount()
	{
		return preApprovedAmount;
	}

	public void setPreApprovedAmount(String preApprovedAmount)
	{
		this.preApprovedAmount = preApprovedAmount;
	}

	public String getPreApprovedInterestRate()
	{
		return preApprovedInterestRate;
	}

	public void setPreApprovedInterestRate(String preApprovedInterestRate)
	{
		this.preApprovedInterestRate = preApprovedInterestRate;
	}

	public String getPreApprovedPeriod()
	{
		return preApprovedPeriod;
	}

	public void setPreApprovedPeriod(String preApprovedPeriod)
	{
		this.preApprovedPeriod = preApprovedPeriod;
	}

	public String getOperUID()
	{
		return operUID;
	}

	public void setOperUID(String operUID)
	{
		this.operUID = operUID;
	}

	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}

	public String getNeedVisitOfficeReason()
	{
		return needVisitOfficeReason;
	}

	public void setNeedVisitOfficeReason(String needVisitOfficeReason)
	{
		this.needVisitOfficeReason = needVisitOfficeReason;
	}

	public String getFioKI()
	{
		return fioKI;
	}

	public void setFioKI(String fioKI)
	{
		this.fioKI = fioKI;
	}

	public String getLoginKI()
	{
		return loginKI;
	}

	public void setLoginKI(String loginKI)
	{
		this.loginKI = loginKI;
	}

	public String getFioTM()
	{
		return fioTM;
	}

	public void setFioTM(String fioTM)
	{
		this.fioTM = fioTM;
	}

	public String getLoginTM()
	{
		return loginTM;
	}

	public void setLoginTM(String loginTM)
	{
		this.loginTM = loginTM;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel(String channel)
	{
		this.channel = channel;
	}

	public String getAgreementDate()
	{
		return agreementDate;
	}

	public void setAgreementDate(String agreementDate)
	{
		this.agreementDate = agreementDate;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getSubProductCode()
	{
		return subProductCode;
	}

	public void setSubProductCode(String subProductCode)
	{
		this.subProductCode = subProductCode;
	}

	public String getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public String getLoanPeriod()
	{
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod)
	{
		this.loanPeriod = loanPeriod;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
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

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(String birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getCitizen()
	{
		return citizen;
	}

	public void setCitizen(String citizen)
	{
		this.citizen = citizen;
	}

	public String getDocumentSeries()
	{
		return documentSeries;
	}

	public void setDocumentSeries(String documentSeries)
	{
		this.documentSeries = documentSeries;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getPassportIssueDate()
	{
		return passportIssueDate;
	}

	public void setPassportIssueDate(String passportIssueDate)
	{
		this.passportIssueDate = passportIssueDate;
	}

	public String getPassportIssueByCode()
	{
		return passportIssueByCode;
	}

	public void setPassportIssueByCode(String passportIssueByCode)
	{
		this.passportIssueByCode = passportIssueByCode;
	}

	public String getPassportIssueBy()
	{
		return passportIssueBy;
	}

	public void setPassportIssueBy(String passportIssueBy)
	{
		this.passportIssueBy = passportIssueBy;
	}

	public boolean isHasOldPassport()
	{
		return hasOldPassport;
	}

	public void setHasOldPassport(boolean hasOldPassport)
	{
		this.hasOldPassport = hasOldPassport;
	}

	public String getOldDocumentSeries()
	{
		return oldDocumentSeries;
	}

	public void setOldDocumentSeries(String oldDocumentSeries)
	{
		this.oldDocumentSeries = oldDocumentSeries;
	}

	public String getOldDocumentNumber()
	{
		return oldDocumentNumber;
	}

	public void setOldDocumentNumber(String oldDocumentNumber)
	{
		this.oldDocumentNumber = oldDocumentNumber;
	}

	public String getOldPassportIssueDate()
	{
		return oldPassportIssueDate;
	}

	public void setOldPassportIssueDate(String oldPassportIssueDate)
	{
		this.oldPassportIssueDate = oldPassportIssueDate;
	}

	public String getOldPassportIssueBy()
	{
		return oldPassportIssueBy;
	}

	public void setOldPassportIssueBy(String oldPassportIssueBy)
	{
		this.oldPassportIssueBy = oldPassportIssueBy;
	}

	public String getApplicationType()
	{
		return applicationType;
	}

	public void setApplicationType(String applicationType)
	{
		this.applicationType = applicationType;
	}

	public String getAcctIdType()
	{
		return acctIdType;
	}

	public void setAcctIdType(String acctIdType)
	{
		this.acctIdType = acctIdType;
	}

	public String getCardNum()
	{
		return cardNum;
	}

	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}

	public String getBusinessProcess()
	{
		return businessProcess;
	}

	public void setBusinessProcess(String businessProcess)
	{
		this.businessProcess = businessProcess;
	}

	public String getInsuranceProgram()
	{
		return insuranceProgram;
	}

	public void setInsuranceProgram(String insuranceProgram)
	{
		this.insuranceProgram = insuranceProgram;
	}

	public boolean isIncludeInsuranceFlag()
	{
		return includeInsuranceFlag;
	}

	public void setIncludeInsuranceFlag(boolean includeInsuranceFlag)
	{
		this.includeInsuranceFlag = includeInsuranceFlag;
	}

	public String getInsurancePremium()
	{
		return insurancePremium;
	}

	public void setInsurancePremium(String insurancePremium)
	{
		this.insurancePremium = insurancePremium;
	}

	public boolean getPeapprovedFlag()
	{
		return peapprovedFlag;
	}

	public void setPeapprovedFlag(boolean peapprovedFlag)
	{
		this.peapprovedFlag = peapprovedFlag;
	}
}
