package com.rssl.phizicgate.wsgateclient.services.currency;

import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService;
import com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyServiceImpl_Impl;
import com.rssl.phizicgate.wsgateclient.services.types.CurrencyImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyServiceWrapper  extends AbstractService implements com.rssl.phizic.gate.currency.CurrencyService
{
	private StubUrlBackServiceWrapper<CurrencyService> wrapper;
	private Map<Class, Class> types = new HashMap<Class, Class>();

	public CurrencyServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<CurrencyService>("CurrencyServiceImpl"){
			protected void createStub()
			{
				CurrencyServiceImpl_Impl service = new CurrencyServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getCurrencyServicePort());
			}
		};

		setTypes();
	}
	public List<Currency> getAll() throws GateException
	{
		try
		{
			List src = wrapper.getStub().getAll();
			List dest = new ArrayList(src.size());
			BeanHelper.copyPropertiesWithDifferentTypes(dest, src, types);
			return dest;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalGateException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findById(String currencyId) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency src = wrapper.getStub().findById(currencyId);
			CurrencyImpl dest = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(dest, src, types);
			return dest;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalGateException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findByAlphabeticCode(String currencyCode) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency src = wrapper.getStub().findByAlphabeticCode(currencyCode);
			CurrencyImpl dest = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(dest, src, types);
			return dest;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalGateException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findByNumericCode(String currencyCode) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency src = wrapper.getStub().findByNumericCode(currencyCode);
			CurrencyImpl dest = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(dest, src, types);
			return dest;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalGateException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency getNationalCurrency() throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency src = wrapper.getStub().getNationalCurrency();
			CurrencyImpl dest = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(dest, src, types);
			return dest;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalGateException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void setTypes()
	{
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Currency.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Currency.class, com.rssl.phizicgate.wsgateclient.services.types.CurrencyImpl.class);
	}
}
