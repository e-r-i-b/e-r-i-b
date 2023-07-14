package com.rssl.phizic.business.fund.sender;

import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.fund.Response;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ����� ����������� �� ���� ������� � ����� �����������
 */
public class FundSenderResponse implements Response
{
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDate;
	private String tb;
	private String passport;
	private String externalId;
	private String externalRequestId;
	private FundResponseState state;
	private BigDecimal sum;
	private String message;
	private Long paymentId;
	private String initiatorFirstName;
	private String initiatorSurName;
	private String initiatorPatrName;
	private Calendar initiatorBirthDate;
	private String initiatorTb;
	private String initiatorPassport;
	private String initiatorPhones;
	private String requestMessage;
	private FundRequestState requestState;
	private BigDecimal requiredSum;
	private BigDecimal reccomendSum;
	private String toResource;
	private Calendar closedDate;
	private Calendar expectedClosedDate;
	private Calendar createdDate;
	private FundRequestState viewRequestState;
	private Calendar viewClosedDate;

	/**
	 * @return ��� ����������� �����
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName ��� ����������� �����
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ������� ����������� �����
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName ������� ����������� �����
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return �������� ����������� �����
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName �������� ����������� �����
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return ���� �������� ����������� �����
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate ���� �������� ����������� �����
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * @return ������� ����������� �����
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb ������� ����������� �����
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ��� ����������� �����
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport ��� ����������� �����
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return ������� ������������� ������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId ������� ������������� ������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ������� ������������� �������
	 */
	public String getExternalRequestId()
	{
		return externalRequestId;
	}

	/**
	 * @param externalRequestId ������� ������������� �������
	 */
	public void setExternalRequestId(String externalRequestId)
	{
		this.externalRequestId = externalRequestId;
	}

	/**
	 * @return ������ ��������� ������� �� ���� �������
	 */
	public FundResponseState getState()
	{
		return state;
	}

	/**
	 * @param state ������ ��������� ������� �� ���� �������
	 */
	public void setState(FundResponseState state)
	{
		this.state = state;
	}

	/**
	 * @return ����� ��������
	 */
	public BigDecimal getSum()
	{
		return sum;
	}

	/**
	 * @param sum ����� ��������
	 */
	public void setSum(BigDecimal sum)
	{
		this.sum = sum;
	}

	/**
	 * @return ��������� ����������� �����
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message ��������� ����������� �����
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return ������������� �������� �����
	 */
	public Long getPaymentId()
	{
		return paymentId;
	}

	/**
	 * @param paymentId ������������� �������� �����
	 */
	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	/**
	 * @return ��� ���������� �������
	 */
	public String getInitiatorFirstName()
	{
		return initiatorFirstName;
	}

	/**
	 * @param initiatorFirstName ��� ���������� �������
	 */
	public void setInitiatorFirstName(String initiatorFirstName)
	{
		this.initiatorFirstName = initiatorFirstName;
	}

	/**
	 * @return ������� ���������� �������
	 */
	public String getInitiatorSurName()
	{
		return initiatorSurName;
	}

	/**
	 * @param initiatorSurName ������� ���������� �������
	 */
	public void setInitiatorSurName(String initiatorSurName)
	{
		this.initiatorSurName = initiatorSurName;
	}

	/**
	 * @return �������� ���������� �������
	 */
	public String getInitiatorPatrName()
	{
		return initiatorPatrName;
	}

	/**
	 * @param initiatorPatrName �������� ���������� �������
	 */
	public void setInitiatorPatrName(String initiatorPatrName)
	{
		this.initiatorPatrName = initiatorPatrName;
	}

	/**
	 * @return ���� �������� ����������
	 */
	public Calendar getInitiatorBirthDate()
	{
		return initiatorBirthDate;
	}

	/**
	 * @param initiatorBirthDate ���� �������� ����������
	 */
	public void setInitiatorBirthDate(Calendar initiatorBirthDate)
	{
		this.initiatorBirthDate = initiatorBirthDate;
	}

	/**
	 * @return ����� �������� ����������
	 */
	public String getInitiatorTb()
	{
		return initiatorTb;
	}

	/**
	 * @param initiatorTb ����� �������� ����������
	 */
	public void setInitiatorTb(String initiatorTb)
	{
		this.initiatorTb = initiatorTb;
	}

	/**
	 * @return ��� ����������
	 */
	public String getInitiatorPassport()
	{
		return initiatorPassport;
	}

	/**
	 * @param initiatorPassport ��� ����������
	 */
	public void setInitiatorPassport(String initiatorPassport)
	{
		this.initiatorPassport = initiatorPassport;
	}

	/**
	 * @return �������� ���������� ������� ����� ����������� ","
	 */
	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	/**
	 * ��������� �������, ����������� ������ ��������� ���������� �������
	 * @return  ������ �� ������� ���������
	 */
	public String[] getListInitiatorPhones()
	{
		return initiatorPhones.split(",");
	}

	/**
	 * @param initiatorPhones �������� ���������� ������� ����� ����������� ","
	 */
	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}

	/**
	 * @return ��������� ������������
	 */
	public String getRequestMessage()
	{
		return requestMessage;
	}

	/**
	 * @param requestMessage ��������� ������������
	 */
	public void setRequestMessage(String requestMessage)
	{
		this.requestMessage = requestMessage;
	}

	/**
	 * @return ������ ������� �� ���� �������
	 */
	public FundRequestState getRequestState()
	{
		return requestState;
	}

	/**
	 * @param requestState ������ ������� �� ���� �������
	 */
	public void setRequestState(FundRequestState requestState)
	{
		this.requestState = requestState;
	}

	/**
	 * @return ����������� ����� �����
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum ����������� ����� �����
	 */
	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	/**
	 * @return ��������������� �����
	 */
	public BigDecimal getReccomendSum()
	{
		return reccomendSum;
	}

	/**
	 * @param reccomendSum ��������������� �����
	 */
	public void setReccomendSum(BigDecimal reccomendSum)
	{
		this.reccomendSum = reccomendSum;
	}

	/**
	 * @return ������ ����������
	 */
	public String getToResource()
	{
		return toResource;
	}

	/**
	 * @param toResource ������ ����������
	 */
	public void setToResource(String toResource)
	{
		this.toResource = toResource;
	}

	/**
	 * @return ���� �������� ������� �� ���� �������
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate ���� �������� ������� �� ���� �������
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return �������� ���� �������� ������� �� ���� �������
	 */
	public Calendar getExpectedClosedDate()
	{
		return expectedClosedDate;
	}

	/**
	 * @param expectedClosedDate �������� ���� �������� ������� �� ���� �������
	 */
	public void setExpectedClosedDate(Calendar expectedClosedDate)
	{
		this.expectedClosedDate = expectedClosedDate;
	}

	/**
	 * @return ���� �������� ������� �� ���� �������
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate ���� �������� ������� �� ���� �������
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return ��������� ������� ������ ������� �� ���� �������
	 */
	public FundRequestState getViewRequestState()
	{
		return viewRequestState;
	}

	/**
	 * @param viewRequestState ��������� ������� ������ ������� �� ���� �������
	 */
	public void setViewRequestState(FundRequestState viewRequestState)
	{
		this.viewRequestState = viewRequestState;
	}

	/**
	 * @return ��������� ������� ���� �������� ������� �� ���� �������
	 */
	public Calendar getViewClosedDate()
	{
		return viewClosedDate;
	}

	/**
	 * @param viewClosedDate ��������� ������� ���� �������� ������� �� ���� �������
	 */
	public void setViewClosedDate(Calendar viewClosedDate)
	{
		this.viewClosedDate = viewClosedDate;
	}

	/**
	 * ��������� ������� ����� ���������� ������� ��� ����������� �� ���������
	 * @return ������ ��� � �������: ��� �������� �, ���� �������� ���, �� ��� �
	 */
	public  String getInitiatorFIO(){
		return initiatorFirstName + " " + (StringHelper.isNotEmpty(initiatorPatrName)? initiatorPatrName + " " : "") + initiatorSurName.charAt(0);
	}

	/**
	 * ��������� ������� ����� ����������� ����� ��� ����������� �� ���������
	 * @return ������ ��� � �������: ��� �������� �, ���� �������� ���, �� ��� �
	 */
	public  String getFIO(){
		return firstName + " " + (StringHelper.isNotEmpty(patrName)? patrName + " " : "") + surName.charAt(0);
	}
}
