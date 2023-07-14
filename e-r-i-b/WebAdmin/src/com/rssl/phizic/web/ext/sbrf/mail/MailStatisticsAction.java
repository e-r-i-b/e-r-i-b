package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailType;
import com.rssl.phizic.business.mail.RecipientMailState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EmployeeMailStatisticsExportOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.MailStatisticsExportOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.MailStatisticsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MailStatisticsAction extends OperationalActionBase
{
	private static final String FILE_NAME = "MailStatistics.csv";                         //�������� ������������ �����
	private static final String MAIL_STATISTICS_FILE_TYPE = "MailStatisticsFileType"; //��������� ������������ �����
	private static final String STATISTICS_QUERY_NAME = "statistics";
	private static final String EMPLOYEE_STATISTICS_QUERY_NAME = "employeeStatistics";
	private static final String AVERAGE_TIME_QUERY_NAME = "getAverageTime";
	private static final String FIRST_MAIL_QUERY_NAME = "getFirstMailDate";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "apply");
		map.put("button.export.excel", "export");
		map.put("button.export.employee", "exportEmployee");
		return map;
	}

	private void setDefaultValues(ListFormBase form)
	{
		for(RecipientMailState rmState : RecipientMailState.values())
		{
			form.setField(rmState.name(), false);
		}
		form.setField(MailStatisticsForm.DELETED, false);
		Date toDate = new Date();
		Date fromDate = new Date();
		form.setField("toDate", toDate);
		Long defaultPeriod = ConfigFactory.getConfig(MailConfig.class).getDefaultPeriod();
		fromDate.setDate(toDate.getDate() - defaultPeriod.intValue());
		form.setField("fromDate", fromDate);
	}


	protected FormProcessor<ActionMessages, ?> getFormProcessor(MailStatisticsForm frm) throws Exception
	{
		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);
		Form editForm = MailStatisticsForm.STATISTICS_FORM;
		//��������� ������, ��������� ������������
		addLogParameters(new FormLogParametersReader("������, ��������� �������������", editForm, fields));

		return createFormProcessor(valuesSource, editForm);
	}


	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		frm.setFromStart(true);
		setDefaultValues(frm);
		MailStatisticsOperation op = createOperation(MailStatisticsOperation.class);
		frm.setField("areas", op.getAreas());
		return mapping.findForward(FORWARD_START);
	}

	protected void updateAdditionalFormData(ListFormBase frm, MailStatisticsOperation op) throws BusinessException
	{
		frm.setField("areas", op.getAreas());
	}


	/**
	 * ��������� ������
	 * @param mapping ������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward apply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MailStatisticsForm frm = (MailStatisticsForm) form;
		MailStatisticsOperation operation = createOperation(MailStatisticsOperation.class);

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			doFilter(result, operation, frm);
			updateAdditionalFormData(frm, operation);
		}
		else
		{
			updateAdditionalFormData(frm, operation);
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ��������� � excel
	 * @param mapping ������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MailStatisticsForm frm = (MailStatisticsForm) form;
		MailStatisticsExportOperation operation = createOperation(MailStatisticsExportOperation.class);
		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Calendar fromDate = DateHelper.toCalendar((Date)result.get("fromDate"));
			Calendar toDate = DateHelper.toCalendar((Date)result.get("toDate"));
			Query unloadDataQuery = getQuery(result, operation, frm, STATISTICS_QUERY_NAME);
			Query averageTimeQuery = getQuery(result, operation, frm, AVERAGE_TIME_QUERY_NAME);
			Query firstMailQuery = getQuery(result, operation, frm, FIRST_MAIL_QUERY_NAME);
			operation.initialize(fromDate,toDate,unloadDataQuery,averageTimeQuery,firstMailQuery);
			//��������� ���� �� ��������� ���������� ��� ����������� ��������
			DownloadAction.unload(operation.getEntity(), MAIL_STATISTICS_FILE_TYPE, FILE_NAME, frm, request);
			doFilter(result, operation, frm);
			updateAdditionalFormData(frm, operation);
		}
		else
		{
			updateAdditionalFormData(frm, operation);
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ��������� � excel
	 * @param mapping ������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward exportEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MailStatisticsForm frm = (MailStatisticsForm) form;
		EmployeeMailStatisticsExportOperation operation = createOperation(EmployeeMailStatisticsExportOperation.class);
		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Query unloadDataQuery = getQuery(result, operation, frm, EMPLOYEE_STATISTICS_QUERY_NAME);
			operation.initialize(unloadDataQuery);
			//��������� ���� �� ��������� ���������� ��� ����������� ��������
			DownloadAction.unload(operation.getEntity(), MAIL_STATISTICS_FILE_TYPE, FILE_NAME, frm, request);
			doFilter(result, operation, frm);
			updateAdditionalFormData(frm, operation);
		}
		else
		{
			updateAdditionalFormData(frm, operation);
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	private Query getQuery(Map<String, Object> filterParams, MailStatisticsOperation operation, MailStatisticsForm frm, String queryName) throws Exception
	{
		Query query = operation.createQuery(queryName);
		fillQuery(query, filterParams, frm);
		return query;
	}

	/**
	 * ��������� ������ ����������� ��� ��������� ������.
	 * @param query ������
	 * @param filterParams ��������� �������.
	 */
	protected void fillQuery(Query query, Map<String, Object> filterParams, MailStatisticsForm frm) throws BusinessException
	{
		query.setParameterList("type", getType(filterParams));
		query.setParameterList("state", getState(filterParams));
		query.setParameter("theme", filterParams.get("theme"));
		query.setParameter("response_method", filterParams.get("response_method"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
		Date toDate = (Date) filterParams.get("toDate");
		// ���������� 1 ����, ����� ����������� ������, ������������ ����� 00:00 ��������� toDate ����.
		// � ����� � ����, � hql �����������     mail.CREATION_DATE < :extra_toDate
		//toDate != null �.� ��� ���� ���������� ��������� �������������� ����������
		toDate = DateHelper.add(toDate, 0, 0, 1);
		query.setParameter("toDate", toDate);
		query.setParameterList("user_TB", frm.getUserTBs());
		query.setParameterList("area_uuid", frm.getAreaUUIDs());
	}

	/**
	 * ��������� ������ � ���������� �����.
	 * @param filterParams ��������� ����������.
	 * @param operation �������� ��� ��������� ������
	 * @param frm ����� ��� ����������.
	 */
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, MailStatisticsForm frm) throws Exception
	{
		Query query = operation.createQuery(STATISTICS_QUERY_NAME);
		fillQuery(query, filterParams, frm);
		//noinspection unchecked
		frm.setData(query.executeList());

		Query averageTimeQuery = operation.createQuery(AVERAGE_TIME_QUERY_NAME);
		fillQuery(averageTimeQuery, filterParams, frm);
		frm.setAverageTime((MailDateSpan) averageTimeQuery.executeUnique());

		Query firstMailQuery = operation.createQuery(FIRST_MAIL_QUERY_NAME);
		fillQuery(firstMailQuery, filterParams, frm);
		frm.setFirstMailDate((Calendar) firstMailQuery.executeUnique());
	}


	private List<String> getState(Map<String, Object> data)
	{
		List<String> stateRecipient = new ArrayList<String>();
		for(RecipientMailState rmState : RecipientMailState.values())
		{
			if( (Boolean) data.get(rmState.name()) )
		        stateRecipient.add(rmState.name());
		}
		if((Boolean) data.get(MailStatisticsForm.DELETED))
			stateRecipient.add(MailStatisticsForm.DELETED);
		return stateRecipient;
	}

	private List<String> getType(Map<String, Object> data)
	{
		List<String> type = new ArrayList<String>();
		for(MailType mType : MailType.values())
		{
			if((Boolean)data.get(mType.name()) )
		        type.add(mType.name());
		}
		return type;
	}
}
