package com.rssl.phizic.web.migration;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.migration.MigrationResult;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.migration.ListMigrationClientsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ ����������� ��������
 */

public class ListMigrationClientsAction extends ListActionBase
{
	private static final Map<MigrationResult, String> migrationResultMessages = new HashMap<MigrationResult, String>(2);

	static
	{
		migrationResultMessages.put(MigrationResult.MIGRATED, "������ %s ������� ����������.");
		migrationResultMessages.put(MigrationResult.ERROR, "������ �������� ������� %s.");
	}

	@Override
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> methodMap = super.getAditionalKeyMethodMap();
		methodMap.put("button.migrate", "migrate");
		return methodMap;
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMigrationClientsOperation.class);
	}

	protected ListMigrationClientsOperation createMigrationOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMigrationClientsOperation.class);
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation listOperation) throws Exception
	{
		ListMigrationClientsForm form = (ListMigrationClientsForm) frm;
		ListMigrationClientsOperation operation = (ListMigrationClientsOperation) listOperation;
		form.setDepartments(operation.getDepartments());
		form.setAgreementTypes(operation.getAgreementTypes());
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListMigrationClientsForm.FILTER_FORM;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (frm.isFromStart())
		{
			updateFormAdditionalData(frm, operation);
			return;
		}
		super.doFilter(filterParams, operation, frm);
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter(ListMigrationClientsOperation.CLIENT_PARAMETER_NAME,           filterParams.get(ListMigrationClientsForm.CLIENT_FIELD_NAME));
		query.setParameter(ListMigrationClientsOperation.DOCUMENT_PARAMETER_NAME,         filterParams.get(ListMigrationClientsForm.DOCUMENT_FIELD_NAME));
		query.setParameter(ListMigrationClientsOperation.DEPARTMENT_PARAMETER_NAME,       filterParams.get(ListMigrationClientsForm.DEPARTMENT_FIELD_NAME));
		query.setParameter(ListMigrationClientsOperation.BIRTHDAY_PARAMETER_NAME,         filterParams.get(ListMigrationClientsForm.BIRTHDAY_FIELD_NAME));
		query.setParameter(ListMigrationClientsOperation.AGREEMENT_TYPE_PARAMETER_NAME,   filterParams.get(ListMigrationClientsForm.AGREEMENT_TYPE_FIELD_NAME));
		query.setParameter(ListMigrationClientsOperation.AGREEMENT_NUMBER_PARAMETER_NAME, filterParams.get(ListMigrationClientsForm.AGREEMENT_NUMBER_FIELD_NAME));
	}

	/**
	 * ������ ��������
	 * @param mapping    ������ �������
	 * @param actionForm ������ �����
	 * @param request    ������
	 * @param response   �����
	 * @return ��������� ����������
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedParameters"/*response*/, "UnusedDeclaration"})
	public ActionForward migrate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListMigrationClientsForm form = (ListMigrationClientsForm) actionForm;
		ListMigrationClientsOperation migrationClientsOperation = createMigrationOperation(form);
		for (String id : form.getSelectedIds())
			migrate(migrationClientsOperation, id, request);

		return mapping.findForward(FORWARD_START);
	}

	private void migrate(ListMigrationClientsOperation operation, String id, HttpServletRequest request)
	{
		try
		{
			Long clientId = Long.valueOf(id);
			MigrationResult result = operation.migrate(clientId);
			String message = migrationResultMessages.get(result);
			saveMessage(request, String.format(message, "id=" + id));
		}
		catch (Exception e)
		{
			log.error("������ �������� ������� id=" + id, e);
			saveError(request, "������ �������� ������� id=" + id);
		}
	}
}
