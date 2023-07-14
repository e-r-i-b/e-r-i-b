package com.rssl.phizic.business.documents.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author vagin
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с заявками на копилку.
 */
public class MoneyBoxService
{
	/**
	 * Поиск заявок на создание Копилки в статусе "SAVED" для указанного логина по заданному номеру карты
	 * @param cardNumber номер карты с которой бдет спсание для копилки
	 * @param loginId - loginID клиента.
	 * @return списко заявок
	 */
	public List<AutoSubscription> findSavedClaimByLoginAndCard(final String cardNumber,final Long loginId) throws BusinessException
	{
		return findSavedClaimsBase(loginId, null, cardNumber);
	}

	/**
	 * Поиск заявок на создание Копилки в статусе "SAVED" для указанного логина по заданному номеру счета
	 * @param accountNumber номер счета на который будет зачисление для копилки
	 * @param loginId - loginID клиента.
	 * @return список заявок
	 */
	public List<AutoSubscription> findSavedClaimByLoginAndAccount(final String accountNumber,final Long loginId) throws BusinessException
	{
		return findSavedClaimsBase(loginId, accountNumber, null);
	}

	/**
	 * Поиск заявок на создание Копилки в статусе "SAVED" для указанного логина по заданному номеру карты И заданному номеру счета
	 * @param accountNumber номер счета на который будет зачисление для копилки
	 * @param cardNumber - номер карты
	 * @param loginId - loginID клиента.
	 * @return список заявок
	 */
	public List<AutoSubscription> findSavedClaimByLoginCardAndAccount(final String accountNumber, final String cardNumber, final Long loginId) throws BusinessException
	{
		return findSavedClaimsBase(loginId, accountNumber, cardNumber);
	}

	/**
	 * получить список всех копилок клиента, ожидающих подтверждения
	 * @param loginId логин клиента
	 * @return список неподтвержденнных копилок
	 * @throws BusinessException
	 */
	public List<AutoSubscription> findSavedClaimByLogin(final Long loginId) throws BusinessException
	{
		return findSavedClaimsBase(loginId, null, null);
	}

	/**
	 * Преобразование документов CreateMoneyBoxPayment к виду AutoSubscriptionLink
	 * @param claims - заявки на создание копилки(статус "Черновик")
	 * @return - обертка AutoSubscriptionLink для бизнес документа.
	 */
	public static List<AutoSubscriptionLink> adaptToLinks(List<AutoSubscription> claims)
	{
		List<AutoSubscriptionLink> result = new ArrayList<AutoSubscriptionLink>(claims.size());
		for (AutoSubscription claim : claims)
		{
			result.add(new AutoSubscriptionLink(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId(), claim));
		}
		return result;
	}

	private List<AutoSubscription> findSavedClaimsBase(final Long loginId, final String accountNumber, final String cardNumber) throws BusinessException
	{
		final Calendar fromDate = getFromDate();

		if (fromDate == null)
		{
			return Collections.emptyList();
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AutoSubscription>>()
			{
				public List<AutoSubscription> run(Session session) throws Exception
				{
					return (List<AutoSubscription>) session.getNamedQuery("com.rssl.phizic.business.documents.moneyBox.MoneyBoxService.findSavedClaimsBase")
							.setParameter("login_id", loginId)
							.setParameter("accountNumber", accountNumber)
							.setParameter("cardNumber", cardNumber)
							.setParameter("from_date", fromDate)
							.setBoolean("forEmployee", ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private Calendar getFromDate()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		String selectPeriodString = config.getProperty("com.rssl.iccs.moneybox.not.finished.claim.forSelect.period");

		//если не надо запрашивать недозаполненые заявки
		if (StringHelper.isEmpty(selectPeriodString) || selectPeriodString.equals("0"))
		{
			return null;
		}

		return DateHelper.addDays(Calendar.getInstance(), -Integer.valueOf(selectPeriodString));
	}
}
