package com.rssl.phizicgate.mdm.integration.mdm.message;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор оффлайн jms запросов в шину
 */

public abstract class TicketMessageProcessorBase extends MessageProcessorBase<TicketMessageProcessor> implements TicketMessageProcessor
{
	@SuppressWarnings("NoopMethodInAbstractClass")
	protected final void addHeaders(TextMessage message) {}
}
