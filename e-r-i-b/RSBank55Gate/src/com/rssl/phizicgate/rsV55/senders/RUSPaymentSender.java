package com.rssl.phizicgate.rsV55.senders;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizicgate.rsV55.demand.ExpandedPaymentDemand;
import com.rssl.phizicgate.rsV55.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV55.demand.Remittee;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class RUSPaymentSender extends AbstractPaymentSender
{
	private static final long BANK_CODE = 3L;
	private static final String PARAMETER_SUBOPERATION_TYPE_KORR = "suboperation-type-korr";
	private static final String PARAMETER_SUBOPERATION_TYPE_JUR = "suboperation-type-transfer-jur";
	private static final String PARAMETER_SUBOPERATION_TYPE_FIZ = "suboperation-type-transfer-fiz";
	private static final String PARAMETR_SUBOPERATION_TYPE_TAX = "suboperation-tax-type";
	private static final String PARAMETR_GROUND = "ground";
	private static final String PARAMETER_PRIORITY_NAME = "priority";

	/**
	 * создать заявку
	 * @return заявка
	 */
	protected PaymentDemandBase createDemand()
	{
		return new ExpandedPaymentDemand();
	}

	protected boolean getReceiverIsResidentProperty (AbstractRUSPayment rusPayment)
	{
		Integer accountMask = Integer.valueOf(rusPayment.getReceiverAccount().substring(0, 5));

		if ((accountMask == 40820) || (accountMask == 42601))
		   return false;
		else
		   return true;    //40807*, 40820*, 426*. 
	}

	protected Remittee createReceiver(AbstractRUSPayment rusPayment) throws GateLogicException, GateException
	{
		Client client = getOwner(rusPayment);

		Remittee receiver = new Remittee();
		receiver.setPayerInn(client.getINN());
		receiver.setReceiverInn(rusPayment.getReceiverINN());
		receiver.setReceiverName(PaymentHelper.getReceiverName(rusPayment));
		if (rusPayment instanceof AbstractJurTransfer)
		{
			AbstractJurTransfer jurPayment = (AbstractJurTransfer) rusPayment;
			receiver.setReceiverKpp(jurPayment.getReceiverKPP());
		}

        receiver.setBankCode(BANK_CODE); //TODO: Что такое 3? В конфиг?
		receiver.setBank(rusPayment.getReceiverBank().getName());
		receiver.setNotResident(!getReceiverIsResidentProperty(rusPayment));
		receiver.setGround(((AccountRUSPayment)rusPayment).getGround());
		receiver.setPriority(Long.valueOf(getPaymentPriority()));
/*  TODO: если добавляем текст к назначению платежа (при передачи в Ретейл), то и в печатной форме это должно печататься! (см. реализацию в 1.9 МЗБ)
		receiver.setGround("п/п по заявлению клиента от " + DateHelper.toString(rusPayment.getClientCreationDate().getTime()) + ". " + rusPayment.getGround());
*/
		return receiver;
	}

	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof AbstractRUSPayment))
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		AbstractRUSPayment rusPayment = (AbstractRUSPayment) document;

		ExpandedPaymentDemand expandedPaymentDemand = (ExpandedPaymentDemand) demand;

		super.fillDemand(demand, rusPayment);

		ResidentBank residentBank = rusPayment.getReceiverBank();

		if(document.getType().equals(RUSTaxPayment.class))
			expandedPaymentDemand.setApplType(Long.valueOf(getTransferSuboperationTax()));
		else if (ConfigFactory.getConfig(PaymentsConfig.class).getBanksList().contains(residentBank.getBIC()))
				expandedPaymentDemand.setApplType(Long.valueOf(getTransferSuboperationKORR()));
			else if (rusPayment instanceof AbstractPhizTransfer)
					expandedPaymentDemand.setApplType(Long.valueOf(getTransferSuboperationFIZ()));
				else
					expandedPaymentDemand.setApplType(Long.valueOf(getTransferSuboperationJUR()));

		expandedPaymentDemand.setReceiverAccount(rusPayment.getReceiverAccount());
		expandedPaymentDemand.setReceiverCorAccount(residentBank.getAccount());
		expandedPaymentDemand.setReceiverBic(residentBank.getBIC());


		Remittee receiver = createReceiver(rusPayment);
		expandedPaymentDemand.setReceiver(receiver);
		expandedPaymentDemand.setDestination(receiver);
		expandedPaymentDemand.setGround(getDocumentGround());
	}

	private String getTransferSuboperationKORR()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_KORR);
	}
	private String getTransferSuboperationJUR()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_JUR);
	}
	private String getTransferSuboperationFIZ()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_FIZ);
	}
	private String getTransferSuboperationTax()
	{
		return (String) getParameter(PARAMETR_SUBOPERATION_TYPE_TAX);
	}
	/**
	* Получить описание основания платежа из настроек
	* Domain: Text
	* @return основание
	*/
	private String getDocumentGround()
	{
		return (String) getParameter(PARAMETR_GROUND);
	}
	/**
	* Получить очерёдность платежа из настроек
	* Domain: Text
	* @return очерёдность
	*/
    private String getPaymentPriority()
	{
		return (String) getParameter(PARAMETER_PRIORITY_NAME);
	}
}
