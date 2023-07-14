package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author gladishev
 * @ created 19.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EInvoicingRequestProcessorBase extends RequestProcessorBase
{
	protected static final ShopOrderService service = new ShopOrderServiceImpl(null);

	protected String getOrderUUID(Element documentElement) throws GateException, GateLogicException
	{
		return  XmlHelper.getSimpleElementValue(documentElement, Constants.UUID_TAG);
	}
}
