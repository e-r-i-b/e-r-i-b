package com.rssl.phizic.web.csa.nodes;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.csa.nodes.EditNodesAvailabilityInfoOperation;
import com.rssl.phizic.operations.csa.nodes.KickClientsOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ � �������
 */

public class EditNodesAvailabilityInfoAction extends EditActionBase
{
	@Override
	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> methodMap = super.getAdditionalKeyMethodMap();
		methodMap.put("button.kick", "kick");
		return methodMap;
	}

	@Override
	protected EditNodesAvailabilityInfoOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditNodesAvailabilityInfoOperation operation = createOperation("ViewNodesAvailabilityInfoOperation", "ViewNodesAvailabilityInfoManagement");
		operation.initialize();
		return operation;
	}

	@Override
	protected EditNodesAvailabilityInfoOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditNodesAvailabilityInfoOperation operation = createOperation("EditNodesAvailabilityInfoOperation", "EditNodesAvailabilityInfoManagement");
		operation.initialize();
		return operation;
	}

	protected KickClientsOperation createKickOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		KickClientsOperation operation = createOperation(KickClientsOperation.class, "KickClientsManagement");
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditNodesAvailabilityInfoForm form = (EditNodesAvailabilityInfoForm) frm;
		//noinspection unchecked
		List<NodeInfo> infoList = (List<NodeInfo>) entity;
		int index = 0;
		Long[] nodeIds = new Long[infoList.size()];
		for (NodeInfo info : infoList)
		{
			nodeIds[index++] = info.getId();
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_NAME_FIELD_PREFIX + info.getId(), info.getName());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_NEW_USERS_ALLOWED_FIELD_PREFIX + info.getId(), info.isNewUsersAllowed());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_EXISTING_USERS_ALLOWED_FIELD_PREFIX + info.getId(), info.isExistingUsersAllowed());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_TEMPORARY_USERS_ALLOWED_FIELD_PREFIX + info.getId(), info.isTemporaryUsersAllowed());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_USERS_TRANSFER_ALLOWED_FIELD_PREFIX + info.getId(), info.isUsersTransferAllowed());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_ADMIN_AVAILABLE_FIELD_PREFIX + info.getId(), info.isAdminAvailable());
			form.setField(EditNodesAvailabilityInfoForm.NODE_INFO_GUEST_AVAILABLE_FIELD_PREFIX + info.getId(), info.isGuestAvailable());
		}
		form.setNodeIds(nodeIds);
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditNodesAvailabilityInfoForm) frm).getEditForm();
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		//noinspection unchecked
		List<NodeInfo> infoList = (List<NodeInfo>) entity;
		for (NodeInfo info : infoList)
		{
			info.setNewUsersAllowed((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_NEW_USERS_ALLOWED_FIELD_PREFIX + info.getId()));
			info.setExistingUsersAllowed((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_EXISTING_USERS_ALLOWED_FIELD_PREFIX + info.getId()));
			info.setTemporaryUsersAllowed((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_TEMPORARY_USERS_ALLOWED_FIELD_PREFIX + info.getId()));
			info.setUsersTransferAllowed((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_USERS_TRANSFER_ALLOWED_FIELD_PREFIX + info.getId()));
			info.setAdminAvailable((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_ADMIN_AVAILABLE_FIELD_PREFIX+ info.getId()));
			info.setGuestAvailable((Boolean) data.get(EditNodesAvailabilityInfoForm.NODE_INFO_GUEST_AVAILABLE_FIELD_PREFIX+ info.getId()));
		}
	}

	/**
	 * ��������� ������ �������� � �����
	 * @param mapping ������ �������
	 * @param frm    ������ �����
	 * @param request ������
	 * @param response �����
	 * @return ��������� ����������
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedParameters"/*response*/, "UnusedDeclaration"})
	public ActionForward kick(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditNodesAvailabilityInfoForm form = (EditNodesAvailabilityInfoForm) frm;
		try
		{
			KickClientsOperation operation = createKickOperation(form);
			operation.kick();
			return mapping.findForward(FORWARD_SUCCESS);
		}
		catch (BusinessLogicException e)
		{
			updateFormAdditionalData(form, createViewOperation(form));
			saveMessage(request, e.getMessage());
			return createStartActionForward(frm, mapping);
		}
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		//���� �� ����� ������������ ���� �������� � ��������/���������
		EditNodesAvailabilityInfoForm form = (EditNodesAvailabilityInfoForm) frm;
		Boolean valid = true;

		Long [] nodeIds = form.getNodeIds();
		for(Long id: nodeIds)
		{
			Boolean mainBlock  = form.getField(EditNodesAvailabilityInfoForm.NODE_INFO_EXISTING_USERS_ALLOWED_FIELD_PREFIX + id) != null;   //��������
			Boolean tempBlock  = form.getField(EditNodesAvailabilityInfoForm.NODE_INFO_TEMPORARY_USERS_ALLOWED_FIELD_PREFIX + id) != null;  //���������
			Boolean guestBlock = form.getField(EditNodesAvailabilityInfoForm.NODE_INFO_GUEST_AVAILABLE_FIELD_PREFIX + id) != null;          //��������
			if (mainBlock && guestBlock || tempBlock && guestBlock)
			{
				valid = false;
				break;
			}
		}
		if (valid)
			return new ActionMessages();

		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�� �� ������ ��� ������ ����� ������� ������������ �������� ������ � ����������� ������ � ��� ���������� �/��� ��������� ��������.", false));
		return msgs;
	}
}
