package com.rssl.phizic.web.loanclaim.creditProduct.condition;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition.ListCreditProductConditionOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition.PublishingCreditProductConditionOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition.RemoveCreditProductConditionOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.loanclaim.Constants;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * Ёкшен просмотра условий по кредитным продуктам.
 */
public class ListCreditProductConditionAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> keyMap =  super.getAditionalKeyMethodMap();
		keyMap.put("button.publish", "publish");
		keyMap.put("button.unpublish", "unpublish");
		return keyMap;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCreditProductConditionOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCreditProductConditionForm.FILTER_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCreditProductConditionOperation.class);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter(Constants.CREDIT_PRODUCT_TYPE_ID, filterParams.get(Constants.CREDIT_PRODUCT_TYPE_ID));
		query.setParameter(Constants.CREDIT_PRODUCT_ID, filterParams.get(Constants.CREDIT_PRODUCT_ID));
		query.setParameter(Constants.STATUS, filterParams.get(Constants.STATUS));
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListCreditProductConditionOperation op = (ListCreditProductConditionOperation) operation;
		List<CreditProductCondition> conditionList = frm.getData();
		frm.setData(op.getUpdateDataList(conditionList, frm.getFilters()));
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PublishingCreditProductConditionOperation op =  createOperation(PublishingCreditProductConditionOperation.class);
		ListCreditProductConditionForm frm = (ListCreditProductConditionForm)form;
		ActionMessages errors = new ActionMessages();
		try
		{
			op.publish(frm.getSelectedId());
		}
		catch (BusinessLogicException e)
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveErrors(request, errors);
		return start(mapping, form, request, response);
	}

	public ActionForward unpublish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PublishingCreditProductConditionOperation  op = createOperation(PublishingCreditProductConditionOperation.class);
		ListCreditProductConditionForm frm = (ListCreditProductConditionForm)form;
		ActionMessages errors = new ActionMessages();
		try
		{
			op.unpublish(frm.getSelectedId());
		}
		catch (BusinessLogicException e)
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveErrors(request, errors);
		return start(mapping, form, request, response);
	}


	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}
}
