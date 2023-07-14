package com.rssl.phizic.web.atm.cards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Форма просмотра списка кредитных офисов
 *
 * @author  Balovtsev
 * @version 23.08.13 14:43
 */
public class ListCreditCardOfficeAtmAction extends ListCreditCardOfficeAction
{
	@Override
	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ListCreditCardOfficeAtmForm form = (ListCreditCardOfficeAtmForm) frm;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("region", form.getRegion());
		map.put("street", form.getStreet());
		map.put("name", form.getName());
		return map;
	}
}
