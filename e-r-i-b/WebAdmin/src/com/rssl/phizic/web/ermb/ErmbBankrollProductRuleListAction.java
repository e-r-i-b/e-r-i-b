package com.rssl.phizic.web.ermb;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductAvailabilityType;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.operations.ermb.BankrollProductRuleActivateOrDeactivateOperation;
import com.rssl.phizic.operations.ermb.BankrollProductRuleListOperation;
import com.rssl.phizic.operations.ermb.BankrollProductRuleRemoveOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн просмотра списка правил включени€ видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBankrollProductRuleListAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map=new HashMap<String, String>();
		map.put("button.activate", "activate");
		map.put("button.deactivate", "deactivate");
		map.put("button.remove", "remove");
		map.put("button.filter", "filter");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BankrollProductRuleListOperation operation = createOperation("BankrollProductRuleListOperation");
		operation.initialize();

		ErmbBankrollProductRuleListForm frm = (ErmbBankrollProductRuleListForm) form;

		List<BankrollProductRuleView>  ruleViews = buildRulesView(operation.getRules());
		frm.setRules(ruleViews);

		return mapping.findForward(FORWARD_START);
	}

	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	private List<BankrollProductRuleView> buildRulesView(List<BankrollProductRule> rules)
	{
		List<BankrollProductRuleView> ruleViews = new ArrayList<BankrollProductRuleView>(rules.size());

		for (BankrollProductRule rule : rules)
		{
			BankrollProductRuleView ruleView = new BankrollProductRuleView();
			ruleView.setId(rule.getId());
			ruleView.setName(rule.getName());
			ruleView.setCondition(buildConditionString(rule));
			ruleView.setProductsVisibility(buildProductsVisibilityString(rule));
			ruleView.setProductsNotification(buildProductsNotificationString(rule));
			if (rule.isActive())
				ruleView.setStatus(true);
			else ruleView.setStatus(false);

			ruleViews.add(ruleView);
		}

		return ruleViews;
	}

	private String buildConditionString(BankrollProductRule rule)
	{
		BankrollProductAvailabilityType creditCardCriteria = rule.getCreditCardCriteria();
		BankrollProductAvailabilityType depositCriteria = rule.getDepositCriteria();
		BankrollProductAvailabilityType loanCriteria = rule.getLoanCriteria();

		StringBuilder availableProductsCondition = new StringBuilder();

		List<String> availableProducts = new ArrayList<String>();
		if (creditCardCriteria == BankrollProductAvailabilityType.AVAILABLE)
			availableProducts.add("\" редитные карты\"");

		if (depositCriteria == BankrollProductAvailabilityType.AVAILABLE)
			availableProducts.add("\"¬клады\"");

		if (loanCriteria == BankrollProductAvailabilityType.AVAILABLE)
			availableProducts.add("\" редиты\"");

		if (!availableProducts.isEmpty())
		{
			availableProductsCondition.append("наличие продукта ");
			availableProductsCondition.append(StringUtils.join(availableProducts, ", "));
		}

		StringBuilder unavailableProductsCondition = new StringBuilder();

		List<String> unavailableProducts = new ArrayList<String>();
		if (creditCardCriteria == BankrollProductAvailabilityType.UNAVAILABLE)
			unavailableProducts.add("\" редитные карты\"");

		if (depositCriteria == BankrollProductAvailabilityType.UNAVAILABLE)
			unavailableProducts.add("\"¬клады\"");

		if (loanCriteria == BankrollProductAvailabilityType.UNAVAILABLE)
			unavailableProducts.add("\" редиты\"");

		if (!unavailableProducts.isEmpty())
		{
			unavailableProductsCondition.append("отсутствие продукта ");
			unavailableProductsCondition.append(StringUtils.join(unavailableProducts, ", "));
		}

		Integer ageFrom = rule.getAgeFrom();
		Integer ageUntil = rule.getAgeUntil();

		String ageCondition = "";

		if(ageFrom != null && ageUntil != null)
			ageCondition = "возраст " + ageFrom + "-" + ageUntil;
		else if (ageFrom != null)
			ageCondition = "возраст от " + ageFrom;
		else if (ageUntil != null)
			ageCondition = "возраст до " + ageUntil;

		List<String> conditions = new ArrayList<String>();

		String availableProductsConditionString = availableProductsCondition.toString();
		if (StringHelper.isNotEmpty(availableProductsConditionString))
			conditions.add(availableProductsConditionString);

		String unavailableProductsConditionString = unavailableProductsCondition.toString();
		if (StringHelper.isNotEmpty(unavailableProductsConditionString))
			conditions.add(unavailableProductsConditionString);

		if (StringHelper.isNotEmpty(ageCondition))
			conditions.add(ageCondition);

		return StringUtils.join(conditions, ", ");
	}

	private String buildProductsVisibilityString(BankrollProductRule rule)
	{
		List<String> visibleProducts = new ArrayList<String>();
		if (rule.isCardsVisibility())
			visibleProducts.add("карты");
		if (rule.isDepositsVisibility())
			visibleProducts.add("вклады");
		if (rule.isLoansVisibility())
			visibleProducts.add("кредиты");
		if (rule.isNewProductsVisibility())
			visibleProducts.add("новые продукты");

		return StringUtils.join(visibleProducts, ", ");
	}

	private String buildProductsNotificationString(BankrollProductRule rule)
	{
		List<String> notifyProducts = new ArrayList<String>();
		if (rule.isCardsNotification())
			notifyProducts.add("карты");
		if (rule.isCardsNotification())
			notifyProducts.add("вклады");
		if (rule.isLoansNotification())
			notifyProducts.add("кредиты");
		if (rule.isNewProductsNotification())
			notifyProducts.add("новые продукты");

		return StringUtils.join(notifyProducts, ", ");

	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			BankrollProductRuleActivateOrDeactivateOperation operation = createOperation("BankrollProductRuleActivateOrDeactivateOperation");

			ErmbBankrollProductRuleListForm frm = (ErmbBankrollProductRuleListForm) form;

			operation.initialize(frm.getSelectedIds());

			Map<String, Exception> exceptions = operation.activate();
			for (String name : exceptions.keySet())
			{
				Exception ex = exceptions.get(name);
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(ex.getMessage(), new ActionMessage(ex.getMessage(), false));
				saveErrors(request, actionErrors);
			}
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}

		return start(mapping, form, request, response);
	}

	public ActionForward deactivate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			BankrollProductRuleActivateOrDeactivateOperation operation = createOperation("BankrollProductRuleActivateOrDeactivateOperation");

			ErmbBankrollProductRuleListForm frm = (ErmbBankrollProductRuleListForm) form;

			operation.initialize(frm.getSelectedIds());

			Map<String, Exception> exceptions = operation.deactivate();
			for (String name : exceptions.keySet())
			{
				Exception ex = exceptions.get(name);
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(ex.getMessage(), new ActionMessage(ex.getMessage(), false));
				saveErrors(request, actionErrors);
			}
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}


		return start(mapping, form, request, response);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			BankrollProductRuleRemoveOperation operation = createOperation("BankrollProductRuleRemoveOperation");
			ErmbBankrollProductRuleListForm frm = (ErmbBankrollProductRuleListForm) form;
			operation.initialize(frm.getSelectedIds());
			operation.remove();
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}

		return start(mapping, form, request, response);
	}
}
