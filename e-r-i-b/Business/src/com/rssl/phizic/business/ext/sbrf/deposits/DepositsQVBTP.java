package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * Сущность для записей из таблицы QVB_TP справочника ЦАС НСИ (зависимость вкладов от тарифных планов)
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsQVBTP extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id в БД
	private Long id;
	// Код вида вклада (TP_QDTN1)
	private Long depositType;
	// Код подвида вклада (TP_QDTSUB)
	private Long depositSubType;
	// Код признака "рубли-валюта" (TP_QVAL). 0 - рубли; 1 - иностранная валюта
	private Boolean foreignCurrency;
	// Код тарифного плана (TP_CODE)
	private Long code;
	// Дата начала действия (TP_DBEG)
	private Calendar dateBegin;
	// Дата окончания действия (TP_DEND)
	private Calendar dateEnd;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(depositType).append(KEY_DELIMITER).
				append(depositSubType).append(KEY_DELIMITER).
				append(foreignCurrency).append(KEY_DELIMITER).
				append(code);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsQVBTP) that).setId(getId());
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

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
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
}
