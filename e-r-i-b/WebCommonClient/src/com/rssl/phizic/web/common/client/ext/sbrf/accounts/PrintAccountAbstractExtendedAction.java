package com.rssl.phizic.web.common.client.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.claims.operation.scan.claim.CreatePrivateScanClaimOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractAction;
import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 03.08.2006
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"ProhibitedExceptionDeclared"})
public class PrintAccountAbstractExtendedAction extends PrintAccountAbstractAction
{
	public static final String FORWARD_ABSTRACT = "PrintAbstract";
	public static final String FORWARD_INFORMATION = "PrintInformation";
	private static final String DATE_STAMP = "dd/MM/yyyy";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		PrintAccountAbstractExtendedForm frm = (PrintAccountAbstractExtendedForm) form;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson user = personData.getPerson();
		frm.setUser(user);
		String typePeriod = request.getParameter("typePeriod");
		if ("week".equals(typePeriod) || "month".equals(typePeriod))
		{
			frm.setTypeExtract(typePeriod);
			initForm(typePeriod, frm);
		}
		else
		{
			FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(new RequestValuesSource(request), buildForm());
			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
				return findForward(frm, mapping);
			}
		}
		return print(mapping, form, request, response);
	}

	protected GetAccountAbstractOperation createGetAccountAbstractOperation(Long accountId, PrintAccountAbstractForm frm)
			throws BusinessException, BusinessLogicException
	{
		/**
		 * в зависимости от параметра формы сopying, выводится либо выписка(copying=false), либо справка о состоянии вклада.
		 */
		PrintAccountAbstractExtendedForm form = (PrintAccountAbstractExtendedForm)frm;
		if(!form.getCopying())
		{
			GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
			operation.initialize(accountId);
			return operation;
		}
		else
		{
			GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
			operation.initialize(accountId);
			return operation;
		}
	}

	@Override
	protected Boolean isWithCardUseInfo()
	{
		try
		{
			return checkAccess(CreatePrivateScanClaimOperation.class);
		}
		catch (BusinessException be)
		{
			log.error(be);
			return false;
		}
	}

	protected ActionForward findForward(PrintAccountAbstractForm frm, ActionMapping mapping)
	{
		PrintAccountAbstractExtendedForm form = (PrintAccountAbstractExtendedForm)frm;
		if(!form.getCopying())
		{
			return mapping.findForward(FORWARD_ABSTRACT);
		}
		else
		{
			return mapping.findForward(FORWARD_INFORMATION);
		}
	}

	protected PrintAccountAbstractForm fillAccountSpecificData(PrintAccountAbstractForm frm, GetAccountAbstractOperation operation) throws Exception
	{
		PrintAccountAbstractExtendedForm form     = (PrintAccountAbstractExtendedForm) frm;
		List<Account>                    accounts = form.getAccounts();

		if(accounts == null)
		   accounts = new ArrayList<Account>();

		AccountLink account = operation.getAccount();
		accounts.add(account.getAccount());
		form.setAccounts(accounts);

		if(form.getCopying())
		{
			GetAccountAbstractExtendedOperation oper = (GetAccountAbstractExtendedOperation)operation;

			Map<Account, DateSpan> periods = form.getPeriodToClose();
			if(periods==null)
				periods = new HashMap<Account, DateSpan>();

			periods.put(account.getAccount(), oper.getPeriod());
			form.setPeriodToClose(periods);
		}

		return form;
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

	private void initForm(String typePeriod, PrintAccountAbstractExtendedForm form)
	{
		Calendar fromDate = DateHelper.getCurrentDate();
		if ("month".equals(typePeriod))
			fromDate.add(Calendar.MONTH, -1);
		else //week
			fromDate.add(Calendar.WEEK_OF_MONTH, -1);

		form.setFromDate(fromDate);
		form.setToDate(DateHelper.getCurrentDate());
	}
}
