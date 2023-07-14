package com.rssl.phizic.business.resources.external.security;

import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author lukina
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class StoredSecurityAccount  extends AbstractStoredResource<SecurityAccount, Void> implements SecurityAccount
{

	private Money nominalAmount;
	private Money  incomeAmt;
	private BigDecimal incomeRate;

	private Calendar composeDt;
	private Calendar  termStartDt;
	private String docNum;
	private Calendar docDt;
	private String bankId;
	private String bankName;
	private String bankPostAddr;

	private String issuerBankId;
	private String issuerBankName;

	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getSerialNumber()
	{
		return getResourceLink().getNumber();
	}

	public Money getNominalAmount()
	{
		return nominalAmount;
	}

	public Money getIncomeAmt()
	{
		return incomeAmt;
	}

	public BigDecimal getIncomeRate()
	{
		return incomeRate;
	}

	public Calendar getComposeDt()
	{
		return composeDt;
	}

	public Calendar getTermStartDt()
	{
		return termStartDt;
	}

	public String getDocNum()
	{
		return docNum;
	}

	public Calendar getDocDt()
	{
		return docDt;
	}

	public String getBankId()
	{
		return bankId;
	}

	public String getBankName()
	{
		return bankName;
	}

	public String getBankPostAddr()
	{
		return bankPostAddr;
	}

	public String getIssuerBankId()
	{
		return issuerBankId;
	}

	public String getIssuerBankName()
	{
		return issuerBankName;
	}

	public void setNominalAmount(Money nominalAmount)
	{
		this.nominalAmount = nominalAmount;
	}

	public void setIncomeAmt(Money incomeAmt)
	{
		this.incomeAmt = incomeAmt;
	}

	public void setIncomeRate(BigDecimal incomeRate)
	{
		this.incomeRate = incomeRate;
	}

	public void setComposeDt(Calendar composeDt)
	{
		this.composeDt = composeDt;
	}

	public void setTermStartDt(Calendar termStartDt)
	{
		this.termStartDt = termStartDt;
	}

	public void setDocNum(String docNum)
	{
		this.docNum = docNum;
	}

	public void setDocDt(Calendar docDt)
	{
		this.docDt = docDt;
	}

	public void setBankId(String bankId)
	{
		this.bankId = bankId;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public void setBankPostAddr(String bankPostAddr)
	{
		this.bankPostAddr = bankPostAddr;
	}

	public void setIssuerBankId(String issuerBankId)
	{
		this.issuerBankId = issuerBankId;
	}

	public void setIssuerBankName(String issuerBankName)
	{
		this.issuerBankName = issuerBankName;
	}

	public void update(SecurityAccount securityAccount)
	{
		this.nominalAmount = securityAccount.getNominalAmount();
		this.incomeAmt = securityAccount.getNominalAmount();
		this.incomeRate = securityAccount.getIncomeRate();
		this.composeDt = securityAccount.getComposeDt();
		this.termStartDt = securityAccount.getTermStartDt();
		this.docNum = securityAccount.getDocNum();
		this.docDt = securityAccount.getDocDt();
		this.bankId = securityAccount.getBankId();
		this.bankName = securityAccount.getBankName();
		this.bankPostAddr = securityAccount.getBankPostAddr();
		this.issuerBankId = securityAccount.getIssuerBankId();
		this.issuerBankName = securityAccount.getIssuerBankName();

		setEntityUpdateTime( Calendar.getInstance() );
	}

	public boolean needUpdate(SecurityAccount securityAccount)
	{
		if (!MoneyUtil.equalsNullIgnore(nominalAmount, securityAccount.getNominalAmount()))
			return true;
		if (!MoneyUtil.equalsNullIgnore(incomeAmt, securityAccount.getIncomeAmt()))
			return true;
		if (DateHelper.nullSafeCompare(composeDt, securityAccount.getComposeDt()) != 0)
			return true;
		if (DateHelper.nullSafeCompare(termStartDt, securityAccount.getTermStartDt()) != 0)
			return true;
		if (DateHelper.nullSafeCompare(docDt, securityAccount.getDocDt()) != 0)
			return true;
		if (!NumericUtil.equalsNullIgnore(incomeRate, securityAccount.getIncomeRate()))
			return true;
		if (!StringHelper.equalsNullIgnore(docNum, securityAccount.getDocNum()))
			return true;
		if (!StringHelper.equalsNullIgnore(bankId, securityAccount.getBankId()))
			return true;
		if (!StringHelper.equalsNullIgnore(bankName, securityAccount.getBankName()))
			return true;
		if (!StringHelper.equalsNullIgnore(bankPostAddr, securityAccount.getBankPostAddr()))
			return true;
		if (!StringHelper.equalsNullIgnore(issuerBankId, securityAccount.getIssuerBankId()))
			return true;
		if (!StringHelper.equalsNullIgnore(issuerBankName, securityAccount.getIssuerBankName()))
			return true;
		return false;
	}

	/**
	 * Неиспользуемые свойства
	 **/
	public String getBlankType(){   return null; 	}
	public String getTermType() {   return null; 	}
	public Long getTermDays(){   return null; 	}
	public Calendar getTermFinishDt() {   return null; 	}
	public Calendar getTermLimitDt()  {   return null; 	}
	public boolean getOnStorageInBank() {	return true;}
}

