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
 * ���������� ����� ��������� �� �����
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
	 * �������� ������������ ���������� �������
	 * @param payment ������
	 * @return ������������ ����������
	 */
	protected String getReceiverName(AbstractTransfer payment) throws GateLogicException, GateException
	{
		return helper.getReceiverName(payment);
	}

	/**
	 * �������� ���� ����������(�������� ����������) �� �������
	 * @param payment ������
	 * @return ���� ����������
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
	 * �������� ���� ���������� �������
	 * @param payment ������
	 * @return ���� ����������. ����� ����������� ��� ��������� � ������ 1 �������������.
	 */
	protected abstract ResidentBank getReceiverBank(AbstractTransfer payment) throws GateException;

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AbstractTransfer))
		{
			throw new GateException("�������� ��� ���������. ��������� AbstractTransfer");
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
			throw new GateException("����������� ��� �������. ���������� �������� ��������� ����� ����������");
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
			throw new GateException("�������� ��� ���������. ��������� AbstractGatePayment");
		}
		AbstractAccountTransfer payment = (AbstractAccountTransfer) gateDocument;
		if (isOurBank(payment))
		{
			return "transferAccountToAccountDemand_q";
		}
		return "transferOtherBank_q";
	}

	/**
	 * �������� �� ������� ��������� � ������ 1 �������������
	 * @param payment �������
	 * @return ��/���
	 */
	protected boolean isOurBank(AbstractTransfer payment) throws GateLogicException, GateException
	{
		return helper.isOurBank(payment);
	}

    /**
     * ��������� ������������ �� ���� ��������� �������� � ���� ���������� ������ ��.
     * @param gateDocument ��������
     * �� ����� ������� ����������� ��������� �������� �� ���� ����������� ���������� �����
     * @return true - �����������
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
        //��������� ���������� ���� �� ������, ���� �� ������
        for (String tb : ourTBString.split(SPLITER))
        {
            if (tb.length() == 1)
                tb = StringHelper.appendLeadingZeros(tb, 2);
            group.append(tb).append(SPLITER);
        }

        //���� ��� �� �� ����� ������, �� ������ � ����� ��
        if (group.indexOf(payerTB) > -1 && group.indexOf(receiverTB) > -1)
            return true;

        return false;
    }

    /**
     * �������� ��� ���������� �������
     * @param payment ������
     * @return ������������ ����������
     */
    protected String getReceiverFirstName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverFirstName(payment);
    }

    /**
     * �������� ������� ���������� �������
     * @param payment ������
     * @return ������������ ����������
     */
    protected String getReceiverSecondName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverSecondName(payment);
    }

    /**
     * �������� �������� ���������� �������
     * @param payment ������
     * @return ������������ ����������
     */
    protected String getReceiverSurName(AbstractAccountTransfer payment) throws GateException
    {
        return helper.getReceiverSurName(payment);
    }
}
