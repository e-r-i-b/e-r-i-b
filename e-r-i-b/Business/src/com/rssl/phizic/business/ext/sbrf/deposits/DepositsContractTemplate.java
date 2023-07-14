package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * Сущность для записей из таблицы ContractTemplate справочника ЦАС НСИ (тексты шаблонов договоров)
 * Тип шаблона: 0 - договор; 1 - доп соглашение об изменении условий вклада; 2 - доп соглашение о переводе с одного вида вклада на другой
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsContractTemplate extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id в БД
	private Long id;
	// Идентификатор шаблона (TEMPLATEID)
	private Long templateId;
	// Шаблон действителен с (START)
	private Calendar dateBegin;
	// Шаблон действителен по (STOP)
	private Calendar dateEnd;
	// Тип шаблона (TYPE)
	private Long type;
	// Наименование шаблона (LABEL)
	private String name;
	// Текст шаблона (TEXT)
	private String text;
	// Код договора (CODE)
	private String code;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(templateId);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsContractTemplate) that).setId(getId());
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

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
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

	public Long getType()
	{
		return type;
	}

	public void setType(Long type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
