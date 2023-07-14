package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.csa.ViewCSAActionLogOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.LogFilterBaseAction;

import java.util.Calendar;
import java.util.Map;

/**
 * @author vagin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ������� ������ � ���
 */
public class ViewCSAActionLogAction extends LogFilterBaseAction
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewCSAActionLogOperation operation = createOperation("ViewCSAActionLogOperation", "ViewCSAActionLogService");
		ViewCSAActionLogForm form = (ViewCSAActionLogForm)frm;
		operation.initializePerson(form.getId());
		return operation;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		super.doFilter(filterParams, operation, form);
		updateFormAdditionalData(form, operation);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ViewCSAActionLogForm.CSA_ACTION_FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams);

		query.setParameter("identificationType", filterParams.get("identificationType"));
		query.setParameter("operationType", filterParams.get("operationType"));
		query.setParameter("confirmationType", filterParams.get("confirmationType"));
		query.setParameter("ipAddress", StringHelper.isEmpty((String) filterParams.get("ipAddress")) ? null : filterParams.get("ipAddress"));
		query.setParameter("employeeFio", StringHelper.isEmpty((String) filterParams.get("employeeFio")) ? null : filterParams.get("employeeFio"));
		query.setParameter("employeeLogin", StringHelper.isEmpty((String) filterParams.get("employeeLogin")) ? null : filterParams.get("employeeLogin"));
	}

	/**
	 * � ������� ������ ��������� ����� �� ����� 1 �����
	 * @return �������� ���� �� ���������.
	 */
	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, -24);
		return date;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation)
	{
		ViewCSAActionLogOperation op = (ViewCSAActionLogOperation)operation;
		ViewCSAActionLogForm form = (ViewCSAActionLogForm)frm;
		form.setActivePerson(op.getPerson());
		form.setPassport(op.getPassport());
	}

	@Override
	protected String[] getIndexedParameters()
	{
		//� query ������ ���� �������� ��� ����������� ������� CSA_ACTIONLOG_UNIVERSAL_ID(���+���+��+��+���� ������ ��������) - ��������� � ��������.
		return new String[0];
	}

	@Override
	protected void completeParametersForIndex(Query query)
	{
		//� ������� ������ ��� ��� ������� �� ����������. ������ �� ������.
	}

	@Override
	protected String getLogName()
	{
		//�������� ������� �� �������������
		return null;
	}
}
