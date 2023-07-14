package com.rssl.phizic.web.common.mobile.finances.financeCalendar;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.web.common.client.finances.financeCalendar.GetDayExtractToFinanceCalendarAction;
import com.rssl.phizic.web.common.client.finances.financeCalendar.GetDayExtractToFinanceCalendarForm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ѕолучение выписки финансового календар€ за день
 */
public class GetDayExtractToFinanceCalendarMobileAction extends GetDayExtractToFinanceCalendarAction
{
	protected Map<String, Object> getNormalizedDateParams(Map<String, Object> filterParams)
	{
		filterParams.put("openPageDate", Calendar.getInstance().getTime());
		return super.getNormalizedDateParams(filterParams);
	}

	protected FieldValuesSource getFieldValuesSource(GetDayExtractToFinanceCalendarForm frm)
	{
		GetDayExtractToFinanceCalendarMobileForm form = (GetDayExtractToFinanceCalendarMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("onDate", form.getOnDate());
		map.put("selectedCardIds", form.getSelectedCardIds());
		return new MapValuesSource(map);
	}

	protected Form getForm()
	{
		return GetDayExtractToFinanceCalendarMobileForm.DAY_EXTRACT_FORM;
	}

	protected boolean getEmptyErrorPage()
	{
		return false;
	}
}
