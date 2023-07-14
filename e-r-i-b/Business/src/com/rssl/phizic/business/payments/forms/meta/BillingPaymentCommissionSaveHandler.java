package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author bogdanov
 * @ created 12.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ’ендлер дл€ сохранени€ комиссии и вывода сообщени€ при ее отсутствии.
 */

public class BillingPaymentCommissionSaveHandler extends DefaultCommissionSaveHandler
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	private static final String DEFAULT_MAY_COMMISSION_MESSAGE = "«а данную операцию может взиматьс€ комисси€ в соответствии с тарифами банка. " +
			"—умму комиссии ¬ы можете посмотреть <a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' class='paperEnterLink' target='_blank'>здесь</a>.";
	private static final String DEFAULT_MAY_COMMISSION_MESSAGE_MOBILE = "«а данную операцию может взиматьс€ комисси€ в соответствии с тарифами банка. " +
			"—умму комиссии ¬ы можете посмотреть на сайте банка.";
	private static final String ZERO_COMMISSION_MASSEGE = "«а выполнение данной операции комисси€ не взимаетс€.";

	@Override
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment) && !(document instanceof RurPayment && ((RurPayment)document).isServiceProviderPayment()))
			throw new DocumentException("document должен быть JurPayment или RurPayment");

		super.process(document, stateMachineEvent);

		try
		{
			RurPayment payment = (RurPayment) document;

			// ≈сли создаетс€ автоплатеж, то информаци€ о комисси выводитс€ в поле автоплатежа
			if (payment.isLongOffer())
				return;

			ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
			//если есть сообщение о комиссии в поставщике, то необходимо всегда его отображать.
			if (provider != null && !StringHelper.isEmpty(provider.getCommissionMessage()))
			{
				stateMachineEvent.addMessage(provider.getCommissionMessage());
				//причем остальные сообщени€ (о том, что комиссии€ не пришла и комисси€ нулева€) выводить не будем.
				return;
			}

			//если комисси€ не пришла, то выводим стандартное сообщение о возможности взимани€ комиссии.
			if (payment.getCommission() == null)
			{
				stateMachineEvent.addMessage(ApplicationUtil.isApi() ? DEFAULT_MAY_COMMISSION_MESSAGE_MOBILE : DEFAULT_MAY_COMMISSION_MESSAGE);
				return;
			}

			//если комисси€ нулева€, то сообщаем об этом пользователю.
			if (payment.getCommission().isZero())
				stateMachineEvent.addMessage(ZERO_COMMISSION_MASSEGE);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
