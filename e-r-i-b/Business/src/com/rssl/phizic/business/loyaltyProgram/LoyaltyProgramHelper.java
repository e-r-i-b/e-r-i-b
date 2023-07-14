package com.rssl.phizic.business.loyaltyProgram;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramHelper
{
	private  static final SimpleService simpleService  = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Обновляет инфомацию о программе лояльности для клиента
	 * @throws BusinessException
	 */
	public static void updateLoyaltyProgram() throws BusinessException, BusinessLogicException
	{
	    LoyaltyProgramService service = GateSingleton.getFactory().service(LoyaltyProgramService.class);
		PersonData personData  =  PersonContext.getPersonDataProvider().getPersonData();
	    Login login = personData.getPerson().getLogin();
		LoyaltyProgramLink link = personData.getLoyaltyProgram();
		if (link == null)
		{
			link = new LoyaltyProgramLink();
			link.setLoginId(login.getId());
			link.setShowInMain(true);
		}

		String cardNumber = "";
		if (ApplicationUtil.isErmbSms())
		{
			CardLink cardLink = CardsUtil.getLongTermCard(personData.getCards());
			if (cardLink != null)
				cardNumber = cardLink.getNumber();
		}
		else
		{
			cardNumber = login.getLastLogonCardNumber();
		}

		if (!cardNumber.equals(""))
		{
			try
			{
				if (link.getExternalId() != null)
					link.reset();
				LoyaltyProgram program = service.getClientLoyaltyProgram(cardNumber);
				if (program != null)
				{
					//если получили пограммму лояльности, обновляем или добавляем линк на нее
					link.setExternalId(program.getExternalId());
					link.setState(LoyaltyProgramState.ACTIVE);
					link.setBalance(program.getBalance());
					link.setUpdateTime(Calendar.getInstance());
				}
				else
				{
					//ставим статус незарегистрирован
					link.setExternalId(null);
					link.setBalance(null);
					link.setUpdateTime(null);
					link.setState(LoyaltyProgramState.UNREGISTERED);
				}
			}
			catch (GateException ex)
			{
				log.error("Не удалось получить информацию о программе лояльности ", ex);
				throw new BusinessException(ex) ;
			}
			catch (GateLogicException e)
			{
				log.error("Не удалось получить информацию о программе лояльности ", e);
				throw new BusinessLogicException(e);
			}
		}
		else //не получили карту входа, ставим статус INACTIVE
		{
			link.setState(LoyaltyProgramState.INACTIVE);
			log.error("Ошибка поиска получения номера карты последнего входа.");
		}

		simpleService.addOrUpdate(link);
	}

	/**
	 * @return линк программы лояльности для текущего пользователя
	 */
	public static LoyaltyProgramLink getLoyaltyProgramLink()
	{
		if (!PersonContext.isAvailable())
			return null;

		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			return data.getLoyaltyProgram();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при получении программы лояльности для пользователя loginId=" + data.getPerson().getLogin().getId(), e);
			return null;
		}
	}

}
