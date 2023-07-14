package com.rssl.phizic.web.common.socialApi.finances.lt7;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.web.common.client.finances.ShowCategoryAbstractFormInterface;
import com.rssl.phizic.web.common.socialApi.finances.lt7.FinanceMobileFormBase;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowCategoryAbstractMobileForm extends FinanceMobileFormBase implements ShowCategoryAbstractFormInterface
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
