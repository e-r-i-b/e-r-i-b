package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверяет, нужно ли запускать процедуру подключения услуги "Мобильный Банк"
 */
public class RegisterMobileBankStageVerifier implements StageVerifier
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	///////////////////////////////////////////////////////////////////////////

	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		if (!(context.getLogin() instanceof Login))
			throw new ConfigurationException("RegisterMobileBankStageVerifier можно использовать только в контексте клиента");

		Login login = (Login) context.getLogin();

		return !isMobileBankRegisteredAlready(login);
	}

	private boolean isMobileBankRegisteredAlready(Login login) throws SecurityException
	{
		try
		{
			//1. Проверяем, подключен ли клиент к ЕРМБ
			if (ErmbHelper.hasErmbProfileByLogin(login.getId()))
				return true;

			// 2. Получаем список карт клиента
			String[] cards = getClientCardNumbers(login);

			// 3. Получаем список подключений к МБ
			boolean firstLogin = login.getLastLogonDate() == null;
			List<MobileBankRegistration> registrations = mobileBankService.loadRegistrations(cards, firstLogin);

			// 4. Смотрим, есть ли подключения к МБ
			if (CollectionUtils.isEmpty(registrations))
				return false;

			// 5. Смотрим, есть ли среди карт клиента карта, которая подключена как информационная
			Set<String> cardsSet = new HashSet<String>(Arrays.asList(cards));

			if (firstLogin)
			{
				for (MobileBankRegistration registration : registrations)
				{
					cardsSet.add(registration.getMainCardInfo().getCardNumber());
				}
			}

			for (MobileBankRegistration registration : registrations)
			{
				List<MobileBankCardInfo> cardsInfo = registration.getLinkedCards();
				if (CollectionUtils.isEmpty(cardsInfo))
					continue;

				for (MobileBankCardInfo cardInfo : cardsInfo)
				{
					if (cardsSet.contains(cardInfo.getCardNumber()))
					{
						return true;
					}
				}
			}
			return false;
		}
		catch (Exception ignore)
		{
			//если МБ не доступен, то не показываем
			//форму с оформлением заявки
			return true;
		}
	}

	private String[] getClientCardNumbers(CommonLogin login) throws SecurityException
	{
		try
		{
			List<CardLink> cardLinks = resourceService.getLinks(login, CardLink.class);
			String logonCardNumber = login.getLastLogonCardNumber();

			Set<String> cardNumbers = new LinkedHashSet<String>(1+cardLinks.size());
			if (StringHelper.isNotEmpty(logonCardNumber))
				cardNumbers.add(logonCardNumber);
			for (CardLink cardLink : cardLinks)
				cardNumbers.add(cardLink.getNumber());
			
			return cardNumbers.toArray(new String[cardNumbers.size()]);
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new SecurityException(e);
		}
	}
}
