package com.rssl.phizic.test.esberibmock;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Кредит (для заглушки)
 @author: Egorovaa
 @ created: 06.03.2012
 @ $Author$
 @ $Revision$
 */
public class MockCredit
{
	private Long id;
	/**
	 * Идентификатор системы-источника продукта
	 */
	private String systemId;
	/**
	 * Номер ссудного счета
	 */
	private String acctId;
	/**
	 * Номер кредитного договора
	 */
	private String agreemtNum;
	/**
	 * Идентификатор банковского продукта
	 */
	private String prodType;
	/**
	 * Краткое название кредита. Текст на русском языке готовый для отображения клиенту.
	 */
	private String loanType;
	/**
	 * Валюта
	 */
	private String acctCur;
	/**
	 * Сумма по договору
	 */
	private BigDecimal origAmt;
	/**
	 * Номер филиала, в котором ведется договор
	 */
	private String branchId;
	/**
	 * Номер ОСБ, где ведется кредит 
	 */
	private String agencyId;
	/**
	 * Номер тербанка(ТБ) 
	 */
	private String regionId;
	/**
	 * Номер ОСБ, где ведется ссудный счет
	 */
	private String rbBrchId;
	/**
	 * Признак аннуитетности (True-аннуитетный, false – дифференцированный)
	 */
	private Boolean ann;
	/**
	 * Дата подписания договора
	 */
	private Calendar startDt;
	/**
	 * Дата окончания/закрытия договора
	 */
	private Calendar expDt;
	/**
	 * Роль клиента в договоре (1 – заемщик, созаемщик, 2 – поручитель, залогодатель)
	 */
	private Long personRole;
	/**
	 * Информация о физическом лице
	 */
	private MockPersonInfo personInfo;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public String getAgreemtNum()
	{
		return agreemtNum;
	}

	public void setAgreemtNum(String agreemtNum)
	{
		this.agreemtNum = agreemtNum;
	}

	public String getProdType()
	{
		return prodType;
	}

	public void setProdType(String prodType)
	{
		this.prodType = prodType;
	}

	public String getLoanType()
	{
		return loanType;
	}

	public void setLoanType(String loanType)
	{
		this.loanType = loanType;
	}

	public String getAcctCur()
	{
		return acctCur;
	}

	public void setAcctCur(String acctCur)
	{
		this.acctCur = acctCur;
	}

	public BigDecimal getOrigAmt()
	{
		return origAmt;
	}

	public void setOrigAmt(BigDecimal origAmt)
	{
		this.origAmt = origAmt;
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

	public Boolean isAnn()
	{
		return ann;
	}

	public void setAnn(Boolean ann)
	{
		this.ann = ann;
	}

	public Calendar getStartDt()
	{
		return startDt;
	}

	public void setStartDt(Calendar startDt)
	{
		this.startDt = startDt;
	}

	public Calendar getExpDt()
	{
		return expDt;
	}

	public void setExpDt(Calendar expDt)
	{
		this.expDt = expDt;
	}

	public Long getPersonRole()
	{
		return personRole;
	}

	public void setPersonRole(Long personRole)
	{
		this.personRole = personRole;
	}

	public MockPersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(MockPersonInfo personInfo)
	{
		this.personInfo = personInfo;
	}
}
