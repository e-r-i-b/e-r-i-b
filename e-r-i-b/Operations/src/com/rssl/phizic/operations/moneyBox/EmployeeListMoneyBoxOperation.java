package com.rssl.phizic.operations.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.moneyBox.MoneyBoxService;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.AutoSubscriptionOperationBase;
import com.rssl.phizicgate.esberibgate.types.AutoSubscriptionImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция просмотра сотрудником списка копилок
 */
public class EmployeeListMoneyBoxOperation extends AutoSubscriptionOperationBase implements ListEntitiesOperation
{
	private static MoneyBoxService moneyBoxService = new MoneyBoxService();

	private List<AutoSubscriptionLink> moneyBoxes = new ArrayList<AutoSubscriptionLink>();

	/**
	 * Инициализация операция
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		//Выводим все копилки, пришедшие из AutoPay
		for (AutoSubscriptionLink link : getPersonData().getAutoSubscriptionLinks())
		{
			if (link.getValue().getType().equals(CardToAccountLongOffer.class))
			{
				moneyBoxes.add(link);
			}
		}

		//Так же выводим копилки, ожидающие подтверждения клиентом
		moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxService.findSavedClaimByLogin(getPersonData().getLogin().getId())));

		//Сортируем список копилок по статусам
		Collections.sort(moneyBoxes, new EmployeeListMoneyBoxComparator());
	}

	/**
	 * @return список колпилок
	 */
	public List<AutoSubscriptionLink> getData()
	{
		return Collections.unmodifiableList(moneyBoxes);
	}
}
