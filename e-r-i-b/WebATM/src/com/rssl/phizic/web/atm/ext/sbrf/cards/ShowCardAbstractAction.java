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
	//��� �������� ���������� ���������
	private static final String PAGINATION_OFFSET_KEY = "paginationOffset";
	private static final String PAGINATION_SIZE_KEY = "paginationSize";
	private static final String CREDIT_CARD = "������� �� ����� ���� ������������ ��� ��������� �����. �� ��������� ����� ����� ���������� ������ ����-������� (10 ��������� ��������).";
	private static final String NO_CARD_ACCOUNT = "��� ������ ����� �� �� ������ �������� ����������� �������.";

	//��������� ���� ���������� ��� ���������
	private MapValuesSource getMapSource(FilterFormBase frm)
	{
		//��������� ���� ���������� ��� ���������
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
		// ���� count == null �� ���������� ���� ������� from � to, ��������� ����������� �������
		if (!abstractAvailable(operation)) // ��� ��������� �����, ��� ��� ����� � ������� ��� ���� ����� ������� �� ��������
			return mapping.findForward(FORWARD_START);
		// /private/accounts/print ������� ���� � ������� dd/MM/yyyy
		String from = String.format(FORMAT_PATTERN,(Date)result.get(FilterFormBase.FROM_DATE_NAME));
		String to = String.format(FORMAT_PATTERN,(Date)result.get(FilterFormBase.TO_DATE_NAME));

		// �������� �� ������ �����
		ActionRedirect redirect = new ActionRedirect(PRINT_ABSTRACT_PATH);
		// ������������� ��������� ��� ������
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
		addLogParameters(new BeanLogParemetersReader("������ ��������������� ��������", operation.getCard()));
		return forward;
	}

	private boolean abstractAvailable(GetCardAbstractOperation operation) throws BusinessException
	{
		// ����� NPE ���������� �� �����, ���� ��� � ���������, �� ������, ��� ������������� ��������.
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
