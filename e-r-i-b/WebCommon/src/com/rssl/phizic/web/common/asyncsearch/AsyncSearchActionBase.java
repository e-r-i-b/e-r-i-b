package com.rssl.phizic.web.common.asyncsearch;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.asynchSearch.AsynchSearchOperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����� ��� ����������� ������������ "������" ������
 * @ author: Gololobov
 * @ created: 24.09.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AsyncSearchActionBase extends AsyncOperationalActionBase
{
	//������ � ��������� ������ ������ ������������ "�����" �����
	public static final String ASYNC_SEARCH_ACCESS = "AsyncSearchAccess";
	//����������� ��������� ��� ����� ��������� ���������� �������
	private static final char DELIMITER = '@';
	protected static final String SEARCH_FIELD_NAME = "q";

	/**
	 * ������ � ������ ����
	 * @throws BusinessException
	 */
	protected List<String> search(AsyncSearchForm frm) throws BusinessException
	{
		//�������� ������� � "������" ������ (���/���� "�����" ����� ��� �������)
		AsynchSearchOperationBase operation = createSearchOperation();
		if (operation == null)
			return null;
		return operation.search(getQueryParametersMap(frm));
	}

	/**
	 * ������� �������� ��� ����������� ������. ����� �������������� ������ ��� ������� ������� � ������ ��������.
	 * ���� ����������, ����������� ������ �������� ����
	 * @return AsynchSearchOperationBase
	 */
	protected abstract AsynchSearchOperationBase createSearchOperation() throws BusinessException;

	/**
	 * ���� � ����������� ��� ���������� �������
	 * @return Map<String, Object> - ���� � ����������� ��� ���������� �������:
	 * key - �������� ��������, value - �������� ���������
	 * @throws BusinessException
	 */
	protected abstract Map<String, Object> getQueryParametersMap(AsyncSearchForm frm) throws BusinessException;

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncSearchForm frm = (AsyncSearchForm) form;
		//�������� ������ ������ ��������� ���������� "q"
		String search = (String) frm.getField(SEARCH_FIELD_NAME);
		if (search == null || StringHelper.isEmpty(search.trim()))
			return null;

		List<String> list = search(frm);
		if (CollectionUtils.isNotEmpty(list))
		{
			frm.setResult(StringHelper.stringListToString(list, DELIMITER));
			return mapping.findForward(FORWARD_SHOW);
		}

		return null;
	}
}
