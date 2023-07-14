package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.CompositeInactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * ������ ��� ��������� ����� ������������ ��������� �������
 *
 * @author khudyakov
 * @ created 02.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ClientResourceHelper
{
	/**
	 * �������� ��������� �������� ������� � ������ �� �����������
	 * @param person ������
	 * @return ��������� �������� ������� � ������ �� �����������
	 */
	public static Pair<List<Class>, Map<Class,String>> getClientsProducts(ActivePerson person, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Client client             = person.asClient();
		CreationType creationType = person.getCreationType();

		if (isEsbSupported(client))
		{
			switch (creationType)
			{
				// ��� ���� ������� �������� ��� ��������� ��������
				case UDBO:
				{
					return getResolvedProducts(person, checkReceiveLoansOnLogin);
				}
				// ��� ���� ������� �������� ������ ����� � �����
				case SBOL:
				{
					return getResolvedProductsForSBOL(client, checkReceiveLoansOnLogin);
				}
				// ��������� ��� - ��������� ������. �������������� ��� ���� �������� ������ �����.
				default:
				{
					return checkProduct(client, Card.class);
				}
			}
		}
		else
		{
			//� ������, ���� ���� �� �������������� � ������ �� ����, ������� ������������ ������ �����.
			return (creationType == CreationType.SBOL) ? getResolvedProductsForSBOL(client, checkReceiveLoansOnLogin) : checkProduct(client, Card.class);
		}
	}

	private static boolean isEsbSupported(Client client) throws BusinessException
	{
		return ((ExtendedDepartment) client.getOffice()).isEsbSupported();
	}

	/**
	 * �������� ��������� ��������, ��� ������� ����� ��������� ����� ����
	 * @param person ������
	 * @return ��������� ��������
	 */
	public static Class[] getResolvedProductsWithPermissionCheck(ActivePerson person) throws BusinessException
	{
		Pair<List<Class>, Map<Class, String>> pair = new Pair<List<Class>, Map<Class,String>>(new ArrayList<Class>(), new HashMap<Class, String>());
		Client client = person.asClient();
		joinPair(pair, checkLoanProduct(client, true));
		joinPair(pair, checkProduct(client, IMAccount.class, "IMAccountInfoService"));

		if ((person.getIsRegisteredInDepo() == null || ApplicationUtil.isApi()) ? false : person.getIsRegisteredInDepo())
			joinPair(pair, checkProduct(client, DepoAccount.class, "DepoAccountInfo"));

		if (CollectionUtils.isEmpty(pair.getFirst()))
		{
			//���� ������ ���, �� ������ �� ������
			if (CollectionUtils.isEmpty(pair.getSecond().keySet()))
				return null;

			List<String> errors = new ArrayList<String>();
			for (Class productWithException : pair.getSecond().keySet())
			{
				errors.add(pair.getSecond().get(productWithException));
			}

			throw new CompositeInactiveExternalSystemException(errors);
		}

		return pair.getFirst().toArray(pair.getFirst().toArray(new Class[pair.getFirst().size()]));
	}

	/**
	 * �������� ��������, ��������� ��� ���������� �� ����� � ������ ATM
	 * @param person ������
	 * @return ��������� ��������

	 */
	public static Pair<List<Class>, Map<Class,String>> getClientProductsForATM(ActivePerson person) throws BusinessException
	{
		return checkProduct(person.asClient(), Card.class);
	}

	private static Pair<List<Class>, Map<Class,String>> getResolvedProducts(ActivePerson person, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Pair<List<Class>, Map<Class, String>> pair = new Pair<List<Class>, Map<Class, String>>(new ArrayList<Class>(), new HashMap<Class, String>());
		Client client = person.asClient();

		joinPair(pair, checkProduct(client, Account.class));
		joinPair(pair, checkProduct(client, Card.class));
		joinPair(pair, checkLoanProduct(client, checkReceiveLoansOnLogin));
		joinPair(pair, checkProduct(client, IMAccount.class, "IMAccountInfoService"));

		if ((person.getIsRegisteredInDepo() == null || ApplicationUtil.isApi()) ? false : person.getIsRegisteredInDepo() )
			 joinPair(pair, checkProduct(client, DepoAccount.class, "DepoAccountInfo"));

		return pair;
	}

	private static Pair<List<Class>, Map<Class,String>> getResolvedProductsForSBOL(Client client, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Pair<List<Class>, Map<Class,String>> pair = new Pair<List<Class>, Map<Class, String>>(new ArrayList<Class>(), new HashMap<Class,String>());

		joinPair(pair, checkProduct(client, Card.class));
		joinPair(pair, checkLoanProduct(client, checkReceiveLoansOnLogin));

		if (isEsbSupported(client))
			joinPair(pair, checkProduct(client, Account.class));

		return pair;
	}

	/**
	 * �������� �� ����������� ��������� ������ �� ��������. ����������� ����� �������, ���������� ������� �������
	 * @param client ������
	 * @param product �������
	 * @param service ������
	 * @return ���� - �������/������
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkProduct(Client client, Class product, String service) throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule == null || !authModule.implies(new ServicePermission(service)))
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());

		//��������� ������� ������� �� ����������, ���������� ����������� ����
		return checkProduct(client, product);
	}

	/**
	 * �������� �� ����������� ��������� ������ �� �������. ����������� ����� �������, ���������� ������� �������
	 * @param client ������
	 * @return ���� - �������/������
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkLoanProduct(Client client, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule == null || !authModule.implies(new ServicePermission("LoanInfo")))
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());

		if (checkReceiveLoansOnLogin && !authModule.implies(new ServicePermission("ReceiveLoansOnLogin")))
		{
			if (PersonContext.isAvailable())
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				personData.setNeedLoadLoans(true);
			}
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());
		}

		//��������� ������� ������� �� ����������, ���������� ����������� ����
		return checkProduct(client, Loan.class);
	}

	/**
	 * �������� �� ����������� ��������� ������ �� ��������. ����������� ���������� ������� �������
	 * @param client ������
	 * @param product �������
	 * @return ���� - �������/������
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkProduct(Client client, Class product) throws BusinessException
	{
		List<Class> products = Collections.singletonList(product);
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		try
		{
			//��������� ������ � ������� �������
			ExternalSystemHelper.check(externalSystemGateService.findByProduct(client.getOffice(), BankProductTypeWrapper.getBankProductType(product)));
			return new Pair<List<Class>, Map<Class,String>>(products, Collections.<Class, String>emptyMap());
		}
		catch (InactiveExternalSystemException e)
		{
			PersonHelper.setPersonDataNeedUpdate();
			//��� ���� ��������� ����� ���������� way
			if (Card.class == product)
			{
				try
				{
					if (ExternalSystemHelper.isActive(externalSystemGateService.findByProduct(client.getOffice(), BankProductType.CardWay)))
						return new Pair<List<Class>, Map<Class,String>>(products, Collections.<Class, String>emptyMap());
				}
				catch (GateException ge)
				{
					throw new BusinessException(ge);
				}
			}
			return new Pair<List<Class>, Map<Class,String>>(Collections.<Class>emptyList(), Collections.singletonMap(product, e.getMessage()));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private static Pair<List<Class>, Map<Class,String>> joinPair(Pair<List<Class>, Map<Class,String>> dest, Pair<List<Class>, Map<Class,String>> orig)
	{
		dest.getFirst().addAll(orig.getFirst());
		dest.getSecond().putAll(orig.getSecond());

		return dest;
	}

}
