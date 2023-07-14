package com.rssl.phizicgate.rsV55.senders;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.deposit.*;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.claims.DepositClosingClaim;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.rsV55.demand.DocumentRetailService;
import com.rssl.phizicgate.rsV55.demand.PaymentDemand;
import com.rssl.phizicgate.rsV55.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV55.bankroll.AccountImpl;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositClosingClaimSender extends AbstractDocumentSender
{
	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if( !(document instanceof DepositClosingClaim) )
                throw new GateException("Неверный тип платежа, должен быть - DepositClosingClaim.");

		DepositClosingClaim claim = (DepositClosingClaim) document;
		DepositService depositService = GateSingleton.getFactory().service(DepositService.class);
		Deposit deposit = depositService.getDepositById(claim.getExternalDepositId().toString());
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		Client client = clientService.getClientById(claim.getClientInfo().getExternalOwnerId());

		DocumentRetailService documentService = new DocumentRetailService();
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(deposit.getAmount().getCurrency().getCode());

		// закрываем одним документом (65-й операцией в составе 81)
		send(claim, currency,  client,  documentService);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}

	private void send(DepositClosingClaim claim, Currency currency,
		                   Client client, DocumentRetailService documentService) throws GateException, GateLogicException
	{
		OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);

		//todo пострараться по максимому использовать сендер для соответствующего платежа
		DepositService depositService = GateSingleton.getFactory().service(DepositService.class);
		Deposit deposit = depositService.getDepositById(claim.getExternalDepositId().toString());
		PaymentDemand paymentDemand = createDemand();
		DepositInfo depositInfo = depositService.getDepositInfo(deposit);
		paymentDemand.setAccount(depositInfo.getAccount().getNumber());
		paymentDemand.setClientCode(Long.valueOf(client.getId()));
		paymentDemand.setDocumentDate(claim.getClosingDate().getTime());
		paymentDemand.setIsResident(client.isResident());
		paymentDemand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setIsCur(!currency.getCode().equals("RUB"));

		paymentDemand.setReceiverAccount(claim.getDestinationAccount());

		AccountImpl account = getAccount(paymentDemand.getReceiverAccount(), paymentDemand.getClientCode());
		paymentDemand.setReceiverOffice( account.getBranch() );
		paymentDemand.setReceiverCorAccount(account.getDescription());

		paymentDemand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getTransferOtherAccountOperation());
		paymentDemand.setOperationKind(0L);
		paymentDemand.setComplexOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getCloseAccountOperation());
		paymentDemand.setApplType(6L);

		setGeneralDocumentParams(paymentDemand);

		PaymentDemandBase result = documentService.sendDocument(paymentDemand, claim.getId());
		claim.setOffice( officeService.getOfficeById(Long.toString( result.getDepartment()) ));
		claim.setExternalId(result.getId().toString()); 
	}

	private PaymentDemand createDemand()
	{
		return new PaymentDemand();
	}

	private void setGeneralDocumentParams(PaymentDemandBase paymentDemand) throws GateException
	{
		AccountImpl account = getAccount(paymentDemand.getAccount(), paymentDemand.getClientCode());
		paymentDemand.setDepartment( account.getBranch());
		paymentDemand.setAccountType(account.getDescription());
		paymentDemand.setReferenc(Long.valueOf(account.getId()));
	}

}