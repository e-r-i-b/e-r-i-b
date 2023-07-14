package com.rssl.phizic.web.common.socialApi.autopayments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.web.common.client.autopayments.ShowAutoPaymentInfoAction;
import com.rssl.phizic.web.common.client.autopayments.ShowAutoPaymentInfoForm;
import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action для отображения графика автоплатежей типа AutoPayment в мобильной версии
 * @author Mescheryakova
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoPaymentAbstractMobileAction extends ShowAutoPaymentInfoAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return super.showAllScheduleReport(mapping, form, request, response);
	}

	public void updateForm(GetAutoPaymentInfoOperation operation, ShowAutoPaymentInfoForm frm, boolean isAllSchedule) throws BusinessException, BusinessLogicException
	{
		ShowAutoPaymentAbstractMobileForm form = (ShowAutoPaymentAbstractMobileForm) frm;
		Form filterForm = FilterFormBase.FILTER_DATE_FORM;
		MapValuesSource source = getMapSource(form);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(source, filterForm);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
		    Calendar from = DateHelper.toCalendar((Date)result.get(FilterFormBase.FROM_DATE_NAME));
			Calendar to = DateHelper.toCalendar((Date)result.get(FilterFormBase.TO_DATE_NAME));
			form.setScheduleItems(operation.getScheduleItemsMobile(from, to));
		}
		else
			saveErrors(currentRequest(), processor.getErrors());

	}

	//формируем поля фильтрации для валидации
	private MapValuesSource getMapSource(ShowAutoPaymentAbstractMobileForm frm)
	{
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    return new MapValuesSource(filter);
	}
}
