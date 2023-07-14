package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.payments.systems.contact.ContactPayment;
import com.rssl.phizic.gate.payments.systems.contact.ContactPersonalPayment;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.demand.*;
import org.w3c.dom.Document;

import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class ContactPaymentSender extends AbstractDocumentSender
{
public static final String PARAMETR_PACK_NUMBER = "pack-number";
	private static final String PARAMETER_OPERATION_TYPE_NAME = "operation-type";
	private static final String PARAMETER_SUBOPERATION_TYPE_JUR = "suboperation-type-transfer-jur";
	private static final String PARAMETER_SUBOPERATION_TYPE_FIZ = "suboperation-type-transfer-fiz";

	/**
	 * списываем средства со счета платильщика
	 * @param contactPayment
	 * @throws GateException
	 */
	private void DrawDown(ContactPersonalPayment contactPayment, ContactDocument contactDocument) throws GateException, GateLogicException
	{
		OfficeGateService officeService = GateSingleton.getFactory().service(OfficeGateService.class);

		Client client = getOwner(contactPayment);

		PaymentDemand paymentDemand = new PaymentDemand();
		AccountImpl account = getAccount(contactPayment.getChargeOffAccount(), Long.valueOf(client.getId()));
		paymentDemand.setDepartment(account.getBranch());
		paymentDemand.setAccountType(account.getDescription());
		paymentDemand.setReferenc(Long.valueOf(account.getId()));
		paymentDemand.setAccount(contactPayment.getChargeOffAccount());
		paymentDemand.setClientCode(Long.valueOf(client.getId()));
		paymentDemand.setDocumentDate(contactPayment.getClientCreationDate().getTime());
		paymentDemand.setIsResident(client.isResident());
		paymentDemand.setGround("списание средств");

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(contactPayment.getChargeOffAmount().getCurrency().getCode());
		paymentDemand.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setCurrencyCode(Long.valueOf(currency.getExternalId()));
		paymentDemand.setIsCur(!currency.compare(currencyService.getNationalCurrency()));
		paymentDemand.setSummaOut(contactPayment.getChargeOffAmount().getDecimal());
		paymentDemand.setOperationType(getOperationType());
		paymentDemand.setOperationKind(getOperationKind());
		paymentDemand.setTransferLink(contactDocument.getAutoKey());
		if (contactPayment.getReceiverFirstName() != null)
			paymentDemand.setApplType(Long.valueOf(getTransferSuboperationFIZ()));
		else
			paymentDemand.setApplType(Long.valueOf(getTransferSuboperationJUR()));

	    paymentDemand.setFlag(2097152L);
		paymentDemand.setTransferLink(contactDocument.getAutoKey());
		paymentDemand.setId(contactDocument.getId());

		String documentNumber = contactPayment.getDocumentNumber();
		if (documentNumber.length() > 3)
		{
			paymentDemand.setNDoc(Long.valueOf(documentNumber.substring(documentNumber.length() - 3)));
		}
		else
		{
			paymentDemand.setNDoc(Long.valueOf(documentNumber));
		}
		paymentDemand.setNPack(getPackNumber());

		PaymentDemandBase result  = new DocumentRetailService().sendPaymentDocument(paymentDemand, contactPayment.getId());
		//TODO зачем это!?
		account = getAccount(result.getAccount(),Long.parseLong(client.getId()));
		if(account == null)
			throw new GateException("Ќе найдена информаци€ по счету:"+ result.getAccount());
		contactPayment.setOffice(officeService.getOfficeById(String.valueOf(account.getBranch())));
	}

	private PaymentDemandBase TransferContact(ContactPersonalPayment contactPayment) throws GateException, GateLogicException
	{
		ContactDocument payment = new ContactDocument();
		payment.setReceiverPoint(contactPayment.getReceiverPointCode());
		Client client = getOwner(contactPayment);

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(contactPayment.getChargeOffAmount().getCurrency().getCode());

		String accountNumber = contactPayment.getChargeOffAccount();

		AccountImpl account = getAccount(accountNumber, Long.parseLong(client.getId()));

		payment.setSumma(contactPayment.getChargeOffAmount().getDecimal());
		if (contactPayment.getCommission() != null)
			payment.setCommission(contactPayment.getCommission().getDecimal());
		payment.setDepartment(account.getBranch());
		payment.setReceiverCurrencyCode(Long.valueOf(currency.getExternalId()));
		payment.setCommissionCurrencyCode(Long.valueOf(currency.getExternalId()));
		payment.setIsCurr(!currency.compare(currencyService.getNationalCurrency()));
		payment.setPayerCode(Long.valueOf(client.getId()));
		payment.setPayerFirstName(client.getFirstName());
		payment.setPayerSecondName(client.getPatrName());
		payment.setPayerLastName(client.getSurName());
		payment.setPayerBornDate(DateHelper.toDate(client.getBirthDay()));
		payment.setPayerAddress(client.getRealAddress() != null ? client.getRealAddress().toString():"");
		payment.setPayerPhone(client.getMobilePhone());

		String documentNumber = null;
		String documentSeries = null;
		String documentIssueBy = null;
		Calendar documentIssueDate = null;

		if (client.getDocuments()!=null)
		{
			for(ClientDocument document : client.getDocuments())
			{
				documentNumber = document.getDocNumber();
				documentSeries = document.getDocSeries();
				documentIssueBy = document.getDocIssueBy();
				documentIssueDate = document.getDocIssueDate();
				break;
			}
		}
		payment.setPayerPaperNumber(documentNumber);
		payment.setPayerPaperSeries(documentSeries);
		payment.setPayerPaperIssuedDate(DateHelper.toDate(documentIssueDate));
		payment.setPayerPaperIssuer(documentIssueBy);

		payment.setReceiverFirstName(contactPayment.getReceiverFirstName());    //»м€
		payment.setReceiverSecondName(contactPayment.getReceiverPatrName()); // отчество
		payment.setReceiverLastName(contactPayment.getReceiverSurName()); // фамили€
		if (contactPayment.getReceiverBornDate() != null)
			payment.setReceiverBornDate(contactPayment.getReceiverBornDate().getTime());
		payment.setAdditionalIinformation(contactPayment.getGround());//TODO дл€ оплаты услуг как склеить пол€?!

		payment.setTransferenceDate(DateHelper.toDate(contactPayment.getClientCreationDate()));
		payment.setTransferenceDateEnd(DateHelper.add(DateHelper.toDate(contactPayment.getClientCreationDate()), new DateSpan(0, 0, 30)));
		payment.setState("O");//TODO ’ћ

		TransferBos transferBos = new TransferBos();
		transferBos.setDirection(1L);
		payment.setReceiverBos(transferBos);

		PaymentDemandBase p = new DocumentRetailService().sendContactDocument(payment, contactPayment.getId());
		contactPayment.setExternalId(p.getId().toString());

		return p;
	}

	public String getContactTransitAccount(Long reference) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getContactTransitAccount_q");
		message.addParameter("reference", reference.toString());

		try
		{
			Document document = service.sendOnlineMessage(message, null);

			return XmlHelper.selectSingleNode(document.getDocumentElement(), "transitAccount").getTextContent();
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	private void InsertContactTransitAccount(ContactPersonalPayment contactPayment, ContactDocument contactDocument) throws GateException
	{
		contactPayment.setTransitAccount(getContactTransitAccount(contactDocument.getReferenc()));
		//todo Ќужно обновить документ. —м. BUG017530
	/*	UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		try
		{
			updateDocumentService.update(contactPayment);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}   */
	}

	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof ContactPayment))
			throw new GateException("Ќеверный тип платежа, должен быть - ContactPayment.");

		ContactPersonalPayment contactPayment = (ContactPersonalPayment) document;

		// переводим списанные средства по  онтакту
		ContactDocument contactDocument = (ContactDocument) TransferContact(contactPayment);

		// списываем средства со счета платильщика
		DrawDown(contactPayment, contactDocument);

		if (contactPayment.getReceiverBornDate() != null)
			// добавим транзитный номер счета в контактовский перевод
			InsertContactTransitAccount(contactPayment, contactDocument);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		DocumentRetailService documentService = new DocumentRetailService();

		ContactDocument contactDocument = new ContactDocument();
		contactDocument.setId(ClaimId.valueOf(document.getWithdrawExternalId()));
		contactDocument.setTransferenceDate(document.getClientCreationDate().getTime());

		documentService.sendContactRecallDocument(contactDocument, document.getWithdrawInternalId());
	}

	/**
	 * @return подопераци€ при оплате товаров и услуг
	 */
	private String getTransferSuboperationJUR()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_JUR);
	}
	/**
	 * @return подопераци€ при переводе физ.лицу
	 */
	private String getTransferSuboperationFIZ()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_FIZ);
	}
	/**
	 * @return тип операции
	 */
	protected Long getOperationType()
	{
		return Long.decode((String) getParameter(PARAMETER_OPERATION_TYPE_NAME));
	}

	/**
	 * @return вид операции
	 */
	protected Long getOperationKind()
	{
		return 3L;
	}

    /**
	 * @return номер пачки
	 */
	protected Long getPackNumber()
	{
		return Long.decode((String) getParameter(PARAMETR_PACK_NUMBER));
	}
}