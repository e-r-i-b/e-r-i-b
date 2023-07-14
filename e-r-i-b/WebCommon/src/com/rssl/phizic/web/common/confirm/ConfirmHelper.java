package com.rssl.phizic.web.common.confirm;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.operations.access.PaymentConfirmStrategySource;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Хелпер для подтверждений
 * @author Rydvanskiy
 * @ created 25.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmHelper
{
	private static final PaymentConfirmStrategySource source = new PaymentConfirmStrategySource();
	private static final List<ConfirmStrategy> confirmStrategies = new ArrayList<ConfirmStrategy>();
	private static final List<State> documentStates = new ArrayList<State>();
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	static
	{
        confirmStrategies.add(new SmsPasswordConfirmStrategy());
        confirmStrategies.add(new PasswordCardConfirmStrategy());
        confirmStrategies.add(new CompositeConfirmStrategy());
		documentStates.add(new State("INITIAL"));
		documentStates.add(new State("SAVED"));
		documentStates.add(new State("DRAFT"));
		documentStates.add(new State("INITIAL_LONG_OFFER"));
	}

	// Наименование параметра в PreConfirmObject ответственный
	// за хранение информации о времени жизни отправленного пароля 
	public static final String SMS_LIFE_TIME_KEY = "smsPasswordLifeTime";

	/**
	 * В зависимости от объекта preConfirmObject возращаемого в результате preConfirm строится ActionMessages
	 * который в свою очередь можно использовать для сохранения либо ощибок либо сообщений
	 * @param preConfirmObject
	 * @return ActionMessages или null если сообщений нет
	 */
	public static ActionMessages getPreConfirmMsg (PreConfirmObject preConfirmObject)
	{
		String preConfirmMsg = getPreConfirmString(preConfirmObject);
		ActionMessages msgs  = new ActionMessages();
		if(StringHelper.isEmpty(preConfirmMsg))
			return msgs;

		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( preConfirmMsg, false));
		return msgs;
	}

	/**
	 * @param preConfirmObject 
	 * @return сообщение для пользователя на основе preConfirmObject
	 */
	public static String getPreConfirmString (PreConfirmObject preConfirmObject)
	{
		if (preConfirmObject == null) return null;
		Long passwordLifeTime = (Long)preConfirmObject.getPreConfirmParam(SMS_LIFE_TIME_KEY);
		if ( passwordLifeTime == null ) return null;

		StringBuilder result = new StringBuilder();
		result.append("Вам отправлен пароль для подтверждения операции. Время действия пароля ").append(passwordLifeTime).append(" сек.");
		return result.toString();
	}

	/**
	 * @param passwordsLeft количество оставшихся паролей с чека
	 * @return сообщение для стратегии подтверждения карточными паролями
	 */
	public static List<String> getPasswordCardConfirmStrategyAdditionalInfo(String passwordsLeft)
	{
		List<String> additionInfo = new ArrayList<String>();
		additionInfo.add("Обратите внимание! На Вашем чеке осталось " + passwordsLeft + " одноразовых паролей.");
		additionInfo.add("Получить новый чек с паролями Вы можете в устройствах самообслуживания (банкоматах, терминалах), используя банковскую карту Сбербанка России.");
		return additionInfo;
	}

	/**
	 * @return сообщение для стратегии подтверждения cap-паролями
	 */
	public static String getPreConfirmCapString()
	{
		return "Нажмите кнопку на карте для генерации пароля.";
	}

	/**
	 * @return сообщение для пользователя на основе preConfirmObject
	 */
	public static String getPreConfirmOZONString()
	{
		return "Обратите внимание: после подтверждения операции Вы не сможете отозвать данный платеж.";
	}

	/**
	 * Сохранить ошибки в ConfirmRequest
	 * @param confirmRequest
	 * @param errors ошибки
	 */
	public static void saveConfirmErrors(ConfirmRequest confirmRequest, List<String> errors)
	{
		StringBuilder sb = new StringBuilder();
		if (!StringHelper.isEmpty(confirmRequest.getErrorMessage()))
			sb.append(confirmRequest.getErrorMessage()).append("<br/>");
		for(String error: errors)
			sb.append(error);
		confirmRequest.setErrorMessage(sb.toString());
	}

	public static void clearConfirmErrors(ConfirmRequest confirmRequest)
	{
		confirmRequest.setErrorMessage("");
	}
	/**
	 * Получить тип стратегии, по кот. был подтвержден документ
	 * @param document - документ, для которого получаем стратегию подтверждения
	 * @return - название стратегии
	 */
	public static ConfirmStrategyType getConfirmStrategyType(BusinessDocument document) throws Throwable
	{
		if (document == null)
			return null;

		if (documentStates.contains(document.getState()))
			return getNeedOrNoneConfirmStrategy(document);

		return document.getConfirmStrategyType();
	}

	private static ConfirmStrategyType getNeedOrNoneConfirmStrategy(BusinessDocument document)
	{
		for (ConfirmStrategy strategy : confirmStrategies)
		{
			ConfirmStrategy confirmStrategy = source.getStrategy(document, strategy, strategy.getType().toString());
			if (confirmStrategy != null && !(confirmStrategy instanceof NotConfirmStrategy))
				return ConfirmStrategyType.need;
		}

		return ConfirmStrategyType.none;
	}

	/**
	 * Получение userId из мобильного банка по id карты(линка) которую
	 * выбрал клиент в качестве карты для подтверждения операции по чеку
	 * или из cardLink если ранее он уже был получен.
	 * Если userid получен через МБ то это значение записывается в cardlink.
	 * @param id-id кардлинка
	 * @param login Логин клиента.Если PersonContext еще не проинициализирован(подтверждение входа клиента) то параметр обязателен.
	 * @param useStoredValue - признак того, нужно ли использовать при поиске данные сохраненные в базе(cardLink).
	 * @return first - UserIdClientTypes(МБ или из кардлинка(DB)), second - userId
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static Pair<UserIdClientTypes, String> getUserIdByConfirmCard(Long id, Login login, boolean useStoredValue) throws BusinessLogicException, BusinessException
	{
		CardLink link = resourceService.findLinkById(CardLink.class, id);
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		if (link == null)
		{
			try
			{
				UserInfo info = mobileBankService.getClientByLogin(login.getCsaUserId());
				if (info == null)
				{
					throw new BusinessLogicException("По выбранной карте не удалось получить информацию о владельце. Пожалуйста повторите попытку позднее.");
				}

				return new Pair<UserIdClientTypes, String>(UserIdClientTypes.MB, info.getUserId());
			}
			catch (BusinessLogicException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessLogicException("По выбранной карте не удалось получить информацию о владельце. Пожалуйста повторите попытку позднее.", e);
			}

		}
		else
		{
			//проверяем принадлежит ли данная карта этому клиенту
			//если линк не того клиента в контексте которго запрашиваем - падаем с описанием ошибки.
			if (!link.getLoginId().equals(login.getId()))
			{
				throw new BusinessLogicException("Данная карта не принадлежит вам.");
			}

			if (useStoredValue && StringHelper.isNotEmpty(link.getUserId()))
			{
				return new Pair<UserIdClientTypes,String>(UserIdClientTypes.DB, link.getUserId());
			}

			try
			{
				UserInfo info = mobileBankService.getClientByCardNumber(link.getNumber());
				if (info == null)
				{
					throw new BusinessLogicException("По выбранной карте не удалось получить информацию о владельце. Пожалуйста повторите попытку позднее.");
				}

				link.setUserId(info.getUserId());
				resourceService.addOrUpdateLink(link);

				return new Pair<UserIdClientTypes, String>(UserIdClientTypes.MB ,info.getUserId());
			}
			catch (BusinessLogicException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessLogicException("По выбранной карте не удалось получить информацию о владельце. Пожалуйста повторите попытку позднее.", e);
			}
		}
	}

	/**
	 * @param strategy - стратегия
	 * @param strategyType - тип стратегии
	 * @return - Поддерживается ли тип стратегии в данной стратегии
	 */
	public static boolean strategySupported(ConfirmStrategy strategy, ConfirmStrategyType strategyType)
	{
		if (strategy == null)
			return false;
		
		if (strategy instanceof CompositeConfirmStrategy)
		{
			for (ConfirmStrategyType type : ((CompositeConfirmStrategy)strategy).getStrategies().keySet())
				if (type == strategyType)
					return true;

			return false;
		}

		return strategy.getType() == strategyType;
	}
}
