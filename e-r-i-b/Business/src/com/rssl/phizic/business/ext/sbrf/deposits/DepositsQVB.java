package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * �������� ��� ������� �� ������� qvb ����������� ��� ��� (�������� ���������� �� ����� � �������� �������)
 *
 * @author EgorovaA
 * @ created 24.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsQVB extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ������ (QDTN1)
	private Long depositType;
	// ������ ������ (QDTSUB)
	private Long depositSubType;
	// ��� �������� "�����-������" (QVAL). 0 - �����, 1 - ������
	private Boolean foreignCurrency;
	// ������������ ������ (QDN)
	private String fullName;
	// ����������� ������������ ������ (QSNAME)
	private String shortName;
	// ��� ���� ������ (QTYPE)
	private Long typeCode;
	// ���� ������ ������ (QOPBEG)
	private Calendar dateBegin;
	// ���� ����������� �������� ������� (QOPEND)
	private Calendar dateEnd;
	// ����������� ����� ��������������� ������ (QPFS)
	private BigDecimal sumInitialFee;
	// ������ ������������ ��������� (QMINADD)
	private BigDecimal minAdditionalFee;
	// ������� ������������ ������� (QN_RESN)
	private Boolean minimumBalance;
	// ��� ���� ������������� �� ������� ������ (QCAP)
	private Long capitalization;
	// ��� �������������� ��� ��������� ������� (QCAP_NU)
	private Long violationCapitalization;
	// ��� ������������� ��� ����������� (QCAPN)
	private Long prolongationCapitalization;
	// ��� ������� ��� ������� ��������� (QPRC)
	private Long balanceType;
	// ���������� ����������� ����������� (QPROL)
	private Long renewals;
	// ���� ������� ����������� (Q_DT_PROL)
	private Calendar renewalProhibition;
	// ��� ���� ���������� ������ (QPRCTAR)
	private Long rateType;
	// ��� ������� �������� ���������� ������ (QPRCTYPE)
	private Long ratePeriodCode;
	// ���� �������� (Q_SROK)
	private String period;
	// ������� ��������� - ����������� (FL_RES). 0 -�����/���� ����������� ������ ���������;1 -�����/���� ����������� ������ �����������;2 - �����/���� ����������� ��������� � �����������
	private Long residentDeposit;
	// ������� ����. ����.-����. �������� �� �� ����� �������� (Q_PERMIT). �������, ������������ ����������� ���������� ��������-��������� �������� �� �� ����� �������� ������
	private Boolean remotelyOperations;
	// ����������� ��������� �������� (Q_EXPENS)
	private Long debitOperationsCode;
	// ��� ������ ������� (Q_GROUP)
	private Long groupCode;
	// ����������� �������������� ����� (������) (Q_MINSD)
	private BigDecimal cashlessAdditionalFee;
	// ������� ��� �������� �� ��������� �������� ���-�� (Q_CDED)
	private Long chargeOffConditions;
	// ���� ������������� ��� ��������� ������������� (Q_CICET)
	private Long earlyTerminationCapitalization;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(depositType).append(KEY_DELIMITER).
				append(depositSubType).append(KEY_DELIMITER);

		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsQVB) that).setId(getId());
		super.updateFrom(that);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getDepositType()
	{
		return depositType;
	}

	public void setDepositType(Long depositType)
	{
		this.depositType = depositType;
	}

	public Long getDepositSubType()
	{
		return depositSubType;
	}

	public void setDepositSubType(Long depositSubType)
	{
		this.depositSubType = depositSubType;
	}

	public Boolean getForeignCurrency()
	{
		return foreignCurrency;
	}

	public void setForeignCurrency(Boolean foreignCurrency)
	{
		this.foreignCurrency = foreignCurrency;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public Long getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(Long typeCode)
	{
		this.typeCode = typeCode;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Calendar getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public BigDecimal getSumInitialFee()
	{
		return sumInitialFee;
	}

	public void setSumInitialFee(BigDecimal sumInitialFee)
	{
		this.sumInitialFee = sumInitialFee;
	}

	public BigDecimal getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	public void setMinAdditionalFee(BigDecimal minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}

	public Boolean getMinimumBalance()
	{
		return minimumBalance;
	}

	public void setMinimumBalance(Boolean minimumBalance)
	{
		this.minimumBalance = minimumBalance;
	}

	public Long getCapitalization()
	{
		return capitalization;
	}

	public void setCapitalization(Long capitalization)
	{
		this.capitalization = capitalization;
	}

	public Long getViolationCapitalization()
	{
		return violationCapitalization;
	}

	public void setViolationCapitalization(Long violationCapitalization)
	{
		this.violationCapitalization = violationCapitalization;
	}

	public Long getProlongationCapitalization()
	{
		return prolongationCapitalization;
	}

	public void setProlongationCapitalization(Long prolongationCapitalization)
	{
		this.prolongationCapitalization = prolongationCapitalization;
	}

	public Long getBalanceType()
	{
		return balanceType;
	}

	public void setBalanceType(Long balanceType)
	{
		this.balanceType = balanceType;
	}

	public Long getRenewals()
	{
		return renewals;
	}

	public void setRenewals(Long renewals)
	{
		this.renewals = renewals;
	}

	public Calendar getRenewalProhibition()
	{
		return renewalProhibition;
	}

	public void setRenewalProhibition(Calendar renewalProhibition)
	{
		this.renewalProhibition = renewalProhibition;
	}

	public Long getRateType()
	{
		return rateType;
	}

	public void setRateType(Long rateType)
	{
		this.rateType = rateType;
	}

	public Long getRatePeriodCode()
	{
		return ratePeriodCode;
	}

	public void setRatePeriodCode(Long ratePeriodCode)
	{
		this.ratePeriodCode = ratePeriodCode;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public Long getResidentDeposit()
	{
		return residentDeposit;
	}

	public void setResidentDeposit(Long residentDeposit)
	{
		this.residentDeposit = residentDeposit;
	}

	public Boolean getRemotelyOperations()
	{
		return remotelyOperations;
	}

	public void setRemotelyOperations(Boolean remotelyOperations)
	{
		this.remotelyOperations = remotelyOperations;
	}

	public Long getDebitOperationsCode()
	{
		return debitOperationsCode;
	}

	public void setDebitOperationsCode(Long debitOperationsCode)
	{
		this.debitOperationsCode = debitOperationsCode;
	}

	public Long getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(Long groupCode)
	{
		this.groupCode = groupCode;
	}

	public BigDecimal getCashlessAdditionalFee()
	{
		return cashlessAdditionalFee;
	}

	public void setCashlessAdditionalFee(BigDecimal cashlessAdditionalFee)
	{
		this.cashlessAdditionalFee = cashlessAdditionalFee;
	}

	public Long getChargeOffConditions()
	{
		return chargeOffConditions;
	}

	public void setChargeOffConditions(Long chargeOffConditions)
	{
		this.chargeOffConditions = chargeOffConditions;
	}

	public Long getEarlyTerminationCapitalization()
	{
		return earlyTerminationCapitalization;
	}

	public void setEarlyTerminationCapitalization(Long earlyTerminationCapitalization)
	{
		this.earlyTerminationCapitalization = earlyTerminationCapitalization;
	}
}
