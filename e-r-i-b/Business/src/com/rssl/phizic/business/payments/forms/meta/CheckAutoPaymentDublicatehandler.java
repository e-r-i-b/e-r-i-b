package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl;
import com.rssl.phizic.business.longoffer.autopayment.links.AutoPaymentLinksService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: vagin
 * @ created: 15.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckAutoPaymentDublicatehandler extends BusinessDocumentHandlerBase
{
	/**
	 * —татусы автоплатежа, при которых разрешено создавать автоплатеж с такими же реквизитами. 
	 */
	private static final Set<AutoPaymentStatus> unactiveStatuses = new HashSet<AutoPaymentStatus>();
	static {
		unactiveStatuses.add(AutoPaymentStatus.BLOCKED);
		unactiveStatuses.add(AutoPaymentStatus.DELETED);
		unactiveStatuses.add(AutoPaymentStatus.NO_CREATE);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			CreateAutoPaymentImpl payment = (CreateAutoPaymentImpl)document;

			AutoPaymentLinksService autoPaymentLinkService = new AutoPaymentLinksService();
		    Login personLogin =PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			//запрашиваем все автоплатежи по карте списани€.
			List<AutoPaymentLink> autoPayments = autoPaymentLinkService.findByUserId(personLogin);

			for(AutoPaymentLink autoPayment: autoPayments)
			{
				//«апрос на регистрацию можно посылать дл€ автоплатежей в состо€нии: «аблокирован, ”дален, Ќе существует.
			    if (unactiveStatuses.contains(autoPayment.getValue().getReportStatus()))
			        continue;

			    String requsite = autoPayment.getRequisite();
			    Long receiverId = Long.valueOf(autoPayment.getReceiverCode());
			    String cardNum = autoPayment.getCardNumber();
			    String paymentReceiverCode = payment.getReceiverPointCode().split("\\|")[0].split("@")[1];
				//при совпедении номера,карты и провайдера автоплатеж не должен быть создан.
			    if(payment.getRequisite().equals(requsite) && receiverId.equals(Long.valueOf(paymentReceiverCode)) && payment.getCardNumber().equals(cardNum))
			    {
				    throw new DocumentLogicException(getErrorMessage(document));
			    }
		    }
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	protected String getErrorMessage(StateObject document)
	{
		return "јвтоплатеж в пользу данного поставщика уже существует. ѕри необходимости ¬ы можете отредактировать действующий автоплатеж.";
	}
}
