package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import com.rssl.phizic.operations.fund.response.ListFundSenderResponseOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author usachev
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Action для просмотра списка входящих запросов на сбор средств
 */

public class ListFundSenderResponseAction extends OperationalActionBase
{
	private static String FORWARD_START = "Start";
	/**
	 * Получение списка входящих запросов на сбор средств
	 */
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFundSenderResponseForm frm = (ListFundSenderResponseForm) form;
		ListFundSenderResponseOperation operation = createOperation(ListFundSenderResponseOperation.class);
		Map<String, Object> params = getParameters(frm);
		operation.init(params);
		frm.setData(operation.getData());
		frm.setAvatarsMap(operation.getAvatarMap());
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Валидация входных данных
	 * @param form Форма с параметрами
	 * @return Map'а с валидированными значениями
	 * @throws Exception
	 */
	private Map<String, Object> getParameters(ListFormBase form) throws Exception
	{
		ListFundSenderResponseForm frm = (ListFundSenderResponseForm) form;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()),frm.FORM);
		if (!processor.process())
		{
			throw new FormProcessorException(processor.getErrors());
		}
		return processor.getResult();
	}
}
