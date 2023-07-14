package com.rssl.phizgate.common.ws;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;
import org.apache.commons.codec.binary.Base64;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

/**
 * Добавляем информацию о промоутере в сообщение
 * @author Pankin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class WSCSARequestHandler extends GenericHandler
{
	private static final String PROMOTER_SHIFT_KEY = PromoterContext.class.getName().toUpperCase() + ".SHIFT_ID";
	private static final String PROMOTER_ID_KEY = PromoterContext.class.getName().toUpperCase() + ".PROMOTER_ID";
	private HandlerInfo handlerInfo;

	public void init(HandlerInfo info)
	{
		handlerInfo = info;
	}

	public boolean handleRequest(MessageContext messageContext)
	{
		SOAPMessageContext smc = (SOAPMessageContext) messageContext;
		SOAPMessage message = smc.getMessage();

		String localeId = MultiLocaleContext.getLocaleId();
		if (!StringHelper.isEmpty(localeId))
			message.getMimeHeaders().addHeader(MultiLocaleContext.LOCALE_KEY, new String(Base64.encodeBase64(localeId.getBytes())));

		String shift = PromoterContext.getShift();
		if (StringHelper.isEmpty(shift))
			return true;

		message.getMimeHeaders().addHeader(PROMOTER_SHIFT_KEY, shift);
		message.getMimeHeaders().addHeader(PROMOTER_ID_KEY, new String(Base64.encodeBase64(PromoterContext.getPromoterID().getBytes())));
		return true;
	}

	public QName[] getHeaders()
	{
		return handlerInfo.getHeaders();
	}
}
