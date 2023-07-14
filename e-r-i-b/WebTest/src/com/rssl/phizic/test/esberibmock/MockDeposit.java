package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * Вклад (для заглушки)
 * User: Egorovaa
 * Date: 29.11.2011
 * Time: 8:16:03
 */
public class MockDeposit
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
	 * Номер филиала, в котором открыт счет
	 */
	private String branchId;
	/**
	 * Идентификатор отделения (ОСБ), в котором открыт счет
	 */
	private String agencyId;
	/**
	 * Номер тербанка(ТБ) 
	 */
	private String regionId;
	/**
	 * Номер ОСБ, ведущего счет по вкладу
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
	 * Валюта счета
	 */
	private String acctCur;
	/**
	 * Краткое наименование счета из поля qsname справочника qvb ЦАС НСИ Должен приходить текст
	 * на русском языке полностью готовый для отображения пользователю как есть.
	 */
	private String acctName;
	/**
	 * Дата открытия счета
	 */
	private Calendar openDate;
	/**
	 * Дата закрытия счета
	 */
	private Calendar endDate;
	/**
	 * Срок вклада в днях 
	 */
	private Long period;
	/**
	 * Процентная ставка по вкладу
	 */
	private BigDecimal rate;
	/**
	 * Разрешено ли списание со счета
	 */
	private Boolean creditAllowed;
	/**
	 * Разрешено ли зачисление на счет
	 */
	private Boolean debitAllowed;
	/**
	 * Разрешена ли пролонгация на следующий срок
	 */
	private Boolean prolongationAllowed;
	/**
	 * Разрешено ли списание со счета в других ОСБ ( признак «Зеленая улица»)
	 */
	private Boolean creditCrossAgencyAllowed;
	/**
	 * Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая улица»)
	 */
	private Boolean debitCrossAgencyAllowed;
	/**
	 * Признак наличия сберкнижки
	 */
	private Boolean passBook;
	/**
	 * Требуется ли возвращать информацию о правах доступа к счету (тег BankAcctPermiss выходных данных)
	 */
	private Boolean needBankAcctPermiss;
	/**
	 * Требуется ли  отображать в системе дополнительную информацию по вкладу (тег DepAccInfo выходных данных)
	 */
	private Boolean needDepAccInfo;
	/**
	 * Статус счета :Opened – Открыт,.Closed – Закрыт, Arrested – Арестован, Lost-passbook – Утеряна сберкнижка.
	 */
	private String status;
	/**
	 * Информация, предназначенная для перечисления процентов. Номер счета
	 */
	private String interestOnDepositAcctId;
	/**
	 * Информация, предназначенная для перечисления процентов. Номер карты
	 */
	private String interestOnDepositCardNum;
	/**
	 * Права доступа к счету или карте. Для ЦОД обязатльно 2. Указывается код фронтальной системы, для которой
	 * проставляется право, значения фиксированные («BP_ES» - СмартВиста, «BP_ERIB» - СБОЛ),
	 *  а также признак отсутствия указанного права(Если false – то счет виден)
	 */
	private String bankAcctPermiss;
	/**
	 * Тип остатка
	 */
	private String acctBal;
	/**
	 * Информация о владельце
	 */
	private MockPersonInfo personInfo;


	public Boolean isNeedBankAcctPermiss()
	{
		return needBankAcctPermiss;
	}

	public void setNeedBankAcctPermiss(Boolean needBankAcctPermiss)
	{
		this.needBankAcctPermiss = needBankAcctPermiss;
	}

	public Boolean isNeedDepAccInfo()
	{
		return needDepAccInfo;
	}

	public void setNeedDepAccInfo(Boolean needDepAccInfo)
	{
		this.needDepAccInfo = needDepAccInfo;
	}

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

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Long getPeriod()
	{
		return period;
	}

	public void setPeriod(Long period)
	{
		this.period = period;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getInterestOnDepositAcctId()
	{
		return interestOnDepositAcctId;
	}

	public void setInterestOnDepositAcctId(String interestOnDepositAcctId)
	{
		this.interestOnDepositAcctId = interestOnDepositAcctId;
	}

	public String getInterestOnDepositCardNum()
	{
		return interestOnDepositCardNum;
	}

	public void setInterestOnDepositCardNum(String interestOnDepositCardNum)
	{
		this.interestOnDepositCardNum = interestOnDepositCardNum;
	}

	public Boolean isCreditAllowed()
	{
		return creditAllowed;
	}

	public void setCreditAllowed(Boolean creditAllowed)
	{
		this.creditAllowed = creditAllowed;
	}

	public Boolean isDebitAllowed()
	{
		return debitAllowed;
	}

	public void setDebitAllowed(Boolean debitAllowed)
	{
		this.debitAllowed = debitAllowed;
	}

	public Boolean isProlongationAllowed()
	{
		return prolongationAllowed;
	}

	public void setProlongationAllowed(Boolean prolongationAllowed)
	{
		this.prolongationAllowed = prolongationAllowed;
	}

	public Boolean isCreditCrossAgencyAllowed()
	{
		return creditCrossAgencyAllowed;
	}

	public void setCreditCrossAgencyAllowed(Boolean creditCrossAgencyAllowed)
	{
		this.creditCrossAgencyAllowed = creditCrossAgencyAllowed;
	}

	public Boolean isDebitCrossAgencyAllowed()
	{
		return debitCrossAgencyAllowed;
	}

	public void setDebitCrossAgencyAllowed(Boolean debitCrossAgencyAllowed)
	{
		this.debitCrossAgencyAllowed = debitCrossAgencyAllowed;
	}

	public Boolean isPassBook()
	{
		return passBook;
	}

	public void setPassBook(Boolean passBook)
	{
		this.passBook = passBook;
	}

	public String getAcctBal()
	{
		return acctBal;
	}

	public void setAcctBal(String acctBal)
	{
		this.acctBal = acctBal;
	}

	public String getBankAcctPermiss()
	{
		return bankAcctPermiss;
	}

	public void setBankAcctPermiss(String bankAcctPermiss)
	{
		this.bankAcctPermiss = bankAcctPermiss;
	}
}
