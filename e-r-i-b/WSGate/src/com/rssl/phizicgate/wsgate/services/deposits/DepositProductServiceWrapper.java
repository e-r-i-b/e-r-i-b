package com.rssl.phizicgate.wsgate.services.deposits;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositProductService_Impl;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositProductService_PortType_Stub;
import com.sun.xml.rpc.client.ClientTransportException;
import org.w3c.dom.Document;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author Krenev
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductServiceWrapper extends JAXRPCClientSideServiceBase<DepositProductService_PortType_Stub> implements DepositProductService
{
	public DepositProductServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/DepositProductService";

		DepositProductService_Impl service = new DepositProductService_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((DepositProductService_PortType_Stub) service.getDepositProductService_PortTypePort(), url);
	}

	public Document getDepositsInfo(Office office) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.deposits.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.deposits.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, TypesCorrelation.types);
			String encoded = encodeData(generatedOffice.getSynchKey());
			generatedOffice.setSynchKey(encoded);
			String rezult = getStub().getDepositsInfo(generatedOffice);
			return convert2DOM(rezult);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.deposits.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.deposits.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, TypesCorrelation.types);
			String encoded = encodeData(generatedOffice.getSynchKey());
			generatedOffice.setSynchKey(encoded);
			String rezult = getStub().getDepositProduct(convert2String(params), generatedOffice);
			return convert2DOM(rezult);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private Document convert2DOM(String data) throws GateException
	{
		try
		{
			return XmlHelper.parse(data);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private String convert2String(Document document) throws GateException
	{
		try
		{
			return XmlHelper.convertDomToText(document);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
