package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.EditSmsTemplateOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.web.client.ext.sbrf.mobilebank.MobilebankActionForwardHelper.appendRegistrationParams;

/**
 * @author Erkin
 * @ created 10.06.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class ViewSmsTemplateAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLOSE = "Close";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.back-to-payments", "backToPayments");
		return map;
	}

	private EditSmsTemplateOperation createOperation()
			throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewMobileBankSmsTemplateOperation");
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		Long updateId = frm.getUpdateId();
		if (updateId == null)
			throw new BusinessException("�� ������ ID ������� ������ �������� ���������� �����");

		try {
			// 1. ������������� ��������
			EditSmsTemplateOperation operation = createOperation();
			operation.initialize(updateId);

			// 2. ���������� �����
			frm.setUpdate(operation.getUpdate());
			frm.setCardLink(operation.getCardLink());
			frm.setSmsCommands(operation.getNewSmsCommands());

			// 3. �������� ������������ �������
			operation.deleteUpdate();

		} catch (BusinessLogicException ex) {
			saveError(request, ex);
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ������������ ����� ������ "��������� �� �������� ��������"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backToPayments(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;

		ActionForward forward = mapping.findForward(FORWARD_CLOSE);
		forward = appendRegistrationParams(forward, frm.getPhoneCode(), frm.getCardCode());
		return forward;
	}
}
