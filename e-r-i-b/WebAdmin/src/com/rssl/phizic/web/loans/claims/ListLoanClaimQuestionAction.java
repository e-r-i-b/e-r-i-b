package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.loanclaim.questionnaire.EditLoanClaimQuestionOperation;
import com.rssl.phizic.operations.loanclaim.questionnaire.ListLoanClaimQuestionOperation;
import com.rssl.phizic.operations.loanclaim.questionnaire.RemoveLoanClaimQuestionOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн для работы со спсиком вопросов в заявке на кредит
 */
public class ListLoanClaimQuestionAction extends ListActionBase
{
	@Override
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.save.use.questionnaire", "saveUseQuestionnaire");
		map.put("button.publish", "publish");
		map.put("button.unpublish", "unpublished");
		return map;
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoanClaimQuestionOperation.class);
	}

	@Override
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLoanClaimQuestionOperation.class);
	}

	private EditLoanClaimQuestionOperation createEditOperation() throws BusinessException
	{
		return createOperation(EditLoanClaimQuestionOperation.class);
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("questionId", filterParams.get("questionId"));
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListLoanClaimQuestionForm.FILTER_FORM;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListLoanClaimQuestionForm form = (ListLoanClaimQuestionForm) frm;
		ListLoanClaimQuestionOperation opr = (ListLoanClaimQuestionOperation) operation;
		form.setUseQuestionnaire(opr.isUseQuestionnaire());
	}

	public final ActionForward saveUseQuestionnaire(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ListLoanClaimQuestionForm frm = (ListLoanClaimQuestionForm) form;
			ListLoanClaimQuestionOperation operation = (ListLoanClaimQuestionOperation) createListOperation(frm);
			operation.setUseQuestionnaire(frm.isUseQuestionnaire());
			return start(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return start(mapping, form, request, response);
		}
	}

	public final ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		Long id = StrutsUtils.parseId(frm.getSelectedIds());
		EditLoanClaimQuestionOperation operation = createEditOperation();
		ActionMessages msgs = new ActionMessages();
		try
		{
			operation.publish(id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getLocalizedMessage(), false));
			saveMessages(request, msgs);
		}
		return start(mapping, form, request, response);
	}

	public final ActionForward unpublished(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		Long id = StrutsUtils.parseId(frm.getSelectedIds());
		EditLoanClaimQuestionOperation operation = createEditOperation();
		ActionMessages msgs = new ActionMessages();
		try
		{
			operation.initialize(id);
			operation.unpublished(id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getLocalizedMessage(), false));
			saveMessages(request, msgs);
		}
		return start(mapping, form, request, response);
	}


	public final ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());
		ActionMessages msgs = new ActionMessages();
		try
		{
			RemoveEntityOperation operation = createRemoveOperation(frm);
			for (Long id : ids)
			{
				msgs.add(doRemove(operation, id));
				//Фиксируем удаляемые сущности.
				addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));
			}
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getLocalizedMessage(), false));
			saveMessages(request, msgs);
		}
		return filter(mapping, form, request, response);
	}
}
