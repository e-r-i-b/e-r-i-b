package com.rssl.phizic.web.configure.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.operations.locale.LoadEribMessagesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedParameters")
public class LoadLocaleAction extends OperationalActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.load", "load");
		return map;
	}

	private LoadEribMessagesOperation createLoadOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		LoadLocaleForm form = (LoadLocaleForm)frm;
		LoadEribMessagesOperation operation = createOperation(form);

		String id = form.getLocaleId();
		if (StringHelper.isNotEmpty(id))
		{
			operation.initialize(id);
		}
		else
			throw new BusinessLogicException("�� ��������� ���� ��� ��������.");
		return operation;
	}

	protected LoadEribMessagesOperation createOperation(LoadLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(false);
		return createOperation(LoadEribMessagesOperation.class);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * �������� ����� � ����������� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request �������
	 * @param response �������
	 * @return ������������
	 * @throws Exception
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final LoadLocaleForm frm = (LoadLocaleForm) form;
		LoadEribMessagesOperation operation = createLoadOperation(frm);
		FormFile content = frm.getContent();

		ActionMessages actionMessages = validateFile(content);
		if (!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}
		try
		{
			operation.readInputStream(content.getInputStream());
			operation.synch();
		}
		catch (BusinessLogicException e)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}
		if (LocaleHelper.isDefaultLocale(frm.getLocaleId()))
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("��� �������� ����� ��������� ����������� ������ ������ ��� ������ atmAPI", false));
		saveSessionMessages(request, actionMessages);
		return mapping.findForward(FORWARD_SUCCESS);
	}

	private ActionMessages validateFile(FormFile content)
	{
		return FileNotEmptyValidator.validate(content);
	}
}
