package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.AccountClosingClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.demand.*;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountClosingClaimSender extends AbstractDocumentSender
{
	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if( !(document instanceof AccountClosingClaim) )
			throw new GateException("Неверный тип платежа, должен быть - AccountClosingClaim.");

		AccountClosingClaim claim = (AccountClosingClaim) document;
		GateDocument gateDocument = claim.getTransferPayment();

		if (gateDocument == null)
			throw new GateException("Ошибкка содержания платежа заявки id="+claim.getId());

		DocumentRetailService documentService = new DocumentRetailService();
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		Client client = clientService.getClientById(claim.getClientInfo().getExternalOwnerId());
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(((AbstractTransfer)gateDocument).getChargeOffAmount().getCurrency().getCode());

		if (!(((AbstractTransfer)gateDocument).getChargeOffAmount().getDecimal().doubleValue() == 0))
		{   // вначале списываем остаток средств клиента и заполняем rt_paym_1
			drawDown(claim, gateDocument, currency,  client,  documentService);
		}

		// после списания закрываем счет
		closeAccount(claim, currency, client, documentService);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}

	private PaymentDemand createDemand()
	{
		return new PaymentDemand();
	}

	private ExpandedPaymentDemand createExpandedDemand()
	{
		return new ExpandedPaymentDemand();
	}

    private void drawDown(AccountClosingClaim claim, GateDocument gateDocument, Currency currency,
	                       Client client, DocumentRetailService documentService) throws GateException
	{
//TODO нехорошо использовать цепочку ифов:( надо бы юзать сендеры.		
		if (ClientAccountsTransfer.class.equals(gateDocument.getType()))
		{
			//todo пострараться по максимому использовать сендер для соответствующего платежа
			PaymentDemand demand = createDemand();
			demand.setAccount(claim.getClosedAccount());
			demand.setClientCode(Long.valueOf(client.getId()));
			demand.setDocumentDate(claim.getClientCreationDate().getTime());
			demand.setIsResident(client.isResident());
			demand.setGround("Списание средств");
			BigDecimal decimal = ((AbstractTransfer) gateDocument).getChargeOffAmount().getDecimal();
			demand.setSummaOut(decimal);
			demand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setIsCur(!currency.getCode().equals("RUB"));
			ClientAccountsTransfer accountsTransfer = (ClientAccountsTransfer)gateDocument;
			demand.setReceiverAccount(accountsTransfer.getReceiverAccount());
			AccountImpl account = getAccount(demand.getReceiverAccount(), demand.getClientCode());
			demand.setReceiverOffice( account.getBranch() );
			demand.setReceiverCorAccount(account.getDescription());

			demand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getTransferOtherAccountOperation());
			demand.setOperationKind(3L);
			setGeneralDocumentParams(demand);
			documentService.sendDocument(demand, claim.getId());
		}
		else if (AbstractRUSPayment.class.equals(gateDocument.getType()))
		{
			//todo пострараться по максимому использовать сендер для соответствующего платежа
			AccountRUSPayment rusPayment = (AccountRUSPayment)gateDocument;
			ExpandedPaymentDemand demand = createExpandedDemand();
			demand.setAccount(claim.getClosedAccount());
			demand.setClientCode(Long.valueOf(client.getId()));
			demand.setDocumentDate(claim.getClientCreationDate().getTime());
			demand.setIsResident(client.isResident());
			demand.setGround("Списание средств");
			BigDecimal decimal = ((AbstractTransfer) gateDocument).getChargeOffAmount().getDecimal();
			demand.setSummaOut(decimal);
			demand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setIsCur(!currency.getCode().equals("RUB"));
			demand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getTransferOtherBankOperation());
			demand.setReceiverAccount(rusPayment.getReceiverAccount());
			ResidentBank residentBank = rusPayment.getReceiverBank();
			demand.setReceiverCorAccount(residentBank.getAccount());
			demand.setReceiverBic(residentBank.getBIC());
			Remittee receiver = new Remittee();
			receiver.setBank(!StringHelper.isEmpty(residentBank.getName()) ? residentBank.getName() : "");
			receiver.setReceiverInn(rusPayment.getReceiverINN());
			receiver.setReceiverName(PaymentHelper.getReceiverName(rusPayment));
			if (rusPayment instanceof AbstractJurTransfer)
			{
				AbstractJurTransfer jurPayment = (AbstractJurTransfer) rusPayment;
				receiver.setReceiverKpp(jurPayment.getReceiverKPP());
			}
//			receiver.setBankCode(3L);
			demand.setReceiver(receiver);
			receiver.setGround(rusPayment.getGround());
			demand.setDestination(receiver);
			demand.setOperationKind(3L);
			setGeneralDocumentParams(demand);
			documentService.sendDocument(demand, claim.getId());
		}
		else if (SWIFTPayment.class.equals(gateDocument.getType()))
		{
			//todo пострараться по максимому использовать сендер для соответствующего платежа
			ExpandedPaymentDemand demand = createExpandedDemand();
			demand.setAccount(claim.getClosedAccount());
			demand.setClientCode(Long.valueOf(client.getId()));
			demand.setDocumentDate(claim.getClientCreationDate().getTime());
			demand.setIsResident(client.isResident());
			demand.setGround("Списание средств");
			BigDecimal decimal = ((AbstractTransfer) gateDocument).getChargeOffAmount().getDecimal();
			demand.setSummaOut(decimal);
			demand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
			demand.setIsCur(!currency.getCode().equals("RUB"));
			SWIFTPayment swiftPayment = (SWIFTPayment)gateDocument;
			demand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getTransferOtherBankOperation());
			demand.setReceiverAccount(swiftPayment.getReceiverAccount());
//			demand.setReceiverCorAccount(swiftPayment.getReceiverBankСorrespondentAccount());
			demand.setReceiverBic(swiftPayment.getReceiverSWIFT() != null ? swiftPayment.getReceiverSWIFT() : "");
			Remittee receiver = new Remittee();
			receiver.setBank(swiftPayment.getReceiverBankName() != null ? swiftPayment.getReceiverBankName() : "");
			receiver.setReceiverName(swiftPayment.getReceiverName());
			receiver.setPayerInn(client.getINN());
//			receiver.setBankCode(6L);
			demand.setReceiver(receiver);
			receiver.setGround(swiftPayment.getGround());
			demand.setDestination(receiver);
			demand.setOperationKind(3L);
			setGeneralDocumentParams(demand);
			documentService.sendDocument(demand, claim.getId());
		}else{
			throw new UnsupportedOperationException("Неподдерживаемый тип документа " + gateDocument.getType());
		}
	}

    private void closeAccount(AccountClosingClaim claim, Currency currency,
	                       Client client, DocumentRetailService documentService) throws GateException, GateLogicException
    {
		OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);

		PaymentDemand paymentDemand = createDemand();
		paymentDemand.setAccount(claim.getClosedAccount());
		paymentDemand.setClientCode(Long.valueOf(client.getId()));
		paymentDemand.setDocumentDate(claim.getClosingDate().getTime());
		paymentDemand.setGround("Закрытие счета");
		paymentDemand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setIsCur(!currency.getCode().equals("RUB"));
		paymentDemand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getCloseAccountOperation());
		paymentDemand.setOperationKind(0L);
		setGeneralDocumentParams(paymentDemand);
		PaymentDemandBase result = documentService.sendDocument(paymentDemand, claim.getId());
		claim.setOffice( officeService.getOfficeById(Long.toString( result.getDepartment())) );
		claim.setExternalId(result.getId().toString()); 
	}

	private void setGeneralDocumentParams(PaymentDemandBase paymentDemand) throws GateException
	{
		AccountImpl account = getAccount(paymentDemand.getAccount(), paymentDemand.getClientCode());
		paymentDemand.setDepartment( account.getBranch());
		paymentDemand.setAccountType(account.getDescription());
		paymentDemand.setReferenc(Long.valueOf(account.getId()));
	}
}