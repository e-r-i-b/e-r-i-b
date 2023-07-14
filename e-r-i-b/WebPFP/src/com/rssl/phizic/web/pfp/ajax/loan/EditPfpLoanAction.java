package com.rssl.phizic.web.pfp.ajax.loan;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.pfp.EditPfpLoanOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditPfpLoanAction extends OperationalActionBase
{
	private static final String EDIT_RESULT = "EditResult";
	private static final String REMOVE_RESULT = "RemoveResult";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.save","save");
		map.put("button.remove","remove");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new UnsupportedOperationException("Данный метод не поддерживается");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpLoanForm frm = (EditPfpLoanForm) form;
		EditPfpLoanOperation operation = createOperation(EditPfpLoanOperation.class);
		try
		{
			if(frm.getLoanId() == null)
				operation.initializeNew(frm.getProfileId());
			else
				operation.initialize(frm.getProfileId(),frm.getLoanId());

			FormProcessor<ActionMessages, ?> processor = createFormProcessor(operation,frm);
			if(processor.process())
			{
				Map<String,Object> result = processor.getResult();
				PersonLoan personLoan = operation.getLoan();
				personLoan.setDictionaryLoan((LoanKindProduct)result.get("dictionaryLoan"));
				personLoan.setComment((String)result.get("loanComment"));

				Currency nationalCurrency = MoneyUtil.getNationalCurrency();
				BigDecimal amount = (BigDecimal) result.get("loanAmount");
				personLoan.setAmount(new Money(amount,nationalCurrency));

				Date startDate = (Date)result.get("startDate");
				Date endDate = (Date)result.get("endDate");
				personLoan.setStartDate(DateHelper.toCalendar(startDate));
				personLoan.setEndDate(DateHelper.toCalendar(endDate));

				BigDecimal rate = (BigDecimal)result.get("loanRate");
				personLoan.setRate(rate);

				operation.save();
				frm.setPersonLoan(operation.getLoan());

			}
			else
			{
				saveErrors(request,processor.getErrors());
			}
		}
		catch(BusinessLogicException ble)
		{
			saveError(request, new ActionMessage(ble.getMessage(), false));
		}
		return mapping.findForward(EDIT_RESULT);
	}

	private FormProcessor<ActionMessages, ?> createFormProcessor(EditPfpLoanOperation operation, EditPfpLoanForm form) throws BusinessException
	{
		MapValuesSource mapValuesSource = new MapValuesSource(form.getFields());
		Form validationForm = EditPfpLoanForm.createForm(operation.getLoanKindDictionary());
		return createFormProcessor(mapValuesSource, validationForm);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpLoanForm frm = (EditPfpLoanForm) form;
		EditPfpLoanOperation operation = createOperation(EditPfpLoanOperation.class);
		try
		{
			operation.initialize(frm.getProfileId(),frm.getLoanId());
			operation.removeLoan();
		}
		catch (BusinessLogicException e)
		{
			saveError(request,e);
		}
		return mapping.findForward(REMOVE_RESULT);
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
