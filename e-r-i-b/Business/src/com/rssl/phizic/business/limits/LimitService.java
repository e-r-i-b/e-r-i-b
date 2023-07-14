package com.rssl.phizic.business.limits;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.imsi.LoginIMSIError;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author gulov
 * @ created 23.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class LimitService
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private static final SimpleService simpleService = new SimpleService();
	private static final PersonService personService = new PersonService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * ���������� ����� �� id
	 * @param id - ���������� ��� ������
	 * @param instanceName ������� ��
	 * @return ������ �����
	 * @throws BusinessException
	 */
	public Limit findById(Long id, String instanceName) throws BusinessException
	{
		 return simpleService.findById(Limit.class, id, instanceName);
	}

	/**
	 * ���������� ��� ���������� ������
	 * @param limit - �����, ������� ����� ���������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void addOrUpdate(final Limit limit, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdate(limit, instanceName);
					dictionaryRecordChangeInfoService.addChangesToLog(limit, ChangeType.update);
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������.", e);
		}
	}

	/**
	 * �������� ������
	 * @param limit - �����, ������� ����� �������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void remove(final Limit limit, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(limit, instanceName);
					dictionaryRecordChangeInfoService.addChangesToLog(limit, ChangeType.delete);
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������.", e);
		}
	}

	/**
	 * ��������� ������ �������� ������� �� ��������� � ���� ������
	 * @param document ��������
	 * @param limitType ��� ������
	 * @param channelType ��� ������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Limit> findActiveLimits(BusinessDocument document, LimitType limitType, ChannelType channelType) throws BusinessException
	{
		return findLimits(document, limitType, channelType, null, Status.ACTIVE);
	}

	/**
	 * ��������� ������ �������� ������� �� ���� ������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Limit> findActiveOverallLimits() throws BusinessException
	{
		return findLimits(null, LimitType.OVERALL_AMOUNT_PER_DAY, null, null, Status.ACTIVE, null);
	}

	/**
	 * ��������� ������ �������� ������� �� ��������� � ������ �����
	 * @param document ��������
	 * @param channelType ��� ������
	 * @param groupRisk ������ �����
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Limit> findActiveLimits(BusinessDocument document, ChannelType channelType, GroupRisk groupRisk) throws BusinessException
	{
		return findLimits(document, LimitType.GROUP_RISK, channelType, groupRisk == null ? new GroupRisk() : groupRisk, Status.ACTIVE);
	}

	/**
	 * ��������� ������ ������� �� �����, ������ ����� � �������
	 * @param department �������������
	 * @param type ��� ������
	 * @param channelType ��� ������
	 * @param groupRisk ������ �����
	 * @param status ������
	 * @param securityType ������� ������������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Limit> findLimits(final Department department, final LimitType type, final ChannelType channelType, final GroupRisk groupRisk, final Status status, final SecurityType securityType) throws BusinessException
	{
		return findLimits(department, type, channelType, groupRisk, status, securityType, null);
	}

	/**
	 * ��������� ������ ������� �� �����, ������ ����� � �������
	 * @param office �������������
	 * @param type ��� ������
	 * @param channelType ��� ������
	 * @param groupRisk ������ �����
	 * @param status ������
	 * @param securityType ������� ������������
	 * @param instanceName ������� ��
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Limit> findLimits(final Office office, final LimitType type, final ChannelType channelType, final GroupRisk groupRisk, final Status status, final SecurityType securityType, final String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Limit>>()
			{
				public List<Limit> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(Limit.class)
							.add(Expression.eq("type", type));

					status.buildExpression(criteria);

					//null ������ ��� �������� ������ ����� �������, ����� ���������� ���� ��� �������� �������
					if (office != null)
						criteria.add(Expression.eq("tb", new SBRFOfficeCodeAdapter(office.getCode()).getRegion()));

					//null ������ ��� �������� ������ ����� �������, ����� ���������� ���� ��� �������� �������
					if (channelType != null)
						criteria.add(Expression.eq("channelType", channelType));

					if (groupRisk != null)
					{
						criteria.add(Expression.eq("groupRisk.id", groupRisk.getId()));

						if (securityType != null)
						{
							// ������ �� ������ ����� ���� � ������� �������� ������ ������������ �������
							criteria.add(Expression.eq("securityType", securityType));
						}
					}

					//noinspection unchecked
					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �� ��� ����� �����
	 * @param limit �����
	 * @param status ������
	 * @param instance �������
	 * @return true - ����������
	 * @throws BusinessException
	 */
	public boolean isExistLimit(final Limit limit, final Status status, final String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(Limit.class)
							.add(Expression.eq("tb", limit.getTb()))
							.add(Expression.eq("type", limit.getType()))
							.add(Expression.eq("restrictionType", limit.getRestrictionType()))
							.add(Expression.eq("channelType", limit.getChannelType()))
							.setProjection(Projections.rowCount());

					status.buildExpression(criteria);

					if (limit.getGroupRisk() != null)
					{
						criteria.add(Expression.eq("groupRisk.id", limit.getGroupRisk().getId()));
						criteria.add(Expression.eq("securityType", limit.getSecurityType()));
					}

					return ((Integer)criteria.uniqueResult()) != 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

    /**
     * ���������� �� ��� ����� �����
     * @param limit �����
     * @return true - ����������
     * @throws BusinessException
     */
   public boolean isLimitsDatesIntersected(final Limit limit, final String instance) throws BusinessException
   {
   		try
   		{
   			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
   			{
   				public Boolean run(Session session) throws Exception
   				{
   					Criteria criteria = session.createCriteria(Limit.class)
   							.add(Expression.eq("type", limit.getType()))
                            .add(Restrictions.ne("id", limit.getId()))
                            .add(Restrictions.or(
                                    Restrictions.and(
                                            Restrictions.ge("startDate", limit.getStartDate()),
                                            Restrictions.le("endDate", limit.getStartDate())
                                    ),
                                    Restrictions.and(
                                            Restrictions.ge("startDate", limit.getEndDate()),
                                            Restrictions.le("endDate", limit.getEndDate())
                                    )
                            ))
   							.setProjection(Projections.rowCount());

					return ((Integer)criteria.uniqueResult()) != 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��������� ��������� IMSI �������� sim-�����
	 * @param document ��������
	 * @return ��������� ��������
	 * @throws BusinessException
	 */
	public LoginIMSIError getIMSICkeckResult(BusinessDocument document) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LoginIMSIError.class);
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		criteria.add(Expression.eq("login", documentOwner.getLogin()));
		criteria.addOrder(Order.desc("checkDate"));
		criteria.add(Expression.ge("checkDate", DateHelper.addDays(DateHelper.getCurrentDate(), -2).getTime()));
		List<LoginIMSIError> IMSIErrors = simpleService.find(criteria);

		if (CollectionUtils.isEmpty(IMSIErrors))
			return null;

		return IMSIErrors.get(0);
	}

	/**
	 * ��������� ������ �������, ������������� � ������� ������������ ������� ������� �� ���������
	 * @param document ��������
	 * @param limitType ��� �����
	 * @param channelType ����� ����� ������
	 * @param groupRisk ������ �����
	 * @param status ������ ������
	 * @return ������ �������
	 * @throws BusinessException
	 */
	private List<Limit> findLimits(BusinessDocument document, LimitType limitType, ChannelType channelType, GroupRisk groupRisk, Status status) throws BusinessException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		Person person = personService.findPersonByLogin(documentOwner.getLogin());
		return findLimits((Department) document.getDepartment(), limitType, channelType, groupRisk, status, person == null ? null : person.restoreSecurityType());
	}

	/**
	 * �������� ������ �� ������� �� ��������� ��������� �����
	 * @param login ����� ������������
	 * @param fromDate ����, �� ������� �������� �����
	 * @param channelType ����� �������
	 * @return ������ ���������� �� ����������
	 * @throws BusinessException
	 */
	public List<LimitDocumentInfo> getLimitDocumentInfoByLogin(final CommonLogin login, final Calendar fromDate, final ChannelType channelType) throws BusinessException
	{
		if (login == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null.");
		}
		if (fromDate == null)
		{
			throw new IllegalArgumentException("���� ������ ������ �� ����� ���� null.");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LimitDocumentInfo>>()
			{
				public List<LimitDocumentInfo> run(Session session) throws Exception
				{
					return (List<LimitDocumentInfo>) session.getNamedQuery("com.rssl.phizic.business.limits.users.LimitDocumentInfo.getByLogin")
							.setParameter("login_id",       login.getId())
							.setParameter("operation_date", fromDate)
							.setParameter("channel_type",   channelType == null ? null : channelType.name())
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �� ������� �� ��������� ��������� �����
	 * @param person �������
	 * @param fromDate ���� �� ������� ������� �����
	 * @param channelType ����� �������
	 * @return ������ ���������� �� ����������
	 */
	public List<LimitDocumentInfo> getLimitDocumentInfoByPerson(final Person person, final Calendar fromDate, final ChannelType channelType) throws BusinessException
	{
		if (person == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null.");
		}
		if (fromDate == null)
		{
			throw new IllegalArgumentException("���� ������ ������ �� ����� ���� null.");
		}
		if (channelType == null)
		{
			throw new IllegalArgumentException("��� ������ �� ����� ���� null.");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LimitDocumentInfo>>()
			{
				public List<LimitDocumentInfo> run(Session session) throws Exception
				{
					DetachedCriteria criteria = DetachedCriteria.forClass(LimitDocumentInfo.class);
					criteria.add(Expression.eq("profileId", person.getId()));
					criteria.add(Expression.ge("operationDate", fromDate));
					if (channelType != ChannelType.ALL)
					{
						criteria.add(Expression.eq("channelType", channelType));
					}

					//noinspection unchecked
					return criteria.getExecutableCriteria(session).list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �� ������� �� ��������� ��������� �����
	 * @param documentExternalId ������� ������������� ���������
	 * @param operationDate ���� ��������
	 * @return ������ ���������� �� ����������
	 */
	public LimitDocumentInfo findLimitDocumentInfo(final String documentExternalId, final Calendar operationDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LimitDocumentInfo>()
			{
				public LimitDocumentInfo run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(LimitDocumentInfo.class);
					criteria.add(Expression.eq("documentExternalId", documentExternalId));
					criteria.add(Expression.eq("operationDate", operationDate));
					criteria.setMaxResults(1);

					//noinspection unchecked
					return (LimitDocumentInfo) criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void addLimitDocumentInfos(final List<LimitDocumentInfo> infos) throws BusinessException
	{
		simpleService.addList(infos);
	}

	/**
	 * �������� ����� ����� �������� ���������� �� ����� (����� ����� ��������)
	 * @param limit �����
	 * @param document ��������
	 * @param fromDate ���� ������
	 * @return �����
	 * @throws BusinessException
	 */
	public Money getExternalCardProviderAccumulatedAmount(final Limit limit, final BusinessDocument document, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Money>()
			{
				public Money run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LimitDocumentInfo.class)
							.add(Expression.eq("externalCard", LimitHelper.getExternalCardProviderValue(document)))
							.add(Expression.eq("channelType", limit.getChannelType()))
							.add(Expression.ge("operationDate", fromDate));

					Criteria criteria = detachedCriteria.getExecutableCriteria(session)
							.setProjection(Projections.sum("amount.decimal"));

					BigDecimal value = (BigDecimal) criteria.uniqueResult();
					return new Money(value != null ? value : BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ����� ����� �������� ���������� �� ����� (������� �����)
	 * @param limit �����
	 * @param document ��������
	 * @param fromDate ���� ������
	 * @return �����
	 * @throws BusinessException
	 */
	public Money getExternalPhoneProviderAccumulatedAmount(final Limit limit, final BusinessDocument document, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Money>()
			{
				public Money run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LimitDocumentInfo.class)
							.add(Expression.eq("externalPhone", PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(LimitHelper.getExternalPhoneProviderValue(document))))
							.add(Expression.eq("channelType", limit.getChannelType()))
							.add(Expression.ge("operationDate", fromDate));

					Criteria criteria = detachedCriteria.getExecutableCriteria(session)
							.setProjection(Projections.sum("amount.decimal"));

					BigDecimal value = (BigDecimal) criteria.uniqueResult();
					return new Money(value != null ? value : BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���� ��������� � �������� ��������� ������
	 * @param limit ����� ����� � ������� ������
	 * @param instance �������
	 * @throws BusinessException
	 */
	public void updateEndDate(final Limit limit, final String instance) throws BusinessException
	{
		if (limit == null)
		{
			throw new IllegalArgumentException("����� �� ����� ���� null.");
		}
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					List<Limit> activeLimits = getActiveLimit(limit.getTb(), limit.getType(), limit.getRestrictionType(), limit.getChannelType(), limit.getGroupRisk(), limit.getSecurityType(), instance);

					for (Limit activeLimit: activeLimits)
					{
						activeLimit.setEndDate(limit.getStartDate());
						simpleService.addOrUpdate(activeLimit, instance);
						dictionaryRecordChangeInfoService.addChangesToLog(activeLimit, ChangeType.update);
					}

					logErrorActiveLimits(activeLimits);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void logErrorActiveLimits(List<Limit> activeLimits)
	{
		if (activeLimits.size() > 1)
		{
			StringBuilder errorBuilder = new StringBuilder();
			Iterator<Limit> iterator = activeLimits.iterator();

			while (iterator.hasNext())
			{
				Limit errorLimit = iterator.next();
				errorBuilder.append(errorLimit.getId());

				if (iterator.hasNext())
				{
					errorBuilder.append(",");
				}
			}

			log.error("���������� ��������� �������� ������� � ����������������: " + errorBuilder.toString());
		}
	}

	/**
	 * �������� ������ �������� �������, �� ���� ������ ���� ������ ���� �������� �����
	 * �� ���� �������� ������ ��� ������� ��, �� ���������� �� ������, ������ ����� ����������
	 * @param tb �������
	 * @param limitType ��� ������
	 * @param channelType ��� ������
	 * @param groupRisk ������ �����
	 * @param securityType ������� ������������
	 * @param instance �������
	 * @return �������� �����
	 * @throws BusinessException
	 */
	public List<Limit> getActiveLimit(final String tb, final LimitType limitType, final RestrictionType restrictionType, final ChannelType channelType, final GroupRisk groupRisk, final SecurityType securityType, String instance) throws BusinessException
	{
		if (StringHelper.isEmpty(tb))
		{
			throw new IllegalArgumentException("������� �� ����� ���� null.");
		}
		if (limitType == null)
		{
			throw new IllegalArgumentException("��� ������ �� ����� ���� null.");
		}
		if (restrictionType == null)
		{
			throw new IllegalArgumentException("��� ����������� �� ����� ���� null.");
		}
		if (channelType == null)
		{
			throw new IllegalArgumentException("��� ������ �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<Limit>>()
			{
				public List<Limit> run(Session session) throws Exception
				{
					return (List<Limit>) session.getNamedQuery("com.rssl.phizic.business.limits.Limit.getActiveLimit")
							.setParameter("tb", tb)
							.setParameter("limit_type", limitType.name())
							.setParameter("restriction_type", restrictionType.name())
							.setParameter("channel_type", channelType.name())
							.setParameter("group_risk_id", groupRisk == null ? -1 : groupRisk.getId())
							.setParameter("security_type", securityType == null ? "NULL" : securityType.name())
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}