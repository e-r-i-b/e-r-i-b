package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author Erkin
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция начала подключения услуги "Мобильный Банк"
 */
public class StartMobileBankRegistrationOperation extends OperationBase
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private RegistrationHelper helper;

	private MobileBankRegistrationClaim claim;

	private long repeatIntervalMinutes;

	private boolean phoneAvailable;

	private boolean cardsAvailable;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализировать операцию для использования на этапе аутентификации
	 * @param authContext - контекст аутентификации
	 */
	public void initializeForLogin(AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init();
	}

	/**
	 * Инициализировать операцию для использования после успешной аутентификации
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		helper = new AuthenticatedRegistrationHelper();

		init();
	}

	private void init() throws BusinessException, BusinessLogicException
	{
		MobileBankConfig mobileBankConfig = ConfigFactory.getConfig(MobileBankConfig.class);
		Login login = helper.getPerson().getLogin();

        claim = mobileBankService.getLastCompletedRegistrationClaim(login.getId());
		repeatIntervalMinutes = mobileBankConfig.getRegRepeatInterval();
		phoneAvailable = PhoneNumberFormat.check(StringHelper.getEmptyIfNull(helper.getPhone()));
		cardsAvailable = !helper.buildNumber2MaskCardsMap().isEmpty();
	}

	/**
	 * @return true, если заявку можно повторить
	 */
	public boolean canRepeatClaim()
	{
		// 1. Получаем минимальное время между успешными заявками
		if (repeatIntervalMinutes <= 0)
			return true; // интервал не указан => разрешаем повтор заявки

		// 2. Берём время последней успешной заявки
		Date lastGoodClaimTime = (claim != null) ? DateHelper.toDate(claim.getDate()) : null;
		if (lastGoodClaimTime == null)
			return true; // заявок ещё не было

		// 3. Проверяем, не закончился ли перерыв
		Date nextClaimStartTime = DateUtils.addMinutes(lastGoodClaimTime, (int) repeatIntervalMinutes);
		Date now = new Date();
		return now.after(nextClaimStartTime);
	}

	/**
	 * @return предыдущая заявка, если была, или null
	 */
	public MobileBankRegistrationClaim getPreviousClaim()
	{
		return claim;
	}

	/**
	 * @return true, если в анкете клиента мобильный телефон указан и валиден
	 */
	public boolean isPhoneAvailable()
	{
		return phoneAvailable;
	}

	/**
	 * @return true, если есть активные карты, доступные для подключения
	 */
	public boolean isCardsAvailable()
	{
		return cardsAvailable;
	}
}
