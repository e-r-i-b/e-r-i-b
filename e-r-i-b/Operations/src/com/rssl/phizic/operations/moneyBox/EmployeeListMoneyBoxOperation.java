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
 * �������� ��������� ����������� ������ �������
 */
public class EmployeeListMoneyBoxOperation extends AutoSubscriptionOperationBase implements ListEntitiesOperation
{
	private static MoneyBoxService moneyBoxService = new MoneyBoxService();

	private List<AutoSubscriptionLink> moneyBoxes = new ArrayList<AutoSubscriptionLink>();

	/**
	 * ������������� ��������
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		//������� ��� �������, ��������� �� AutoPay
		for (AutoSubscriptionLink link : getPersonData().getAutoSubscriptionLinks())
		{
			if (link.getValue().getType().equals(CardToAccountLongOffer.class))
			{
				moneyBoxes.add(link);
			}
		}

		//��� �� ������� �������, ��������� ������������� ��������
		moneyBoxes.addAll(MoneyBoxService.adaptToLinks(moneyBoxService.findSavedClaimByLogin(getPersonData().getLogin().getId())));

		//��������� ������ ������� �� ��������
		Collections.sort(moneyBoxes, new EmployeeListMoneyBoxComparator());
	}

	/**
	 * @return ������ ��������
	 */
	public List<AutoSubscriptionLink> getData()
	{
		return Collections.unmodifiableList(moneyBoxes);
	}
}
