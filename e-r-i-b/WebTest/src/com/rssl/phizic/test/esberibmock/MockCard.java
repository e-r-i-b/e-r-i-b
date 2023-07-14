package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * Карта (для заглушки)
 * User: Egorovaa
 * Date: 15.11.2011
 * Time: 11:57:59
 */
public class MockCard
{
	private Long id;
	/**
	 * Вид вклада
	 */
	private Long acctCode;
	/**
	 * Подвид вклада
	 */
	private Long acctSubCode;
	/**
	 * Дата выпуска карты
	 */
	private Calendar issDt;
	/**
	 * Дата окончания действия карты
	 */
	private Calendar endDt;
	/**
	 * Дата платежа 
	 */
	private Calendar pmtdt;
	/**
	 * Номер карты
	 */
	private String cardNum;
	/**
	 * Номер карточного счета 
	 */
	private String acctId;
	/**
	 * Номер основной карты. Обязателен в случае, если данная карта – дополнительная, иначе должен отсутствовать
	 */
	private String mainCard;
	/**
	 * Тип дополнительной карты. Обязателен в случае, если данная карта – дополнительная, иначе должен отсутствовать
	 */
	private String additionalCard;
	/**
	 * Тип карты. debit (дебетовая), credit (кредитная), overdraft (овердрафтная)
	 */
	private String cardType;
	/**
	 * Валюта
	 */
	private String acctCur;
	/**
	 * Имя на карте
	 */
	private String cardHolder;
	/**
	 * Идентификатор системы-источника продукта
	 */
	private String systemId;
	/**
	 * Номер филиала, в котором ведется договор
	 */
	private String branchId;
	/**
	 * Номер ОСБ
	 */
	private String agencyId;
	/**
	 * Номер тербанка(ТБ) 
	 */
	private String regionId;
	/**
	 * Номер ОСБ, ведущего счет карты
	 */
	private String rbBrchId;
	/**
	 * Наименование карты
	 */
	private String cardName;
	/**
	 * Код статуса. Active – Активная, Stoped – Неактивная, Blocked – Заблокированная
	 */
	private String bankacctstatuscode;
	/**
	 * Описание статуса карты
	 */
	private String statusdesc;
	/**
	 * Тип остатка
	 */
	private String acctBal;
	/**
	 * Срок действия карты в формате процессинга YYMM 
	 */
	private String endDtForWay;
	/**
	 * Информация о владельце
	 */
	private MockPersonInfo personInfo;
    /**
	 * Дата очередного отчета по счету карты
	 */
	private Calendar nextReportDate;

	public MockPersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(MockPersonInfo personInfo)
	{
		this.personInfo = personInfo;
	}

	public Calendar getPmtdt()
	{
		return pmtdt;
	}

	public void setPmtdt(Calendar pmtdt)
	{
		this.pmtdt = pmtdt;
	}

	public String getEndDtForWay()
	{
		return endDtForWay;
	}

	public void setEndDtForWay(String endDtForWay)
	{
		this.endDtForWay = endDtForWay;
	}

	public String getAcctBal()
	{
		return acctBal;
	}

	public void setAcctBal(String acctBal)
	{
		this.acctBal = acctBal;
	}

	public String getBankacctstatuscode()
	{
		return bankacctstatuscode;
	}

	public void setBankacctstatuscode(String bankacctstatuscode)
	{
		this.bankacctstatuscode = bankacctstatuscode;
	}

	public String getStatusdesc()
	{
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc)
	{
		this.statusdesc = statusdesc;
	}

	public Calendar getIssDt()
	{
		return issDt;
	}

	public void setIssDt(Calendar issDt)
	{
		this.issDt = issDt;
	}

	public Calendar getEndDt()
	{
		return endDt;
	}

	public void setEndDt(Calendar endDt)
	{
		this.endDt = endDt;
	}

	public String getCardName()
	{
		return cardName;
	}

	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	public Long getAcctCode()
	{
		return acctCode;
	}

	public void setAcctCode(Long acctCode)
	{
		this.acctCode = acctCode;
	}

	public Long getAcctSubCode()
	{
		return acctSubCode;
	}

	public void setAcctSubCode(Long acctSubCode)
	{
		this.acctSubCode = acctSubCode;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}

	public String getAcctCur()
	{
		return acctCur;
	}

	public void setAcctCur(String acctCur)
	{
		this.acctCur = acctCur;
	}

	public String getCardHolder()
	{
		return cardHolder;
	}

	public void setCardHolder(String cardHolder)
	{
		this.cardHolder = cardHolder;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getBranchId()
	{
		return branchId;
	}

	public void setBranchId(String branchId)
	{
		this.branchId = branchId;
	}

	public String getAgencyId()
	{
		return agencyId;
	}

	public void setAgencyId(String agencyId)
	{
		this.agencyId = agencyId;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}

	public String getRbBrchId()
	{
		return rbBrchId;
	}

	public void setRbBrchId(String rbBrchId)
	{
		this.rbBrchId = rbBrchId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCardNum()
	{
		return cardNum;
	}

	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}

	public String getAcctId()
	{
		return acctId;
	}

	public void setAcctId(String acctId)
	{
		this.acctId = acctId;
	}

	public String getMainCard()
	{
		return mainCard;
	}

	public void setMainCard(String mainCard)
	{
		this.mainCard = mainCard;
	}

	public String getAdditionalCard()
	{
		return additionalCard;
	}

	public void setAdditionalCard(String additionalCard)
	{
		this.additionalCard = additionalCard;
	}

    public Calendar getNextReportDate()
    {
        return nextReportDate;
    }

    public void setNextReportDate(Calendar nextReportDate)
    {
        this.nextReportDate = nextReportDate;
	}
}
