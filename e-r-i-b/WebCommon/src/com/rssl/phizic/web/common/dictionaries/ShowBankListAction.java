package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.operations.dictionaries.GetBankListOperation;
import com.rssl.phizic.operations.dictionaries.RemoveBankOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.common.forms.Form;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakov
 * @ created 15.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class ShowBankListAction extends ListActionBase
{

	private static final String BANK_BY_BIC_FORWARD = "BankByBic";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.getByBIC", "getByBIC");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		//Для ATM делаем возможность получить справочник банков без авторизации
		if (ApplicationUtil.isATMApi() && !SecurityUtil.isAuthenticationComplete())
			return new GetBankListOperation();
		return createOperation(GetBankListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowBankListForm.FILTER_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveBankOperation.class);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter("name",filterParams.get("title"));
        query.setParameter("BIC",filterParams.get("BIC"));
        query.setParameter("place",filterParams.get("city"));
		//если ourBank не true то в запрос кидаем null, чтобы вернуть все банки
		query.setParameter("ourBank",Boolean.valueOf((String)filterParams.get("ourBank"))?true:null);
		query.setParameter("currentDate", Calendar.getInstance());
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;

		RemoveBankOperation operation = (RemoveBankOperation) createRemoveOperation(frm);
		ActionMessages errors = new ActionMessages();

		for(String id : frm.getSelectedIds())
		{
			try
			{
				operation.initialize(id);
				//Фиксируем удаляемые сущности.
				addLogParameters(new BeanLogParemetersReader("Данные удаляемой сущности", operation.getEntity()));
				operation.remove();
			}
			catch (Exception e)
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить банк " + operation.getEntity().getName() + ".", false));
			}
		}
		saveErrors(request, errors);

		return filter(mapping, form, request, response);
	}

	/**
	 * Получение банка по БИК
	 */
	public ActionForward getByBIC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		ListEntitiesOperation operation = createListOperation(frm);
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("BIC", frm.getField("BIC"));
		doFilter(filterParameters, operation, frm);
		return mapping.findForward(BANK_BY_BIC_FORWARD);
	}
}
