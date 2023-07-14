package com.rssl.phizic.web.atm.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.web.common.client.finances.CategoriesDynamicFormInterface;
import com.rssl.phizic.web.common.mobile.finances.FinanceATMFormBase;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 28.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesDynamicMoveATMForm extends FinanceATMFormBase implements CategoriesDynamicFormInterface
{
	//Список доступных категорий по которым у пользователя есть операции
	private List<CardOperationCategory> categoriesList;
	//ИД выбранной категории
	private long currentCategoryId;


	public List<CardOperationCategory> getCategoriesList()
	{
		return categoriesList;
	}

	public void setCategoriesList(List<CardOperationCategory> categoriesList)
	{
		this.categoriesList = categoriesList;
	}

	public long getCurrentCategoryId()
	{
		return currentCategoryId;
	}

	public void setCurrentCategoryId(long currentCategoryId)
	{
		this.currentCategoryId = currentCategoryId;
	}
}
