package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.DepositOpeningClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.demand.DocumentRetailService;
import com.rssl.phizicgate.rsV51.demand.PaymentDemand;
import com.rssl.phizicgate.rsV51.demand.PaymentDemandBase;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositOpeningClaimSender extends AbstractDocumentSender
{
	// Уникальный идентификатор счета плательщика
	private static final Long REFERENC = 0L;
	// Виды операций
	private static final Long KIND_OPER_DEPOSIT_OPENING = 5L;
	private static final Long KIND_OPER_DRAW_DOWN = 3L;


	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof DepositOpeningClaim))
			throw new GateException("Неверный тип платежа, должен быть - DepositOpeningClaim.");

		DepositOpeningClaim claim = (DepositOpeningClaim) document;
		DocumentRetailService documentService = new DocumentRetailService();
		Client client = getOwner(document);
		Currency currency = claim.getChargeOffAmount().getCurrency();

		boolean isDeposit = claim.getPeriod() != null;//период есть - значит открываем вклад
		if(isDeposit)
		{
			depositOpening(claim, currency, client, documentService);
		}
		else
			accountOpening(claim, currency, client, documentService);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}

	private PaymentDemand createDemand()
	{
		return new PaymentDemand();
	}

	private void depositOpening(DepositOpeningClaim claim, Currency currency,
	                      Client client, DocumentRetailService documentService) throws GateException, GateLogicException
	{
		OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);
		PaymentDemand paymentDemand = createDemand();
		paymentDemand.setAccount(claim.getChargeOffAccount());
		paymentDemand.setReceiverCorAccount(claim.getDepositConditionsId());
		paymentDemand.setClientCode(Long.valueOf(client.getId()));
		paymentDemand.setDocumentDate(claim.getClientCreationDate().getTime());
		paymentDemand.setIsResident(client.isResident());
		paymentDemand.setGround("списание средств");
		paymentDemand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setIsCur(!currency.getCode().equals("RUB"));
		paymentDemand.setSummaOut(claim.getChargeOffAmount().getDecimal());
		paymentDemand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getTransferOtherAccountOperation());
		paymentDemand.setOperationKind(KIND_OPER_DRAW_DOWN);
		setGeneralDocumentParams(paymentDemand);

		PaymentDemandBase result = documentService.sendDocument(paymentDemand, claim.getId());
		claim.setOffice( officeService.getOfficeById(Long.toString( result.getDepartment()) ));
	}

	private void accountOpening(DepositOpeningClaim claim, Currency currency,
	                            Client client, DocumentRetailService documentService) throws GateException, GateLogicException
	{
		OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);

		PaymentDemand paymentDemand = createDemand();
		paymentDemand.setAccount("");
		paymentDemand.setReferenc(REFERENC);
		String accountType = claim.getDepositConditionsId();
		accountType += currency.getCode();

        Object parameter = getParameter(accountType);
		if (parameter == null)
		{
			throw new GateException("неизвестный тип открываемого счета: "+claim.getDepositConditionsId());
		}

		paymentDemand.setAccountType(String.valueOf(parameter));
		paymentDemand.setDepartment( Long.parseLong( claim.getOfficeExternalId()));

		paymentDemand.setClientCode(Long.valueOf(client.getId()));
		paymentDemand.setDocumentDate(claim.getClientCreationDate().getTime());
		paymentDemand.setIsResident(client.isResident());
		paymentDemand.setGround("Открытие счета");
		paymentDemand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setIsCur(!currency.getCode().equals("RUB"));
		paymentDemand.setOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getOpenAccountOperation());
		paymentDemand.setComplexOperationType(ConfigFactory.getConfig(PaymentsConfig.class).getOpenAccountOperation());
		paymentDemand.setOperationKind(KIND_OPER_DEPOSIT_OPENING);

		PaymentDemandBase result = documentService.sendDocument(paymentDemand, claim.getId());
		claim.setOffice( officeService.getOfficeById(Long.toString( result.getDepartment()) ));
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