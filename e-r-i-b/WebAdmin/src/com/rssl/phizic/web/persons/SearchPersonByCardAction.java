package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.userlog.WorkingFromPlasticCardLogger;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.web.persons.formBuilders.PersonFormBuilderBase;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.util.EmployeeInfoUtil;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Поиск клиента по банковской карте
 *
 * @author khudyakov
 * @ created 17.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class SearchPersonByCardAction extends SearchPersonAction
{
	private static final String FORWARD_SHOW_SEARCH_FORM = "ShowSearchForm";

	@Override
	protected void updateForm(SearchPersonForm frm, SearchPersonOperation operation) throws BusinessException, BusinessLogicException
	{
		clearClientCache();
	}

	@Override
	protected Form getSearchForm(SearchPersonForm frm)
	{
		return SearchPersonForm.CARD_SEACH_FORM;
	}

	@Override
	protected ActionForward getErrorActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_SHOW_SEARCH_FORM);
	}

	protected void saveLogicError(HttpServletRequest request, BusinessLogicException e)
	{
		saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
	}

	@Override
	protected void saveInactiveESMessage(HttpServletRequest request, ActionMessages actionMessages)
	{
		ActionMessages allErrors = HttpSessionUtils.getSessionAttribute(request, ActionMessagesKeys.inactiveExternalSystem.getKey());
		if (allErrors != null)
			allErrors.add(actionMessages);
		else allErrors = actionMessages;

		saveSessionErrors(request, allErrors);    
	}

	@Override
	protected void writeErrorLog() throws BusinessException
	{
		writeLog(null);
	}

	@Override
	protected UserVisitingMode getUserVisitingMode(SearchPersonForm frm)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_BY_CARD;
	}

	@Override
	protected void writeLog(Map<String, Object> data) throws BusinessException
	{
		Map<String, String> strData = new HashMap<String, String>();
		strData.put(WorkingFromPlasticCardLogger.OPERATION_FIELD, "Аутентификация через POS-терминал");
		strData.put(WorkingFromPlasticCardLogger.EMPLOYEE_LOGIN_ID_FIELD, EmployeeInfoUtil.getEmployeeInfo().getLogin().getUserId());
		if (data != null)
		{
			strData.put(WorkingFromPlasticCardLogger.SUCCESSFULL_FIELD, "");
			strData.put(WorkingFromPlasticCardLogger.CARD_NUMBER_FIELD, (String) data.get(PersonFormBuilderBase.CARD_NUMBER_FIELD));
			strData.put(WorkingFromPlasticCardLogger.CARD_TYPE_FIELD, (String) data.get(PersonFormBuilderBase.CARD_TYPE_FIELD));
			strData.put(WorkingFromPlasticCardLogger.TRANSACTION_DATE_FIELD, (String) data.get(PersonFormBuilderBase.TRANSACTION_DATE_FIELD));
			strData.put(WorkingFromPlasticCardLogger.TRANSACTION_TIME_FIELD, (String) data.get(PersonFormBuilderBase.TRANSACTION_TIME_FIELD));
			strData.put(WorkingFromPlasticCardLogger.TERMINAL_NUMBER_FIELD, (String) data.get(PersonFormBuilderBase.TERMINAL_NUMBER_FIELD));
		}
		try
		{
			WorkingFromPlasticCardLogger.writeLog(strData);
		}
		catch (SystemException ex)
		{
			throw new BusinessException(ex);
		}
	}
}