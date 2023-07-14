package com.rssl.phizicgate.manager.services.routable;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeAndAdapterRelationService;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ������� ����� ���� ���������������� ��������
 *
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class RoutableServiceBase extends AbstractService
{
	private static final NodeAndAdapterRelationService relationService = new NodeAndAdapterRelationService();
	private static final AdapterService adapterService = new AdapterService();

	protected RoutableServiceBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * �������� ���������� � ������������� �� �����
	 * ���� ����� �� ��������� ���������������� ����������
	 * ���������� ����� ���� � ���� � �������� �� ���� ���� � �������������
	 * �����: �� ������ ������ ��������� ��� 2 ��� ������ ���. (��� ������������������ �� ��� ����� � ���),
	 * ������� ��� ���������� ����� ����� ���� �� ��������� �� � ��� ���� �� ������ �������� � ��������� ����������� ��� ���
	 * � ���� ��� (��. BUG025623 �������� ������������� ��������)
	 * @param office ����
	 * @return ���������� � �������������.
	 */
	protected String getOfficeRouteInfo(final Office office) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws GateException
				{
					Query query = session.getNamedQuery(RoutableServiceBase.class.getName() + ".getDepartmentSynchKeyByRegionAndBranch");
					query.setMaxResults(1);
					Map<String, String> fields = office.getCode().getFields();
					String region = fields.get("region");
					query.setParameter("region", region);
					String branch = fields.get("branch");
					query.setParameter("branch", branch);
					String routeInfo = (String) query.uniqueResult();
					if (routeInfo == null)
					{
						throw new GateException("�� ������� �� ������ ����� � ��� " + region + "/" + branch+". ������������� ����������");
					}
					return routeInfo;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� ������� ����������������� ������� �� ������ �������������
	 * @param routeInfo ���������� � �������������
	 * @return �������
	 * @throws GateException
	 */
	public GateFactory getDelegateFactory(String routeInfo) throws GateException, GateLogicException
	{
		//��� ��������� uuid �������� ������ �������� �� ���������� ������� �������
		String uuid = ExternalSystemHelper.getCode(IDHelper.restoreRouteInfo(routeInfo));
		return GateManager.getInstance().getGate(getNode(uuid)).getFactory();
	}

	/**
	 * @param account ������ �������������
	 * @return �������� ������� �� ������� ������������� "����"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Account account) throws GateException, GateLogicException
	{
		return getDelegateFactory(account.getId());
	}

	/**
	 * ��������� ������� ����������������� ������� �� ������ �����
	 * @param loginId ������������ ������ ��������� �����
	 * @param accountNumber ����� �����
	 * @return �������� �������
	 */
	protected GateFactory getDelegateFactoryByAccount(Long loginId, String accountNumber) throws GateLogicException, GateException
	{
		//������� ���� ������� �����
		BackRefBankrollService backRefBankrollService = getFactory().service(BackRefBankrollService.class);
		Office accountOffice = backRefBankrollService.getAccountOffice(loginId, accountNumber);
		//�������� � ���� ���������� � �������������.
		return getDelegateFactory(getOfficeRouteInfo(accountOffice));
	}

	/**
	 * @param card �����
	 * @return �������� ������� �� ������� ������������� "�����"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Card card) throws GateException, GateLogicException
	{
		return getDelegateFactory(card.getId());
	}

	/**
	 * @param loan ������
	 * @return �������� ������� �� ������� ������������� "������"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Loan loan) throws GateException, GateLogicException
	{
		return getDelegateFactory(loan.getId());
	}

	/**
	 * @param client ������
	 * @return �������� ������� �� ������� ������������� "������"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Client client) throws GateException, GateLogicException
	{
		return getDelegateFactory(client.getId());
	}

	/**
	 * @param office ����
	 * @return �������� ������� �� ������� ������������� "����"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Office office) throws GateException, GateLogicException
	{
		return getDelegateFactory(office.getSynchKey().toString());
	}

	/**
	 * @param deposit �����
	 * @return �������� ������� �� ������� ������������� "�����"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Deposit deposit) throws GateException, GateLogicException
	{
		return getDelegateFactory(deposit.getId());
	}

	/**
	 * @param billing �������
	 * @return �������� ������� �� ������� ������������� "�������"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Billing billing) throws GateException, GateLogicException
	{
		return getDelegateFactory(billing.getSynchKey().toString());
	}

	/**
	 * @param recipient ���������� �������
	 * @return �������� ������� �� ������� ������������� "���������� �������"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Recipient recipient) throws GateException, GateLogicException
	{
		return getDelegateFactory(recipient.getSynchKey().toString());
	}

	/**
	 * @param imAccount ���
	 * @return �������� ������� �� ������� ������������� "���"
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(IMAccount imAccount) throws GateException, GateLogicException
	{
		return getDelegateFactory(imAccount.getId());
	}

	/**
	 * ��������� �������� �� ���������.
	 * ������� ���������� �� �����, � ������� ������������(���) ��������
	 * @param document ��������
	 * @return �������.
	 * @throws GateException
	 */
	public GateFactory getDelegateFactory(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegateFactory(document.getOffice());
	}

	/**
	 * @param autoPayment ����������
	 * @return ������� ������� �� ������� ������������� "����������"
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public GateFactory getDelegateFactory(AutoPayment autoPayment) throws GateException, GateLogicException
	{
		return getDelegateFactory(autoPayment.getExternalId());
	}

	/**
	 * @param loyaltyProgram ��������� ����������
	 * @return ������� ������� �� ������� ������������� "��������� ����������"
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public GateFactory getDelegateFactory(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		return getDelegateFactory(loyaltyProgram.getExternalId());
	}

	/**
	 * @param object  ������ �������������
	 * @return �������� ������� �� ������� �������������
	 * @throws GateException
	 */
	protected GateFactory getDelegateFactory(Object object) throws GateException, GateLogicException
	{
		if (object instanceof Account)
		{
			return getDelegateFactory((Account) object);
		}
		else if (object instanceof Card)
		{
			return getDelegateFactory((Card) object);
		}
		else if (object instanceof Loan)
		{
			return getDelegateFactory((Loan) object);
		}
		else if (object instanceof Deposit)
		{
			return getDelegateFactory((Deposit) object);
		}
		else if (object instanceof Office)
		{
			return getDelegateFactory((Office) object);
		}
		else if (object instanceof Billing)
		{
			return getDelegateFactory((Billing) object);
		}
		else if (object instanceof Client)
		{
			return getDelegateFactory((Client) object);
		}
		else if (object instanceof Recipient)
		{
			return getDelegateFactory((Recipient) object);
		}
		else if (object instanceof IMAccount)
		{
			return getDelegateFactory((IMAccount) object);
		}
		else if (object instanceof String)
		{
			return getDelegateFactory((String) object);
		}
		else if (object instanceof GateDocument)
		{
			return getDelegateFactory((GateDocument) object);
		}
		else if (object instanceof AutoPayment)
		{
			return getDelegateFactory((AutoPayment) object);
		}
		else if (object instanceof LoyaltyProgram)
		{
			return getDelegateFactory((LoyaltyProgram) object);
		}
		throw new IllegalArgumentException("����������� ��� " + object);
	}

	/**
	 * @param object  ������ �������������
	 * @return �������� ������� �� ������� �������������
	 * @throws GateException
	 */
	protected <T> Map<GateFactory, List<T>> getDelegateFactory(T... object) throws GateLogicException, GateException
	{
		Map<GateFactory, List<T>> map = new HashMap<GateFactory, List<T>>();
		for (T obj : object)
		{
			GateFactory factory = getDelegateFactory(obj);
			if (!map.containsKey(factory))
				map.put(factory, new ArrayList<T>());
			map.get(factory).add(obj);
		}
		return map;
	}

	/**
	 * @param firstIsMain - ������ ������ ���� �������� �������� �������������
	 * @param pairs - ������ ��� ��������
	 * @return �������� ������� �� ������� �������������
	 * @throws GateException
	 */
	protected <K, T> Map<GateFactory, List<Pair<K, T>>> getDelegateFactory(boolean firstIsMain, Pair<K, T>... pairs) throws GateLogicException, GateException
	{
		Map<GateFactory, List<Pair<K, T>>> map = new HashMap<GateFactory, List<Pair<K, T>>>();
		for (Pair<K, T> pair : pairs)
		{
			GateFactory factory = getDelegateFactory(firstIsMain ? pair.getFirst() : pair.getSecond());
			if (!map.containsKey(factory))
				map.put(factory, new ArrayList<Pair<K, T>>());
			map.get(factory).add(pair);
		}
		return map;
	}

	/**
	 * ��������� ���� ��������� ��������
	 * @param info - ���������� � �������������
	 * @return ����
	 * @throws GateException
	 */
	private Node getNode(String info) throws GateException
	{
		Adapter adapter = adapterService.getAdapterByUUID(info);
		if (adapter == null)
			throw new GateException("������� �� ������. ���������� � ������������� (uuid ��������): " + info);

		List<Node> nodes = relationService.getNodes(adapter);
		return nodes.get(0);
	}
}
