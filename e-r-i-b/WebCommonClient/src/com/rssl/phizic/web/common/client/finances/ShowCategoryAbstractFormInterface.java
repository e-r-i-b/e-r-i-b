package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.operations.finances.CardOperationDescription;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 * Интерфейс формы для получения выписки по категории в разделе "Анализ расходов": общая часть для мобильной и основной версии
 */

public interface ShowCategoryAbstractFormInterface
{
	/**
	 * Id категории, выписку по которой смотрим и редактируем
	 */
	Long getCategoryId();
	void setCategoryId(Long categoryId);
	/**
	 * @return Категория, выписку по которой смотрим и редактируем
	 */
	CardOperationCategory getCategory();
	void setCategory(CardOperationCategory category);

	void setData(List<CardOperationDescription> cardOperationDescriptions);
}
