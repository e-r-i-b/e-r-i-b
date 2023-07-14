package com.rssl.phizic.test.ermb.messaging.paymentSms;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.ermb.messaging.paymentSms.generated.SendPaymentSmsRq_Type;
import com.rssl.phizic.test.ermb.messaging.paymentSms.generated.SendPaymentSmsRs_Type;
import com.rssl.phizic.test.ermb.messaging.paymentSms.generated.SendPaymentSmsService_PortType;
import org.apache.commons.logging.Log;
import java.rmi.RemoteException;

/**
 * Заглушка служебного канала ЕРМБ
 * @author Rtischeva
 * @created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public class SendPaymentSmsServiceImpl implements SendPaymentSmsService_PortType
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public SendPaymentSmsRs_Type sendPaymentSms(SendPaymentSmsRq_Type sendPaymentSMSRq) throws RemoteException
	{
		SendPaymentSmsRs_Type response = new SendPaymentSmsRs_Type();
		response.setRqUID(sendPaymentSMSRq.getRqUID());
		response.setStatus(0L);

		log.info("Принято сообщение об успешном выполнении платежа. Код мобильного банка: " + sendPaymentSMSRq.getMbOperCode() + " Текст сообщения: " + sendPaymentSMSRq.getText());

		return response;
	}
}
