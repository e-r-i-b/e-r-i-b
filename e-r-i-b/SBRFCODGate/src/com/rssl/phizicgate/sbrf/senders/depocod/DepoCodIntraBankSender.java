package com.rssl.phizicgate.sbrf.senders.depocod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * Сендер перевода физическому лицу в другой банк внутри Сбербанка России
 *
 * @author gladishev
 * @ created 22.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodIntraBankSender extends DepoCodSenderBase
{
	public DepoCodIntraBankSender(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected String getOperationCode(AbstractAccountTransfer payment)
	{
		return null;
	}

	@Override
	protected ResidentBank getReceiverBank(AbstractTransfer payment) throws GateException
	{
		if (!(payment instanceof AbstractRUSPayment))
			throw new GateException("Некорректный тип платежа. Ожидался AbstractRUSPayment.");

		AbstractRUSPayment rusPayment = (AbstractRUSPayment) payment;
		return rusPayment.getReceiverBank();
	}

    protected void fillOurBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
    {
        fillPayerParams(gateMessage, payment.getChargeOffAccount(), getBusinessOwner(payment));

        gateMessage.addParameter(DST_PATH_KEY + ACCOUNT_KEY, getReceiverAccount(payment));
        if (isSameTB(payment))
        {
            addReceiverInfo(gateMessage, DST_CLIENT_PATH_KEY, payment);
        }
        else
        {
            gateMessage.addParameter(DST_PATH_KEY + CLIENT_KEY, toUpperCase(getReceiverName(payment)));
            gateMessage.addParameter(DST_PATH_KEY + REC_ADDRESS_KEY, null);
            gateMessage.addParameter(DST_PATH_KEY + REC_PHONE_KEY, null);
        }

        fillConditionParams(gateMessage, getAmount(payment), processGround(payment), getOperationCode(payment));
    }

    private void addReceiverInfo(GateMessage gateMessage, String path, AbstractAccountTransfer payment) throws GateException
    {
        gateMessage.addParameter(path.substring(0, path.length()-1), null);
        gateMessage.addParameter(path + "Name", toUpperCase(getReceiverFirstName(payment)));
        gateMessage.addParameter(path + "SecondName", toUpperCase(getReceiverSecondName(payment)));
        gateMessage.addParameter(path + "SurName", toUpperCase(getReceiverSurName(payment)));
        gateMessage.addParameter(path + "DateOfBirth", DEFAULT_DATE);
        gateMessage.addParameter(path + "Resident", true);
        gateMessage.addParameter(path + "Sex", 0);
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
}
