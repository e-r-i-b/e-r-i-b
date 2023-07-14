package com.rssl.phizic.web.pfp.ajax;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.pfp.EditPfpPersonTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpTargetAction extends OperationalActionBase
{
	private static final String DUBLICATE_TAREGET_NAME_MESSAGE = "Укажите комментарий для Вашей цели.";
	private static final String DUBLICATE_TAREGET_FULL_NAME_MESSAGE = "Укажите другой комментарий для Вашей цели.";
	//Символ мягкого пробела, заменяется на FFFD
	private static final String WRONG_CHARACTER = Character.toString('\uFFFD');

	private static final String EDIT_RESULT = "EditResult";
	private static final String REMOVE_RESULT = "RemoveResult";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("edit","edit");
		map.put("remove","remove");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpTargetForm frm = (EditPfpTargetForm) form;
		EditPfpPersonTargetOperation operation = createOperation(EditPfpPersonTargetOperation.class);
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource,EditPfpTargetForm.EDIT_FORM);
		if(processor.process())
		{
			Map<String,Object> result = processor.getResult();
			String nameComment = (String) result.get("nameComment");
			try
			{
				Long dictionaryTargetId = (Long) result.get("dictionaryTarget");
				operation.initializeForEdit(frm.getProfileId(),frm.getId(),dictionaryTargetId);
				PersonTarget target = operation.getPersonTarget();
				Target dictionaryTarget = operation.getDictionaryTarget();

				Date planedDate = (Date)result.get("planedDate");
				BigDecimal targetAmount = (BigDecimal)result.get("amount");
				Currency nationalCurrency = MoneyUtil.getNationalCurrency();

				target.setDictionaryTarget(dictionaryTarget);
				target.setNameComment(StringHelper.isNotEmpty(nameComment) ? nameComment.replaceAll(WRONG_CHARACTER, "") : nameComment);
				target.setPlannedDate(DateHelper.toCalendar(planedDate));
				target.setAmount(new Money(targetAmount,nationalCurrency));
				target.setVeryLast(dictionaryTarget.isLaterAll() && dictionaryTarget.isOnlyOne());

				operation.saveTarget();
				frm.setPersonTargetList(operation.getPersonTargets());
			}
			catch (ConstraintViolationException ignore)
			{
				saveError(request, StringHelper.isEmpty(nameComment)? DUBLICATE_TAREGET_NAME_MESSAGE: DUBLICATE_TAREGET_FULL_NAME_MESSAGE);
			}
			catch (BusinessLogicException ex)
			{
				saveError(request, new ActionMessage(ex.getMessage(), false));
			}
		}
		else
		{
			saveErrors(request,processor.getErrors());
		}

		return mapping.findForward(EDIT_RESULT);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpTargetForm frm = (EditPfpTargetForm) form;
		EditPfpPersonTargetOperation operation = createOperation(EditPfpPersonTargetOperation.class);
		try
		{
			operation.initialize(frm.getProfileId(),frm.getId());
			operation.removeTarget();
		}
		catch (BusinessLogicException e)
		{
			saveError(request,e);
		}
		return mapping.findForward(REMOVE_RESULT);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
