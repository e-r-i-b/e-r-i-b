package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Проверяет доступен ли какой либо тип автоплатежа (доступен во внешней системе + у поставщика)
 * @author: vagin
 * @ created: 30.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckAutoPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final Map<Class<? extends AutoPaySchemeBase>, List<ExecutionEventType>> executionEventTypesForAutoPayScheme;

	static
	{
		// Мапа строится на основе AutoPaymentTypesListsSource.getEntityListBuilder(Map)
		Map<Class<? extends AutoPaySchemeBase>, List<ExecutionEventType>> types = new HashMap<Class<? extends AutoPaySchemeBase>, List<ExecutionEventType>>();

		List<ExecutionEventType> alwaysTypes = new ArrayList<ExecutionEventType>();
		alwaysTypes.add(ExecutionEventType.ONCE_IN_YEAR);
		alwaysTypes.add(ExecutionEventType.ONCE_IN_HALFYEAR);
		alwaysTypes.add(ExecutionEventType.ONCE_IN_QUARTER);
		alwaysTypes.add(ExecutionEventType.ONCE_IN_MONTH);

		List<ExecutionEventType> invoiceTypes = new ArrayList<ExecutionEventType>();
		invoiceTypes.add(ExecutionEventType.BY_INVOICE);

		List<ExecutionEventType> thresholdTypes = new ArrayList<ExecutionEventType>();
		thresholdTypes.add(ExecutionEventType.REDUSE_OF_BALANCE);

		types.put(AlwaysAutoPayScheme.class, alwaysTypes);
		types.put(InvoiceAutoPayScheme.class, invoiceTypes);
		types.put(ThresholdAutoPayScheme.class, thresholdTypes);
		executionEventTypesForAutoPayScheme = Collections.unmodifiableMap(types);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		 if (!(document instanceof AutoPaymentBase))
		{
			throw new RestrictionViolationException("Документ не может быть регулярным");
		}
		AutoPaymentBase autoPayment = (AutoPaymentBase) document;

		BillingServiceProvider provider = (BillingServiceProvider) autoPayment.getServiceProvider();
		String cardNumber = autoPayment.getChargeOffCard();
		String requisite = autoPayment.getRequisite();
		String providerSynchKey = (String) provider.getSynchKey();

		List<ExecutionEventType> gateAllowedAutoPaymentTypes = null;
		try
		{
			AutoPaymentService autoPaymentService = GateSingleton.getFactory().service(AutoPaymentService.class);
			gateAllowedAutoPaymentTypes = autoPaymentService.getAllowedAutoPaymentTypes(cardNumber, requisite, providerSynchKey);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException("Ошибка при получении информации по доступным видам автоплатежа.", e);
		}

		if (CollectionUtils.isEmpty(gateAllowedAutoPaymentTypes))
		{
			throw new DocumentLogicException("Вы не можете создать автоплатеж данного вида. Пожалуйста, обратитесь в банк.");
		}

		boolean isAccess = (provider.getAlwaysAutoPayScheme() != null && CollectionUtils.containsAny(gateAllowedAutoPaymentTypes, executionEventTypesForAutoPayScheme.get(AlwaysAutoPayScheme.class)));
		isAccess = isAccess || (provider.getInvoiceAutoPayScheme() != null && CollectionUtils.containsAny(gateAllowedAutoPaymentTypes, executionEventTypesForAutoPayScheme.get(InvoiceAutoPayScheme.class)));
		isAccess = isAccess	|| (provider.getThresholdAutoPayScheme() != null && CollectionUtils.containsAny(gateAllowedAutoPaymentTypes, executionEventTypesForAutoPayScheme.get(ThresholdAutoPayScheme.class)));

		if (!isAccess)
			throw new DocumentLogicException("Вы не можете создать автоплатеж данного вида. Пожалуйста, обратитесь в банк.");

	}
}
