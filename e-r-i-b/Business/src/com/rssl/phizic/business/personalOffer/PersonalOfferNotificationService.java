package com.rssl.phizic.business.personalOffer;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferNotificationService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ��� ���������� ���������� � �������������� �����������.

	 * @return ����������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public PersonalOfferNotification addOrUpdate(PersonalOfferNotification offerNotification) throws BusinessException
	{
		return simpleService.addOrUpdate(offerNotification);
	}

	/**
	 * �������� ����������.
	 * @param offerNotification ����������
	 * @throws BusinessException
	 */
	public void remove(PersonalOfferNotification offerNotification) throws BusinessException
	{
		offerNotification.setState(PersonalOfferState.DELETED);
		simpleService.update(offerNotification);
	}

	/**
	 * ����� ���������� �� ��������������.
	 * @param id ����������
	 * @return ������
	 * @throws BusinessException
	 */
	public PersonalOfferNotification findById(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonalOfferNotification.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.ne("state", PersonalOfferState.DELETED)));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ����� ������ � ��������� ����������� �����������
	 * @param login  - ����� �������
	 * @param notification  - �����������
	 * @return ������ � ��������� �����������
	 * @throws BusinessException
	 */
	public NotificationDateDisplay findDateByLoginId(Login login, PersonalOfferNotification notification) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificationDateDisplay.class);
		criteria.add(Expression.and(Expression.eq("login", login), Expression.eq("notification", notification)));
		return simpleService.findSingle(criteria);
	}

	public void updateNotificationDateDisplay(Login login, PersonalOfferNotification notification) throws BusinessException
	{
		NotificationDateDisplay dateDisplay = new NotificationDateDisplay();
		dateDisplay.setLogin(login);
		dateDisplay.setNotification(notification);
		dateDisplay.setDisplayDate(DateHelper.getCurrentDate());
		simpleService.addOrUpdate(dateDisplay);
	}

	/**
	 * ����� ���������� �� ���������������.
	 * @param ids ��������������
	 * @return ����������
	 * @throws BusinessException
	 */
	public List<PersonalOfferNotification> findByIds(List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(PersonalOfferNotification.class, ids);
	}


	public List<PersonalOfferNotification> getNotificationList(final Calendar date, final String tb) throws BusinessException
{
	try
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<PersonalOfferNotification>>()
		{
			public List<PersonalOfferNotification> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.personalOffer.PersonalOfferNotificationService.getNotificationList");
				query.setParameter("currentDate", date);
				query.setParameter("TB", tb);
				return query.list();
			}
		});
	}
	catch (Exception e)
	{
		throw new BusinessException(e);
	}
}
}

