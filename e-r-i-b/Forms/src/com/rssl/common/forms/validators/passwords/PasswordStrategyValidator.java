package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class PasswordStrategyValidator extends FieldValidatorBase
{
	public static final String STRATEGY_NAME = "STRATEGY_NAME";

	private String strategyKey = null;
	private ThreadLocal<String> message = new ThreadLocal<String>();

	/** конструктор */
	@SuppressWarnings({"UnusedDeclaration"})
	public PasswordStrategyValidator()
	{
	}

	public PasswordStrategyValidator(String strategyKey)
	{
		this.strategyKey = strategyKey;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		PasswordValidationConfig config = ConfigFactory.getConfig(PasswordValidationConfig.class);

		String key = getParameter(STRATEGY_NAME);
		if (key == null || key.length() == 0)
			throw new RuntimeException("Не определен параметр STRATEGY_NAME.");

		ValidationStrategy strategy = config.findStrategy(key);
		if (strategy == null)
			throw new RuntimeException("Не найдена стратегия - " + key);

		Pair<Boolean, String> p = strategy.validate(value);
		if (p.getFirst())
			return true;

		message.set(p.getSecond());
		return false;
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setParameter(String name, String value)
	{
		if (name.equals(STRATEGY_NAME))
		{
			strategyKey = value;
		}
	}

	public String getParameter(String name)
	{
		if (name.equals(STRATEGY_NAME))
		{
			return strategyKey;
		}
		return null;
	}
}