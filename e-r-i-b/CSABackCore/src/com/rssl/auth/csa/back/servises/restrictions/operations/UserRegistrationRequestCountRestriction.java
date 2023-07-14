package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на количество запросов регистрации пользователя
 */
public class UserRegistrationRequestCountRestriction implements OperationRestriction<UserRegistrationOperation>
{
	private static final int STUPID_COUNT = 100; // защита от дурака
	private static final OperationState[] operationStates = {OperationState.NEW, OperationState.REFUSED, OperationState.EXECUTED, OperationState.CONFIRMED};

	private static final Restriction INSTANCE = new UserRegistrationRequestCountRestriction();

	public static Restriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		Long profileId = operation.getProfileId();
		int maxCount = getMaxRequestCount();

		List<Calendar> creationDates = Operation.getCreateOperationLastDates(profileId, operation.getClass(), maxCount + 1, operationStates);
		if(CollectionUtils.isEmpty(creationDates))
			return;

		int counter = 0;
		Calendar prevDate = Calendar.getInstance();

		// перебираем с конца
		for(Calendar creationDate : creationDates)
		{
			if(DateHelper.diff(prevDate, creationDate) >= getRequestsInterval() * 1000)
				break;

			counter++;
			prevDate = creationDate;
		}

		if(counter >= maxCount)
			throw new TooManyRequestException(profileId);
	}

	protected int getMaxRequestCount()
	{
		int configMaxCount = ConfigFactory.getConfig(Config.class).getMaxUserRegistrationRequestCount();
		return configMaxCount > STUPID_COUNT ? STUPID_COUNT : configMaxCount;
	}

	protected int getRequestsInterval()
	{
		return ConfigFactory.getConfig(Config.class).getUserRegistrationRequestCheckInterval();
	}
}