package com.rssl.phizic.web.common.client.mail;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;

import java.util.Date;
import java.util.Map;

/**
 * Базовый action для списков входящих, отправленных, удаленных писем в основном и мобильном приложениях
 * @author Dorzhinov
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListMailActionBase extends ListActionBase
{
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		Date toDate = (Date)filterParams.get(ListMailFormBase.FIELD_TO_DATE_NAME);
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("toDate", toDate);
		query.setParameter("fromDate", filterParams.get(ListMailFormBase.FIELD_FROM_DATE_NAME));
		query.setParameter("num", filterParams.get(ListMailFormBase.FIELD_NUM_NAME));
		query.setParameter("isAttach", filterParams.get(ListMailFormBase.FIELD_IS_ATTACH_NAME));
		query.setParameter("subject", filterParams.get(ListMailFormBase.FIELD_SUBJECT_NAME));
	}
}
