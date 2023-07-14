package com.rssl.phizic.web.client.loyalty;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 15.08.2012
 * @ $Author$
 * @ $Revision$
 *
 * операции по программе лояльности
 */

public class LoyaltyProgramInfoAction extends OperationalActionBase
{
	private static final String FORWARD_REG = "Register";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.filter",  "filter");
		return keyMap;
	}

	public ActionForward filter(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyProgramInfoOperation op = createOperation(LoyaltyProgramInfoOperation.class);
		op.initialize();
		return filter(mapping, frm, op);
	}

	private ActionForward filter(ActionMapping mapping, ActionForm frm, LoyaltyProgramInfoOperation op) throws Exception
	{
		LoyaltyProgramInfoForm form = (LoyaltyProgramInfoForm) frm;
		LoyaltyProgramLink loyaltyProgramLink = op.getEntity();
		form.setLink(loyaltyProgramLink);
		try
		{
			Map<String, Object> filterParameters = getFilterParameters(form);
			form.setFilters(filterParameters);
			if (loyaltyProgramLink != null && loyaltyProgramLink.getState() != LoyaltyProgramState.UNREGISTERED && loyaltyProgramLink.getExternalId() != null)
			{
				FormProcessor<List<String>, StringErrorCollector> formProcessor =  new FormProcessor<List<String>, StringErrorCollector>(new MapValuesSource(filterParameters), LoyaltyProgramInfoForm.FILTER_FORM, new StringErrorCollector(), DocumentValidationStrategy.getInstance());
				if (!formProcessor.process())
				{
					form.setValidateErrors(formProcessor.getErrors());
					form.setBackError(true);
					form.setOperations(new ArrayList<LoyaltyProgramOperation>());
				}
				else
				{
					form.setOperations(op.getLoyaltyOperationInfo((Date)formProcessor.getResult().get("fromDate"), (Date)formProcessor.getResult().get("toDate")));
				}
			}
			form.setCallCentrePassw(op.getCallCentrePassw());
		}
		catch(BusinessLogicException ex)
		{
			log.warn(ex);
			form.setRegError(true);
		}

		form.setBackError(op.isBackError());
		form.setOverMaxItem(op.isOverMaxItems());
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyProgramInfoOperation operation = createOperation(LoyaltyProgramInfoOperation.class);
		operation.initialize();
		LoyaltyProgramLink loyaltyProgramLink = operation.getEntity();
		if (loyaltyProgramLink != null && loyaltyProgramLink.getState() == LoyaltyProgramState.UNREGISTERED && !((LoyaltyProgramInfoForm) form).isRegError())
			return mapping.findForward(FORWARD_REG);

		return filter(mapping, form, operation);
	}

	private Map<String, Object> getFilterParameters(LoyaltyProgramInfoForm frm) throws BusinessException
	{
		if (frm.getFilters().isEmpty())
		{
			Map<String, Object> filterParameters = new HashMap<String, Object>();
			Calendar endDate = Calendar.getInstance();
			Calendar startDate = (Calendar) endDate.clone();
			startDate.add(Calendar.MONTH, -1);
			filterParameters.put("toDate", DateHelper.formatDateToStringWithSlash(endDate));
			filterParameters.put("fromDate", DateHelper.formatDateToStringWithSlash(startDate));
			return filterParameters;
		}
		return frm.getFilters();
	}

}
