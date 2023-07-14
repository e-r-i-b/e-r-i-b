package com.rssl.phizic.security.config;

import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 19.09.2005 Time: 12:41:14
 */
public abstract class SecurityConfig extends Config
{
	protected SecurityConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return см. PermissionsProvider
	 */
    public abstract String getPermissionProviderClassName();

	/**
	 * @return Имя класса, предоставляющего права для гостей
	 */
    public abstract String getGuestPermissionProviderClassName();

	/**
	 * @return см. PermissionsCalculator
	 */
	public abstract String getPermissionCalculatorClassName();

	/**
	 * @return см. LoginInfoProvider
	 */
    public abstract String getLoginInfoProviderClassName();

	/**
	 * @return имя пользователя по умолчанию для PhizIA
	 */
    public abstract String getDefaultAdminName();

	/**
	 * @return имя анонимного пользователя
	 */
	public abstract String getAnonymousClientName();

	/**
	 * @return количество попыток ввода пароля
	 */
    public abstract int getLoginAttempts ();

	/**
	 * @return количество дней жизни пароля
	 */
    public abstract int getPasswordLifeTime ();

    /**
     * @return время блокировки в секундах
     */
    public abstract int getBlockedTimeout ();

	/**
	 * @return количество попыток подтверждения
	 */
    public abstract int getConfirmAttempts ();

	/**
     * @return длинна пароля
     */
    public abstract int getPasswordLength();

    /**
     * @return возвращает строку символов, из которолй генератор строит пароли
     */
    public abstract String getPasswordAllowedChars();

	/**
	 *
	 * @return строка символов для создания логина
	 */
	public abstract String getLoginAllowedChars();

    /**
     * @return длинна пароля в карте ключей
     */
    public abstract int getCardPasswordLength();

    /**
     * @return возвращает строку символов, из которой генератор строит пароли для карты ключей
     */
    public abstract String getCardPasswordAllowedChars();

    /**
     * @return количество карт создаваемых по-умолчанию
     */
    public abstract int getCardsCount();

    /**
     * @return количество паролей в карте ключей создаваемых по-умолчанию
     */
    public abstract int getCardPasswords();

	/**
	 * Класс вычисляющий полномочия для пользователя admin
	 * @return класс
	 */
	public abstract String getAdminPermissionProvider();

	/**
	 * @return признак автоматической привязки карт паролей пользователю 
	 */
	public abstract boolean isCardPasswordAutoAssign();

	/**
	 * @return возвращает имя класса реализации CryptoService
	 */
	public abstract String  getCryptoServiceClassName();

	/**
	 * нужно ли блокировать админа, при неправильном вводе пароля.
	 * @return
	 */
	public abstract boolean isAdminNeedBlocked();

	/**
	 * Обязательно ли менять пароль при первом входе в систему
	 * @return
	 */
	public abstract boolean getNeedChangePassword();

	/**
	 * @return наборы символов, из которых может состоять пароль
	 */
	public abstract List<Charset> getPasswordCharsets();

	/**
	 * @return уровня иерархии (число) подразделений для привязки сотрудникам.
	 * Т.е. насколько детальна привязка сотрудников к подразделениям (например, Северный  - 1 уровень, т.е. ОСБ)
	 */
	public abstract int getDepartmentsAllowedLevel();

	/**
	 * @return лимит администраторов при привязке подразделения
	 */
	public abstract int getDepartmentAdminsLimit();

	/**
	 * Показывает, запрещено ли администратору устанавливать сотрудникам схему Индивидуальные права
	 * @return true - запрещено, false - разрешено
	 */
	public abstract boolean isDenyCustomRights();

	/**
	 * Получение минимальной длины пароля для мобильного приложения
	 * @return длина пароля для мобильного приложения
	 */
	public abstract int getMobilePINLength();

	/**
	 * Регэксп, определяющий разрешенные символы в пароле для мобильного приложения
	 * @return регэкс
	 */
	public abstract String getMobilePINRegExp();

	/**
	 * признак необходимости автовыбора стратегии подтверждения при входе учитывая дефолтную стратегию подтверждения
	 * @return необходимость автовыбора стратегии подтверждения
	 */
	public abstract boolean getNeedLoginConfirmAutoselect();

	/**
	 * признак необходимости автовыбора стратегии подтверждения при подтверждении платежа учитывая дефолтную стратегию подтверждения
	 * @return необходимость автовыбора стратегии подтверждения
	 */
	public abstract boolean getNeedPaymentConfirmAutoselect();

	public abstract int getSmsBankingSessionLifetime();
	/**
	 * @return число попыток перебора логинов при регистрации изнутри СБОЛ.
	 */
	public abstract int getNumberOfLoginAttemptsAtRegistration();

	/**
	 * @return число минут через сколько после последней неудачной попытки ввода логина сброситься ввод капчи при регистрации изнутри СБОЛ.
	 */
	public abstract int getMinuteToResetCaptchaAtRegistration();

	/**
	 * @return нужно ли подключать скрипты Касперского.
	 */
	public abstract boolean getNeedKasperskyScript();

	/**
	 * время до блокировки учетной записи сотрудника по длительной неактивности
	 * @return
	 */
	public abstract long getTimeToBlock();
}
