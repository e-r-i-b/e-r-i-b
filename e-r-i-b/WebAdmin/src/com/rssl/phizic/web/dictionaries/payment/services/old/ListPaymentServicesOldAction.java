package com.rssl.phizic.web.dictionaries.payment.services.old;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOld;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.api.EditPaymentServiceOldOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.api.RemovePaymentServiceOldOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListPaymentServicesOldAction extends ListActionBase
{
	private static final String ADD_FORWARD = "Add";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.add", "add");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListPaymentServicesOperationOld");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemovePaymentServiceOldOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemovePaymentServiceOldOperation op = (RemovePaymentServiceOldOperation) operation;
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			if (((PaymentServiceOld)operation.getEntity()).isSystem())
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			}
			else
			{
				ActionMessage message = new ActionMessage("������ " + op.getEntity().getName() +
						" �� ����� ���� ������� �� �����������, ��� ��� ������� ���������� �����, " +
						"������������ ��, ���� ������� ����������� ������", false);
				msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			}
		}
		return msgs;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		String[] ids = frm.getSelectedIds();
		//���� ������� �� 1 ������ ��������� �� ����� ���������� ������ �������� ������(�������� �������������� �� jsp)
		if (ids.length != 1)
			return  mapping.findForward(ADD_FORWARD);
		Long id = new Long(ids[0]);
		EditPaymentServiceOldOperation operation = createOperation(EditPaymentServiceOldOperation.class);
		//���� � ������ ��������� ����������, �� ������� ��������� �� ������
		if (operation.hasProvider(id))
		{
			ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("� ������ � id="+id+" ������ �������� ����������� ������, ��� ��� ������� ���������� �����, ������������ ��.", false));
		    saveMessages(request, msgs);
			return filter(mapping, form, request, response);
		}
		//���� ������� �������� ������ 3, �� ������� ���������
		if (operation.getLevelOfHierarchy(id) >= 3)
		{
			ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�� �� ������ �������� ������ ����� ������ 3 ������ �����������.", false));
		    saveMessages(request, msgs);
			return filter(mapping, form, request, response);
		}
		//���� ��� ������, ��������� �� ����� ���������� ������
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(ADD_FORWARD));
		redirect.addParameter("parentId", id);
		return redirect;
	}
}
