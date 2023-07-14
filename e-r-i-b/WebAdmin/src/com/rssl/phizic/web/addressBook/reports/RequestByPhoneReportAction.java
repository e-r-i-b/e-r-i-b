package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.Form;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.ermb.RequestCardByPhoneLogEntry;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.addressBook.reports.RequestCardByPhoneReportOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author lukina
 * @ created 10.11.2014
 * @ $Author$
 * @ $Revision$
 *
 * Экшен постороения отчета «По запросам информации по номеру телефона»
 */
public class RequestByPhoneReportAction    extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RequestCardByPhoneReportOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return RequestByPhoneReportForm.FILTER_FORM;
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		Date toDate = (Date)filterParams.get(RequestByPhoneReportForm.TO_DATE_FIELD_NAME);
		if (toDate != null)
		{
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("loginId",  filterParams.get(RequestByPhoneReportForm.LOGIN_FIELD_NAME));
		query.setParameter("fromDate", filterParams.get(RequestByPhoneReportForm.FROM_DATE_FIELD_NAME));
		query.setParameter("toDate",   toDate);
		query.setParameter("blockId",  ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		RequestByPhoneReportForm form = (RequestByPhoneReportForm) frm;
		if (form.isFromStart())
		{
			form.setData(Collections.<RequestCardByPhoneLogEntry>emptyList());
			form.setFilters(filterParams);
			updateFormAdditionalData(form, operation);
		}
		else
		{
			super.doFilter(filterParams, operation, form);
		}
	}

}
