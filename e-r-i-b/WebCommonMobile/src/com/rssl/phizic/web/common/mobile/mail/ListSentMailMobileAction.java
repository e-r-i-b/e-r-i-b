package com.rssl.phizic.web.common.mobile.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.mail.ListSentMailAction;
import com.rssl.phizic.web.common.client.mail.ListMailFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Список отправленных писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListSentMailMobileAction extends ListSentMailAction
{
	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ListSentMailMobileForm frm = (ListSentMailMobileForm)form;
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(ListMailFormBase.FIELD_FROM_DATE_NAME, frm.getFrom());
	    filter.put(ListMailFormBase.FIELD_TO_DATE_NAME, frm.getTo());
		filter.put(ListMailFormBase.FIELD_NUM_NAME, frm.getNum());
		filter.put(ListMailFormBase.FIELD_IS_ATTACH_NAME, frm.hasAttach() ? "on" : null);
		filter.put(ListMailFormBase.FIELD_SUBJECT_NAME, frm.getSubject());
		filter.put("type", frm.getType());
	    return new MapValuesSource(filter);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListSentMailMobileForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		//в super-методе добавляется сообщение в риквест. Если произошла ошибка, то это сообщение не нужно выводить.
		if (getErrors(currentRequest()).isEmpty())
			super.updateFormAdditionalData(frm, operation);
	}
}
