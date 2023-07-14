package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * ОМС (для заглушки)
 @author: Egorovaa
 @ created: 24.02.2012
 @ $Author$
 @ $Revision$
 */
public class MockIMAccount
{
	private Long id;
	/**
	 * Номер филиала(ВСП)
	 */
	private String branchId;
	/**
	 * Номер подразделения(ОСБ)
	 */
	private String agencyId;
	/**
	 * Номер тербанка(ТБ) 
	 */
	private String regionId;
	/**
	 * Номер ОСБ, ведущего карт счет 
	 */
	private String rbBrchId;
	/**
	 * Идентификатор системы-источника продукта
	 */
	private String systemId;
	/**
	 * Номер счета
	 */
	private String acctId;
	/**
	 * Вид металла 
	 */
	private String acctCur;
	/**
	 * Краткое наименование на русском языке, готовое для отображения пользователю
	 */
	private String acctName;
	/**
	 * Дата открытия счета
	 */
	private Calendar startDate;
	/**
	 * Дата закрытия счета. Обязательно для закрытых счетов.
	 */
	private Calendar endDate;
	/**
	 * Статус ОМС (Opened – Открыт, Closed – Закрыт)
	 */
	private String status;
	/**
	 * Номер договора счета ОМС
	 */
	private String agreementNumber;
	/**
	 * Строка с записью об остатке и лимите списания.
	 * Запись вида Avail - [остаток], AvailCash - [сумма списания]
	 * Остаток в граммах (значение дробной части должно быть указано с точностью до второго знака)
	 */
	private String acctBal;
	/**
	 * Информация о владельце
	 */
	private MockPersonInfo personInfo;

	public MockPersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(MockPersonInfo personInfo)
	{
		this.personInfo = personInfo;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getAcctId()
	{
		return acctId;
	}

	public void setAcctId(String acctId)
	{
		this.acctId = acctId;
	}

	public String getAcctCur()
	{
		return acctCur;
	}

	public void setAcctCur(String acctCur)
	{
		this.acctCur = acctCur;
	}

	public String getAcctName()
	{
		return acctName;
	}

	public void setAcctName(String acctName)
	{
		this.acctName = acctName;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAcctBal()
	{
		return acctBal;
	}

	public void setAcctBal(String acctBal)
	{
		this.acctBal = acctBal;
	}
}
