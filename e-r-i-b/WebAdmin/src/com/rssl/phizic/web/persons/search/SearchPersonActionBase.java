package com.rssl.phizic.web.persons.search;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonDataForEmployee;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.operations.person.search.multinode.ChangeNodeLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.persons.SearchPersonForm;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class SearchPersonActionBase extends OperationalActionBase
{
	private static final String CHANGE_NODE_FORWARD = "ChangeNode";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.search", "search");
		return map;
	}

	/**
	 * @return ��������
	 * @throws BusinessException
	 */
	protected abstract SearchPersonOperation createSearchOperation() throws BusinessException;

	protected abstract ActionForward getStartActionForward();
	protected abstract ActionForward getErrorActionForward();
	protected abstract ActionForward getNextActionForward(SearchPersonForm frm) throws BusinessLogicException, BusinessException;
	protected abstract UserVisitingMode getUserVisitingMode(SearchPersonForm frm);

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchPersonForm frm = (SearchPersonForm) form;
		SearchPersonOperation operation = createSearchOperation();

		// ���� ������ �� "�����" � ������ "������ ������ ������������", ������ ����������
		if (frm.getResetClientInformation())
			return doReset(frm);

		if(frm.isContinueSearch())
			return continueSearch(request, frm, operation);

		// ���� ������ �� ����� ��� ����������, ������ ������ ������ �������� ������
		if (MapUtils.isEmpty(frm.getFields()))
			return doStart(frm);

		// ����� �������� ��������� ����� �������
		return doSearch(operation, frm, request);
	}

	private ActionForward doReset(SearchPersonForm form) throws Exception
	{
		clearClientInfo(form);
		return doStart(form);
	}

	private ActionForward doStart(SearchPersonForm frm)
	{
		frm.setDocumentTypes(PersonHelper.getDocumentTypes());
		return getStartActionForward();
	}

	private ActionForward doSearch(SearchPersonOperation operation, SearchPersonForm form, HttpServletRequest request) throws BusinessException
	{
		// ��������� ������, ��������� ������������
		addLogParameters(form);
		try
		{
			FormProcessor<ActionMessages, ?> processor = getFormProcessor(form);
			// ���� ������ �������, �� �������� ����� �������
			if (processor.process())
			{
				doSearch(processor.getResult(), operation, form);
				return getNextActionForward(form);
			}
			//�� ������� ������ -- ��������� ������ � ������ �������� ������
			saveErrors(request, processor.getErrors());
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}
		catch (BusinessLogicException e)
		{
			saveError(request,e);
		}
		return doStart(form);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchPersonForm frm = (SearchPersonForm) form;
		SearchPersonOperation operation = createSearchOperation();

		//��������� ������, ��������� ������������
		addLogParameters(frm);

		try
		{
			//�������� ������ ���������� � �������
			frm.setResetClientInformation(true);
			updateForm(frm, operation);

			FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
			if (processor.process())
			{
				Map<String, Object> params = processor.getResult();
				doSearch(params, operation, frm);
				writeLog(params);
			}
			else
			{
				writeErrorLog();
				saveErrors(request, processor.getErrors());
				updateForm(frm, operation);

				return getErrorActionForward();
			}
		}
		catch (ChangeNodeLogicException e)
		{
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(CHANGE_NODE_FORWARD));
			redirect.addParameter("nodeId",e.getNodeId());
			redirect.addParameter("action",request.getServletPath());
			redirect.addParameter("parameters(continueSearch)","true");
			return redirect;
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			updateForm(frm, operation);

			return getErrorActionForward();
		}
		catch (BusinessLogicException e)
		{
			saveLogicError(request,e);
			writeErrorLog();
			updateForm(frm, operation);

			return getErrorActionForward();
		}

		return getNextActionForward(frm);
	}

	/**
	 * �������� ������ � ��� (��� ������)
	 * @param form �����
	 */
	protected void addLogParameters(SearchPersonForm form)
	{
		addLogParameters(new FormLogParametersReader("������, ��������� ������������� ��� ������ �������", getSearchForm(form), form.getFields()));
	}

	/**
	 * @param form �����, ��� ������� ��������� ����-���������
	 * @return ����-���������
	 */
	protected FormProcessor<ActionMessages, ?> getFormProcessor(SearchPersonForm form)
	{
		return createFormProcessor(new MapValuesSource(form.getFields()), getSearchForm(form));
	}

	/**
	 * ��������������� ����� ������� �� ������ ���������� ����������
	 * @param params �������� ������
	 * @param operation �������� ��� ������
	 * @param form - �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void doSearch(Map<String, Object> params, SearchPersonOperation operation, SearchPersonForm form) throws BusinessException, BusinessLogicException
	{
		operation.initialize(params, getUserVisitingMode(form));
		ActivePerson person = operation.getEntity();
		PersonContext.getPersonDataProvider().setPersonData(new StaticPersonDataForEmployee(person));
	}

	/**
	 * ���������� ����� ������� � ������������� �������
	 * @param request - �������
	 * @param form - �����
	 * @param operation �������� ��� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected ActionForward continueSearch(HttpServletRequest request,SearchPersonForm form, SearchPersonOperation operation) throws BusinessException, BusinessLogicException
	{
		try
		{
			operation.continueSearch(getUserVisitingMode(form));
			PersonContext.getPersonDataProvider().setPersonData(new StaticPersonDataForEmployee(operation.getEntity()));
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			updateForm(form, operation);

			return getErrorActionForward();
		}
		catch (BusinessLogicException e)
		{
			saveLogicError(request,e);
			writeErrorLog();
			updateForm(form, operation);

			return getErrorActionForward();
		}

		return getNextActionForward(form);
	}

	/**
	 * ������� ���� � ������� �� ��������� � �����
	 * @param frm �����
	 */
	protected void clearClientInfo(SearchPersonForm frm)
	{
		PersonContext.getPersonDataProvider().setPersonData(null);
		frm.setActivePerson(null);
	}

	/**
	 * ���������� ������ � ���� ���.
	 * @param data ������ �����.
	 */
	protected void writeLog(Map<String, Object> data) throws BusinessException
	{
	}

	/**
	 * ���������� ������ ����� � ���.
	 */
	protected void writeErrorLog() throws BusinessException
	{
	}

	protected void updateForm(SearchPersonForm frm, SearchPersonOperation operation) throws BusinessException, BusinessLogicException
	{
		frm.setField("identityType", "by_identity_document");

		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		frm.setDocumentTypes(PersonHelper.getDocumentTypes());
		//���� �������� ���� �� ������ ������.
		if (dataProvider == null || dataProvider.getPersonData() == null)
			return;

		//���� ����� ��������� ������ � ��������, �� ������ ��������
		if (frm.getResetClientInformation())
		{
			clearClientCache();
			clearClientInfo(frm);
			return;
		}

		//����� �������������� ������� ��������
		operation.initialize();
		frm.setActivePerson(dataProvider.getPersonData().getPerson());
	}

	protected Form getSearchForm(SearchPersonForm frm)
	{
		return SearchPersonForm.IDENTY_DOCUMENT_SEACH_FORM;
	}

	/**
	 * ���������� ����������� ���������� � ������������ ������ ������������
	 * @param request - �������
	 * @param e - BusinessLogicException.
	 */
	protected void saveLogicError(HttpServletRequest request, BusinessLogicException e)
	{
		saveError(request, e);
	}

	/**
	 * ������� ���� ����(*.xml ������������ ������������) �������
	 */
	protected void clearClientCache() throws BusinessException, BusinessLogicException
	{
	}
}
