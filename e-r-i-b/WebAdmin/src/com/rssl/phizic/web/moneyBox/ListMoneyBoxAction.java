package com.rssl.phizic.web.moneyBox;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ChangeStatusMoneyBoxEmployeeOperation;
import com.rssl.phizic.operations.moneyBox.EmployeeListMoneyBoxOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.EmployeeConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 * ���� ����������� ������ ������� � ��� ����������
 */
public class ListMoneyBoxAction extends ListActionBase
{
	private static final String FORWARD_CONFIRM_CHANGE_STATE = "ConfirmChangeState";
	private static final String FORWARD_SUCCESS = "Success";

	private static final String BUTTON_NAME_REFUSE = "refuse";
	private static final String BUTTON_NAME_RECOVER = "recover";
	private static final String BUTTON_NAME_CLOSE = "disable";

	private static final String BUSINESS_EXCEPTION_FAIL = "�������� �������� ����������";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.refuse", "refuse");
		map.put("button.recover", "recover");
		map.put("button.disable", "close");
		map.put("button.confirmRefuse", "confirmRefuse");
		map.put("button.confirmRecover", "confirmRecover");
		map.put("button.confirmDisable", "confirmClose");
		map.put("button.claim.confirm", "confirmClaim");
		map.put("button.claim.remove", "cancelClaim");
		return map;
	}

	/**
	 * ���-����� ������������ �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return changeStatus(mapping, form, request, ChangePaymentStatusType.REFUSE);
	}

	/**
	 * ���-����� �������������� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward recover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return changeStatus(mapping, form, request, ChangePaymentStatusType.RECOVER);
	}

	/**
	 * ���-����� �������� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return changeStatus(mapping, form, request, ChangePaymentStatusType.CLOSE);
	}

	/**
	 * ������������� ������������ �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward confirmRefuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return confirmChangeState(form, BUTTON_NAME_REFUSE, ChangePaymentStatusType.REFUSE);
	}

	/**
	 * ������������� �������������� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward confirmRecover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return confirmChangeState(form, BUTTON_NAME_RECOVER, ChangePaymentStatusType.RECOVER);
	}

	/**
	 * ������������� �������� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward confirmClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return confirmChangeState(form, BUTTON_NAME_CLOSE, ChangePaymentStatusType.CLOSE);
	}

	private ActionForward confirmChangeState(ActionForm form, String buttonName, ChangePaymentStatusType type) throws Exception
	{
		ListMoneyBoxForm frm = (ListMoneyBoxForm) form;
		String[] ids = frm.getSelectedIds();

		if (ids.length != 1)
		{
			saveSessionError("������� ���� ������.", null);
			return start(getCurrentMapping(), form, currentRequest(), currentResponse());
		}

		Long selectedId = frm.getSelectedId();
		ChangeStatusMoneyBoxEmployeeOperation operation = getConfirmOperation(type, selectedId);
		ConfirmationManager.sendRequest(operation);

		ConfirmStrategyType confirmStrategyType = operation.getStrategyType();
		if (ConfirmStrategyType.none == confirmStrategyType)
		{
			return changeStatus(getCurrentMapping(), frm, currentRequest(), type);
		}

		operation.getRequest().setPreConfirm(true);

		frm.setId(selectedId);
		frm.setButtonName(buttonName);

		return getCurrentMapping().findForward(FORWARD_CONFIRM_CHANGE_STATE);
	}

	private ChangeStatusMoneyBoxEmployeeOperation getConfirmOperation(ChangePaymentStatusType type, Long id) throws BusinessLogicException, BusinessException
	{
		ChangeStatusMoneyBoxEmployeeOperation operation = createOperation(ChangeStatusMoneyBoxEmployeeOperation.class, type.getEmployeeServiceKey());

		operation.initialize(id, type);
		operation.setStrategyType();
		operation.setConfirmStrategyTypeForDocument(operation.getStrategyType());
		saveOperation(currentRequest(), operation);

		return operation;
	}

	/**
	 *  ������������� ������ � ������� "��������"
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward confirmClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final ListMoneyBoxForm frm = (ListMoneyBoxForm) form;
		return processClaim(new ClaimAction()
		{
			public void process() throws Exception
			{
				ExistingSource source = new ExistingSource(frm.getSelectedId(), new ChecksOwnersPaymentValidator());
				EmployeeConfirmFormPaymentOperation operation = createOperation(EmployeeConfirmFormPaymentOperation.class, DocumentHelper.getEmployeeServiceName(source));
				operation.initialize(source);
				operation.confirm();
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("������ ������� ������������.", false), null);
			}
		}, frm.getSelectedId());
	}

	/**
	 * ������ ������ � ������� "��������"
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward cancelClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final ListMoneyBoxForm frm = (ListMoneyBoxForm) form;
		return processClaim(new ClaimAction()
		{
			public void process() throws Exception
			{
				ExistingSource source = new ExistingSource(frm.getSelectedId(), new ChecksOwnersPaymentValidator());
				RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, DocumentHelper.getEmployeeServiceName(source));
				removeOperation.initialize(source);
				removeOperation.remove();
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("������ ������� �������.", false), null);
			}
		}, frm.getSelectedId());
	}

	private ActionForward processClaim(ClaimAction action, Long id)
	{
		ActionMessages msgs = new ActionMessages();
		doProcess(action, id, msgs);

		if (!msgs.isEmpty())
		{
			saveSessionErrors(currentRequest(), msgs);
		}

		return getCurrentMapping().findForward(FORWARD_SUCCESS);
	}

	private void doProcess(ClaimAction action, Long id, ActionMessages msgs)
	{
		try
		{
			if (id != null && id != 0)
			{
				action.process();
			}
			else
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�������� ���� ������", false));
			}
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(BUSINESS_EXCEPTION_FAIL, false));
		}
	}

	/**
	 * ����� ����� ��� ��������� ������� ����� �������
	 * @param type - ��� �������� ��������� �������, ������� �� ���-������, �� �������� ������ ������
	 */
	private ActionForward changeStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, ChangePaymentStatusType type) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		doChange(request, type, msgs);

		if (!msgs.isEmpty())
		{
			saveSessionErrors(currentRequest(), msgs);
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}

	private void doChange(HttpServletRequest request, ChangePaymentStatusType type, ActionMessages msgs) throws Exception
	{
		try
		{
			ChangeStatusMoneyBoxEmployeeOperation operation = getOperation(request);
			ConfirmHelper.clearConfirmErrors(operation.getRequest());
			resetOperation(request);

			List<String> errors = ConfirmationManager.readResponse(operation, getCompositeFieldValueSource(request, operation));
			if (!errors.isEmpty())
			{
				ConfirmHelper.saveConfirmErrors(operation.getRequest(), errors);
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errors.get(0), false));
				return;
			}

			operation.confirm();
			saveOperationMessages(operation);

			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(type.getSuccessMessage(), false));
		}
		catch (BusinessException e)
		{
			String message = e.getMessage();
			message = StringHelper.isEmpty(message) ? BUSINESS_EXCEPTION_FAIL : message;
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		catch (SecurityLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
	}

	/**
	 * ��������� ����������� ���� ��� ���������������.
	 * @param request �������.
	 * @param operation ��������.
	 * @return ������ �����.
	 * @throws BusinessException
	 */
	private FieldValuesSource getCompositeFieldValueSource(HttpServletRequest request, ConfirmFormPaymentOperation operation) throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		BusinessDocument payment = operation.getDocument();
		map.put(com.rssl.phizic.security.config.Constants.CONFIRM_PLASTIC_FORM_NAME_FIELD, payment.getFormName());
		map.put(com.rssl.phizic.security.config.Constants.CONFIRM_PLASTIC_EMPLOYEE_LOGIN_FIELD, new SimpleService().findById(CommonLogin.class, payment.getCreatedEmployeeLoginId()).getUserId());

		MapValuesSource mapValuesSource = new MapValuesSource(map);

		return new CompositeFieldValuesSource(new RequestValuesSource(request), mapValuesSource);
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		EmployeeListMoneyBoxOperation operation = createOperation(EmployeeListMoneyBoxOperation.class, "EmployeeMoneyBoxManagement");
		operation.initialize();
		return operation;
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ListMoneyBoxForm frm = (ListMoneyBoxForm) form;
		EmployeeListMoneyBoxOperation op = (EmployeeListMoneyBoxOperation) operation;

		frm.setActivePerson(op.getPerson());
		frm.setData(op.getData());
	}

	private void saveOperationMessages(ChangeStatusMoneyBoxEmployeeOperation operation)
	{
		ActionMessages msgs = new ActionMessages();
		for (String message : operation.getStateMachineEvent().getMessageCollector().getMessages())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		}
		saveSessionMessages(currentRequest(), msgs);
	}

	private interface ClaimAction
	{
		public void process() throws Exception;
	}
}
