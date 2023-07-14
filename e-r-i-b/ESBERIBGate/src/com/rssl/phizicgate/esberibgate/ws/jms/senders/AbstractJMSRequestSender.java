package com.rssl.phizicgate.esberibgate.ws.jms.senders;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.config.ExternalSystemConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;

/**
 *
 * @author sergunin
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractJMSRequestSender<D, Rs> extends AbstractService
{
    public static final ESBSegment SEGMENT = ESBSegment.federal;
	private final ESBSegment segment = SEGMENT;

	protected AbstractJMSRequestSender(GateFactory factory)
    {
        super(factory);
    }

    protected abstract OnlineMessageProcessor<Rs> getRequestProcessor(D document, String rbTbBrch) throws GateException;

	protected JMSTransportProvider getTransportProviderInstance() throws GateException
	{
		return JMSTransportProvider.getInstance(segment);
	}

    private long getTimeout(String serviceName)
    {
        return ConfigFactory.getConfig(ExternalSystemConfig.class).getMQIntegrationTimeout(serviceName);
    }

    /**
     * Отправляет документ
     *
     * @param doc документ
     * @throws GateException
     * @throws GateLogicException
     */
    public final Rs send(Object doc, String rbTbBrch) throws GateException, GateLogicException
    {
        checkExternalSystem();
        //noinspection unchecked
        D document = (D) doc;
        OnlineMessageProcessor<Rs> processor = getRequestProcessor(document, rbTbBrch);
        Request request = processor.makeRequest();
        //noinspection unchecked
        Response<Rs> response = getTransportProviderInstance().processOnline(request, getTimeout(processor.getServiceName()));
	    processor.processResponse(response);
        return response.getResponse();
    }

    protected void checkExternalSystem() throws GateException
    {
        ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());
    }
}
