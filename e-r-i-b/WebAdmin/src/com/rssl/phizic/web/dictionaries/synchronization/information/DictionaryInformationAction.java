package com.rssl.phizic.web.dictionaries.synchronization.information;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.dictionaries.synchronization.information.DictionaryInformationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ������ � ����������� ������������ � ����� � ������� �������������
 */

public class DictionaryInformationAction extends OperationalActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		HashMap<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("button.sendSynchronizationNotification", "sendNotification");
		keyMethodMap.put("button.startSynchronizationInCurrentBlock", "startCurrentBlockSynchronization");
		return keyMethodMap;
	}

	private DictionaryInformationOperation createListOperation() throws BusinessException
	{
		DictionaryInformationOperation operation = createSendOperation();
		operation.initialize();
		return operation;
	}

	private DictionaryInformationOperation createSendOperation() throws BusinessException
	{
		return createOperation(DictionaryInformationOperation.class);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryInformationOperation operation = createListOperation();
		DictionaryInformationForm form = (DictionaryInformationForm) frm;
		form.setData(operation.getInformation());
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ������������ ������ ������������ ������������� ������������
	 * @param mapping  ������-�������
	 * @param form     ������-�����
	 * @param request  ������
	 * @param response �����
	 * @return ������-�������
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ActionForward sendNotification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryInformationOperation operation = createListOperation();
		operation.sendSynchronizationNotification();
		return mapping.findForward(FORWARD_SUCCESS);
	}

	/**
	 * ������������ ������ ������������ ������������� ������������ � ������� �����
	 * @param mapping  ������-�������
	 * @param form     ������-�����
	 * @param request  ������
	 * @param response �����
	 * @return ������-�������
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ActionForward startCurrentBlockSynchronization(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryInformationOperation operation = createListOperation();
		operation.startCurrentBlockSynchronization();
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
