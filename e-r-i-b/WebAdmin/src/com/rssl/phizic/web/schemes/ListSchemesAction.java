package com.rssl.phizic.web.schemes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.scheme.GetSchemesListOperation;
import com.rssl.phizic.operations.scheme.RemoveSchemeOperation;
import com.rssl.phizic.operations.scheme.ReplicateAccessSchemeOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.util.NodeUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 08.09.2005
 * Time: 21:17:45
 */
public class ListSchemesAction extends SaveFilterParameterAction
{
	private static final String FORWARD_SYNCH = "Synch";
	private static final String ERROR_NODES_SYNCH_MESSAGE = "�������� ���� ���� ��� ������������� � ����� [%s] ����������� �������.";
	private static final String SUCCESSFULL_SYNCH_MESSAGE = "����� ���� ������� ����������������.";


	protected Map<String, String> getAditionalKeyMethodMap()
	{
	    Map<String,String> keyMethodMap =  new HashMap<String, String>();
		keyMethodMap.put("button.synchronize", "synchronize");
		return keyMethodMap;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetSchemesListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListSchemesForm form = (ListSchemesForm) frm;
		RemoveSchemeOperation operation = createOperation(RemoveSchemeOperation.class);
		operation.setCategories(form.getCategories());
		return operation;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		frm.setData(((GetSchemesListOperation)operation).getAccessSchemes());
		updateFormAdditionalData(frm, operation);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	/**
	 * ���������������� ��������� ����� �������
	 * @param mapping �������
	 * @param frm �����
	 * @param request �������
	 * @param response �������
	 * @return ������������
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward synchronize(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListSchemesForm form = (ListSchemesForm)frm;
		if(ArrayUtils.isEmpty(form.getSelectedNodes())|| ArrayUtils.isEmpty(form.getSelectedIds()))
			throw new BusinessLogicException("�� ������� �������� ��� ����������.");
		if (ArrayUtils.contains(form.getSelectedNodes(), NodeUtil.getCurrentNode()) )
			throw new BusinessLogicException("������ ������������� � ������� ����.");
		ReplicateAccessSchemeOperation operation = getOperationFactory().create(ReplicateAccessSchemeOperation.class);
		operation.initialize(form.getSelectedIds(), form.getSelectedNodes());
		List<Long> errNodes = operation.replicate();

		String message = errNodes.isEmpty() ? SUCCESSFULL_SYNCH_MESSAGE : String.format(ERROR_NODES_SYNCH_MESSAGE, StringUtils.join(errNodes, ", "));
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);
		return getCurrentMapping().findForward(FORWARD_SYNCH);
	}
}