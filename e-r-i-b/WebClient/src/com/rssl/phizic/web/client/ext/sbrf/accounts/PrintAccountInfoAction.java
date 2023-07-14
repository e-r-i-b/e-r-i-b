package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: filimonova
 * @ created: 06.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintAccountInfoAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "print";
	public static final String FORWARD_ABSTRACT_PRINT = "printAbstract";
	private static final String DATE_STAMP = "dd/MM/yyyy";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);

		PrintAccountInfoForm frm  = (PrintAccountInfoForm) form;


		operation.initialize(frm.getId());

		AccountLink account = operation.getAccount();
		frm.setAccountLink(account);
		if (frm.isPrintAbstract())
		{
			if (operation.isUseStoredResource())
			{
				saveMessage(request, StoredResourceMessages.getUnreachableStatement());
				return mapping.findForward(FORWARD_ABSTRACT_PRINT);
			}

			try
			{
				setAbstractPeriod(frm, operation, request);
			}
			catch (BusinessLogicException e)
			{
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Неверно задан период выписки", false));
				saveMessages(request, errors);
			}
			try
			{
				AccountAbstract accountAbstract = operation.getAccountAbstract();
				frm.setAccountAbstract(accountAbstract);
				if (account == null || accountAbstract == null)
				{
					saveMessage(request, getResourceMessage("accountInfoBundle", "accountAbstractError"));
				}
			}
			catch (BusinessLogicException e)
			{
				if (GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE .equals(e.getMessage()))
					saveMessage(request, getResourceMessage("accountInfoBundle", "accountAbstractError"));
				else
					saveMessage(request, e.getMessage());
			}
			return mapping.findForward(FORWARD_ABSTRACT_PRINT);
		}

		return mapping.findForward(FORWARD_PRINT);
	}

	private void setAbstractPeriod(PrintAccountInfoForm form, GetAccountAbstractExtendedOperation operation, HttpServletRequest request) throws BusinessLogicException
	{
		String typePeriod = request.getParameter("typePeriod");

		if ("week".equals(typePeriod) || "month".equals(typePeriod))
		{
			Calendar toDate = DateHelper.getCurrentDate();
			Calendar fromDate = DateHelper.getCurrentDate();
			if("month".equals(typePeriod))
				fromDate.add(Calendar.MONTH, -1);
			else
				fromDate.add(Calendar.WEEK_OF_MONTH, -1);
			operation.setDateFrom(fromDate);
			operation.setDateTo(toDate);
		}
		else
		{
			FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(new RequestValuesSource(request), buildForm());
			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
			}
			else
			{
				operation.setDateTo(DateHelper.toCalendar((Date)formProcessor.getResult().get("toDateString")));
				operation.setDateFrom(DateHelper.toCalendar((Date)formProcessor.getResult().get("fromDateString")));
			}
		}
	}

	private Form buildForm()
	{
	    ArrayList<Field> fields = new ArrayList<Field>();
	    FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
	    fieldBuilder.setName("fromDateString");
	    fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите начальную дату для построения выписки."),
				new DateFieldValidator(DATE_STAMP, "Вы неправильно указали начальную дату выписки. Пожалуйста, введите дату в формате ДД/ММ/ГГГГ."));
		fieldBuilder.setParser(new DateParser(DATE_STAMP));
	    fields.add( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("toDateString");
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите конечную дату для построения выписки."),
				new DateFieldValidator(DATE_STAMP, "Вы неправильно указали конечную дату выписки. Пожалуйста, введите дату в формате ДД/ММ/ГГГГ."));
		fieldBuilder.setParser(new DateParser(DATE_STAMP));
		fields.add( fieldBuilder.build() );

		FormBuilder formBuilder = new FormBuilder();
	    formBuilder.setFields(fields);
		return formBuilder.build();
	}


}
