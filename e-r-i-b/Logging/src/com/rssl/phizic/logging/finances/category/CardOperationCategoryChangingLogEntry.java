package com.rssl.phizic.logging.finances.category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Запись журнала статистики по изменению клиентом категорий
 * @author lepihina
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CardOperationCategoryChangingLogEntry  implements Serializable
{
	private Long id;
	private Calendar date;
	private String TB;
	private String VSP;
	private String FIO;
	private String operationName;
	private Long mccCode;
	private BigDecimal amount;
	private String parentCategory;
	private String newCategories;
	private Integer newCategoriesCount;

	/**
	 * @return id записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - id записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата редактирования операции
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - дата редактирования операции
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return ТБ клиента
	 */
	public String getTB()
	{
		return TB;
	}

	/**
	 * @param TB - ТБ клиента
	 */
	public void setTB(String TB)
	{
		this.TB = TB;
	}

	/**
	 * @return ВСП клиента
	 */
	public String getVSP()
	{
		return VSP;
	}

	/**
	 * @param VSP - ВСП клиента
	 */
	public void setVSP(String VSP)
	{
		this.VSP = VSP;
	}

	/**
	 * @return ФИО клиента
	 */
	public String getFIO()
	{
		return FIO;
	}

	/**
	 * @param FIO - ФИО клиента
	 */
	public void setFIO(String FIO)
	{
		this.FIO = FIO;
	}

	/**
	 * @return название редактируемой операции
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - название редактируемой операции
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return МСС-код редактируемой операции
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - МСС-код редактируемой операции
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return сумма (в национальной валюте) редактируемой операции
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - сумма (в национальной валюте) редактируемой операции
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return исходная категория редактируемой операции
	 */
	public String getParentCategory()
	{
		return parentCategory;
	}

	/**
	 * @param parentCategory - исходная категория редактируемой операции
	 */
	public void setParentCategory(String parentCategory)
	{
		this.parentCategory = parentCategory;
	}

	/**
	 * @return новые категории редактируемой операции (одна, если перенесли в другую категорию; несколько - если разбили операцию)
	 */
	public String getNewCategories()
	{
		return newCategories;
	}

	/**
	 * @param newCategories - новые категории редактируемой операции (одна, если перенесли в другую категорию; несколько - если разбили операцию)
	 */
	public void setNewCategories(String newCategories)
	{
		this.newCategories = newCategories;
	}

	/**
	 * @return количество новых категорий
	 */
	public Integer getNewCategoriesCount()
	{
		return newCategoriesCount;
	}

	/**
	 * @param newCategoriesCount - количество новых категорий
	 */
	public void setNewCategoriesCount(Integer newCategoriesCount)
	{
		this.newCategoriesCount = newCategoriesCount;
	}
}
