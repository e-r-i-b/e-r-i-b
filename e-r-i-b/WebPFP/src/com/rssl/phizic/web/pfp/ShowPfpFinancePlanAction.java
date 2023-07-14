package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.ShowPfpFinancePlanOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.download.DownloadAction;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPfpFinancePlanAction extends PassingPFPActionBase
{
	private static final String PFP_FINANCE_PLAN_OPERATION_TYPE = "PfpFinancePlan";
	private static final String EDIT_PORTFOLIO = "EditPortfolio";
	private static String FILE_NAME = "Raschet_buduschey_stoimosti_portfelya";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMap = super.getKeyMethodMap();
		keyMap.put("button.unloadCalculation","unloadCalculation");
		keyMap.put("button.changeStartAmount", "changeStartAmount");
		keyMap.put("button.changeIncome", "changeIncome");
		keyMap.put("button.next","next");
		return keyMap;
	}

	protected ShowPfpFinancePlanOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ShowPfpFinancePlanOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException, BusinessLogicException
	{
		updateFormData((ShowPfpFinancePlanForm) form, operation.getProfile());
		updateFormAdditionalData((ShowPfpFinancePlanForm) form, (ShowPfpFinancePlanOperation) operation);
	}

	private void updateFormData(ShowPfpFinancePlanForm form, PersonalFinanceProfile profile)
	{
		if(profile.getPlanMoney() != null)
			form.setField("wantedAmount",profile.getPlanMoney().getDecimal());
		if(profile.getPlanDate() != null)
		{
			Calendar planedDate = profile.getPlanDate();
			form.setField("incomeYear",planedDate.get(Calendar.YEAR));
			form.setField("incomeQuarter",DateHelper.getQuarter(planedDate));
		}
	}

	private void updateFormAdditionalData(ShowPfpFinancePlanForm form, ShowPfpFinancePlanOperation operation) throws BusinessException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		form.setExecutionDate(profile.getStartPlaningDate());
		form.setLastTargetDate(profile.getLastTargetDate());
		form.setStartCapital(profile.getPortfolioByType(PortfolioType.START_CAPITAL));
		form.setQuarterlyInvest(profile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST));
		form.setPersonTargetList(profile.getPersonTargets());
		form.setPersonLoanList(profile.getPersonLoans());
		form.setDictionaryLoanList(operation.getLoanKindDictionaryList());
		form.setElderPerson(ShowPfpFinancePlanForm.MAX_CREDIT_AGE.compareTo(operation.getPersonAge()) <= 0);
	}

	private ActionForward show(ActionMapping mapping, ShowPfpFinancePlanForm form, ShowPfpFinancePlanOperation operation) throws Exception
	{
		updateFormAdditionalData(form, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward unloadCalculation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpFinancePlanForm frm = (ShowPfpFinancePlanForm) form;
		ShowPfpFinancePlanOperation operation = getOperation(ShowPfpFinancePlanOperation.class,frm);												
		DownloadAction.unload(operation.createCalculationPDF(), PFP_FINANCE_PLAN_OPERATION_TYPE, FILE_NAME + ".pdf", frm, request);

		return show(mapping,frm,operation);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpFinancePlanForm frm = (ShowPfpFinancePlanForm) form;
		ShowPfpFinancePlanOperation operation = getOperation(ShowPfpFinancePlanOperation.class, frm);
		try
		{
			operation.nextStep();
			return getRedirectForward(operation);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}
	}

	public ActionForward changeStartAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpFinancePlanForm frm = (ShowPfpFinancePlanForm) form;
		ShowPfpFinancePlanOperation operation = getOperation(ShowPfpFinancePlanOperation.class, frm);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()),ShowPfpFinancePlanForm.CHANGE_PORTFOLIO_AMOUNT_FORM);
		if(processor.process())
		{
			Map<String,Object> result = processor.getResult();
			PortfolioType portfolioType = PortfolioType.valueOf((String)result.get("changedPortfolio"));
			BigDecimal addAmountDecimal = (BigDecimal)result.get("addAmount");
			Money addAmount = new Money(addAmountDecimal, MoneyUtil.getNationalCurrency());
			operation.changeStartAmount(portfolioType,addAmount);
		}
		else
		{
			saveErrors(request,processor.getErrors());
		}
		return show(mapping,frm,operation);
	}

	public ActionForward changeIncome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpFinancePlanForm frm = (ShowPfpFinancePlanForm) form;
		ShowPfpFinancePlanOperation operation = getOperation(ShowPfpFinancePlanOperation.class, frm);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()),ShowPfpFinancePlanForm.CHANGE_PORTFOLIO_INCOME_FORM);
		if(processor.process())
		{
			try
			{
				Map<String,Object> result = processor.getResult();
				PortfolioType portfolioType = PortfolioType.valueOf((String)result.get("changedPortfolio"));
				BigDecimal wantedIncome = (BigDecimal)result.get("wantedIncome");
				operation.changePortfolioIncome(portfolioType,wantedIncome);
				return goToEditPortfolio(mapping,frm,operation,portfolioType);
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
			}
		}
		else
		{
			saveErrors(request,processor.getErrors());
		}
		return show(mapping,frm,operation);
	}

	private ActionForward goToEditPortfolio(ActionMapping mapping, ShowPfpFinancePlanForm frm, ShowPfpFinancePlanOperation operation, PortfolioType portfolioType)
	{
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(EDIT_PORTFOLIO).getPath());
		redirect.addParameter("id", frm.getId());
		redirect.addParameter("portfolioId", operation.getProfile().getPortfolioByType(portfolioType).getId());
		return redirect;
	}

}
