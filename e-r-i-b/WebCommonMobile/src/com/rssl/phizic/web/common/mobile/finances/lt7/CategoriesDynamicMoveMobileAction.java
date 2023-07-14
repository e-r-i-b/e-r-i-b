package com.rssl.phizic.web.common.mobile.finances.lt7;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.CategoriesDynamicMoveOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.web.common.client.finances.CategoriesDynamicFormInterface;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Mescheryakova
 * @ created 28.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesDynamicMoveMobileAction extends FinanceFilterMobileActionBase
{

	public FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		CategoriesDynamicMoveOperation op = createOperation(CategoriesDynamicMoveOperation.class);
		op.initialize();
		return op;
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		CategoriesDynamicMoveOperation op = (CategoriesDynamicMoveOperation) operation;
		CategoriesDynamicFormInterface form = (CategoriesDynamicFormInterface) frm;

		//Список категорий по параметрам фильтра
		form.setCategoriesList(op.getCardOperationCategories(filterData));
		//Установка дефолтного значения категории
		form.setCurrentCategoryId(op.getDefaultCurrentCategoryId(form.getCategoriesList(),form.getCurrentCategoryId()));
		//Данные для графика изменения затрат по выбранной категории
		if (!CollectionUtils.isEmpty(form.getCategoriesList()))
			form.setData(op.getDynamicMoveByCategory(filterData,form.getCurrentCategoryId()));
	}

}
