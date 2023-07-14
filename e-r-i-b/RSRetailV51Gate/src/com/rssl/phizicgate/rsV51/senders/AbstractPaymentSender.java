package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.demand.DocumentRetailService;
import com.rssl.phizicgate.rsV51.demand.DocumentService;
import com.rssl.phizicgate.rsV51.demand.PaymentDemandBase;

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractPaymentSender extends AbstractDocumentSender
{
	protected static final DocumentService documentService = new DocumentService();
	public static final String PARAMETER_OPERATION_TYPE_NAME = "operation-type";
	public static final String PARAMETR_PACK_NUMBER = "pack-number";

	/**
	 * @return тип операции
	 */
	protected Long getOperationType()
	{
		return Long.decode((String) getParameter(PARAMETER_OPERATION_TYPE_NAME));
	}

	/**
	 * @return номер пачки
	 */
	protected Long getPackNumber()
	{
		return Long.decode((String) getParameter(PARAMETR_PACK_NUMBER));
	}

	/**
	 * @return вид операции
	 */
	protected Long getOperationKind()
	{
		return 3L;
	}

	/**
	 * заполнить заявку данными из документа
	 * заполняются следующие поля:
	 *      Account
	 *      ClientCode
	 *      DocumentDate
	 *      IsResident
	 *      CurrencyCode
	 *      IsCur
	 *      SummaOut
	 *      OperationType
	 *      OperationKind
	 *
	 * @param demand заявка
	 * @param document документ
	 */
	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateLogicException("Неверный тип документа " + demand.getClass().getName() + ". Ожидается AbstractTransfer");
		}
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) document;

		Currency currency = transfer.getChargeOffAmount().getCurrency();

		Client client = getOwner(document);

		demand.setAccount(transfer.getChargeOffAccount());
		demand.setClientCode(Long.valueOf(client.getId()));
		demand.setDocumentDate(DateHelper.toDate(transfer.getClientCreationDate()));
		demand.setIsResident(client.isResident());

		demand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		demand.setIsCur(!currency.getCode().equals(bankContext.getNationalCurrencyCode()));
		demand.setSummaOut(transfer.getChargeOffAmount().getDecimal());
		demand.setOperationType(getOperationType());
		demand.setOperationKind(getOperationKind());

		AccountImpl account = getAccount(demand.getAccount(), demand.getClientCode());
		demand.setDepartment(account.getBranch());
		demand.setAccountType(account.getDescription());
		demand.setReferenc(Long.valueOf(account.getId()));
		String documentNumber = document.getDocumentNumber();
		if (documentNumber.length() > 3)
		{
			demand.setNDoc(Long.valueOf(documentNumber.substring(documentNumber.length() - 3)));
		}
		else
		{
			demand.setNDoc(Long.valueOf(documentNumber));
		}
		demand.setNPack(getPackNumber());
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		PaymentDemandBase demand = documentService.findById(document.getWithdrawExternalId(), createDemand().getClass()); //Грязный хак
		if (demand == null)
		{
			//Документ не найден в списке отложенных
			throw new GateLogicException("Невозможно отозвать документ");
		}
		documentService.remove(demand);
	}

	/**
	 * создать заявку
	 * @return заявка
	 */
	protected abstract PaymentDemandBase createDemand();

	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		final OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);
		PaymentDemandBase demand = createDemand();

		fillDemand(demand, document);
		PaymentDemandBase payment = new DocumentRetailService().sendPaymentDocument(demand, document.getId());
		if (document instanceof SynchronizableDocument)
			((SynchronizableDocument) document).setExternalId(payment.getId().toString());
		document.setOffice(officeService.getOfficeById(Long.toString(demand.getDepartment())));
	}
}
