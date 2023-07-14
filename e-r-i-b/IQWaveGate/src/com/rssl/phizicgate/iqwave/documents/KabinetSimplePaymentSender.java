package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * Kabinet (интернет)  –
 * при передаче запроса на оплату идентификатор добивается лидирующими нулями слева до шести символов
 * (если цифровая часть меньше 6 знаков) и добавляется лидирующая буква «k» (латинская прописная),
 * в итоге идентификатор должен получится такого вида: kNNNNNN
 *
 * Код сервиса (маршрута)
 * Символьная кодировка	Цифровая кодировка
 *      DA	                145
 */
public class KabinetSimplePaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public KabinetSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		return 'k' + StringHelper.appendLeadingZeros(identifier, 6);
	}
}