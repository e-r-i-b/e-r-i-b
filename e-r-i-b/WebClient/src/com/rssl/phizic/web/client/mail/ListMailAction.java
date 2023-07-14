package com.rssl.phizic.web.client.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.mail.ListClientMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.mail.ListMailForm;

import java.util.Date;
import java.util.Map;

/**
 * @author Gainanov
 * @ created 06.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ListMailAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListClientMailOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("recipientId", personData.getPerson().getLogin().getId());
		query.setParameter("subject", filterParams.get("subject"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("toDate", toDate);
		query.setParameter("important", filterParams.get("important"));
		query.setParameter("state", filterParams.get("state"));
		query.setMaxResults(webPageConfig().getListLimit()+1);
	}
}
