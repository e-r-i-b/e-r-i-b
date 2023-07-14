package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.comparator.AccountLinkComparator;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.operations.ListEntitiesOperation;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author vagin
 * @ created: 20.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoSubscriptionOperation extends AutoSubscriptionOperationBase implements ListEntitiesOperation
{
	private List<AutoSubscriptionLink> subscriptionLinks = new ArrayList<AutoSubscriptionLink>();


	public void initialize(Class<? extends AutoSubscription> ... subscriptionsClasses) throws BusinessException, BusinessLogicException
	{
		super.initialize();

		if (ArrayUtils.isEmpty(subscriptionsClasses))
		{
			return;
		}

		for (AutoSubscriptionLink item : getPersonData().getAutoSubscriptionLinks())
		{
			if (isAllowSubscription(item.getValue(), subscriptionsClasses))
			{
				subscriptionLinks.add(item);
			}
		}
		Collections.sort(subscriptionLinks, AccountLinkComparator.getInstance());
	}

	private boolean isAllowSubscription(AutoSubscription autoSubscription, Class<? extends AutoSubscription> ... subscriptionsClasses)
	{
		for (Class<? extends AutoSubscription> subscriptionClass : subscriptionsClasses)
		{
			if (autoSubscription.getType() == subscriptionClass)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Получения списка подписок на автоплатеж
	 * @return список подписок
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException
	{
		return Collections.unmodifiableList(subscriptionLinks);
	}
}
