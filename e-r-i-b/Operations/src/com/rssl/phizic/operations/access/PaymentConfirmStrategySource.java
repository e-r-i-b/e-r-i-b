package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.auth.modes.NotConfirmStrategy;
import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.access.conditions.*;
import com.rssl.phizic.operations.ext.sbrf.strategy.GuestLoanClaimConfirmStrategy;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Фильтрация стратегий подтверждения платежных форм.
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentConfirmStrategySource implements ConfirmStrategySource
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Map<ConfirmStrategyType, List<StrategyCondition>> conditions;

	static
	{
		List<StrategyCondition> cardConditions = new ArrayList<StrategyCondition>();
		cardConditions.add(new PaymentDestinationStrategyCondition());
		cardConditions.add(new PFRStatementClaimStrategyCondition());
		cardConditions.add(new NoStrategyCondition());
		cardConditions.add(new AccountClosingPaymentStrategyCondition());
		cardConditions.add(new PaymentChargeOffStrategyCondition());
		cardConditions.add(new AccountOpeningNotConfirmStrategyCondition());
		cardConditions.add(new PaymentByTemplateWithSumFactorCondition(false));
		cardConditions.add(new LoanClaimStategyCondition());
		cardConditions.add(new ConnectionUDBOClaimStrategyCondition());
		cardConditions.add(new PostConfirmStrategyCondition());
		cardConditions.add(new ChangeCreditLimitStrategyCondition());

		List<StrategyCondition> smsConditions = new ArrayList<StrategyCondition>();
		smsConditions.add(new DenyMobileLimitPaymentsStrategyCondition());
		smsConditions.add(new PaymentDestinationStrategyCondition());
		smsConditions.add(new PFRStatementClaimStrategyCondition());
		smsConditions.add(new NoStrategyCondition());
		smsConditions.add(new AccountClosingPaymentStrategyCondition());
		smsConditions.add(new AccountOpeningStrategyCondition());
		smsConditions.add(new PaymentByTemplateWithSumFactorCondition(true));
		smsConditions.add(new PostConfirmStrategyCondition());

		List<StrategyCondition> pushConditions = new ArrayList<StrategyCondition>(smsConditions);

		List<StrategyCondition> capConditions = new ArrayList<StrategyCondition>();
		capConditions.add(new PaymentDestinationStrategyCondition());
		capConditions.add(new PFRStatementClaimStrategyCondition());
		capConditions.add(new NoStrategyCondition());
		capConditions.add(new AccountClosingPaymentStrategyCondition());
		capConditions.add(new AccountOpeningStrategyCondition());
		capConditions.add(new PaymentByTemplateWithSumFactorCondition(false));
		capConditions.add(new PostConfirmStrategyCondition());
		capConditions.add(new ChangeCreditLimitStrategyCondition());

		List<StrategyCondition> conditionCompositeConditions = new ArrayList<StrategyCondition>();
		conditionCompositeConditions.add(new DenyMobileLimitPaymentsStrategyCondition());
		conditionCompositeConditions.add(new PaymentLongOfferStrategyCondition());
		conditionCompositeConditions.add(new PaymentByTemplateCondition());
		conditionCompositeConditions.add(new PFRStatementClaimStrategyCondition());
		conditionCompositeConditions.add(new NoStrategyCondition());
		conditionCompositeConditions.add(new AccountClosingPaymentStrategyCondition());
		conditionCompositeConditions.add(new PostConfirmStrategyCondition());

		List<StrategyCondition> denyConditions = new ArrayList<StrategyCondition>();
		denyConditions.add(new PostConfirmStrategyCondition());

		List<StrategyCondition> compositeConditions = new ArrayList<StrategyCondition>();
		compositeConditions.add(new PaymentByTemplateCondition());
		compositeConditions.add(new DenyMobileLimitPaymentsStrategyCondition());

		conditions = new LinkedHashMap<ConfirmStrategyType, List<StrategyCondition>>();
				
		conditions.put(ConfirmStrategyType.composite, compositeConditions); //композитная стратегия (старая). Используется только в mAPI >= 5.00, где подтверждение одноразовыми паролями отключено (см. mobile5-authentication-modes.xml)
		conditions.put(ConfirmStrategyType.conditionComposite, conditionCompositeConditions); //композитная стратегия iPas
		conditions.put(ConfirmStrategyType.card, cardConditions);
		conditions.put(ConfirmStrategyType.sms, smsConditions);
		conditions.put(ConfirmStrategyType.cap, capConditions);
		conditions.put(ConfirmStrategyType.push, pushConditions);
		conditions.put(ConfirmStrategyType.DENY, denyConditions);
	}

	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		if (object instanceof ExtendedLoanClaim)
		{
			ExtendedLoanClaim claim = (ExtendedLoanClaim) object;
			try
			{
				Person person = claim.getOwner().getPerson();
				if (person instanceof GuestPerson && ((GuestPerson) person).isHaveMBKConnection())
					return new GuestLoanClaimConfirmStrategy();
			}
			catch (BusinessException e)
			{
				log.error("Ошибка получения owner у документа с id = " + claim.getId(), e);
			}
		}
		return strategy.filter(conditions, userChoice, object) ? strategy : new NotConfirmStrategy();
	}
}
