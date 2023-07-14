package com.rssl.phizicgate.rsV55.demand;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 13.03.2007
 * Time: 16:31:07
 * To change this template use File | Settings | File Templates.
 */
public class ContactDocument extends PaymentDemandBase
{
	private Long autoKey;
	private String pointPay;
	private String pointReceiv;
	private Long numTransference;
	private String numTransferenceStr;
	private Long psNum;
	private Long isCurr;
	private BigDecimal summa;
	private BigDecimal commission;
	private Long commCodeCurrency;
	private Date dateTransference;
	private Time timeTransference;
	private Date endDateTransference;
	private Long codeType = Long.valueOf(1);
	private String state = "Î";
	private Long codeClientPay;
	private String lastNamePay;
	private String firstNamePay;
	private String secondNamePay;
	private Date bornDatePay;
	private String countryPay = "RUS";
	private String addressPay;
	private String paperSeriesPay;
	private String paperNumberPay;
	private Date paperIssuedDatePay;
	private String paperIssuerPay;
	private String phonePay;
	private String lastNameReceiv;
	private String firstNameReceiv;
	private String secondNameReceiv;
	private Date bornDateReceiv;
	private String addInfo;
	private Long sent = Long.valueOf(1);
	private TransferBos receiver;

	public Long getAutoKey()
	{
		return autoKey;
	}

	public void setAutoKey(Long autoKey)
	{
		this.autoKey = autoKey;
	}

	public String getPayerPoint()
	{
		return pointPay;
	}

	public void setPayerPoint(String pointPay)
	{
		this.pointPay = pointPay;
	}

	public String getReceiverPoint()
	{
		return pointReceiv;
	}

	public void setReceiverPoint(String pointReceiv)
	{
		this.pointReceiv = pointReceiv;
	}

	public Long getPaymentSystemNumber()
	{
		return psNum;
	}

	public void setPaymentSystemNumber(Long psNum)
	{
		this.psNum = psNum;
	}

	public BigDecimal getSumma()
	{
		return summa;
	}

	public void setSumma(BigDecimal summa)
	{
		this.summa = summa;
	}

	public BigDecimal getCommission()
	{
		return commission;
	}

	public void setCommission(BigDecimal com)
	{
		this.commission = com;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public Long getPayerCode()
	{
		return codeClientPay;
	}

	public void setPayerCode(Long codeClientPay)
	{
		this.codeClientPay = codeClientPay;
	}

	public String getPayerLastName()
	{
		return lastNamePay;
	}

	public void setPayerLastName(String lastNamePay)
	{
		this.lastNamePay = lastNamePay;
	}

	public String getPayerFirstName()
	{
		return firstNamePay;
	}

	public void setPayerFirstName(String firstNamePay)
	{
		this.firstNamePay = firstNamePay;
	}

	public String getPayerSecondName()
	{
		return secondNamePay;
	}

	public void setPayerSecondName(String secondNamePay)
	{
		this.secondNamePay = secondNamePay;
	}

	public Date getPayerBornDate()
	{
		return bornDatePay;
	}

	public void setPayerBornDate(Date bornDatePay)
	{
		this.bornDatePay = bornDatePay;
	}

	public String getPayerCountry()
	{
		return countryPay;
	}

	public void setPayerCountry(String countryPay)
	{
		this.countryPay = countryPay;
	}

	public String getPayerAddress()
	{
		return addressPay;
	}

	public void setPayerAddress(String addressPay)
	{
		this.addressPay = addressPay;
	}

	public String getPayerPaperSeries()
	{
		return paperSeriesPay;
	}

	public void setPayerPaperSeries(String paperSeriesPay)
	{
		this.paperSeriesPay = paperSeriesPay;
	}

	public String getPayerPaperNumber()
	{
		return paperNumberPay;
	}

	public void setPayerPaperNumber(String paperNumberPay)
	{
		this.paperNumberPay = paperNumberPay;
	}

	public Long getTransferenceNumber()
	{
		return numTransference;
	}

	public void setTransferenceNumber(Long numTransference)
	{
		this.numTransference = numTransference;
		this.numTransferenceStr = numTransference.toString();
	}

	public void setTransferenceNumberStr(String numTransferenceStr)
	{
		this.numTransferenceStr = numTransferenceStr;
	}

	public String getTransferenceNumberStr()
	{
		return numTransferenceStr;
	}

	public Long getIsCurr()
	{
		return isCurr;
	}

	public void setIsCurr(boolean isCurr)
	{
		if (isCurr)
		   this.isCurr = 1L;
		else
		   this.isCurr = 0L;
	}

	public Long getCommissionCurrencyCode()
	{
		return commCodeCurrency;
	}

	public void setCommissionCurrencyCode(Long commCodeCurrency)
	{
		this.commCodeCurrency = commCodeCurrency;
	}

	public Date getTransferenceDate()
	{
		return dateTransference;
	}

	public void setTransferenceDate(Date dateTransference)
	{
		this.dateTransference = dateTransference;
	}

	public Time getTransferenceTime()
	{
		return timeTransference;
	}

	public void setTransferenceTime(Time timeTransference)
	{
		this.timeTransference = timeTransference;
	}

	public Date getTransferenceDateEnd()
	{
		return endDateTransference;
	}

	public void setTransferenceDateEnd(Date endDateTransference)
	{
		this.endDateTransference = endDateTransference;
	}

	public Long getStatusType()
	{
		return codeType;
	}

	public void setStatusType(Long codeType)
	{
		this.codeType = codeType;
	}

	public Date getPayerPaperIssuedDate()
	{
		return paperIssuedDatePay;
	}

	public void setPayerPaperIssuedDate(Date paperIssuedDatePay)
	{
		this.paperIssuedDatePay = paperIssuedDatePay;
	}

	public String getPayerPaperIssuer()
	{
		return paperIssuerPay;
	}

	public void setPayerPaperIssuer(String paperIssuerPay)
	{
		this.paperIssuerPay = paperIssuerPay;
	}

	public String getPayerPhone()
	{
		return phonePay;
	}

	public void setPayerPhone(String phonePay)
	{
		this.phonePay = phonePay;
	}

	public String getReceiverLastName()
	{
		return lastNameReceiv;
	}

	public void setReceiverLastName(String lastNameReceiv)
	{
		this.lastNameReceiv = lastNameReceiv;
	}

	public String getReceiverFirstName()
	{
		return firstNameReceiv;
	}

	public void setReceiverFirstName(String firstNameReceiv)
	{
		this.firstNameReceiv = firstNameReceiv;
	}

	public String getReceiverSecondName()
	{
		return secondNameReceiv;
	}

	public void setReceiverSecondName(String secondNameReceiv)
	{
		this.secondNameReceiv = secondNameReceiv;
	}

	public Date getReceiverBornDate()
	{
		return bornDateReceiv;
	}

	public void setReceiverBornDate(Date bornDateReceiv)
	{
		this.bornDateReceiv = bornDateReceiv;
	}

	public String getAdditionalIinformation()
	{
		return addInfo;
	}

	public void setAdditionalIinformation(String addInfo)
	{
		this.addInfo = addInfo;
	}

	public Long getSent()
	{
		return sent;
	}

	public void setSent(Long sent)
	{
		this.sent = sent;
	}

	public TransferBos getReceiverBos()
	{
		return receiver;
	}

	public void setReceiverBos(TransferBos transferBos)
	{
		transferBos.setApplicationKey(getApplicationKey());
		transferBos.setApplicationKind(getApplicationKind());
		transferBos.setBranch(getDepartment());

		this.receiver = transferBos;
	}
}
