package com.rssl.ikfl.crediting;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.business.security.ERIBCSAPrincipalPermissionCalculator;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.permissions.ServicePermission;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;

/**
 * @author Nady
 * @ created 24.04.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * получение предложений по задаче "Кредитование УКО"
 * Задачи:
 * + Отправка запроса за предодобренными предложениями по кредитам / кредитным картам [в CRM].
 */
public class GetPersonOffers
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final OfferStorage offerStorage = new OfferStorage();

	private final CRMMessageBuilder messageBuilder = new CRMMessageBuilder();

	private final CRMMessageSender messageSender = new CRMMessageSender();

	private final CRMMessageLogger messageLogger = new CRMMessageLogger();

	private final DepartmentService departmentService = new DepartmentService();

	///////////////////////////////////////////////////////////////////////////

	public void execute()
	{
		try
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			if (permissionOffers(person))
				if ((PersonHelper.isGuest() && PersonContext.getPersonDataProvider().getPersonData().getGuestType() == GuestType.REGISTERED)
					|| !PersonHelper.isGuest())
				// 2. Делаем запрос в CRM за предодобренными предложениями
				requestPersonOffers(person);
		}
		catch (Exception e)
		{
			log.error("Сбой на получении предодобренных предложений из CRM в онлайн-режиме", e);
		}
	}

	private boolean permissionOffers(ActivePerson person) throws BusinessException
	{
		// 1. Проверяем наличие права «Получение персональных/предодобренных предложений из CRM в онлайн-режиме».
		// Если права нет, делать ничего не надо
		AuthModule authModule = AuthModule.getAuthModule();
		Application currentApplication = ApplicationInfo.getCurrentApplication();

		if (currentApplication == Application.PhizIA)
		{
			AuthenticationConfig authConfig = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
			AccessPolicy policy = authConfig.getPolicy(AccessType.simple);

			PrincipalImpl principal = new PrincipalImpl(person.getLogin(), policy, null);
			ERIBCSAPrincipalPermissionCalculator permissionCalculator = new ERIBCSAPrincipalPermissionCalculator(principal);
			return permissionCalculator.impliesService("GetPreapprovedOffersService", false);
		}
		else
			return authModule.implies(new ServicePermission("GetPreapprovedOffersService", false));
	}

	void requestPersonOffers(ActivePerson clientPerson) throws Exception
	{
		// 1. Проверяем в таблице входов наличие записи с текущей календарной датой.
		// Если такая запись есть, делать ничего не надо
		String tb = getOfferTb(clientPerson);
		OfferLogin offerLogin = offerStorage.loadOfferLogin(clientPerson, tb);
		if (offerLogin != null && DateUtils.isSameDay(offerLogin.lastRqTime, Calendar.getInstance()))
			return;

		// 2. Удаляем устаревшие предложения клиента
		offerStorage.deleteClientOffers(clientPerson);

		// 3. Формируем запрос к CRM
		CRMMessage crmMessage = messageBuilder.makeOfferRequestMessage(clientPerson);
		messageSender.sendMessageToCRMForOffers(crmMessage);

		// 4. Запоминаем запрос к CRM в Журнале Сообщений
		messageLogger.logMessage(crmMessage);

		// 5. Обновляем запись в таблице входов
		if (offerLogin == null)
			offerLogin = new OfferLogin();
		offerLogin.surName = clientPerson.getSurName();
		offerLogin.firstName = clientPerson.getFirstName();
		offerLogin.patrName = clientPerson.getPatrName();
		offerLogin.birthDay = clientPerson.getBirthDay();
		PersonDocument document = clientPerson.getPersonDocuments().iterator().next();
		offerLogin.docSeries = document.getDocumentSeries();
		offerLogin.docNumber = document.getDocumentNumber();
		offerLogin.tb = tb;
		offerLogin.lastRqUID = crmMessage.getRequestUID();
		offerLogin.lastRqTime = crmMessage.getRequestTime();
		offerLogin.lastRqStatus = OfferRequestStatus.IN_PROGRESS;
		offerStorage.saveOfferLogin(offerLogin);
	}

	private String getOfferTb(ActivePerson person) throws BusinessException
	{
		if (!PersonHelper.isGuest())
		{
			return departmentService.getNumberTB(person.getDepartmentId());
		}
		else
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			return loanClaimConfig.getGuestLoanDepartmentTb();
		}
	}
}
