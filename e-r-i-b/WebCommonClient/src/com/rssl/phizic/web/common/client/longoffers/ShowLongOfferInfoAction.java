package com.rssl.phizic.web.common.client.longoffers;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action дл€ отображени€ детальной информации автоплатежей типа LongOffer
 * @author osminin
 * @ created 30.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferInfoAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.print", "print");
		map.put("button.saveLongOfferName", "saveLongOfferName");
		map.put("button.showScheduleReport", "showScheduleReport");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getDefaultScheduleItems(mapping, form, request, response);
	}


	protected ActionForward doFilter(ActionMapping mapping, ShowLongOfferInfoForm frm, GetLongOfferInfoOperation operation) throws Exception
	{
		LongOfferLink longOfferLink = operation.getLongOfferLink();
		frm.setLongOfferLink(longOfferLink);
		frm.setField("longOfferName", longOfferLink.getName());
		frm.setPayerAccount(getPayerAccount(longOfferLink, operation));
		return mapping.findForward(FORWARD_SHOW);
	}

	private String getPayerAccount(LongOfferLink link, GetLongOfferInfoOperation operation) throws BusinessException
	{
		StringBuilder payerAccount = new StringBuilder();
		CommonLogin login = getPerson().getLogin();
		if (!StringHelper.isEmpty(link.getChargeOffAccount()))
		{
			payerAccount.append(operation.getAccountName(link.getChargeOffAccount(), login));
			payerAccount.append(" ").append(link.getChargeOffAccount());
			return payerAccount.toString();
		}
		if (!StringHelper.isEmpty(link.getChargeOffCard()))
		{
			payerAccount.append(operation.getCardName(link.getChargeOffCard(), login));
			payerAccount.append(" ").append(MaskUtil.getCutCardNumber(link.getChargeOffCard()));
			return payerAccount.toString();
		}
		return "";
	}

	public ActionForward saveLongOfferName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLongOfferInfoForm frm = (ShowLongOfferInfoForm) form;
		MapValuesSource valuesSourse = new MapValuesSource(frm.getFields());
		Form showForm = ShowLongOfferInfoForm.NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSourse, showForm);
		if (processor.process())
		{
			Map<String, Object> results = processor.getResult();
			EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
			operation.initialize(frm.getId(), LongOfferLink.class);
			operation.saveLinkName((String) results.get("longOfferName"));
			return start(mapping,frm, request, response);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			GetLongOfferInfoOperation op = createOperation(GetLongOfferInfoOperation.class);
			op.initialize(frm.getId());
			return doFilter(mapping, frm, op);
		}
	}

	public ActionForward showScheduleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLongOfferInfoForm frm = (ShowLongOfferInfoForm) form;

		GetLongOfferInfoOperation operation = createOperation(GetLongOfferInfoOperation.class);
		operation.initialize(frm.getId());

		MapValuesSource valuesSourse = new MapValuesSource(frm.getFields());
		Form showForm = ShowLongOfferInfoForm.DATE_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSourse, showForm);
		if (processor.process())
		{
			try
			{
				Map<String, Object> results = processor.getResult();
				Calendar fromDate = DateHelper.toCalendar((Date) results.get("fromDate"));
				Calendar toDate = DateHelper.toCalendar((Date) results.get("toDate"));
				frm.setScheduleItems(operation.getScheduleReport(fromDate, toDate));
			}
			catch (BusinessLogicException e)
			{
				ActionMessages message = new ActionMessages();
				message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveMessages(request, message);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return doFilter(mapping, frm, operation);
	}

	private Person getPerson()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	/**
	 * ќтображаем записи за последние 6 мес€цев
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward getDefaultScheduleItems(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		ShowLongOfferInfoForm frm = (ShowLongOfferInfoForm) form;
		GetLongOfferInfoOperation operation = createOperation(GetLongOfferInfoOperation.class);
		operation.initialize(frm.getId());

		Calendar toDate = operation.getToDate();
		Calendar fromDate = operation.getFromDate();
		frm.setField("fromDate", fromDate.getTime());
		frm.setField("toDate", toDate.getTime());
		try
		{
			frm.setScheduleItems(operation.getScheduleReport(fromDate, toDate));

			if (operation.isUseStoredResource())
			{
				frm.setAbstractMsgError(StoredResourceMessages.getUnreachableStatement());
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getLongOfferLink().getValue()));
			}
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return doFilter(mapping, frm, operation);
	}

}
