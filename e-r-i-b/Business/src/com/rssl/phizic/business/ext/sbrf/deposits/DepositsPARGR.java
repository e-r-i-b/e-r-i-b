package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Сущность для записей из таблицы QVB_PARGR справочника ЦАС НСИ (параметры вкладных продуктов, объединенных по группам)
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsPARGR extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id в БД
	private Long id;
	// Код группы вкладов (PG_CODGR)
	private Long groupCode;
	// Начало действия условия(PG_BDATE)
	private Calendar dateBegin;
	// Отчисления в благотворительные фонды (PG_FCONTR)
	private Boolean charitableContribution;
	// Отдельные ставки для пенсионеров (PG_PENS)
	private Boolean pensionRate;
	// Ограничение максимальной суммы для пенсионеров (PG_FMAXP)
	private Boolean pensionSumLimit;
	// Начисление процентов на выдаваемые проценты (PG_PRONPR)
	private Long percentCondition;
	// Ограничение максимальной суммы вклада (PG_MAXV)
	private Long sumLimit;
	// Условие ставки для расчета % на сумму превышения максимальной суммы (PG_CSTAV)
	private Long sumLimitCondition;
	// Тип группы вкладов (PG_TYPEGR)
	private Boolean socialType;
	// Открытие без первоначального взноса (PG_OPENWPP). 0 - нельзя, 1 - можно, Значение по умолчанию 0
	private Boolean withInitialFee;
	// Порядок уплаты процентов (PG_PRBC). 0 - всегда причисляются к счету вклада, 1 - можно переводить на счет банковской карты, Значение по умолчанию 0
	private Boolean capitalization;
	// Признак наличия промо-ставок (PG_PROMO). 0 - нет промо-ставок, 1 - есть промо-ставки
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
