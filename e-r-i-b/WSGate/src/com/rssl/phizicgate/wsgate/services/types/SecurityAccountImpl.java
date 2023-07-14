package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.security.SecurityAccount;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author lukina
 * @ created 21.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SecurityAccountImpl implements SecurityAccount
{
	private String id;
	private String blankType;
	private String serialNumber;
	private Money nominalAmount;
	private Money  incomeAmt;
	private BigDecimal incomeRate;
	private String  termType;
	private Long      termDays;
	private Calendar composeDt;
	private Calendar  termStartDt;
	private Calendar  termFinishDt;
	private Calendar  termLimitDt;
	private boolean  onStorageInBank;
	//информация о договоре хранения
	private String docNum;
	private Calendar docDt;
	private String bankId;
	private String bankName;
	private String bankPostAddr;

	private String issuerBankId;
	private String issuerBankName;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBlankType()
	{
		return blankType;
	}

	public void setBlankType(String blankType)
	{
		this.blankType = blankType;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}

	public Money getNominalAmount()
	{
		return nominalAmount;
	}

	public void setNominalAmount(Money nominalAmount)
	{
		this.nominalAmount = nominalAmount;
	}

	public Money getIncomeAmt()
	{
		return incomeAmt;
	}

	public void setIncomeAmt(Money incomeAmt)
	{
		this.incomeAmt = incomeAmt;
	}

	public BigDecimal getIncomeRate()
	{
		return incomeRate;
	}

	public void setIncomeRate(BigDecimal incomeRate)
	{
		this.incomeRate = incomeRate;
	}

	public String getTermType()
	{
		return termType;
	}

	public void setTermType(String termType)
	{
		this.termType = termType;
	}

	public Long getTermDays()
	{
		return termDays;
	}

	public void setTermDays(Long termDays)
	{
		this.termDays = termDays;
	}

	public Calendar getComposeDt()
	{
		return composeDt;
	}

	public void setComposeDt(Calendar composeDt)
	{
		this.composeDt = composeDt;
	}

	public Calendar getTermStartDt()
	{
		return termStartDt;
	}

	public void setTermStartDt(Calendar termStartDt)
	{
		this.termStartDt = termStartDt;
	}

	public Calendar getTermFinishDt()
	{
		return termFinishDt;
	}

	public void setTermFinishDt(Calendar termFinishDt)
	{
		this.termFinishDt = termFinishDt;
	}

	public Calendar getTermLimitDt()
	{
		return termLimitDt;
	}

	public void setTermLimitDt(Calendar termLimitDt)
	{
		this.termLimitDt = termLimitDt;
	}

	public boolean getOnStorageInBank()
	{
		return onStorageInBank;
	}

	public void setOnStorageInBank(boolean onStorageInBank)
	{
		this.onStorageInBank = onStorageInBank;
	}

	public String getDocNum()
	{
		return docNum;
	}

	public void setDocNum(String docNum)
	{
		this.docNum = docNum;
	}

	public Calendar getDocDt()
	{
		return docDt;
	}

	public void setDocDt(Calendar docDt)
	{
		this.docDt = docDt;
	}

	public String getBankId()
	{
		return bankId;
	}

	public void setBankId(String bankId)
	{
		this.bankId = bankId;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBankPostAddr()
	{
		return bankPostAddr;
	}

	public void setBankPostAddr(String bankPostAddr)
	{
		this.bankPostAddr = bankPostAddr;
	}

	public String getIssuerBankId()
	{
		return issuerBankId;
	}

	public void setIssuerBankId(String issuerBankId)
	{
		this.issuerBankId = issuerBankId;
	}

	public String getIssuerBankName()
	{
		return issuerBankName;
	}

	public void setIssuerBankName(String issuerBankName)
	{
		this.issuerBankName = issuerBankName;
	}
}

