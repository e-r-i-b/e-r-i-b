package com.rssl.phizic.web.atm.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.web.common.client.finances.ShowCategoryAbstractFormInterface;
import com.rssl.phizic.web.common.mobile.finances.FinanceATMFormBase;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowCategoryAbstractATMForm extends FinanceATMFormBase implements ShowCategoryAbstractFormInterface
{
	private Long currentCategoryId;
	private CardOperationCategory category;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ID категории, выписку по которой смотрим и редактируем
	 */
	public Long getCategoryId()
	{
		return currentCategoryId;
	}

	public void setCategoryId(Long categoryId)
	{
		this.currentCategoryId = categoryId;
	}

	public Long getCurrentCategoryId()
	{
		return currentCategoryId;
	}

	public void setCurrentCategoryId(Long currentCategoryId)
	{
		this.currentCategoryId = currentCategoryId;
	}

	/**
	 * @return Категория, выписку по которой смотрим и редактируем
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	public void setCategory(CardOperationCategory category)
	{
		this.category = category;
	}

}
