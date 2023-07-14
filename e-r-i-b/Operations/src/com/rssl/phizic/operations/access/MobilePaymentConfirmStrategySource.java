package com.rssl.phizic.operations.access;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.operations.access.conditions.NotInternalPaymentCondition;
import com.rssl.phizic.operations.access.conditions.PaymentByMobileTemplateCondition;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Фильтрация стратегий подтверждения платежных форм в мобильной версии.
 @author Pankin
 @ created 01.12.2010
 @ $Author$
 @ $Revision$
 */
public class MobilePaymentConfirmStrategySource implements ConfirmStrategySource
{
	private static final Map<ConfirmStrategyType, List<StrategyCondition>> conditions;

		static
		{
			List<StrategyCondition> compositConditions = new ArrayList<StrategyCondition>();
			compositConditions.add(new PaymentByMobileTemplateCondition());

			List<StrategyCondition> smsConditions = new ArrayList<StrategyCondition>();
			smsConditions.add(new NotInternalPaymentCondition());

			conditions = new LinkedHashMap<ConfirmStrategyType, List<StrategyCondition>>();
			conditions.put(ConfirmStrategyType.conditionComposite, compositConditions);
			conditions.put(ConfirmStrategyType.sms, smsConditions);
		}

		public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
		{
			// Подтверждение паролем с чека в мобильной версии не предусмотрено, убираем стратегию
			if(strategy instanceof CompositeConfirmStrategy)
				((CompositeConfirmStrategy) strategy).removeStrategy(ConfirmStrategyType.card);
			if(!strategy.filter(conditions, null, object))
				return new NotConfirmStrategy();
			return new SmsPasswordConfirmStrategy();
		}

}
