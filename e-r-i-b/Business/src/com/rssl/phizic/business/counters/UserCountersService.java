package com.rssl.phizic.business.counters;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.ermb.RequestCardByPhoneLogEntryHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * ������ ��� ������ �� ���������� ������������
 * @author niculichev
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserCountersService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String QUERY_PREFIX = UserCounter.class.getName() + ".";
	private static final BusinessPropertyService businessPropertyService = new BusinessPropertyService();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ����������� ������� ������������ ��� �������� ���������� � ���������� �� ������ ��������
	 * @param login �����
	 * @param type ��� ��������
	 * @param blockTimeInSeconds ����� ��������� ���������� ��� ���������� ������
	 * @return true - ����������� ��� ���������� ������, false - ����������� � ����������� ������
	 * @throws BusinessException
	 */
	public boolean increment(final Login login, final CounterType type, final Integer blockTimeInSeconds) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					String countLimitStr = ConfigFactory.getConfig(ClientConfig.class).getProperty(type.toValue());

					// ���� �� ����� �� � �� ���������
					if(countLimitStr.length() == 0)
					{
						log.warn("�� ����� ����� �� ������ ���������� � ������� ��� �������� �������� ���� �� ����� �� ������ �������� � ������ ��������� > ����������� �� ��������");
						return true;
					}
					long countLimit = Long.parseLong(countLimitStr);
					// ���� �������
					UserCounter counter = findByLoginId(login.getId(), type, true);
					// ���� ��� ������� �����
					if(counter == null)
					{
						counter = new UserCounter();
						counter.setLoginId(login.getId());
						counter.setValue(0L);
						counter.setCounterType(type);
					}
					// ���� �� ����������, ������� � ������
					else if(!DateHelper.getCurrentDate().equals(DateHelper.clearTime(DateHelper.toCalendar(counter.getChangeDate()))))
					{
						counter.reset();
					}

					// ����������� �� 1
					counter.increment();
					if (blockTimeInSeconds != null && countLimit < counter.getValue())
					{
						counter.reset();
						Calendar blockUntil = DateHelper.addSeconds(Calendar.getInstance(), blockTimeInSeconds);
						counter.setBlockUntil(blockUntil);
						PersonContext.getPersonDataProvider().getPersonData().setPromoBlockUntil(blockUntil);
					}
					// ��������� � ����
					simpleService.addOrUpdate(counter);
					//���� ����� ��������� ��� �������� ��������� ������ � ���
					if (countLimit <= counter.getValue() && type == CounterType.RECEIVE_INFO_BY_PHONE)
						writeToLog();
					return counter.getValue() <= countLimit;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� ��� �������
	 * @param loginId - id ������ �������
	 * @param type - ��� ��������
	 * @return �������
	 */
	public UserCounter findByLoginId(final Long loginId, final CounterType type) throws BusinessException
	{
		return findByLoginId(loginId, type, false);
	}

	private UserCounter findByLoginId(final Long loginId, final CounterType type, final boolean withLock) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<UserCounter>()
			{
				public UserCounter run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getByLoginId");
	                query.setParameter("loginId", loginId);
	                query.setParameter("counterType", type);
					if (withLock)
		                query.setLockMode("counter", LockMode.UPGRADE);

	                return (UserCounter) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void writeToLog()
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		List<PersonDocument> documents = new ArrayList<PersonDocument>(person.getPersonDocuments());
		Collections.sort(documents, new PersonDocumentTypeComparator());
		PersonDocument document = documents.get(0);
		RequestCardByPhoneLogEntryHelper.addLogEntry(person.getLogin().getId(), person.getFIO(), document.getDocumentType().toString(), StringHelper.getEmptyIfNull(document.getDocumentSeries()) +" "+ StringHelper.getEmptyIfNull(document.getDocumentNumber()),  person.getBirthDay());
	}

	/**
	 * ����� ���������� ��������
	 * @param login �����
	 * @param type ��� ��������
	 * @throws BusinessException
	 */
	public void reset(final Login login, final CounterType type) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UserCounter counter = findByLoginId(login.getId(), type, true);
					if (counter != null)
					{
						counter.reset();
						// ��������� � ����
						simpleService.addOrUpdate(counter);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
