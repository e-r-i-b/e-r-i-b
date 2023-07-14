package com.rssl.phizic.operations.offerNotification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.personalOffer.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperationBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author lukina
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class ViewOfferNotificationOperation extends GetLoanOfferViewOperationBase
{
	private static final PersonalOfferNotificationService notificationService = new PersonalOfferNotificationService();
	private static final DepartmentService departmentService = new DepartmentService();

	private PersonalOfferNotification offerNotification;
	private List<PersonalOfferNotification> notificationsList;
	private ActivePerson person;
	private LoanOffer loanOffer;
	private LoanCardOffer loanCardOffer;

	/**
	 * Инициализация операции для основного приложения
	 * @param currentId
	 */
	public void initialize(Long currentId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		person = personData.getPerson();

		List<Long> shownNotificationList = personData.getShownNotificationList();
		if (CollectionUtils.isEmpty(notificationsList))
		{
			Calendar date = DateHelper.getCurrentDate();
			String tb = departmentService.getNumberTB( person.getDepartmentId());
			notificationsList = notificationService.getNotificationList(date, tb);
			if (CollectionUtils.isEmpty(notificationsList) || !PersonHelper.hasRegularPassportRF())
				return;

			applyRequirements(notificationsList);
			personData.setOfferNotificationList(notificationsList);
		}

		if (currentId != 0)
			offerNotification = notificationService.findById(currentId);
		else if (CollectionUtils.isNotEmpty(notificationsList)){
			offerNotification = notificationsList.get(0);
			shownNotificationList.add(offerNotification.getId());
		}
		if(offerNotification != null)
			prepareOfferNotification(offerNotification);

	}

	/**
	 * Получить уведомление для отображения на странице
	 * @return уведомление
	 */
	public PersonalOfferNotification getOfferNotification()
	{
		return offerNotification;
	}

	/**
	 * Получить список id уведомлений в порядке отображения
	 * @return список id уведомлений
	 */
	public List<PersonalOfferNotification> getNotificationsList()
	{
		return notificationsList;
	}

    /**
	 * Сортирует области уведомления и кнопки согласно их порядку отображения
	 * @param offerNotification уведомление
	 */
	private void prepareOfferNotification(PersonalOfferNotification offerNotification)
	{
		PersonalOfferOrderedFieldComparator comparator = new PersonalOfferOrderedFieldComparator();
		Collections.sort(offerNotification.getButtons(), comparator);
		Collections.sort(offerNotification.getAreas(), comparator);
	}

	/**
	 * Удаляет из списка уведомления, несоответствующие требованиям
	 * @param notificationList список id уведомлений
	 */
	private void applyRequirements(List<PersonalOfferNotification> notificationList) throws BusinessException, BusinessLogicException
	{
		List<PersonalOfferNotification> deleteList = new ArrayList<PersonalOfferNotification>();
		loanOffer = personData.getLoanOfferForMainPage();
		loanOffers = personData.getLoanOfferByPersonData(1, null);
		LoanCardOffer loanCardOfferForMainPage = personData.getLoanCardOfferForMainPage();

		if (personData.isLoanCardClaimAvailable() || loanCardOfferForMainPage.getOfferType() != LoanCardOfferType.newCard)
		{
			loanCardOffer = loanCardOfferForMainPage;
			loanCardOffers = personData.getLoanCardOfferByPersonData(1, null);
		}
		else
		{
			loanCardOffer = null;
			loanCardOffers = Collections.emptyList();
		}

		for (PersonalOfferNotification notification : notificationList)
		{
			if (notification.getProductType() == PersonalOfferProduct.LOAN)
			{
				if (CollectionUtils.isNotEmpty(loanOffers) && checkNotification(notification))
				{
					notificationService.updateNotificationDateDisplay(person.getLogin(), notification);
					setAllLoanOfferLikeViewed();
				}
				else
					deleteList.add(notification);
			}
			else
			{
				if (CollectionUtils.isNotEmpty(loanCardOffers) && checkNotification(notification))
				{
					notificationService.updateNotificationDateDisplay(person.getLogin(), notification);
					setAllLoanCardOfferLikeViewed();
				}
				else
					deleteList.add(notification);
			}
		}
		for (PersonalOfferNotification notification : deleteList)
			notificationList.remove(notification);
	}


	private boolean checkNotification(PersonalOfferNotification notification)
	{
		try
		{
			NotificationDateDisplay dateDisplay = notificationService.findDateByLoginId(person.getLogin(), notification);
			if (notification.getDisplayFrequency() == PersonalOfferDisplayFrequency.ONE && dateDisplay != null)
				return false;
			if (notification.getDisplayFrequency() == PersonalOfferDisplayFrequency.PERIOD)
			{
				if (dateDisplay != null)
				{
					if (DateHelper.daysDiff(dateDisplay.getDisplayDate(), DateHelper.getCurrentDate()) < notification.getDisplayFrequencyDay())
						return false;
				}
			}
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения даты последнего отображения уведомления ", e);
		}
		return true;
	}

	public LoanOffer getLoanOffer() throws BusinessException
	{
		return loanOffer;
	}

	public LoanCardOffer getLoanCardOffer() throws BusinessException
	{
		return loanCardOffer;
	}
}
