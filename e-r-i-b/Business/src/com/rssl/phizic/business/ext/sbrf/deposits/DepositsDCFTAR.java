package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Запись таблицы процентных ставок по вкладам (DCF_TAR) справочника ЦАС НСИ
 * @author Pankin
 * @ created 02.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsDCFTAR extends DictionaryRecordBase
{
	private static final String KEY_DELIMETER = "^";

	private Long id; // id в базе
	private Long type; // Вид вклада (KOD_VKL_QDTN1)
	private Long subType; // Подвид вклада (KOD_VKL_QDTSUB)
	private boolean foreignCurrency; // Признак рубли-валюта. 0 - рубли, 1 - валюта (KOD_VKL_QVAL)
	private Long clientCategory; // Код категории клиента (KOD_VKL_CLNT)
	private Long codeSROK; // Код типа учета (DCF_SROK)
	private Calendar dateBegin; // Дата установки ставки (DATE_BEG)
	private BigDecimal sumBegin; // Начальный остаток (SUM_BEG)
	private BigDecimal sumEnd; // Конечный остаток (SUM_END)
	private BigDecimal baseRate; // Ставка при выполнении условий (TAR_VKL)
	private BigDecimal violationRate; // Ставка при нарушении условий (TAR_NRUS)
	private String currencyCode; // Цифровой ISO код валюты (DCF_VAL)
	private Long segment; // Сегмент клиента (DCF_SEG)

	public Comparable getSynchKey()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		DecimalFormat decimalFormat = new DecimalFormat("#0.##");
		StringBuilder sb = new StringBuilder();
		sb.append(type).append(KEY_DELIMETER).
				append(subType).append(KEY_DELIMETER).
				append(foreignCurrency).append(KEY_DELIMETER).
				append(clientCategory).append(KEY_DELIMETER).
				append(codeSROK).append(KEY_DELIMETER).
				append(dateFormat.format(dateBegin.getTime())).append(KEY_DELIMETER).
				append(decimalFormat.format(sumBegin)).append(KEY_DELIMETER).
				append(currencyCode).append(KEY_DELIMETER).
				append(segment);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
    {
	    ((DepositsDCFTAR) that).setId(getId());
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

	public Long getType()
	{
		return type;
	}

	public void setType(Long type)
	{
		this.type = type;
	}

	public Long getSubType()
	{
		return subType;
	}

	public void setSubType(Long subType)
	{
		this.subType = subType;
	}

	public boolean isForeignCurrency()
	{
		return foreignCurrency;
	}

	public void setForeignCurrency(boolean foreignCurrency)
	{
		this.foreignCurrency = foreignCurrency;
	}

	public Long getClientCategory()
	{
		return clientCategory;
	}

	public void setClientCategory(Long clientCategory)
	{
		this.clientCategory = clientCategory;
	}

	public Long getCodeSROK()
	{
		return codeSROK;
	}

	public void setCodeSROK(Long codeSROK)
	{
		this.codeSROK = codeSROK;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public BigDecimal getSumBegin()
	{
		return sumBegin;
	}

	public void setSumBegin(BigDecimal sumBegin)
	{
		this.sumBegin = sumBegin;
	}

	public BigDecimal getSumEnd()
	{
		return sumEnd;
	}

	public void setSumEnd(BigDecimal sumEnd)
	{
		this.sumEnd = sumEnd;
	}

	public BigDecimal getBaseRate()
	{
		return baseRate;
	}

	public void setBaseRate(BigDecimal baseRate)
	{
		this.baseRate = baseRate;
	}

	public BigDecimal getViolationRate()
	{
		return violationRate;
	}

	public void setViolationRate(BigDecimal violationRate)
	{
		this.violationRate = violationRate;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public Long getSegment()
	{
		return segment;
	}

	public void setSegment(Long segment)
	{
		this.segment = segment;
	}
}
