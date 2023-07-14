package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.EditSmsTemplateOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ListPaymentsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class ListPaymentsAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLOSE = "Close";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.remove", "remove");
		map.put("button.filter", "start");
		return map;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		return doStart(mapping, form, request, response);
	}

	private ActionForward doStart(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		ListPaymentsForm frm = (ListPaymentsForm) form;
		ListPaymentsOperation operation = createOperation("ListMobileBankPaymentsOperation");
		operation.initialize();

		// ���� ������� �� ��� ���������, ��������� �� ������� ��������
		if (!updateFormData(frm, operation, request))
			return mapping.findForward(FORWARD_CLOSE);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ��������� ����� �������
	 * @param form - �����-�����
	 * @param operation - �������� ��������� ������ ��������
	 * @param request
	 * @return false, ���� ������ ��� ����� �� �������
	 */
	protected boolean updateFormData(ListPaymentsForm form, ListPaymentsOperation operation, HttpServletRequest request)
	{
		String phoneCode = form.getPhoneCode();
		String cardCode = form.getCardCode();

		if (StringHelper.isEmpty(phoneCode))
			return false;
		if (StringHelper.isEmpty(cardCode))
			return false;

		try
		{
			List<RegistrationProfile> registrationProfiles = new LinkedList<RegistrationProfile>();
			registrationProfiles.add(operation.getRegistrationProfile(phoneCode, cardCode));
			form.setRegistrationProfiles(registrationProfiles);
		}
		catch (BusinessException e)
		{
			saveError(request, e);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return true;
	}

	/**
	 * ������������ ����� ������ "������� ������ SMS-�������"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		ListPaymentsForm frm = (ListPaymentsForm) form;

		// 1. �������� ����������
		String phoneCode = frm.getPhoneCode();
		if (StringHelper.isEmpty(phoneCode))
			throw new BusinessException("�� ������ ��� ������ ��������");

		String cardCode = frm.getCardCode();
		if (StringHelper.isEmpty(cardCode))
			throw new BusinessException("�� ������ ��� ������ �����");

		String recipientCode = frm.getSelectedRecipientCode();
		if (StringHelper.isEmpty(recipientCode))
			throw new BusinessException("�� ������ ��� ���������� � ��������� �����");

		String payerCode = frm.getSelectedPayerCode();
		if (StringHelper.isEmpty(payerCode))
			throw new BusinessException("�� ������ ��� ���������� � ��������� �����");

		// 2. ���������� �������� ��������
		EditSmsTemplateOperation operation = createOperation("RemoveMobileBankSmsTemplateOperation");
		operation.initializeNewRemovingUpdate(phoneCode, cardCode, recipientCode, payerCode);
		operation.applyUpdate();

		// 3. ������� �� �������� ��������
		return doStart(mapping, form, request, response);
	}
}
