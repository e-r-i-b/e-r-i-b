package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Справочник кредитных офисов
 * @author Dorzhinov
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCreditCardOfficeMobileAction extends ListCreditCardOfficeAction
{
	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ListCreditCardOfficeMobileForm form = (ListCreditCardOfficeMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("region", form.getRegion());
		map.put("street", form.getStreet());
		map.put("name", form.getName());
		return map;
	}
}
