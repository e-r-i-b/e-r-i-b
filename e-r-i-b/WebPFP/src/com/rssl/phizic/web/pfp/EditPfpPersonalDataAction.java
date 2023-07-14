package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.personData.ApartmentCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.ChildCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.LoanCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.MaritalStatus;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.pfp.EditPfpPersonalDataOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 05.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPersonalDataAction extends PassingPFPActionBase
{
	private static final String NO_PERSON_SHORT_TERM_ASSETS_MESSAGE = "По техническим причинам информация по картам или вкладам временно недоступна. " +
			"Пожалуйста, самостоятельно заполните поля блока «Наличные и безналичные средства на счетах и вкладах».";
	private static final String NO_PERSON_MEDIUM_TERM_ASSETS_MESSAGE = "По техническим причинам информация по ОМС временно недоступна. " +
			"Пожалуйста, самостоятельно заполните поля блока «Инвестиционные вложения».";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMap = new HashMap<String,String>();
		keyMap.put("button.next","savePersonalData");
		return keyMap;
	}

	protected EditPfpPersonalDataOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(EditPfpPersonalDataOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		EditPfpPersonalDataForm frm = (EditPfpPersonalDataForm) form;
		EditPfpPersonalDataOperation operation = (EditPfpPersonalDataOperation) op;
		updateFormFields(frm, operation);
		updateFormData(frm, operation);
	}

	private ActionForward getStartForward(EditPfpPersonalDataForm frm, EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		updateForm(frm,operation);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private ActionForward getErrorForward(EditPfpPersonalDataForm frm, EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		updateFormData(frm,operation);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private BigDecimal getAmount(Money money)
	{
		if (money == null)
			return null;

		return money.getDecimal();
	}

	private BigDecimal getPersonMediumTermAssetsOther(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		if (profile.getMediumTermAssetsOther() != null)
			return profile.getMediumTermAssetsOther().getDecimal();
		return null;
	}

	private BigDecimal getPersonMediumTermAssetsFunds(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		if (profile.getMediumTermAssetsFunds() != null)
			return profile.getMediumTermAssetsFunds().getDecimal();
		return null;
	}
	private BigDecimal getPersonMediumTermAssetsIMA(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		try
		{
			PersonalFinanceProfile profile = operation.getProfile();
			if (profile.getMediumTermAssetsIMA() != null)
				return profile.getMediumTermAssetsIMA().getDecimal();
			Money personIma = operation.getPersonIMAccounts();
			if (personIma != null && !(personIma.getDecimal().compareTo(BigDecimal.ZERO) == 0))
				return personIma.getDecimal();

			return null;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения суммы средств на ОМС клиента в рублевом эквиваленте по курсу ЦБ.", e);
			saveMessage(currentRequest(), NO_PERSON_MEDIUM_TERM_ASSETS_MESSAGE);
			return null;
		}
	}


	private BigDecimal getPersonShortTermAmountSBRF(EditPfpPersonalDataOperation operation)
	{
		try
		{
			PersonalFinanceProfile profile = operation.getProfile();
			if (profile.getShortTermAssetsSBRF() != null)
				return profile.getShortTermAssetsSBRF().getDecimal();

			Money personSbrf = operation.getPersonShortTermAssets();
		    if (personSbrf!=null && !(personSbrf.getDecimal().compareTo(BigDecimal.ZERO) == 0))
				return personSbrf.getDecimal();
			return null;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения суммы средств на всех счетах и картах клиента в рублевом эквиваленте по курсу ЦБ.", e);
			saveMessage(currentRequest(), NO_PERSON_SHORT_TERM_ASSETS_MESSAGE);
			return null;
		}
	}

	private BigDecimal getPersonShortTermAmountOtherBanks(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		if (profile.getShortTermAssetsOtherBanks() != null)
			return profile.getShortTermAssetsOtherBanks().getDecimal();

		return null;
	}

	private BigDecimal getPersonShortTermAmountCash(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		if (profile.getShortTermAssetsCash() != null)
			return profile.getShortTermAssetsCash().getDecimal();

		return null;
	}


	private LoanCount getCreditCardCount(EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		try
		{
			PersonalFinanceProfile profile = operation.getProfile();
			if (profile.getCreditCardCount() != null)
				return profile.getCreditCardCount();

			return LoanCount.fromValue(operation.getCreditCardCount());
		}
		catch (InactiveExternalSystemException ignore)
		{
			return LoanCount.NONE;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения количества кредитных карт клиента.", e);
			return LoanCount.NONE;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения количества кредитных карт клиента.", e);
			return LoanCount.NONE;
		}
	}

	//поля не редактируемы, если добавлен хотя бы один продукт
	private boolean isViewMode(PersonalFinanceProfile profile)
	{
		List<PersonPortfolio> personPortfolios = profile.getPortfolioList();
		if (CollectionUtils.isEmpty(personPortfolios))
			return false;

		for (PersonPortfolio personPortfolio : personPortfolios)
		{
			if (!personPortfolio.getIsEmpty())
				return true;
		}
		return false;
	}

	private void updateFormFields(EditPfpPersonalDataForm form, EditPfpPersonalDataOperation operation) throws BusinessException, BusinessLogicException
	{
		PersonalFinanceProfile profile = operation.getProfile();
		Person person = operation.getPerson();
		form.setField("personFIO",PersonHelper.getFormattedPersonName(person.getFirstName(),person.getSurName(),person.getPatrName()));
		form.setField("personAge",PersonHelper.getPersonAge(person));
		form.setField("shortTermAssetsSBRF", getPersonShortTermAmountSBRF(operation));
		form.setField("shortTermAssetsOtherBanks", getPersonShortTermAmountOtherBanks(operation));
		form.setField("shortTermAssetsCash", getPersonShortTermAmountCash(operation));
		form.setField("mediumTermAssetsFunds",getPersonMediumTermAssetsFunds(operation));
		form.setField("mediumTermAssetsIMA",getPersonMediumTermAssetsIMA(operation));
		form.setField("mediumTermAssetsOther",getPersonMediumTermAssetsOther(operation));
		form.setField("creditCardCount", getCreditCardCount(operation));

		form.setField("maritalStatus", profile.getMaritalStatus());
		form.setField("childCount", profile.getChildCount());

		form.setField("apartmentCount", profile.getApartmentCount());
		form.setField("mortgageCount", profile.getMortgageCount());
		form.setField("consumerLoanCount", profile.getConsumerLoanCount());
		form.setField("autoLoanCount", profile.getAutoLoanCount());

		form.setField("incomeMoney", getAmount(profile.getIncomeMoney()));
		form.setField("outcomeMoney", getAmount(profile.getOutcomeMoney()));

		form.setField("isViewMode", isViewMode(profile));
	}

	private void updateFormData(EditPfpPersonalDataForm form, EditPfpPersonalDataOperation operation) throws BusinessException
	{		
		form.setPersonTargetList(operation.getPersonTargetList());
	}

	public ActionForward savePersonalData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpPersonalDataForm frm = (EditPfpPersonalDataForm) form;
		EditPfpPersonalDataOperation operation = getOperation(EditPfpPersonalDataOperation.class, frm);
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource, EditPfpPersonalDataForm.EDIT_FORM);		

		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return getErrorForward(frm,operation);
		}

		updateEntity(operation.getProfile(), processor.getResult());
		try
		{
			operation.savePersonalData();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return getStartForward(frm,operation);
		}
		return getRedirectForward(operation);
	}

	public void updateEntity(PersonalFinanceProfile profile, Map<String, Object> result) throws BusinessException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		//Мои средства
		profile.setMaritalStatus(MaritalStatus.valueOf(result.get("maritalStatus").toString()));
		profile.setChildCount(ChildCount.valueOf(result.get("childCount").toString()));

		//Мои кредиты
		profile.setMortgageCount(LoanCount.valueOf(result.get("mortgageCount").toString()));
		profile.setConsumerLoanCount(LoanCount.valueOf(result.get("consumerLoanCount").toString()));
		profile.setAutoLoanCount(LoanCount.valueOf(result.get("autoLoanCount").toString()));
		profile.setCreditCardCount(LoanCount.valueOf(result.get("creditCardCount").toString()));

		if (isViewMode(profile))
			return;

		//Мои средства
		BigDecimal shortTermAssetsSBRF = (BigDecimal)result.get("shortTermAssetsSBRF");
		if (shortTermAssetsSBRF != null)
			profile.setShortTermAssetsSBRF(new Money(shortTermAssetsSBRF, nationalCurrency));
		BigDecimal shortTermAssetsOtherBanks = (BigDecimal)result.get("shortTermAssetsOtherBanks");
		if (shortTermAssetsOtherBanks != null)
			profile.setShortTermAssetsOtherBanks(new Money(shortTermAssetsOtherBanks, nationalCurrency));
		BigDecimal shortTermAssetsCash = (BigDecimal)result.get("shortTermAssetsCash");
		if (shortTermAssetsCash != null)
			profile.setShortTermAssetsCash(new Money(shortTermAssetsCash, nationalCurrency));

		BigDecimal mediumTermAssetsFunds = (BigDecimal)result.get("mediumTermAssetsFunds");
		if (mediumTermAssetsFunds != null)
			profile.setMediumTermAssetsFunds(new Money(mediumTermAssetsFunds, nationalCurrency));
		BigDecimal mediumTermAssetsIMA = (BigDecimal)result.get("mediumTermAssetsIMA");
		if (mediumTermAssetsIMA != null)
			profile.setMediumTermAssetsIMA(new Money(mediumTermAssetsIMA, nationalCurrency));
		BigDecimal mediumTermAssetsOther = (BigDecimal)result.get("mediumTermAssetsOther");
		if (mediumTermAssetsOther != null)
			profile.setMediumTermAssetsOther(new Money(mediumTermAssetsOther, nationalCurrency));
		profile.setApartmentCount(ApartmentCount.valueOf(result.get("apartmentCount").toString()));

		//Мой бюджет
		BigDecimal incomeMoneyAmount = (BigDecimal)result.get("incomeMoney");
		profile.setIncomeMoney(new Money(incomeMoneyAmount,nationalCurrency));
		BigDecimal outcomeMoneyAmount = (BigDecimal)result.get("outcomeMoney");
		profile.setOutcomeMoney(new Money(outcomeMoneyAmount,nationalCurrency));		
	}
}

