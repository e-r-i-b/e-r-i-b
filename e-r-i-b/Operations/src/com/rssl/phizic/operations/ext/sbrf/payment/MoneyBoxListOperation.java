package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.moneyBox.MoneyBoxService;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.*;

/**
 * @author vagin
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 * ќпераци€ просмотра списка копилок дл€ указанного ресурса.
 */
public class MoneyBoxListOperation extends OperationBase implements ListEntitiesOperation
{
	private List<AutoSubscriptionLink> moneyBoxes = new ArrayList<AutoSubscriptionLink>();
	private static MoneyBoxService moneyBoxService = new MoneyBoxService();

	/**
	 * »ницализаци€ списка копилок оформленых на указанный вклад.
	 * @param accountLink - линк на вклад.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(AccountLink accountLink) throws BusinessLogicException, BusinessException
	{
		List<AutoSubscriptionLink> autoSubscriptionLinks = getPersonData().getAutoSubscriptionLinks();

		for (AutoSubscriptionLink subscriptionLink : autoSubscriptionLinks)
		{
			AutoSubscription moneyBox = subscriptionLink.getValue();
			if (moneyBox.getType().equals(CardToAccountLongOffer.class) && accountLink.getNumber().equals(moneyBox.getAccountNumber()) && moneyBox.getAutoPayStatusType() != AutoPayStatusType.Closed)
				moneyBoxes.add(subscriptionLink);
		}
		List<AutoSubscription> moneyBoxClaims = moneyBoxService.findSavedClaimByLoginAndAccount(accountLink.getNumber(), PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxClaims));
	}

	/**
	 * »ницализаци€ списка копилок оформленых с указанной карты
	 * @param cardLink - линк на карту.
	 * @param includeTemplates - необходимо ли включать в список копилки в статусе "„ерновик"
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(CardLink cardLink, boolean includeTemplates) throws BusinessException, BusinessLogicException
	{
		List<AutoSubscriptionLink> autoSubscriptionLinks = PersonContext.getPersonDataProvider().getPersonData().getAutoSubscriptionLinks();

		for (AutoSubscriptionLink subscriptionLink : autoSubscriptionLinks)
		{
			AutoSubscription moneyBox = subscriptionLink.getValue();
			if (moneyBox.getType().equals(CardToAccountLongOffer.class) && cardLink.getNumber().equals(moneyBox.getCardNumber()) && moneyBox.getAutoPayStatusType() != AutoPayStatusType.Closed)
				moneyBoxes.add(subscriptionLink);
		}
		if(includeTemplates)
		{
			List<AutoSubscription> moneyBoxClaims = moneyBoxService.findSavedClaimByLoginAndCard(cardLink.getNumber(), PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
			moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxClaims));
		}
	}

	/**
	 * »ницализаци€ списка копилок оформленых с указанной карты
	 * @param cardLink - линк на карту.
	 * @param accountLink - линк на вклад
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(CardLink cardLink, AccountLink accountLink) throws BusinessException, BusinessLogicException
	{
		List<AutoSubscriptionLink> autoSubscriptionLinks = PersonContext.getPersonDataProvider().getPersonData().getAutoSubscriptionLinks();

		for (AutoSubscriptionLink subscriptionLink : autoSubscriptionLinks)
		{
			AutoSubscription moneyBox = subscriptionLink.getValue();
			if (moneyBox.getType().equals(CardToAccountLongOffer.class) && cardLink.getNumber().equals(moneyBox.getCardNumber()) && accountLink.getNumber().equals(moneyBox.getAccountNumber()) && moneyBox.getAutoPayStatusType() != AutoPayStatusType.Closed)
				moneyBoxes.add(subscriptionLink);
		}
		List<AutoSubscription> moneyBoxClaims = moneyBoxService.findSavedClaimByLoginCardAndAccount(accountLink.getNumber(), cardLink.getNumber(), getPersonData().getLogin().getId());

		moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxClaims));
	}

	/**
	 * »нициализаци€ операции
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		for (AutoSubscriptionLink link : getPersonData().getAutoSubscriptionLinks())
		{
			AutoSubscription subscription = link.getValue();
			if (subscription.getType().equals(CardToAccountLongOffer.class))
			{
				moneyBoxes.add(link);
			}
		}
		//“ак же выводим копилки, ожидающие подтверждени€ клиентом
		moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxService.findSavedClaimByLogin(getPersonData().getLogin().getId())));
	}

	/**
	 * ѕолучение мапы прив€зки целей к копилкам.
	 * @return мап вида <÷ель>:<—писок копилок>, если коплка не дл€ цели в качестве цели строка "EMPTY".
	 * @throws BusinessException
	 */
	public Map<Object, ArrayList<AutoSubscriptionLink>> getMoneyBoxesTargets() throws BusinessException
	{
		Map<Object, ArrayList<AutoSubscriptionLink>> mapResult = new HashMap<Object, ArrayList<AutoSubscriptionLink>>();
		//если вообще есть копилки, смотрим прив€зку к цел€м.
		if (!moneyBoxes.isEmpty())
		{
			AccountTargetService targetService = new AccountTargetService();
			List<AccountTarget> targets = targetService.findTargetsByOwner(PersonContext.getPersonDataProvider().getPersonData().getLogin());
			for (AutoSubscriptionLink moneyBox : moneyBoxes)
			{
				Object target = null;
				for (AccountTarget tar : targets)
				{
					if (tar.getAccountNum() != null && tar.getAccountNum().equals(moneyBox.getValue().getAccountNumber()))
					{
						target = tar;
						break;
					}
				}
				target = target != null ? target : "EMPTY";
				if (mapResult.get(target) != null)
				{
					mapResult.get(target).add(moneyBox);
				}
				else
				{
					ArrayList<AutoSubscriptionLink> list = new ArrayList<AutoSubscriptionLink>();
					list.add(moneyBox);
					mapResult.put(target, list);
				}
			}
		}
		return mapResult;
	}

	/**
	 * ѕолучение списка копилок
	 * @return - список копилок дл€ указанного ресурса.
	 */
	public List<AutoSubscriptionLink> getData()
	{
		return Collections.unmodifiableList(moneyBoxes);
	}

	private PersonData getPersonData()
	{
		return PersonContext.getPersonDataProvider().getPersonData();
	}
}
