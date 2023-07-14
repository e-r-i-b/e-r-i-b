package com.rssl.phizicgate.esberibgate.documents.senders.CardReportDeliveryClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.CardReportDeliveryClaim;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.types.wrappers.ReportDeliveryLanguageWrapper;
import com.rssl.phizicgate.esberibgate.types.wrappers.ReportDeliveryTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.wrappers.ReportTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OfflineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * @author akrenev
 * @ created 14.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на изменение параметров подписки
 */

class CardReportDeliveryClaimProcessor extends OfflineMessageProcessorBase
{
	private static final String REQUEST_TYPE = UpdateCardReportSubscriptionRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;
	private final GateFactory factory;
	private final CardReportDeliveryClaim document;

	private UpdateCardReportSubscriptionRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 */
	CardReportDeliveryClaimProcessor(GateFactory factory, CardReportDeliveryClaim document) throws GateException
	{
		super(ESBSegment.federal);
		this.factory = factory;
		this.document = document;
		requestHelper = new BackRefInfoRequestHelper(factory);
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		this.request = buildRequestObject(document);
		return request;
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getCardAcctId().getSystemId();
	}

	public void processAfterSend(Request request)
	{
		document.setExternalId(request.getJmsMessageId());
	}

	private UpdateCardReportSubscriptionRq buildRequestObject(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		UpdateCardReportSubscriptionRq result = new UpdateCardReportSubscriptionRq();
		result.setRqUID(RequestHelper.generateUUID());
		result.setRqTm(RequestHelper.generateRqTm());
		result.setOperUID(RequestHelper.generateOUUID());
		result.setSPName(SPNameType.BP_ERIB);

		result.setBankInfo(RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(claim)));
		result.setCardAcctId(makeCardAcctId(claim));

		return result;
	}

	private CardAcctIdType makeCardAcctId(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		CardAcctIdType cardAcctIdType = new CardAcctIdType();
		String cardExternalId = claim.getCardExternalIdReportDelivery();
		EntityCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		cardAcctIdType.setSystemId(compositeId.getSystemId());
		cardAcctIdType.setCardNum(compositeId.getEntityId());
		cardAcctIdType.setCustInfo(makeCustInfo(claim));
		cardAcctIdType.setBankInfo(makeRbBrchIdBahkInfo(compositeId.getRbBrchId()));
		if (StringHelper.isNotEmpty(claim.getContractNumber()))
			cardAcctIdType.setContractNumber(claim.getContractNumber());
		return cardAcctIdType;
	}

	private BankInfoType makeRbBrchIdBahkInfo(String rbBrchId)
	{
		BankInfoType info = new BankInfoType();
		info.setRbBrchId(rbBrchId);
		return info;
	}

	private CustInfoType makeCustInfo(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(makePersonInfo(claim));
		return custInfo;
	}

	private PersonInfoType makePersonInfo(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		PersonInfoType personInfo = new PersonInfoType();
		personInfo.setIdentityCard(makeIdentityCard(claim));
		ContactInfoType contactInfo = new ContactInfoType();
		contactInfo.setEmailAddr(claim.getEmailReportDelivery());
		contactInfo.setReportDeliveryType(ReportDeliveryTypeWrapper.toGate(claim.getReportDeliveryType()));
		contactInfo.setReportLangType(ReportDeliveryLanguageWrapper.toGate(claim.getReportDeliveryLanguage()));
		contactInfo.setReportType(ReportTypeWrapper.toGate(claim.isUseReportDelivery()));
		personInfo.setContactInfo(contactInfo);
		return personInfo;
	}

	private ClientDocument getPassportWay(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		Client owner = factory.service(BackRefClientService.class).getClientById(claim.getInternalOwnerId());
		return ClientHelper.getClientWayDocument(owner);
	}

	private IdentityCardType makeIdentityCard(CardReportDeliveryClaim claim) throws GateException, GateLogicException
	{
		IdentityCardType identityCard = new IdentityCardType();
		ClientDocument passportWay = getPassportWay(claim);
		identityCard.setIdNum(passportWay.getDocSeries());
		return identityCard;
	}
}
