package com.rssl.phizic.web.messageTranslate;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateListAction extends SaveFilterParameterAction
{

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return MessageTranslateListForm.FILTER_FORM;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		MessageTranslateListForm form = (MessageTranslateListForm)frm;
		if(form.getIsCSA())
			return createOperation("MessageTranslateCSAListOperation", "CSAMesageDictionaryManagement");
		else
			return createOperation("MessageTranslateListOperation", "SettingMessageTranslate");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		MessageTranslateListForm form = (MessageTranslateListForm)frm;
		if(form.getIsCSA())
			return createOperation("MessageTranslateCSAListOperation", "CSAMesageDictionaryManagement");
		else
			return createOperation("MessageTranslateListOperation", "SettingMessageTranslate");
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("code", filterParams.get("code"));
		query.setParameter("type", filterParams.get("type"));
		query.setParameter("isNew",filterParams.get("isNew") == null ? null : "1");
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation op)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isNew", null);
		return map;
	}
}
