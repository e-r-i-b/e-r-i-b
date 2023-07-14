package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Date;
import java.util.Map;

/**
 * @author gladishev
 * @ created 24.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListSentMailAction extends SaveFilterParameterAction
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListSentMailForm.FILTER_FORM;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagment");
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "sentList";
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter("fromDate", filterParams.get("fromDate"));
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}

		query.setParameter("toDate", toDate);
		query.setParameter("surName", filterParams.get("surName"));
		query.setParameter("firstName", filterParams.get("firstName"));
		query.setParameter("patrName", filterParams.get("patrName"));
		query.setParameter("groupName", filterParams.get("groupName"));
		query.setParameter("recipientType", filterParams.get("recipientType"));
		query.setParameter("subject", filterParams.get("subject"));
		query.setParameter("senderId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setMaxResults(webPageConfig().getListLimit()+1);
	}
}
