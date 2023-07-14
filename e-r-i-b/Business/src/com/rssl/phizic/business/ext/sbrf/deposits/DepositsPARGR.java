package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * �������� ��� ������� �� ������� QVB_PARGR ����������� ��� ��� (��������� �������� ���������, ������������ �� �������)
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsPARGR extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ������ ������� (PG_CODGR)
	private Long groupCode;
	// ������ �������� �������(PG_BDATE)
	private Calendar dateBegin;
	// ���������� � ����������������� ����� (PG_FCONTR)
	private Boolean charitableContribution;
	// ��������� ������ ��� ����������� (PG_PENS)
	private Boolean pensionRate;
	// ����������� ������������ ����� ��� ����������� (PG_FMAXP)
	private Boolean pensionSumLimit;
	// ���������� ��������� �� ���������� �������� (PG_PRONPR)
	private Long percentCondition;
	// ����������� ������������ ����� ������ (PG_MAXV)
	private Long sumLimit;
	// ������� ������ ��� ������� % �� ����� ���������� ������������ ����� (PG_CSTAV)
	private Long sumLimitCondition;
	// ��� ������ ������� (PG_TYPEGR)
	private Boolean socialType;
	// �������� ��� ��������������� ������ (PG_OPENWPP). 0 - ������, 1 - �����, �������� �� ��������� 0
	private Boolean withInitialFee;
	// ������� ������ ��������� (PG_PRBC). 0 - ������ ������������ � ����� ������, 1 - ����� ���������� �� ���� ���������� �����, �������� �� ��������� 0
	private Boolean capitalization;
	// ������� ������� �����-������ (PG_PROMO). 0 - ��� �����-������, 1 - ���� �����-������
	private Boolean promo;

	public Comparable getSynchKey()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		StringBuilder sb = new StringBuilder();
		sb.append(groupCode).append(KEY_DELIMITER).
				append(dateFormat.format(dateBegin.getTime()));
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsPARGR) that).setId(getId());
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

	public Long getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(Long groupCode)
	{
		this.groupCode = groupCode;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Boolean getCharitableContribution()
	{
		return charitableContribution;
	}

	public void setCharitableContribution(Boolean charitableContribution)
	{
		this.charitableContribution = charitableContribution;
	}

	public Boolean getPensionRate()
	{
		return pensionRate;
	}

	public void setPensionRate(Boolean pensionRate)
	{
		this.pensionRate = pensionRate;
	}

	public Boolean getPensionSumLimit()
	{
		return pensionSumLimit;
	}

	public void setPensionSumLimit(Boolean pensionSumLimit)
	{
		this.pensionSumLimit = pensionSumLimit;
	}

	public Long getPercentCondition()
	{
		return percentCondition;
	}

	public void setPercentCondition(Long percentCondition)
	{
		this.percentCondition = percentCondition;
	}

	public Long getSumLimit()
	{
		return sumLimit;
	}

	public void setSumLimit(Long sumLimit)
	{
		this.sumLimit = sumLimit;
	}

	public Long getSumLimitCondition()
	{
		return sumLimitCondition;
	}

	public void setSumLimitCondition(Long sumLimitCondition)
	{
		this.sumLimitCondition = sumLimitCondition;
	}

	public Boolean getSocialType()
	{
		return socialType;
	}

	public void setSocialType(Boolean socialType)
	{
		this.socialType = socialType;
	}

	public Boolean getWithInitialFee()
	{
		return withInitialFee;
	}

	public void setWithInitialFee(Boolean withInitialFee)
	{
		this.withInitialFee = withInitialFee;
	}

	public Boolean getCapitalization()
	{
		return capitalization;
	}

	public void setCapitalization(Boolean capitalization)
	{
		this.capitalization = capitalization;
	}

	public Boolean getPromo()
	{
		return promo;
	}

	public void setPromo(Boolean promo)
	{
		this.promo = promo;
	}
}
