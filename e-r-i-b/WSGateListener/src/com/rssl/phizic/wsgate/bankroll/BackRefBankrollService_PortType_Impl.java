package com.rssl.phizic.wsgate.bankroll;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.bankroll.generated.Account;
import com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType;
import com.rssl.phizic.wsgate.bankroll.generated.Office;
import com.rssl.phizic.wsgate.bankroll.generated.Card;
import com.rssl.phizic.wsgate.types.AccountImpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankrollService_PortType_Impl implements BackRefBankrollService_PortType
{
	public String findAccountBusinessOwner(Account account_1) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		AccountImpl account = new AccountImpl();
		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(account, account_1, TypesCorrelation.types);
			return	service.findAccountBusinessOwner(account);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String findAccountExternalId(String string_2) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			return	service.findAccountExternalId(string_2);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
	}

	public String findCardExternalId2(String string) throws RemoteException
	{
		try
		{
			BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
			return service.findCardExternalId(string);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
	}

	public String findCardExternalId(Long loginId, String string_2) throws RemoteException
	{
	  	BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			return	service.findCardExternalId(loginId, string_2);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
	}

	public Office getAccountOffice(Long loginId, String accountNumber) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			com.rssl.phizic.wsgate.bankroll.generated.Office office = new com.rssl.phizic.wsgate.bankroll.generated.Office();
		    com.rssl.phizic.gate.dictionaries.officies.Office office_1 = service.getAccountOffice(loginId, accountNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_1, TypesCorrelation.types);
			return office;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Office getCardOffice(Long loginId, String cardNumber) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			com.rssl.phizic.wsgate.bankroll.generated.Office office = new com.rssl.phizic.wsgate.bankroll.generated.Office();
		    com.rssl.phizic.gate.dictionaries.officies.Office office_1 = service.getCardOffice(loginId, cardNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_1, TypesCorrelation.types);
			return office;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

    public Card getStoredCard(Long loginId, String cardNumber) throws RemoteException
    {
        BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			com.rssl.phizic.wsgate.bankroll.generated.Card card1 = new com.rssl.phizic.wsgate.bankroll.generated.Card();
			com.rssl.phizic.gate.bankroll.Card card =  service.getStoredCard(loginId, cardNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(card1, card, TypesCorrelation.types);
			return card1;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
    }

	public Account getCardAccount(Long loginId, String cardNumber) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);
		try
		{
			com.rssl.phizic.wsgate.bankroll.generated.Account account1 = new com.rssl.phizic.wsgate.bankroll.generated.Account();
			com.rssl.phizic.gate.bankroll.Account account =  service.getCardAccount(loginId, cardNumber);
			BeanHelper.copyPropertiesWithDifferentTypes(account1, account, TypesCorrelation.types);
			return account1;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Account getCardAccount2(String cardNumber) throws RemoteException
	{
		BackRefBankrollService service = GateSingleton.getFactory().service(BackRefBankrollService.class);

		try
		{
			com.rssl.phizic.wsgate.bankroll.generated.Account generatedAccount = new com.rssl.phizic.wsgate.bankroll.generated.Account();
			com.rssl.phizic.gate.bankroll.Account gateAccount = service.getCardAccount(cardNumber);

			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, gateAccount, TypesCorrelation.types);
			return generatedAccount;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
