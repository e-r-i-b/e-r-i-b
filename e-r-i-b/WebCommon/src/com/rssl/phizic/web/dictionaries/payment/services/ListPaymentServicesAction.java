package com.rssl.phizic.web.dictionaries.payment.services;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.EditPaymentServiceOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.ListPaymentServicesOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.RemovePaymentServiceOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListPaymentServicesAction extends SaveFilterParameterAction
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
		return createOperation(ListPaymentServicesOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemovePaymentServiceOperation.class);
	}
	
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemovePaymentServiceOperation op = (RemovePaymentServiceOperation) operation;
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			if (((PaymentService)operation.getEntity()).isSystem())
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

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListPaymentServicesForm form = (ListPaymentServicesForm) frm;
		Query query = operation.createQuery(getQueryName(frm));
		query.setParameter("parent", form.getId());
		frm.setData(query.executeList());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		String[] ids = frm.getSelectedIds();
		//���� ������� �� 1 ������ ��������� �� ����� ���������� ������ �������� ������(�������� �������������� �� jsp)
		if (ids.length != 1)
			return  mapping.findForward(ADD_FORWARD);
		Long id = new Long(ids[0]);
		EditPaymentServiceOperation operation = createOperation(EditPaymentServiceOperation.class);
		//���� ������� �������� ������ 3, �� ������� ���������
		if (operation.getLevelOfHierarchy(id) >= 2)
		{
			ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�� �� ������ �������� ������ ����� ������ 3 ������ �����������.", false));
		    saveMessages(request, msgs);
			return filter(mapping, form, request, response);
		}
		//���� � ������ ��������� ����������, �� ������� ��������� �� ������
		if (operation.hasProvider(id))
		{
			ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("� ������ � id="+id+" ������ �������� ����������� ������, ��� ��� ������� ���������� �����, ������������ ��.", false));
		    saveMessages(request, msgs);
			return filter(mapping, form, request, response);
		}
		//���� ��� ������, ��������� �� ����� ���������� ������
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(ADD_FORWARD));
		redirect.addParameter("parentId", id);
		return redirect;
	}
}
