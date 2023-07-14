package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.common.forms.validators.passwords.generated.Parameter;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public abstract class PasswordValidationConfig extends Config
{
	protected PasswordValidationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ¬се стратегии
	 */
	public abstract Collection<ValidationStrategy> getStrategies();

	/**
	 * Ќайти стратегию по ключу.
	 * @param key ключ
	 */
	public abstract ValidationStrategy findStrategy(String key);

	/**
	 * получить все параметры
	 * @return
	 */
	public abstract Map<String, List<Parameter>> getParameters();

	/**
	 * получить параметры дл€ определенной стратегии
	 * @param key
	 * @return
	 */
	public abstract List<Parameter> getParameters(String key);

	/**
	 * минимальна€ длина парол€
	 * @return
	 */
	public abstract int getMinimalLength(String key);

	/**
	 * установленна€ длина парол€
	 * @return
	 */
	public abstract int getActualPasswordLength(String key);

	/**
	 * возвращает допустимые символы дл€ парол€
	 * @return
	 */
	public abstract Map<String, List<Charset>> getAllowedCharsets();

	/**
	 * возвращает допустимые символы дл€ парол€
	 * @param key
	 * @return
	 */
	public abstract List<Charset> getAllowedCharsets(String key);

	/**
	 * возвращает допустимые символы дл€ парол€ (по ключу settingDescriptor, а не стратегии)
	 * @param key
	 * @return
	 */
	public abstract String getPreferredAllowedCharset(String key);

	public abstract int getLoginAttempts();

	public abstract int getBlockedTimeout();

	/**
	 * @return минимальна€ длина логина.
	 */
	public abstract int getMinimunLoginLength();
}
