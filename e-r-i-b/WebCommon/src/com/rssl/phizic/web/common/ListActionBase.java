package com.rssl.phizic.web.common;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListActionBase extends OperationalActionBase
{
	/**
	 * ������� ��� ��������� ������� ������ �������������:
	 * final �������� �� ������ ���, �, ���� ���� ������� ��� ������ ��� ���������� ������,
	 * ����� ����������, ������ �� ����� � ��� �������� ���� ��������� ���������� � ���.
	 *
	 * ������ �����, ������������ ������ �������� ��������� ������� ����.
	 */
	protected final Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = getAditionalKeyMethodMap();
		map.put("button.remove", "remove");
		map.put("button.filter", "filter");
		return map;
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected abstract ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException;

	/**
	 * ������� ����� ����������.
	 * � ����������� �������(�� ����������� ENH00319) ����� ������������ frm.FILTER_FORM
	 * @param frm struts-�����
	 * @param operation ��������. ��������� �����(�������� ��������) ������������ ��������
	 * @return ����� ����������
	 */
	protected abstract Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation);

	/**
	 * ��������� ������ ����������� ��� ��������� ������.
	 * @param query ������
	 * @param filterParams ��������� �������.
	 */
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException {}

	/**
	 * ��������� ������ � ���������� �����.
	 * @param filterParams ��������� ����������.
	 * @param operation �������� ��� ��������� ������
	 * @param frm ����� ��� ����������.
	 */
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams);
		frm.setData(query.executeList());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	/**
	 * @param frm �����
	 * @return ������������ ��� �������, ������������ � ��������(�� ��������� "list").
	 */
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "list";
	}

	/**
	 * ���������� ����� ��������������� �������.
	 * ��������: �������� ������ ��� ������ ���� � ������ (������������� ������)
	 * ������ ����� �������������� �� ��������!!!
	 * @param frm ����� ��� ����������
	 * @param operation �������� ��� ��������� ������.
	 */
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception {}

	/**
	 * @param frm
	 * @param operation
	 * @return �������� ���������� �� ���������.
	 */
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		return new HashMap<String, Object>();
	}

	/** �������� �������� ���������� �� ��������� (��� ������� �������)
	 * @param frm  - �����
	 * @return �������� ���������� �� ���������.
	 */
	public Map<String, Object> getDefaultParameters(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListEntitiesOperation operation = createListOperation(frm);
		return getDefaultFilterParameters(frm, operation);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ��������
	 * @param operation �������� ��������
	 * @param id ������������� �������� ��������
	 * @return � ������ ���������� ������ ��������� ��� ����������� ������������. null ��� ������� ��������
	 */
	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();

		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		return msgs;
	}

	/**
	 * �� ���� ������� ������ ����� �������� ��������� �� ������	 
	 */
	public ActionForward getFavouriteForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return filter(mapping, form, request, response);
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		frm.setFromStart(true);

		try
		{
			doStart(createListOperation(frm), frm);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return createActionForward(frm);
	}

	protected void doStart(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Map<String, Object> filterParameters = getFilterParams(frm, operation);

		//��������� ������ ���������� �� ���������
		addLogParameters(new FormLogParametersReader("��������� ����������", getFilterForm(frm, operation), filterParameters));

		setDefaultOrderParameters(currentRequest());
		doFilter(filterParameters, operation, frm);
	}

	/**
	 * ������������� ��������� ���������� �������� �����������.
	 * @param request
	 */
	protected void setDefaultOrderParameters(HttpServletRequest request){	}

	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Form filterForm = getFilterForm(frm, operation);
		FieldValuesSource fieldValuesSource = getFilterValuesSource(frm);
		if (!fieldValuesSource.isEmpty())
		{
			//���� ������ ���� � �����, ���������� �� � ���������� � �������� ����������� ��������
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldValuesSource, filterForm);
			if (processor.process())
				return processor.getResult();
			throw new FormProcessorException(processor.getErrors());
		}

		//����� �������� �����������,
		Map<String, Object> savedFilterParams = loadFilterParameters(frm, operation);
		if (MapUtils.isNotEmpty(savedFilterParams))
			return savedFilterParams;

		// � ���� � �� ���, ���������� ��������� �� ���������.
		return getDefaultFilterParameters(frm, operation);
	}

	/**
	 * �����, ������������ ����������� ��������� ����������
	 * ����������� ������������ (�� 22.09.2011 ���������� � SaveFilterParameterAction).
	 * ���� ����������� ���������� ���, ����� ������ ������� null.
	 * ���� ��� ����, �� ������, ����� ������� ������ Map-�, �� �� null.
	 * ATTENTION! � �����������, ����������� ���� �����, ������ ���� ������������� � �����
	 * saveFilterParameters, ����������� ��������� ����������.
	 * @return ����������� ��������� ����������
	 */
	protected Map<String,Object> loadFilterParameters(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		return null;
	}

	/**
	 * @param frm �����
	 * @return �������� �������� ����� ����������
	 */
	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		return new MapValuesSource(frm.getFilters());
	}

	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		ListEntitiesOperation operation = null;

		try
		{
			operation = createListOperation(frm);
			Map<String, Object> filterParameters = getFilterParams(frm, operation);
			//��������� ������ ����������
			addLogParameters(new FormLogParametersReader("��������� ����������", getFilterForm(frm, operation), filterParameters));
			doFilter(filterParameters, operation, frm);
			saveFilterParameters(filterParameters);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
			frm.setFromStart(true);
			forwardFilterFail(frm, operation);
		}
		catch (InactiveExternalSystemException e)
		{
			//������������� ������ ������� ��� ���� ����� �� ���������� ������ ������ � ���� � ������ ������.
			frm.setFromStart(true);
			forwardFilterFail(frm, operation);
			saveInactiveESMessage(request, e);
		}

		return createActionForward(frm);
	}

	/**
	 * ����� ��� ���������� ���������� ����������.
	 * ����������� ������������ (�� 22.09.2011 ���������� � SaveFilterParameterAction).
	 * ATTENTION! � �����������, ����������� ���� �����, ������ ���� ������������� � �����
	 * loadFilterParameters, ������� ������ ����� ���������� ����������� ���������.
	 * @param filterParams
	 */
	protected void saveFilterParameters(Map<String,Object> filterParams) throws BusinessException {};

	/**
	 * ������� ��� ��������, ����� ������ �� ������ (��������, ��-�� ������������ ���������� �������)
	 * @param form
	 * @param operation
	 */
	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		// ������� ������� ������� �� �����, ���� ��������� ���������� �����������
		form.setData(Collections.emptyList());
		updateFormAdditionalData(form, operation);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());

		ActionMessages errors = new ActionMessages();

		try
		{
			RemoveEntityOperation operation = createRemoveOperation(frm);
			for (Long id : ids)
			{
				errors.add(doRemove(operation, id));
				//��������� ��������� ��������.
				addLogParameters(new BeanLogParemetersReader("������ ��������� ��������", operation.getEntity()));
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		stopLogParameters();
		saveErrors(request, errors);

		return filter(mapping, form, request, response);
	}

	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
