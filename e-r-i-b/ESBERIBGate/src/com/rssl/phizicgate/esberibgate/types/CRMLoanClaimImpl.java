package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.crm.CRMLoanClaim;
import com.rssl.phizic.gate.crm.ChannelType;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 12.12.14
 */
public class CRMLoanClaimImpl implements CRMLoanClaim
{
	//=====================CRMLoanClaim
	private String number;
   	private ChannelType channelType;
   	private String employerLogin;
   	private String employerFIO;

   	private String firstName;
   	private String lastName;
   	private String middleName;
   	private Calendar birthDay;
   	private String passportNumber;
   	private String wayCardNumber;
   	private String campaingMemberId;
   	private PhoneNumber mobilePhone;
   	private PhoneNumber workPhone;
   	private PhoneNumber addPhone;

   	private String productName;
   	private String targetProductType;
   	private String targetProduct;
   	private String productType;
	private String targetProductSub;
	private Money loanAmount;
	private int duration;
	private BigDecimal interestRate;
	private String comments;
	private Code code;
	private Calendar plannedVisitDate;
	private Integer plannedVisitTime;

	//=============== GateDocument
	private Long id;

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channel)
	{
		this.channelType = channel;
	}

	public String getEmployerLogin()
	{
		return employerLogin;
	}

	public void setEmployerLogin(String employerLogin)
	{
		this.employerLogin = employerLogin;
	}

	public String getEmployerFIO()
	{
		return employerFIO;
	}

	public void setEmployerFIO(String employerFIO)
	{
		this.employerFIO = employerFIO;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getPassportNumber()
	{
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber)
	{
		this.passportNumber = passportNumber;
	}

	public String getWayCardNumber()
	{
		return wayCardNumber;
	}

	public void setWayCardNumber(String wayCardNumber)
	{
		this.wayCardNumber = wayCardNumber;
	}

	public String getCampaingMemberId()
	{
		return campaingMemberId;
	}

	public void setCampaingMemberId(String campaingMemberId)
	{
		this.campaingMemberId = campaingMemberId;
	}

	public PhoneNumber getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(PhoneNumber mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public PhoneNumber getWorkPhone()
	{
		return workPhone;
	}

	public void setWorkPhone(PhoneNumber workPhone)
	{
		this.workPhone = workPhone;
	}

	public PhoneNumber getAddPhone()
	{
		return addPhone;
	}

	public void setAddPhone(PhoneNumber addPhone)
	{
		this.addPhone = addPhone;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getTargetProductType()
	{
		return targetProductType;
	}

	public void setTargetProductType(String targetProductType)
	{
		this.targetProductType = targetProductType;
	}

	public String getTargetProduct()
	{
		return targetProduct;
	}

	public void setTargetProduct(String targetProduct)
	{
		this.targetProduct = targetProduct;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getTargetProductSub()
	{
		return targetProductSub;
	}

	public void setTargetProductSub(String targetProductSub)
	{
		this.targetProductSub = targetProductSub;
	}

	public Money getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(Money loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuratione(int duration)
	{
		this.duration = duration;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public Code getOfficeCode()
	{
		return code;
	}

	public void setOfficeCode(Code code)
	{
		this.code = code;
	}

	public Calendar getPlannedVisitDate()
	{
		return plannedVisitDate;
	}

	public void setPlannedVisitDate(Calendar plannedVisitDate)
	{
		this.plannedVisitDate = plannedVisitDate;
	}

	public Integer getPlannedVisitTime()
	{
		return plannedVisitTime;
	}

	public void setPlannedVisitTime(Integer plannedVisitTime)
	{
		this.plannedVisitTime = plannedVisitTime;
	}
}
