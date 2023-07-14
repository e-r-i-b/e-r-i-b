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
 * Ёкшен дл€ организации асинхронного "живого" поиска
 * @ author: Gololobov
 * @ created: 24.09.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AsyncSearchActionBase extends AsyncOperationalActionBase
{
	//ƒоступ к операци€м данной услуги активизирует "живой" поиск
	public static final String ASYNC_SEARCH_ACCESS = "AsyncSearchAccess";
	//–азделитель элементов дл€ строк результат выполнени€ запроса
	private static final char DELIMITER = '@';
	protected static final String SEARCH_FIELD_NAME = "q";

	/**
	 * «апрос к нужной базе
	 * @throws BusinessException
	 */
	protected List<String> search(AsyncSearchForm frm) throws BusinessException
	{
		//ѕроверка доступа к "живому" поиску (вкл/откл "живой" поиск дл€ клиента)
		AsynchSearchOperationBase operation = createSearchOperation();
		if (operation == null)
			return null;
		return operation.search(getQueryParametersMap(frm));
	}

	/**
	 * —оздает операцию дл€ организации поиска. ѕоиск осуществл€етс€ только при наличии доступа к данной операции.
	 * ≈сли необходимо, добавл€ютс€ другие проверки прав
	 * @return AsynchSearchOperationBase
	 */
	protected abstract AsynchSearchOperationBase createSearchOperation() throws BusinessException;

	/**
	 * ћапа с параметрами дл€ выполнени€ запроса
	 * @return Map<String, Object> - мапа с параметрами дл€ выполнени€ запроса:
	 * key - название парметра, value - значение параметра
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
		//¬водима€ строка поиска передаЄтс€ параметром "q"
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
