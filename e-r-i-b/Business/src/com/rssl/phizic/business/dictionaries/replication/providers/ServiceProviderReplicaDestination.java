package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.dictionaries.replication.CriteriaReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.common.types.documents.ServiceProvidersConstants;
import com.rssl.phizic.context.MultiNodeEmployeeData;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;

import java.util.*;

/**
 * @author khudyakov
 * @ created 11.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class ServiceProviderReplicaDestination extends CriteriaReplicaDestinationBase<ServiceProviderForReplicationWrapper>
{
	public static final String ACCESS_REMOVE_SETTING_NAME = "com.rssl.phizic.providers.replica.access.remove";
	public static final String ACCESS_REMOVE_MESSAGE = "[%s] [%s] - ������������ ���� ��� �������� ������";

	private static final String QUERY_PREFIX = ServiceProviderBase.class.getName() + ".";
	//����������� where .. in(arguments...) ������ �� ����� 1000 �������� � �������� ������ ����������
	private static final int MaxCountIdsIn = 999;
	private static final int DEFAULT_VERSION_API = 500;

	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	private Set<Long> billingIds;                   //�������������� ����������� ������
	private List<Long> notReplicatedProvidersIds;   //�������������� ��������������� ����������� �����
	protected ReplicationType replicationType;        //��� ������� ����������
	protected Properties properties;
	private ReplicationTaskResult result;
	private ReplicateProvidersBackgroundTask task;
	private String dbInstanceName;

	/**
	 * ctor
	 * @param ignoreDublicate   - ������������ ��������� (��/���)
	 * @param needSort          - ����������� (��/���)
	 */
	public ServiceProviderReplicaDestination(ReplicationTaskResult result, boolean ignoreDublicate, boolean needSort, String dbInstanceName)
	{
		super(ignoreDublicate, needSort);
		this.result = result;
		this.dbInstanceName = dbInstanceName;
	}

	public ServiceProviderReplicaDestination(ReplicateProvidersBackgroundTask task, boolean ignoreDublicate, boolean needSort, String dbInstanceName)
	{
		super(ignoreDublicate, needSort);
		this.task = task;
		this.dbInstanceName = dbInstanceName;
	}

	//������� ����������
	public ServiceProviderReplicaDestination(ReplicationTaskResult result, ServiceProvidersSAXLoader loader, Properties properties, String dbInstanceName) throws GateException
	{
		super(false, true);
		this.result = result;
		this.billingIds = loader.getReplicatedBillings();
		this.notReplicatedProvidersIds = loader.getNotReplicatedProvidersIds();
		this.replicationType = loader.getReplicationType();
		this.properties = properties;
		this.dbInstanceName = dbInstanceName;
	}
	//������� �������� ��
	public ServiceProviderReplicaDestination(ReplicateProvidersBackgroundTask task, ServiceProvidersSAXLoader loader, String dbInstanceName) throws GateException
	{
		super(false, true);
		this.task = task;
		this.billingIds = loader.getReplicatedBillings();
		this.notReplicatedProvidersIds = loader.getNotReplicatedProvidersIds();
		this.replicationType = loader.getReplicationType();
		this.properties = getProperties(task);
		this.dbInstanceName = dbInstanceName;
	}

	@Override
	protected String getInstanceName()
	{
		return dbInstanceName;
	}

	private Properties getProperties(ReplicateProvidersBackgroundTask task) throws GateException
	{
		try
		{
			return task.getProperties();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected DetachedCriteria getCriteria()
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(!employeeData.isAllTbAccess())
		{
			Filter filter = getSession().enableFilter("provider_filter_by_department");
			filter.setParameter("employeeLoginId", employeeData.getLoginId());
		}


		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BillingServiceProvider.class)
				.add(Expression.in("billing.id", billingIds));

		if (!notReplicatedProvidersIds.isEmpty())
		{
			int size = notReplicatedProvidersIds.size();
			if (size > MaxCountIdsIn)
			{
				//in ������ ������ < MaxCountIdsIn �������, ������� ����� �� �����
				int i = 0;
				while (i < size)
				{
					List<Long> partOfnotReplicateProvIds = notReplicatedProvidersIds.subList(i, i + MaxCountIdsIn - 1 < size ? i + MaxCountIdsIn - 1 : size);
					detachedCriteria.add(Expression.not(Expression.in("id", partOfnotReplicateProvIds)));
					i += MaxCountIdsIn - 1;
				}
			}
			else
			{
				detachedCriteria.add(Expression.not(Expression.in("id", notReplicatedProvidersIds)));
			}
		}
		detachedCriteria.setProjection(Projections.property("id"));
		return DetachedCriteria.forClass(ServiceProviderForReplicationWrapper.class).add(Subqueries.propertyIn("provider.id", detachedCriteria));
	}

	public Iterator<ServiceProviderForReplicationWrapper> iterator() throws GateException, GateLogicException
	{
		initialize();

		if (billingIds.isEmpty())
		{
			addError("� ������������� xml ����� �� ������� �� ����� ������������������ ����������� �������.");
			return new ArrayList<ServiceProviderForReplicationWrapper>().iterator();
		}


		//noinspection unchecked
		List<ServiceProviderForReplicationWrapper> list = getCriteria().getExecutableCriteria(getSession()).list();

		if (needSort)
			Collections.sort(list, new SynchKeyComparator());

		return list.iterator();
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		try
		{
			Session session = getSession();
			ServiceProviderForReplicationWrapper newWrapper = (ServiceProviderForReplicationWrapper) newValue;
			BillingServiceProvider  newProvider = newWrapper.getProvider();
			if (findBy�odeRecipientSBOL(session, newProvider) != null)
			{
				if(task!=null)
					task.getResult().addToReport("��������� ����� � codeRecipientSBOL = " + newProvider.getCodeRecipientSBOL() + " � codeService = "+newProvider.getCodeService() + " ��� �������� � �������.");
				else
					result.addToReport("��������� ����� � codeRecipientSBOL = " + newProvider.getCodeRecipientSBOL() + " � codeService = "+newProvider.getCodeService() + " ��� �������� � �������.");
				return;
			}

			newProvider.setFederal(false);
			updateVersionApi(newProvider);

		    super.add(newProvider);
			for (PaymentService service : newWrapper.getPaymentServices())
				session.saveOrUpdate(new BillingProviderService(newProvider, service));

			addToLog(session, newWrapper, ChangeType.update);

			if(task!=null)
			{
				task.getResult().destionanionRecordInsered();
				session.saveOrUpdate(task);
			}
			else
			{
				result.destionanionRecordInsered();
			}
		}
		catch (Exception e)
		{
			log.error(String.format(ServiceProvidersConstants.REPLICATION_PROVIDER_ERROR_MESSAGE, newValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		try
		{
			//��� �������� ���������� ��������� � ������ ������ �����������
			ServiceProviderForReplicationWrapper newWrapper  = (ServiceProviderForReplicationWrapper) newValue;
			ServiceProviderForReplicationWrapper oldWrapper  = (ServiceProviderForReplicationWrapper) oldValue;
			BillingServiceProvider oldProvider = oldWrapper.getProvider();

			Session session = getSession();

			//������� �������������� ���� ���������� �����
			session.getNamedQuery(QUERY_PREFIX + "removeRecipientFields")
					.setParameter("providerId", oldProvider.getId())
					.executeUpdate();

			//������� ���-������ ���������� �����
			session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase.removeSmsAliases")
					.setParameter("providerId", oldProvider.getId())
					.executeUpdate();

			//������� �� ������ ������, ����� �� ����������� �������������� ����
			session.evict(oldProvider);

			updateEntity(oldWrapper, newWrapper);

			for (FieldDescription description : oldProvider.getFieldDescriptions())
				description.setHolderId(oldProvider.getId());

			updateVersionApi(oldProvider);

			session.saveOrUpdate(oldProvider);
			session.getNamedQuery(QUERY_PREFIX + "removeProviderServices")
					.setParameter("extra_providerId", oldProvider.getId())
					.executeUpdate();
			for (PaymentService service : oldWrapper.getPaymentServices())
			{
				session.saveOrUpdate(new BillingProviderService(oldProvider, service));
			}

			addToLog(session, oldWrapper, ChangeType.update);


			if(task!=null)
			{
				task.getResult().destionanionRecordUpdated();
				session.saveOrUpdate(task);
			}
			else
			{
				result.destionanionRecordUpdated();
			}
		}
		catch (Exception e)
		{
			log.error(String.format(ServiceProvidersConstants.REPLICATION_PROVIDER_ERROR_MESSAGE, newValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	private void updateVersionApi(BillingServiceProvider provider)
	{
		if (provider.getVersionAPI() == null)
		{
			//���������� ������������� ������ ���, ��� �� ���������� �������� � ����
			provider.setVersionAPI(DEFAULT_VERSION_API);
		}
	}

	public void remove(DictionaryRecord oldValue) throws GateException
	{
		ServiceProviderForReplicationWrapper oldWrapper = (ServiceProviderForReplicationWrapper) oldValue;
		BillingServiceProvider oldProvider = oldWrapper.getProvider();
		String isFullAccessProperty = ServiceProvidersSAXLoader.ACCESS_LOAD_TYPE_PREFIX + ServiceProvidersSAXLoader.LoadType.FULL_REPLICATION;
		boolean isFullAccess = properties != null && BooleanUtils.toBoolean(properties.getProperty(isFullAccessProperty));
		//���� �� ����� ������� ����������� ��� ����������
		boolean isRemoveAccess = !isFullAccess || BooleanUtils.toBoolean(properties.getProperty(ACCESS_REMOVE_SETTING_NAME));

		try
		{
			AccountType accountType = oldProvider.getAccountType();
			//��� ����� ���������� ��� ��� ���������� �������� �������� ���������� � �����
			Session session = getSession();
			if (ReplicationType.DEFAULT == replicationType || ReplicationType.REMOVE == replicationType)
			{
				if (isRemoveAccess)
				{
					destionanionRecordDeleted();
				}
			}
			//���� ������ ��������� ������������ ������ �� ����� � �����
			else if (AccountType.ALL == accountType)
			{
				//��� ���������� �� ������� ������� ������� ������ � �����
				if (ReplicationType.ESB == replicationType)
					oldProvider.setAccountType(AccountType.DEPOSIT);

				//��� ���������� �� ������� ������� ��������� ������� ������ �� �����
				if (ReplicationType.TB == replicationType)
					oldProvider.setAccountType(AccountType.CARD);

				session.update(oldProvider);
				addToLog(session, oldProvider, ChangeType.update);

				if(task != null)
				{
					task.getResult().destionanionRecordUpdated();
				}
				else
				{
					result.destionanionRecordUpdated();
				}

				return;
			}
			//���� ����������� �� ������� �������, � ��������� ��� �������� ������������ ������ �� �����, �� ������ ���������� �� ��������� � �����
			else if (ReplicationType.ESB == replicationType && AccountType.DEPOSIT == accountType)
			{
				return;
			}
			//���� ����������� �� ������� ������� ���������, � ��������� ��� �������� ������������ ������ ������, �� ������ ���������� �� ��������� � �����
			else if (ReplicationType.TB == replicationType && AccountType.CARD == accountType)
			{
				return;
			}
			else if (isRemoveAccess)
			{
				destionanionRecordDeleted();
			}

			// ���� ���� ������� �����������, �� ������� ������������ ���
			if(properties == null || isRemoveAccess)
			{
				session.getNamedQuery(QUERY_PREFIX + "removeProviderServices")
						.setParameter("extra_providerId", oldProvider.getId())
						.executeUpdate();
				session.delete(oldProvider);
				addToLog(session, oldWrapper, ChangeType.delete);
			}
			else
			{
				log.error(String.format(ACCESS_REMOVE_MESSAGE, oldProvider.getId(), oldProvider.getName()));
			}

			if (task != null)
			{
				session.saveOrUpdate(task);
			}
		}
		catch (Exception e)
		{
			log.error(String.format(ServiceProvidersConstants.REPLICATION_PROVIDER_ERROR_MESSAGE, oldValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	private void destionanionRecordDeleted()
	{
		if(task != null)
		{
			task.getResult().destionanionRecordDeleted();
		}
		else
		{
			result.destionanionRecordDeleted();
		}

	}

	/**
	 * �������� ����������
	 * @param oldValue ������ ��������
	 * @param newValue ����� ��������
	 */
	protected void updateEntity(ServiceProviderForReplicationWrapper oldValue, ServiceProviderForReplicationWrapper newValue)
	{
		oldValue.updateFrom(newValue, replicationType);
	}

	public void initialize(GateFactory factory) throws GateException
	{

	}

	private BillingServiceProvider findBy�odeRecipientSBOL(Session session,BillingServiceProvider newProvider)
	{
		Query query = session.getNamedQuery(QUERY_PREFIX + "findProviderBy�odeRecipientSBOL");
		query.setParameter("codeRecipientSBOL", newProvider.getCodeRecipientSBOL());
		query.setParameter("codeService", newProvider.getCodeService());
		query.setParameter("billing", newProvider.getBilling());
		return  (BillingServiceProvider)query.uniqueResult();
	}

	private <T> void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}
}
