package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 * Интерфейс формы динамики движения средств по категориям: общая часть для мобильной и основной версий
 */

public interface CategoriesDynamicFormInterface
{
	// Список доступных категорий по которым у пользователя есть операции
	List<CardOperationCategory> getCategoriesList();
	void setCategoriesList(List<CardOperationCategory> categoriesList);

	// ID выбранной категории
	public long getCurrentCategoryId();
	void setCurrentCategoryId(long currentCategoryId);

	// Данные списка
	public void setData(List data);
	List getData();
}
