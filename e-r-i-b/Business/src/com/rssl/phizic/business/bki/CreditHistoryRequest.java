package com.rssl.phizic.business.bki;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Nady
 * @ created 16.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������� �������, ��������� ������������
 */
public class CreditHistoryRequest
{
	private static final String[] MONTHS= new String[]{"������","�������","�����","������","���","����","����","�������","��������","�������","�������","�������"};
	private static final String DATE_TEMPLATE = "dd MMMMMMMM yyyy, HH:mm";

	/**
	 * ���� �������
	 */
	private Calendar dateRequest;

	/**
	 * ������� ������� � ���
	 */
	private String reasonForEnquiry;

	/**
	 * ������������ �������
	 * financeType + "��"  + purposeFoFinance
	 */
	 private String creditName;

	/**
	 * ����� ������� ��� �������, �������� ����� ��� ��������� ����
	 */
	private Money creditLimit;

	/**
	 * ����, � ������� ���� ������
	 */
	private String subscriberName;

	/**
	 * ���� ������, ��� ����� ����������
	 */
	private String applicationDate;

	/**
	 * ��� �������
	 */
	private String financeType;

	/**
	 * ���� ������� + durationUnits
	 */
	private Duration durationOfAgreement;

	/**
	 * ���� �����������, � ������� ���� ������
	 */
	private String subscriberOGRN;

	/**
	 *  ��� �����������, � ������� ���� ������
	 */
	private String subscriberTaxNumber;

	/**
	 * ���� �����������, � ������� ���� ������
	 */
	private String subscriberOKPO;


	public Calendar getDateRequest()
	{
		return dateRequest;
	}

	public void setDateRequest(Calendar dateRequest)
	{
		this.dateRequest = dateRequest;
	}

	public String getReasonForEnquiry()
	{
		return reasonForEnquiry;
	}

	public void setReasonForEnquiry(String reasonForEnquiry)
	{
		this.reasonForEnquiry = reasonForEnquiry;
	}

	public String getCreditName()
	{
		return creditName;
	}

	public void setCreditName(String creditName)
	{
		this.creditName = creditName;
	}

	public Money getCreditLimit()
	{
		return creditLimit;
	}

	public void setCreditLimit(Money creditLimit)
	{
		this.creditLimit = creditLimit;
	}

	public String getSubscriberName()
	{
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName)
	{
		this.subscriberName = subscriberName;
	}

	public String getApplicationDate()
	{
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate)
	{
		this.applicationDate = applicationDate;
	}

	public String getFinanceType()
	{
		return financeType;
	}

	public void setFinanceType(String financeType)
	{
		this.financeType = financeType;
	}

	public Duration getDurationOfAgreement()
	{
		return durationOfAgreement;
	}

	public void setDurationOfAgreement(Duration durationOfAgreement)
	{
		this.durationOfAgreement = durationOfAgreement;
	}

	public String getSubscriberOGRN()
	{
		return subscriberOGRN;
	}

	public void setSubscriberOGRN(String subscriberOGRN)
	{
		this.subscriberOGRN = subscriberOGRN;
	}

	public String getSubscriberTaxNumber()
	{
		return subscriberTaxNumber;
	}

	public void setSubscriberTaxNumber(String subscriberTaxNumber)
	{
		this.subscriberTaxNumber = subscriberTaxNumber;
	}

	public String getSubscriberOKPO()
	{
		return subscriberOKPO;
	}

	public void setSubscriberOKPO(String subscriberOKPO)
	{
		this.subscriberOKPO = subscriberOKPO;
	}

	public int getYearOfDateRequest()
	{
		return dateRequest.get(Calendar.YEAR);
	}
	public String getDateRequestDDmmmYYYY()
	{
		return new SimpleDateFormat("dd MMM yyyy").format(dateRequest.getTime());
	}
	public String getEnquiryDateFormat()
	{
		DateFormatSymbols dfs = new DateFormatSymbols();
		dfs.setMonths(MONTHS);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TEMPLATE);
		sdf.setDateFormatSymbols(dfs);
		return sdf.format(dateRequest.getTime());
	}
}
