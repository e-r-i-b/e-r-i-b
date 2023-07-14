package com.rssl.phizicgate.manager.services.routablePersistent;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.PersistentGateManager;
import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.objects.*;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.*;

/**
 * Роутирующий персистентный сервис.
 *
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class RoutablePersistentServiceBase<E extends Service> extends PersistentServiceBase<E>
{
    protected RoutablePersistentServiceBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получение фабрики маршрутизирующего сервиса по строке маршрутизации
	 * @return фабрика
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public GateFactory getDelegateFactory(String routeInfo) throws GateException, GateLogicException
	{
		return PersistentGateManager.getInstance().getGate(routeInfo).getFactory();
	}

	/**
	 * @param routeInfo маршрутизирующая информация.
	 * @return конечный сервис.
	 */                                                                                                             
	protected abstract Service endService(String routeInfo) throws GateLogicException, GateException;


	@Override
	protected final String getRouteInfo() throws GateLogicException, GateException
	{
		throw new GateException("Для роутирующе-персистентной информация о маршрутизации должна браться из объекта.");
	}

	/**
	 * @param instanceClass класс инстанс.
	 * @param objects список роутируемых объектов.
	 * @return отображение маршрутизирующей информации на список идентификаторов.
	 */
	protected <T extends RouteInfoReturner> Map<String, List<T>> objectIdWithoutRouteInfoToRouteInfo(Class<T> instanceClass, Object ... objects) throws GateException
	{
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		List<T> routeInfos = removeRouteInfo(instanceClass, objects);
		for(T returner : routeInfos)
		{
			if (!map.containsKey(returner.getRouteInfo()))
			{
				map.put(returner.getRouteInfo(), new ArrayList<T>());
			}
			map.get(returner.getRouteInfo()).add(returner);
		}
		return map;
	}

	protected <T extends RouteInfoReturner> List<T> removeRouteInfo(Class<T> instanceClass,  Object ... objects)
	{
		List<T> list = new ArrayList<T>(objects.length);
		for (int i=0; i<objects.length; i++)
		{
			//noinspection unchecked
			list.add((T) removeRouteInfo(objects[i]));
		}
		return list;
	}

	protected RouteInfoReturner removeRouteInfo(Object object)
	{
		if (object instanceof Account)
		{
			return removeRouteInfo((Account) object);
		}
		else if (object instanceof Card)
		{
			return removeRouteInfo((Card) object);
		}
		else if (object instanceof Loan)
		{
			return removeRouteInfo((Loan) object);
		}
		else if (object instanceof Deposit)
		{
			return removeRouteInfo((Deposit) object);
		}
		else if (object instanceof Client)
		{
			return removeRouteInfo((Client) object);
		}
		else if (object instanceof Office)
		{
			return removeRouteInfo((Office) object);
		}
		else if (object instanceof Billing)
		{
			return removeRouteInfo((Billing) object);
		}
		else if (object instanceof Recipient)
		{
			return removeRouteInfo((Recipient) object);
		}
		else if (object instanceof Pair)
		{
			return removeRouteInfoPair((Pair) object);
		}
		else if (object instanceof String)
		{
			return removeRouteInfoString((String) object);
		}
		else if (object instanceof AutoPayment)
		{
			return removeRouteInfo((AutoPayment) object);
		}
		else if (object instanceof LoyaltyProgram)
		{
			return removeRouteInfo((LoyaltyProgram) object);
		}
		throw new IllegalArgumentException("Неизвестный тип " + object);
	}

	protected PairRouteInfo removeRouteInfoPair(Pair pair)
	{
		return new PairRouteInfo(pair, this);
	}

	protected StringRouteInfo removeRouteInfoString(String data)
	{
		return new StringRouteInfo(data);
	}

	protected StringRouteInfo[] removeRouteInfoString(String... data)
	{
		StringRouteInfo stringRouteInfo[] = new StringRouteInfo[data.length];
		for (int i = 0; i < data.length; i++)
		{
			stringRouteInfo[i] = new StringRouteInfo(data[i]);
		}
		return stringRouteInfo;
	}

	/**
	 * Сохранение информации о маршрутизации в идентификаторе объекта маршрутизации
	 * @param id - идентификатор объект
	 * @return - оъбект
	 */
	protected String storeRouteInfo(String id, String routeInfo) throws GateLogicException, GateException
	{
		return IDHelper.storeRouteInfo(id, routeInfo);
	}

	protected ClientWithRouteInfo storeRouteInfo(Client client, String routeInfo) throws GateLogicException, GateException
	{
		return new ClientWithRouteInfo(client, routeInfo);
	}

	protected AccountWithRouteInfo storeRouteInfo(Account account, String routeInfo) throws GateLogicException, GateException
	{
		return new AccountWithRouteInfo(account, routeInfo);
	}

	protected CardWithRouteInfo storeRouteInfo(Card card, String routeInfo) throws GateLogicException, GateException
	{
		return new CardWithRouteInfo(card, routeInfo);
	}

	protected LoanWithRouteInfo storeRouteInfo(Loan loan, String routeInfo) throws GateLogicException, GateException
	{
		return new LoanWithRouteInfo(loan, routeInfo);
	}

	protected List storeRouteInfo(List list, String routeInfo) throws GateLogicException, GateException
	{
		for (int i=0; i< list.size(); i++)
		{
			list.set(i, storeRouteInfo(list.get(i), routeInfo));
		}
		return list;
	}

	protected GroupResult storeRouteInfo(GroupResult groupResult, String routeInfo) throws GateLogicException, GateException
	{
		GroupResult res = new GroupResult();
		for (Object key: groupResult.getKeys())
		{
			try
			{
			    Object value = GroupResultHelper.getResult(groupResult, key);
				res.putResult(storeRouteInfo(key, routeInfo), storeRouteInfo(value, routeInfo));
			}
			catch (IKFLException e)
			{
				res.putException(storeRouteInfo(key), e);
			}
		}
		return res;
	}

	protected OfficeWithRouteInfo storeRouteInfo(Office office, String routeInfo) throws GateLogicException, GateException
	{
		return new OfficeWithRouteInfo(office, routeInfo);
	}

	protected DepositWithRouteInfo storeRouteInfo(Deposit deposit, String routeInfo) throws GateLogicException, GateException
	{
		return new DepositWithRouteInfo(deposit, routeInfo);
	}

	protected RecipientWithRouteInfo storeRouteInfo(Recipient recipient, String routeInfo) throws GateLogicException, GateException
	{
		return new RecipientWithRouteInfo(recipient, routeInfo);
	}

	protected CancelationCallBack storeRouteInfo(CancelationCallBack callBack, String routeInfo) throws GateLogicException, GateException
	{
		return new CancelationCallBackWithRouteInfo(callBack, routeInfo);
	}

	protected BillingWithRouteInfo storeRouteInfo(Billing billing, String routeInfo) throws GateLogicException, GateException
	{
		return new BillingWithRouteInfo(billing, routeInfo);
	}

	protected AutoPaymentWithRouteInfo storeRouteInfo(AutoPayment autoPayment, String routeInfo) throws GateLogicException, GateException
	{
		return new AutoPaymentWithRouteInfo(autoPayment, routeInfo);
	}

	protected LoyaltyProgramWithRouteInfo storeRouteInfo(LoyaltyProgram loyaltyProgram, String routeInfo) throws GateLogicException, GateException
	{
		if (loyaltyProgram == null)
			return null;
		return new LoyaltyProgramWithRouteInfo(loyaltyProgram, routeInfo);
	}

	protected Object storeRouteInfo(Object object, String routeInfo) throws GateLogicException, GateException
	{
		if (object instanceof Account)
		{
			return storeRouteInfo((Account) object, routeInfo);
		}
		else if (object instanceof Card)
		{
			return storeRouteInfo((Card) object, routeInfo);
		}
		else if (object instanceof Loan)
		{
			return storeRouteInfo((Loan) object, routeInfo);
		}
		else if (object instanceof Deposit)
		{
			return storeRouteInfo((Deposit) object, routeInfo);
		}
		else if (object instanceof Client)
		{
			return storeRouteInfo((Client) object, routeInfo);
		}
		else if (object instanceof Office)
		{
			return storeRouteInfo((Office) object, routeInfo);
		}
		else if (object instanceof Billing)
		{
			return storeRouteInfo((Billing) object, routeInfo);
		}
		else if (object instanceof Recipient)
		{
			return storeRouteInfo((Recipient) object, routeInfo);
		}
		else if (object instanceof StringRouteInfo)
		{
			return storeRouteInfo((String) object, routeInfo);
		}
		else if (object instanceof AutoPayment)
		{
			return storeRouteInfo((AutoPayment) object, routeInfo);
		}
		else if (object instanceof LoyaltyProgram)
		{
			return storeRouteInfo((LoyaltyProgram) object, routeInfo);
		}
		else if (object instanceof List)
		{
			return storeRouteInfo((List) object,  routeInfo);
		}
		return object;
	}
}
