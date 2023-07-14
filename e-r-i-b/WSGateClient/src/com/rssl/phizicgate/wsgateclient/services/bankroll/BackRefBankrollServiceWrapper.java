package com.rssl.phizicgate.wsgateclient.services.bankroll;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSGateLogicClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.bankroll.generated.BackRefBankrollService_PortType;
import com.rssl.phizicgate.wsgateclient.services.bankroll.generated.BackRefBankrollService_PortType_Impl_Impl;
import com.rssl.phizicgate.wsgateclient.services.types.AccountImpl;
import com.rssl.phizicgate.wsgateclient.services.types.CardImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankrollServiceWrapper extends AbstractService implements BackRefBankrollService
{
	private StubUrlBackServiceWrapper<BackRefBankrollService_PortType> wrapper;

	public BackRefBankrollServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<BackRefBankrollService_PortType>("BackRefBankrollService_PortType_Impl"){
			protected void createStub()
			{
				BackRefBankrollService_PortType_Impl_Impl service = new BackRefBankrollService_PortType_Impl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getBackRefBankrollService_PortTypePort());
			}
		};
	}

	public String findCardExternalId(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			return wrapper.getStub().findCardExternalId2(cardNumber);
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(e);
			}
			if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(e);
			}
			throw new GateException(e);
		}
	}

	public String findCardExternalId(Long loginId, String cardNumder) throws GateException, GateLogicException, AccountNotFoundException
	{
		try
	    {
	        return wrapper.getStub().findCardExternalId(loginId, cardNumder);
	    }
	    catch (RemoteException e)
	    {
		    if (e.getCause() instanceof CardNotFoundException)
			    throw new CardNotFoundException(e.getMessage());
		    else
		    {
			    if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX)){
					throw new WSGateLogicClientException(e);
				}
				if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX)){
					throw new WSGateClientException(e);
				}
				throw new GateException(e);
		    }
	    }
	}

	public String findAccountExternalId(String accountNumber) throws GateException, GateLogicException, AccountNotFoundException
    {
	    try
	    {
	        return wrapper.getStub().findAccountExternalId(accountNumber);
	    }
	    catch (RemoteException e)
	    {
		    if (e.getCause() instanceof AccountNotFoundException)
			    throw new AccountNotFoundException(e.getMessage());
		    else
		    {
			    if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX)){
					throw new WSGateLogicClientException(e);
				}
				if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX)){
					throw new WSGateClientException(e);
				}
				throw new GateException(e);
		    }
	    }
    }

	public String findAccountBusinessOwner(Account account) throws GateException, GateLogicException
	{
	    try
	    {
		    com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account dest =
				    new com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account();
		    BeanHelper.copyPropertiesWithDifferentTypes(dest, account, TypesCorrelation.getTypes());
	        return wrapper.getStub().findAccountBusinessOwner(dest);
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
		    if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
		    {
			    throw new WSTemporalGateClientException(ex);
		    }
		    if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateLogicClientException(ex);
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

	public Office getAccountOffice(Long loginId, String accountNumber) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizgate.common.services.types.OfficeImpl office = new com.rssl.phizgate.common.services.types.OfficeImpl();
			com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office office_1 = wrapper.getStub().getAccountOffice(loginId, accountNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_1, TypesCorrelation.getTypes());
			return office;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateLogicClientException(e);
		    }
		    if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateClientException(e);
		    }
		    throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public Office getCardOffice(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizgate.common.services.types.OfficeImpl office = new com.rssl.phizgate.common.services.types.OfficeImpl();
			com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office office_1 = wrapper.getStub().getCardOffice(loginId, cardNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_1, TypesCorrelation.getTypes());
			return office;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateLogicClientException(e);
		    }
		    if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateClientException(e);
		    }
		    throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public Account getCardAccount(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			AccountImpl gateAccount = new AccountImpl();
			com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account generatedAccount = wrapper.getStub().getCardAccount2(cardNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, generatedAccount, TypesCorrelation.getTypes());
			return gateAccount;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(e);
			}
			if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(e);
			}
			throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public Account getCardAccount(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account account =  wrapper.getStub().getCardAccount(loginId, cardNumber);
			AccountImpl account1 = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(account1, account, TypesCorrelation.getTypes());
			return account1;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateLogicClientException(e);
		    }
		    if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateClientException(e);
		    }
		    throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public Card getStoredCard(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Card card =  wrapper.getStub().getStoredCard(loginId, cardNumber);
			CardImpl card1 = new CardImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(card1, card, TypesCorrelation.getTypes());
			return card1;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateLogicClientException(e);
		    }
		    if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
		    {
			    throw new WSGateClientException(e);
		    }
		    throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}
}
