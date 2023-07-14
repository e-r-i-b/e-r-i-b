package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.dictionaries.ShowBankListAction;

import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowBankListMobileAction extends ShowBankListAction
{
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		//Перекладываем значения фильтра из полей бина (если они заполнены) в поля логической формы
		ShowBankListMobileForm form = (ShowBankListMobileForm) frm;
		if(!StringHelper.isEmpty(form.getName()))
			filterParams.put("title", form.getName());
		if(!StringHelper.isEmpty(form.getBic()))
			filterParams.put("BIC", form.getBic());
		if(!StringHelper.isEmpty(form.getCity()))
			filterParams.put("city", form.getCity());
		if(!StringHelper.isEmpty(form.getGuid()))
			filterParams.put("guid", form.getGuid());

		super.doFilter(filterParams, operation, frm);
	}
}
