package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestLogonRequestCountRestriction implements OperationRestriction<GuestPhoneAuthenticationOperation>
{
	private static final GuestLogonRequestCountRestriction INSTANCE = new GuestLogonRequestCountRestriction();

	public static GuestLogonRequestCountRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(GuestPhoneAuthenticationOperation operation) throws Exception
	{
		Config config = ConfigFactory.getConfig(Config.class);

		int maxCount = config.getGuestEntryAttempts();
		long requestsInterval = config.getGuestEntrySMSCooldown() * 1000;

		Calendar startDate = DateHelper.getPreviousMilliSeconds(requestsInterval * maxCount);

		List<Calendar> creationDates = GuestOperation.getCreateOperationLastDates(operation.getPhone(), operation.getClass(), maxCount + 1, startDate);
		if(CollectionUtils.isEmpty(creationDates))
			return;

		int counter = 0;
		Calendar prevDate = Calendar.getInstance();

		// перебираем с конца
		for(Calendar creationDate : creationDates)
		{
			if(DateHelper.diff(prevDate, creationDate) >= requestsInterval)
				break;

			counter++;
			prevDate = creationDate;
		}

		if(counter >= maxCount)
			throw new TooManyRequestException("Превышено количество запросов на вход по телефону " + operation.getPhone());
	}

	protected int getRequestsInterval()
	{
		return ConfigFactory.getConfig(Config.class).getUserRegistrationRequestCheckInterval();
	}
}
