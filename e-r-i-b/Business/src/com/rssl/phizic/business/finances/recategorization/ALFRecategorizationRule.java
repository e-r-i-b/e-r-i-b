package com.rssl.phizic.business.finances.recategorization;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

/**
 * @author lepihina
 * @ created 28.03.14
 * $Author$
 * $Revision$
 * ѕравило перекатегоризации дл€ операции
 */
public class ALFRecategorizationRule
{
	private Long id;
	private Long loginId;
	private String description;
	private Long mccCode;
	private CardOperationCategory newCategory;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return логин клиента, которому принадлежит правило перекатегоризации
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId - логин клиента, которому принадлежит правило перекатегоризации
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return описание операции
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - описание операции
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ћ——-код операции
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - ћ——-код операции
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return идентификатор категории, на которую настроена перекатегоризаци€
	 */
	public CardOperationCategory getNewCategory()
	{
		return newCategory;
	}

	/**
	 * @param newCategory - идентификатор категории, на которую настроена перекатегоризаци€
	 */
	public void setNewCategory(CardOperationCategory newCategory)
	{
		this.newCategory = newCategory;
	}
}
