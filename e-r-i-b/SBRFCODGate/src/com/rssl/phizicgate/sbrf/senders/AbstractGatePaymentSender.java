package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.AccountIntraBankPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.sbrf.bankroll.RequestHelper;

import java.util.Map;
import java.util.Properties;

/**
 * @author krenev
 * @ created 14.07.2010
 * @ $Author$
 * @ $Revision$
 * јбстракный класс переводов со счета
 */
public abstract class AbstractGatePaymentSender extends AbstractDocumentSender
{
	private RequestHelper helper;
    private static final String SPLITER = ",";
	private static final String PROPERTY_KEY = "com.rssl.phizic.gate.OurTBcodes";

	protected AbstractGatePaymentSender(GateFactory factory)
	{
		super(factory);
		helper = new RequestHelper(factory);
	}

	/**
	 * получить наименование получател€ платежа
	 * @param payment платеж
	 * @return наименование получател€
	 */
	protected String getReceiverName(AbstractTransfer payment) throws GateLogicException, GateException
	{
		return helper.getReceiverName(payment);
	}

	/**
	 * получить счет получател€(возможно транзитный) из платежа
	 * @param payment платеж
	 * @return счет получател€
	 */
	protected String getReceiverAccount(AbstractTransfer payment) throws GateException, GateLogicException
	{
		return helper.getReceiverAccount(payment);
	}

	protected RequestHelper getRequestHelper()
	{
		return helper;
	}

	/**
	 * ѕолучить банк получтаел€ платежа
	 * @param payment платеж
	 * @return банк получател€. может отсутвовать при переводах в рамках 1 подразделени€.
	 */
	protected abstract ResidentBank getReceiverBank(AbstractTransfer payment) throws GateException;

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AbstractTransfer))
		{
			throw new GateException("Ќеверный тип документа. ќжидаетс€ AbstractTransfer");
		}

		AbstractAccountTransfer payment = (AbstractAccountTransfer) gateDocument;
		if (isOurBank(payment))
		{
			fillOurBankMessage(gateMessage, payment);
		}
		else
		{
			fillExternalBankMessage(gateMessage, payment);
		}
	}

	private void fillOurBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		gateMessage.addParameter("clientId", getClientId(payment.getExternalOwnerId()));
		gateMessage.addParameter("debitAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("sum", getAmount(payment).getDecimal().toString());
		gateMessage.addParameter("creditAccount", getReceiverAccount(payment));
		gateMessage.addParameter("recipient", StringHelper.replaceQuotes(getReceiverName(payment)));
		if (payment.getGround() != null)
		{
			gateMessage.addParameter("purpose", processGround(payment));
		}
	}

	private void fillExternalBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		gateMessage.addParameter("clientId", getClientId(payment.getExternalOwnerId()));
		gateMessage.addParameter("payerAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("recipientName", StringHelper.replaceQuotes(getReceiverName(payment)));
		gateMessage.addParameter("recipientAccount", getReceiverAccount(payment));
		ResidentBank receiverBank = getReceiverBank(payment);
		if (receiverBank == null)
		{
			throw new GateException("Ќепраильный тип платежа. неаозможно получить реквизиты банка получател€");
		}
		gateMessage.addParameter("recipientBankName", StringHelper.replaceQuotes(receiverBank.getName()));
		gateMessage.addParameter("recipientBankBIC", receiverBank.getBIC());
		String recipientBankAccount = receiverBank.getAccount();
		if (!StringHelper.isEmpty(recipientBankAccount))
		{
			gateMessage.addParameter("recipientBankAccount", recipientBankAccount);
		}
		gateMessage.addParameter("sum", getAmount(payment).getDecimal().toString());
		if (payment.getGround() != null)
		{
			gateMessage.addParameter("purpose", processGround(payment));
		}
	}

	protected Money getAmount(AbstractAccountTransfer payment) throws GateException
	{
		return helper.getAmount(payment);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof AbstractAccountTransfer))
		{
			throw new GateException("Ќеверный тип документа. ќжидаетс€ AbstractGatePayment");
		}
		AbstractAccountTransfer payment = (AbstractAccountTransfer) gateDocument;
		if (isOurBank(payment))
		{
			return "transferAccountToAccountDemand_q";
		}
		return "transferOtherBank_q";
	}

	/**
	 * явл€етс€ ли перевод переводом в рамках 1 подразделени€
	 * @param payment перевод
	 * @return да/нет
	 */
	protected boolean isOurBank(AbstractTransfer payment) throws GateLogicException, GateException
	{
		return helper.isOurBank(payment);
	}

    /**
     * ѕровер€ем принадлжедат ли офис источника списани€ и счет получател€ одному “Ѕ.
     * @param gateDocument документ
     * те перед вызовом об€зательно провер€ть €вл€етс€ ли банк получаетел€ отделением сбера
     * @return true - принадлежат
     */
    public static boolean isSameTB(GateDocument gateDocument) throws GateException
	{
        Office office = gateDocument.getOffice();
        String receiverAccount =  ((AccountIntraBankPayment) gateDocument).getReceiverAccount();
        String payerTB = office.getCode().getFields().get("region");
        if (payerTB.length() == 1)
            payerTB = StringHelper.appendLeadingZeros(payerTB, 2);

        String receiverTB = receiverAccount.substring(9, 11);
        if (receiverTB.equals(payerTB))
            return true;

		String ourTBString = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(PROPERTY_KEY);
        StringBuilder group = new StringBuilder();
        //добавл€ем лидирующий ноль на случай, если он упущен
        for (String tb : ourTBString.split(SPLITER))
        {
            if (tb.length() == 1)
                tb = StringHelper.appendLeadingZeros(tb, 2);
            group.append(tb).append(SPLITER);
        }

        //если оба тб из одной группы, то платеж в одном тб
        if (group.indexOf(payerTB) > -1 && group.indexOf(receiverTB) > -1)
            return true;

        return false;
    }

    /**
     * получить им€ получател€ платежа
     * @param payment платеж
     * @return наименование получател€
     */
    protected String getReceiverFirstName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverFirstName(payment);
    }

    /**
     * получить фамилию получател€ платежа
     * @param payment платеж
     * @return наименование получател€
     */
    protected String getReceiverSecondName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverSecondName(payment);
    }

    /**
     * получить отчество получател€ платежа
     * @param payment платеж
     * @return наименование получател€
     */
    protected String getReceiverSurName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverSurName(payment);
    }
}
