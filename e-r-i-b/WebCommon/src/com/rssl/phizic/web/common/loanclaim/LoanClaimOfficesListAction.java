package com.rssl.phizic.web.common.loanclaim;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.loanclaim.LoanClaimOfficesOperation;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Balovtsev
 * @since 04.02.14
 */
public class LoanClaimOfficesListAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		Application currentApplication = ApplicationInfo.getCurrentApplication();

		if (currentApplication == Application.PhizIA)
		{
			return createOperation(LoanClaimOfficesOperation.class, "LoanClaimEmployeeService");
		}
		return createOperation(LoanClaimOfficesOperation.class, "ExtendedLoanClaim");
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		Map<String, Object> filter      = new HashMap<String, Object>();
		Collection<String>  additionTBs = new HashSet<String>();

		Boolean isLoanOffer = Boolean.valueOf((String) form.getFilter("isLoanOffer"));
		if(isLoanOffer)
		{
			String tb = getLastLogonCardTb();
			updateTb(tb,additionTBs);
		}
		else
		{
			String relationType = (String) form.getFilter("relationType");
			if (StringHelper.equalsNullIgnore(relationType, "getPaidOnSbrfAccount") || StringHelper.equalsNullIgnore(relationType, "workInSbrf"))
			{
				CreditProductConditionService conditionService = new CreditProductConditionService();
				for (CreditProductCondition condition : conditionService.getPublicityConditions())
				{
					additionTBs.addAll(condition.getDepartmentsList());
				}
			}
			else
				updateTb(getLastLogonCardTb(),additionTBs);
		}
		filter.put("additionTBs", additionTBs);
		filter.put("officeInfo",  form.getFilter("officeInfo"));

		form.setData( ((LoanClaimOfficesOperation) operation).getExtendedLoanClaimOffices(filter) );
	}

	private void updateTb(String tb,Collection<String>  additionTBs)
	{
		if (StringHelper.isNotEmpty(tb))
			if ("99".equals(tb) || "38".equals(tb))
			{
				additionTBs.add("99");
				additionTBs.add("38");
			}
			else
				additionTBs.add(tb);
	}

	/**
	 * Получить ТБ карты входа клиента
	 * @return
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private String getLastLogonCardTb() throws BusinessLogicException, BusinessException
	{
		PersonData personData    = PersonContext.getPersonDataProvider().getPersonData();
		String     lastLogonCard = personData.getLogin().getLastLogonCardNumber();

		//Для гостевой заявки на кредит ТБ берем из конфига, если нет карты
		if (personData.isGuest() && StringHelper.isEmpty(lastLogonCard))
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			return loanClaimConfig.getGuestLoanDepartmentTb();
		}

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		try
		{
			if (personData.isGuest())
			{
				String[] cardIds = new String[1];
				cardIds[0] = lastLogonCard + "^null^" + "40400001";
				GroupResult<String, Card> cardGroupResult = bankrollService.getCard(cardIds);
				Card card = GroupResultHelper.getResults(cardGroupResult).get(0);
				if (card == null)
					throw new BusinessException("Не найдена карта последнего успешного входа");
				Office office = card.getOffice();
				return office.getCode().getFields().get("region");
			}
			else
			{
				DepartmentService departmentService = new DepartmentService();
				return departmentService.getNumberTB(personData.getPerson().getDepartmentId());
			}
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}
}
