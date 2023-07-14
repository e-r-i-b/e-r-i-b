package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.Map;

/**
 * @author Erkin
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmMobileBankRegistrationOperation extends ConfirmableOperationBase
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private RegistrationHelper helper;

	private MobileBankRegistrationClaim claim;

	private String maskedCard = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализировать операцию для использования на этапе аутентификации
	 * @param claimId - ID заявки
	 * @param authContext - контекст аутентификации
	 */
	public void initializeForLogin(Long claimId, AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init(claimId);
	}

	/**
	 * Инициализировать операцию для использования после успешной аутентификации
	 * @param claimId - ID заявки
	 */
	public void initialize(Long claimId) throws BusinessException, BusinessLogicException
	{
		helper = new AuthenticatedRegistrationHelper();

		init(claimId);
	}

	private void init(Long claimId) throws BusinessException
	{
		claim = mobileBankService.getRegistrationClaim(claimId);
		if (claim == null)
			throw new NotFoundException("Не найдена заявка на подключение МБ. id=" + claimId);
	}

	/**
	 * @return ID заявки
	 */
	public long getId()
	{
		return claim.getId();
	}

	/**
	 * @return тариф
	 */
	public MobileBankTariff getTariff()
	{
		return claim.getTariff();
	}

	/**
	 * @return маскированный номер телефона
	 */
	public String getMaskedPhone()
	{
		return PhoneNumberUtil.getCutPhoneNumber(claim.getPhoneNumber());
	}

	/**
	 * @return маскированный номер карты
	 */
	public String getMaskedCard() throws BusinessException
	{
		if (maskedCard == null)
		{
			Map<String, String> number2maskCardsMap = helper.buildNumber2MaskCardsMap();
			maskedCard = number2maskCardsMap.get(claim.getCardNumber());
			if (maskedCard == null)
				throw new NotFoundException("Не найдена карта " + claim.getCardNumber());
		}
		return maskedCard;
	}

	public PreConfirmObject preConfirm() throws SecurityLogicException, BusinessLogicException, BusinessException
	{
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(helper.getPerson().getLogin());
		callBackHandler.setConfirmableObject(getConfirmableObject());
		// Для отправки СМС используем только телефон в анкете клиента
		callBackHandler.setUseRecipientMobilePhoneOnly(true);

		return preConfirm(callBackHandler);
	}

	public MobileBankRegistrationClaim getConfirmableObject()
	{
		return claim;
	}

	/**
	 * Заполняем и сохраняем заявку
	 * Тариф и карту берём из входных параметров, телефон - из анкеты клиента
	 */
	protected void saveConfirm() throws BusinessException, BusinessLogicException
	{
		// 1. Отправляем заявку в МБ
		try
		{
			mobileBankService.sendRegistrationClaim(helper.getPerson(), claim);
		}
		catch (BusinessException e)
		{
			log.error("Сбой при отправке заявки на подключение в МБ", e);
			throw new BusinessLogicException(formatBadNewsMessage(), e);
		}

		// 2. Обновляем заявку
		claim.setCompleted(true);
		mobileBankService.addOrUpdateRegistrationClaim(claim);
	}

	/**
	 * @return текст сообщения об успешно отправленной заявке
	 */
	public String formatGoodNewsMessage() throws BusinessException
	{
		return "Заявка на подключение услуги Мобильный банк " +
				"по карте " + StringEscapeUtils.escapeHtml(getMaskedCard()) + " " +
				"на телефон " + StringEscapeUtils.escapeHtml(getMaskedPhone()) + " принята. " +
				"Сообщение о подключении будет отправлено в течение суток. " +
				"Получение одноразовых паролей будет доступно только после подключения услуги Мобильный банк.";
	}

	private String formatBadNewsMessage()
	{
		return "Заявка на подключение услуги Мобильный банк не зарегистрирована. " +
				"Пожалуйста, повторите операцию позже.";
	}
}
