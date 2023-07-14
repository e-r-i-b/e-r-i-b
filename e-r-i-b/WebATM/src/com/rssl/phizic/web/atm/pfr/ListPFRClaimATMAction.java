package com.rssl.phizic.web.atm.pfr;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.atm.common.FilterFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список заявок на получение выписки ПФР
 * @author Jatsky
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ListPFRClaimATMAction extends OperationalActionBase
{

	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListPFRClaimATMForm frm = (ListPFRClaimATMForm) form;
		ListPFRClaimOperation operation = createOperation(ListPFRClaimOperation.class);
		operation.initialize();
		updateForm(operation, frm);
		return mapping.findForward(FORWARD_SHOW);
	}

	public void updateForm(ListPFRClaimOperation operation, ListPFRClaimATMForm frm) throws BusinessException, BusinessLogicException
	{
		Form filterForm = FilterFormBase.FILTER_DATE_FORM;
		MapValuesSource source = getMapSource(frm);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(source, filterForm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Calendar from = DateHelper.toCalendar((Date) result.get(FilterFormBase.FROM_DATE_NAME));
			Calendar to = DateHelper.toCalendar((Date) result.get(FilterFormBase.TO_DATE_NAME));
			frm.setClaims(operation.getPfrClaims(from, to));
			frm.setError(operation.isError());
		}
		else
			saveErrors(currentRequest(), processor.getErrors());
	}

	//формируем поля фильтрации для валидации
	private MapValuesSource getMapSource(ListPFRClaimATMForm frm)
	{
		//формируем поля фильтрации для валидации
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
		filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
		return new MapValuesSource(filter);
	}
}
