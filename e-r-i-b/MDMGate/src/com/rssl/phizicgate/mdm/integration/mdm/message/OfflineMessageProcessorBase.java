package com.rssl.phizicgate.mdm.integration.mdm.message;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор оффлайн jms запросов
 */

public abstract class OfflineMessageProcessorBase extends MessageProcessorBase<OfflineMessageProcessor> implements OfflineMessageProcessor
{
	@SuppressWarnings("NoopMethodInAbstractClass")
	protected final void addHeaders(TextMessage message) {}
}
