package com.rssl.phizic.logging.finances.recategorization;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * Запись о добавлении/применении правила перекатегоризации
 */
public class ALFRecategorizationRuleEntry implements Serializable
{
	private Long id;
	private Calendar date;
	private String description;
	private Long mccCode;
	private String originalCategory;
	private String newCategory;
	private ALFRecategorizationRuleEntryType type;
	private int count;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата добавления/применения правила перекатегоризации
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - дата добавления/применения правила перекатегоризации
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return описание операции, для которой было создано правило перекатегоризации
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - описание операции, для которой было создано правило перекатегоризации
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return МСС-код операции, для которой было создано правило перекатегоризации
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - МСС-код операции, для которой было создано правило перекатегоризации
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return название исходной категории
	 */
	public String getOriginalCategory()
	{
		return originalCategory;
	}

	/**
	 * @param originalCategory - название исходной категории
	 */
	public void setOriginalCategory(String originalCategory)
	{
		this.originalCategory = originalCategory;
	}

	/**
	 * @return название новой категории
	 */
	public String getNewCategory()
	{
		return newCategory;
	}

	/**
	 * @param newCategory - название новой категории
	 */
	public void setNewCategory(String newCategory)
	{
		this.newCategory = newCategory;
	}

	/**
	 * @return тип действия (добавление или применение правила перекатегоризации)
	 */
	public ALFRecategorizationRuleEntryType getType()
	{
		return type;
	}

	/**
	 * @param type - тип действия
	 */
	public void setType(ALFRecategorizationRuleEntryType type)
	{
		this.type = type;
	}

	/**
	 * @return количество выполненных действий (например, количество операций для которых применилось правило перекатегоризации)
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param count - количество выполненных действий
	 */
	public void setCount(int count)
	{
		this.count = count;
	}
}
