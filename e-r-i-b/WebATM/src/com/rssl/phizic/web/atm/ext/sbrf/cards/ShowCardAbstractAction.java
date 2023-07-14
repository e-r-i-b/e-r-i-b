package com.rssl.phizic.web.atm.ext.sbrf.cards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.web.atm.cards.ShowCardInfoATMForm;
import com.rssl.phizic.web.atm.common.FilterFormBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowCardAbstractAction extends ShowCardInfoAction
{
	private static final String PRINT_ABSTRACT_PATH = "/private/accounts/print.do";
	private static final String FORMAT_PATTERN = "%1$td/%1$tm/%1$tY";
	//дл€ передачи параметров паганиции
	private static final String PAGINATION_OFFSET_KEY = "paginationOffset";
	private static final String PAGINATION_SIZE_KEY = "paginationSize";
	private static final String CREDIT_CARD = "¬ыписка не может быть сформирована дл€ кредитной карты. ѕо кредитной карте можно посмотреть только мини-выписку (10 последних операций).";
	private static final String NO_CARD_ACCOUNT = "ƒл€ данной карты ¬ы не можете получить расширенную выписку.";

	//формируем пол€ фильтрации дл€ валидации
	private MapValuesSource getMapSource(FilterFormBase frm)
	{
		//формируем пол€ фильтрации дл€ валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    filter.put(FilterFormBase.COUNT_NAME, frm.getCount());
	    return new MapValuesSource(filter);
	}

	protected ActionForward updateFormData(EditFormBase form, ActionMapping mapping, HttpServletRequest request, GetCardAbstractOperation operation) throws Exception
	{
		ShowCardInfoATMForm frm = (ShowCardInfoATMForm) form;
		MapValuesSource source = getMapSource(frm);

		Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(source, filterForm);
	    if(!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		frm.setCardLink(operation.getCard());
		Map<String, Object> result = processor.getResult();
		Long count = (Long) result.get(FilterFormBase.COUNT_NAME);
		if (count != null)
		{
			frm.setCardAbstract(getCardAbstract(count, frm, operation));
			return mapping.findForward(FORWARD_START);
		}
		// если count == null то одназначно были введены from и to, получение расширенной выписки
		if (!abstractAvailable(operation)) // дл€ кредитной карты, или дл€ карты у которой нет карт счета выписку не получаем
			return mapping.findForward(FORWARD_START);
		// /private/accounts/print ожидает дату в формате dd/MM/yyyy
		String from = String.format(FORMAT_PATTERN,(Date)result.get(FilterFormBase.FROM_DATE_NAME));
		String to = String.format(FORMAT_PATTERN,(Date)result.get(FilterFormBase.TO_DATE_NAME));

		// –едирект на другой Ёкшен
		ActionRedirect redirect = new ActionRedirect(PRINT_ABSTRACT_PATH);
		// ”станавливаем параметры дл€ экшена
		redirect.addParameter("sel", "c:" + frm.getId());
		redirect.addParameter("fromDateString", from);
		redirect.addParameter("toDateString", to);
		if (request.getParameter(PAGINATION_OFFSET_KEY) != null)
			redirect.addParameter(PAGINATION_OFFSET_KEY, request.getParameter(PAGINATION_OFFSET_KEY));
		if (request.getParameter(PAGINATION_SIZE_KEY) != null)
			redirect.addParameter(PAGINATION_SIZE_KEY, request.getParameter(PAGINATION_SIZE_KEY));
		return redirect;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	 	EditFormBase frm = (EditFormBase) form;
		GetCardAbstractOperation operation = createGetCardAbstractOperation(frm.getId());
		ActionForward forward = updateFormData(frm, mapping, request, operation);
		addLogParameters(new BeanLogParemetersReader("ƒанные просматриваемой сущности", operation.getCard()));
		return forward;
	}

	private boolean abstractAvailable(GetCardAbstractOperation operation) throws BusinessException
	{
		// здесь NPE возникнуть не может, если где и возникнет, то раньше, при инициализации операции.
		if (operation.getCard().getCard().getCardType() == CardType.credit)
		{
			createActionMessage(CREDIT_CARD);
			return false;
		}
		if (!operation.isExtendedCardAbstractAvailable())
		{
			createActionMessage(NO_CARD_ACCOUNT);
			return false;
		}
		return true;
	}

	private void createActionMessage(String message)
	{
		ActionMessages actionErrors = new ActionMessages();
		actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		saveErrors(currentRequest(), actionErrors);
	}
}
