package com.rssl.phizicgate.sbrf.senders.depocod;

import com.rssl.phizgate.common.payments.PaymentCompositeId;
import com.rssl.phizgate.common.payments.documents.StateUpdateInfoImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.sbrf.senders.AbstractGatePaymentSender;
import com.rssl.phizicgate.sbrf.ws.Constants;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 25.04.2014
 * @ $Author$
 * @ $Revision$
 */

public abstract class DepoCodSenderBase extends AbstractGatePaymentSender
{
	public static final String ACC2ACC143_REQUEST = "accToAcc143Request";
	public static final String ACC2ACC_REQUEST = "accToAccRequest";
	public static final String ACC2ACC365_REQUEST = "accToAcc365Request";
	public static final String CHECK_STATUS_REQUEST = "operationFullResultRequest";
	public static final String DEPO_COD_KEY = "DepoCOD";
	public static final String EXECUTED_STATE_KEY = "EXECUTED";
	public static final String ERROR_STATE_KEY = "ERROR";
	public static final String UNKNOW_STATE_KEY = "UNKNOW";

	protected static final String SRC_PATH_KEY = "SrcAccount/";
	protected static final String DST_PATH_KEY = "DstAccount/";
	protected static final String SRC_CLIENT_PATH_KEY = SRC_PATH_KEY + "Client/";
	protected static final String DST_CLIENT_PATH_KEY = DST_PATH_KEY + "Client/";
	protected static final String CNDTN_PATH_KEY = "Condition/";
	protected static final String DOC_TAG_KEY = "Documents";
	protected static final String DOC_PATH_KEY = DOC_TAG_KEY + "/";
	protected static final String SRC_CRSS_TAG_KEY = "SrcCourses";
	protected static final String SRC_CRSS_PATH_KEY = "SrcCourses/";
	protected static final String ACCOUNT_KEY = "Account";
	protected static final String CLIENT_KEY = "Client";
	protected static final String REC_ADDRESS_KEY = "RecipientAddress";
	protected static final String REC_PHONE_KEY = "RecipientPhone";
	private static final String ZERO_DOUBLE = "0.0";
    protected static final String DEFAULT_DATE = "1800-01-01";
	private static final String RUB_CURRENCY = "643";
	private static final String RUR_CURRENCY = "810";


	protected DepoCodSenderBase(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void send(GateDocument gateDocument) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage gateMessage = service.createRequest(getRequestName(gateDocument));
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) gateDocument;
		if (isOurBank(transfer))
		{
			fillOurBankMessage(gateMessage, transfer);
		}
		else
		{
			fillExternalBankMessage(gateMessage, transfer);
		}

		MessageHead messageHead = new MessageHeadImpl(((SynchronizableDocument) gateDocument).getExternalId(), null, null, null, null, null);
		try
		{
			service.sendOnlineMessage(gateMessage, messageHead);
			gateDocument.setNextState(EXECUTED_STATE_KEY);
		}
		catch (GateTimeOutException ignore)
		{
			gateDocument.setNextState(UNKNOW_STATE_KEY);
		}
	}

	@Override public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//для документов требующих обратной связи устанавливаем идентификатор.
		if (document instanceof SynchronizableDocument)
		{
			StringBuilder result = new StringBuilder();
			result.append(new RandomGUID().getStringValue().toUpperCase())
					.append(Constants.EXTERNAL_ID_DELIMITER)
					.append(XMLDatatypeHelper.formatDateWithoutTimeZone(Calendar.getInstance()))
					.append(Constants.EXTERNAL_ID_DELIMITER)
					.append(DEPO_COD_KEY);

			SynchronizableDocument syncDoc = (SynchronizableDocument) document;
			syncDoc.setExternalId(result.toString());
		}
	}

	protected void fillExternalBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		fillPayerParams(gateMessage, payment.getChargeOffAccount(), getBusinessOwner(payment));

		ResidentBank receiverBank = getReceiverBank(payment);
		if (receiverBank == null)
			throw new GateException("Непраильный тип платежа. неаозможно получить реквизиты банка получателя");

		gateMessage.addParameter(DST_PATH_KEY + ACCOUNT_KEY, getReceiverAccount(payment));
		gateMessage.addParameter(DST_PATH_KEY + CLIENT_KEY, toUpperCase(getReceiverName(payment)));
		gateMessage.addParameter(DST_PATH_KEY + "RecBIC", receiverBank.getBIC());
		String recipientBankAccount = receiverBank.getAccount();
		if (StringHelper.isNotEmpty(recipientBankAccount))
			gateMessage.addParameter(DST_PATH_KEY + "RecCorrAccount", recipientBankAccount);

		gateMessage.addParameter(DST_PATH_KEY + REC_ADDRESS_KEY, null);

		fillConditionParams(gateMessage, getAmount(payment), processGround(payment), getOperationCode(payment));
	}

	protected void fillOurBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		fillPayerParams(gateMessage, payment.getChargeOffAccount(), getBusinessOwner(payment));

		gateMessage.addParameter(DST_PATH_KEY + ACCOUNT_KEY, getReceiverAccount(payment));
		gateMessage.addParameter(DST_PATH_KEY + CLIENT_KEY, toUpperCase(getReceiverName(payment)));
		gateMessage.addParameter(DST_PATH_KEY + REC_ADDRESS_KEY, null);
		gateMessage.addParameter(DST_PATH_KEY + REC_PHONE_KEY, null);

		fillConditionParams(gateMessage, getAmount(payment), processGround(payment), getOperationCode(payment));
	}

	@Override
	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return isOurBank((AbstractTransfer) gateDocument)
				? isSameTB(gateDocument)
					? ACC2ACC_REQUEST
					: ACC2ACC143_REQUEST
				: ACC2ACC365_REQUEST;
	}

	@Override
	protected ResidentBank getReceiverBank(AbstractTransfer payment) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	protected String getOperationCode(AbstractAccountTransfer payment)
	{
		return getRequestHelper().getOperationCode(payment);
	}

	@Override
	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof SynchronizableDocument))
			throw new GateException("Ожидается SynchronizableDocument");

		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest(CHECK_STATUS_REQUEST);
		try
		{
			service.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, ((SynchronizableDocument)document).getExternalId(), null, null));
			return new StateUpdateInfoImpl("EXECUTE", null);
		}
		catch (GateTimeOutException te)
		{
			throw te;
		}
		catch (GateLogicException e)
		{
			return new StateUpdateInfoImpl(ERROR_STATE_KEY, e.getMessage());
		}
	}

	protected void fillPayerParams(GateMessage gateMessage, String payerAccount, Client payer) throws GateException
	{
		gateMessage.addParameter("Osb", "0");

		gateMessage.addParameter(SRC_PATH_KEY + ACCOUNT_KEY, payerAccount);

		addClientInfo(gateMessage, SRC_CLIENT_PATH_KEY, payer);
	}

	protected void fillConditionParams(GateMessage gateMessage, Money amount, String paymentTarget, String operationCode) throws GateException
	{
		gateMessage.addParameter(CNDTN_PATH_KEY + "Summa", amount.getDecimal().toString());
		String currency = amount.getCurrency().getNumber();
		gateMessage.addParameter(CNDTN_PATH_KEY + "CurCode", RUB_CURRENCY.equals(currency) ? RUR_CURRENCY : currency);
		gateMessage.addParameter(CNDTN_PATH_KEY + SRC_CRSS_TAG_KEY, null);
		gateMessage.addParameter(CNDTN_PATH_KEY + SRC_CRSS_PATH_KEY + "CourseSelling", ZERO_DOUBLE);
		gateMessage.addParameter(CNDTN_PATH_KEY + SRC_CRSS_PATH_KEY + "CourseBuying", ZERO_DOUBLE);
		gateMessage.addParameter(CNDTN_PATH_KEY + SRC_CRSS_PATH_KEY + "CourseCB", ZERO_DOUBLE);

		if (StringHelper.isNotEmpty(paymentTarget))
			gateMessage.addParameter(CNDTN_PATH_KEY + "PaymentTarget", paymentTarget.toUpperCase());

		if (StringHelper.isNotEmpty(operationCode))
			gateMessage.addParameter(CNDTN_PATH_KEY + "NotResidentOperCode", operationCode.toUpperCase());

		gateMessage.addParameter(CNDTN_PATH_KEY + "Fight", "true");
	}

	private void addClientInfo(GateMessage gateMessage, String path, Client client)
	{
		gateMessage.addParameter(path.substring(0, path.length()-1), null);
		gateMessage.addParameter(path + "Name", toUpperCase(client.getFirstName()));
		gateMessage.addParameter(path + "SecondName", toUpperCase(client.getPatrName()));
		gateMessage.addParameter(path + "SurName", toUpperCase(client.getSurName()));
		gateMessage.addParameter(path + "DateOfBirth", client.getBirthDay() != null ? XMLDatatypeHelper.formatDateWithoutTimeZone(client.getBirthDay()) : DEFAULT_DATE);
		gateMessage.addParameter(path + "Resident", client.isResident());
		gateMessage.addParameter(path + "Sex", StringHelper.isEmpty(client.getSex()) ? 0 : ("M".equals(client.getSex()) ? 1 : 2));
		gateMessage.addParameter(path + "Address", null);
		gateMessage.addParameter(path + "AddressOfRegistration", null);
		gateMessage.addParameter(path + "Phone", null);
		gateMessage.addParameter(path + "WorkPhone", null);
		gateMessage.addParameter(path + "Fax", null);
		gateMessage.addParameter(path + "Citizenship", null);
		gateMessage.addParameter(path + "BirthPlace", null);

		gateMessage.addParameter(path + DOC_TAG_KEY , null);
		gateMessage.addParameter(path + DOC_PATH_KEY + "Type", "0");
		gateMessage.addParameter(path + DOC_PATH_KEY + "Number", null);
		gateMessage.addParameter(path + DOC_PATH_KEY + "Series", null);
		gateMessage.addParameter(path + DOC_PATH_KEY + "When", DEFAULT_DATE);
		gateMessage.addParameter(path + DOC_PATH_KEY + "Who", null);
	}

	protected String toUpperCase(String str)
	{
		if (StringHelper.isEmpty(str))
			return str;
		return str.toUpperCase();
	}
}
