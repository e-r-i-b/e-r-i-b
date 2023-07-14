package com.rssl.phizgate.ext.sbrf.etsm;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Nady
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfficeLoanClaim
{
	private String applicationNumber;
	private int state;
	private String needVisitOfficeReason;

	private String fioKI;
	private String loginKI;
	private String fioTM;
	private String loginTM;
	private String department;
	private String channel;
	private Calendar agreementDate;

	private String type;
	private String productCode;
	private String subProductCode;
	private BigDecimal productAmount;
	private long productPeriod;
	private BigDecimal loanApprovedAmount;
	private long loanApprovedPeriod;
	private BigDecimal loanApprovedRate;
	private String currency;
	private String paymentType;

	private String surName;
	private String firstName;
	private String patrName;
	private Calendar birthDay;
	private String citizen;
	private String documentSeries;
	private String documentNumber;
	private Calendar passportIssueDate;
	private String passportIssueByCode;
	private String passportIssueBy;
	private boolean hasOldPassport;

	private String oldDocumentSeries;
	private String oldDocumentNumber;
	private Calendar oldPassportIssueDate;
	private String oldPassportIssueBy;
	private Calendar createDate;

	private String typeOfIssue;
	private String accountNumber;
	private String cardNumber;

	private String productName;
	private boolean preapproved;
	private String registrationAddresses;


	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
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

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public void setAgreementDate(Calendar agreementDate)
	{
		this.agreementDate = agreementDate;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
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

	public BigDecimal getProductAmount()
	{
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount)
	{
		this.productAmount = productAmount;
	}

	public long getProductPeriod()
	{
		return productPeriod;
	}

	public void setProductPeriod(long productPeriod)
	{
		this.productPeriod = productPeriod;
	}

	public Money getLoanAmount()
	{
		if (StringHelper.isEmpty(currency))
			return null;

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			Currency currency = currencyService.findByAlphabeticCode(getCurrency());
			return new Money(loanApprovedAmount, currency);
		}
		catch (GateException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public long getLoanPeriod()
	{
		return loanApprovedPeriod;
	}

	public BigDecimal getLoanRate()
	{
		return loanApprovedRate;
	}

	public BigDecimal getLoanApprovedAmount()
	{
		return loanApprovedAmount;
	}

	public void setLoanApprovedAmount(BigDecimal loanApprovedAmount)
	{
		this.loanApprovedAmount = loanApprovedAmount;
	}

	public long getLoanApprovedPeriod()
	{
		return loanApprovedPeriod;
	}

	public void setLoanApprovedPeriod(long loanApprovedPeriod)
	{
		this.loanApprovedPeriod = loanApprovedPeriod;
	}

	public BigDecimal getLoanApprovedRate()
	{
		return loanApprovedRate;
	}

	public void setLoanApprovedRate(BigDecimal loanApprovedRate)
	{
		this.loanApprovedRate = loanApprovedRate;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
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

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
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

	public Calendar getPassportIssueDate()
	{
		return passportIssueDate;
	}

	public void setPassportIssueDate(Calendar passportIssueDate)
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

	public Calendar getOldPassportIssueDate()
	{
		return oldPassportIssueDate;
	}

	public void setOldPassportIssueDate(Calendar oldPassportIssueDate)
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

	public Calendar getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Calendar createDate)
	{
		this.createDate = createDate;
	}

	public String getTypeOfIssue()
	{
		return typeOfIssue;
	}

	public void setTypeOfIssue(String typeOfIssue)
	{
		this.typeOfIssue = typeOfIssue;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public boolean isPreapproved()
	{
		return preapproved;
	}

	public void setPreapproved(boolean preapproved)
	{
		this.preapproved = preapproved;
	}

	public String getRegistrationAddresses()
	{
		return registrationAddresses;
	}

	public void setRegistrationAddresses(String registrationAddresses)
	{
		this.registrationAddresses = registrationAddresses;
	}
}
