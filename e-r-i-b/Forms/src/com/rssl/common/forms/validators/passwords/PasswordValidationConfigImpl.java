package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.passwords.generated.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class PasswordValidationConfigImpl extends PasswordValidationConfig
{
	private static final String PASSWORD_VALIDATION_XML         = "security-config.xml";
	private static final String CLIENT_ONE_TIME_SETTING_KEY     = "client-one-time";

	private static final String CSA_PASSWORD_SETTING_KEY    = "csa_client_password";
	private static final String CSA_LOGIN_SETTING_KEY       = "csa_client_login";

	private static final String PASSWORD_GENERATOR_LENGTH_KEY = "com.rssl.iccs.password-generator.password.length";

	private Map<String, ValidationStrategy> strategyByKey;
	private Map<String, List<Parameter>> parametersByKey;
	private Map<String, List<Charset>> charsetsByKey;
	private Map<String, Integer> minimalLength;
	private Map<String, String> useCharset;
	private Map<String, Integer> actualPasswordLength;
	private Application application;

	private int minimumLoginLength;

	public PasswordValidationConfigImpl(PropertyReader reader, Application application)
	{
		super(reader);
		this.application = application;
	}

	public Collection<ValidationStrategy> getStrategies()
	{
		return strategyByKey.values();
	}

	public ValidationStrategy findStrategy(String key)
	{
		return strategyByKey.get(key);
	}

	public void doRefresh() throws ConfigurationException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		minimumLoginLength = getIntProperty("com.rssl.iccs.login.minimumLength");
		try
		{
			JAXBContext context = JAXBContext.newInstance("com.rssl.common.forms.validators.passwords.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = ResourceHelper.loadResourceAsStream(PASSWORD_VALIDATION_XML);

			SecurityValidationConfig validationStrategies =
					(SecurityValidationConfig) unmarshaller.unmarshal(is);

			useCharset   = new HashMap<String, String>();
			strategyByKey = new HashMap<String, ValidationStrategy>();
			charsetsByKey  = new HashMap<String, List<Charset>>();
			parametersByKey = new HashMap<String, List<Parameter>>();
			minimalLength  = new HashMap<String, Integer>();
			actualPasswordLength = new HashMap<String, Integer>();

			//noinspection unchecked
			List<SettingDescriptor> settingDescriptors = validationStrategies.getSecuritySettings();
			for (SettingDescriptor settingDescriptor : settingDescriptors)
			{
				StrategyDescriptor strategy = settingDescriptor.getValidationSettings();

				String settingKey = settingDescriptor.getKey();
				//String key = strategy.getKey();
				PasswordDescriptor passwordDescriptor = settingDescriptor.getPasswordSettings();

				PasswordMinimalLength passwordMinimalLength = passwordDescriptor.getPasswordMinimalLength();
				minimalLength.put(settingKey, Integer.valueOf(passwordMinimalLength.getMinimalLength()));
				String minimalLengthMessage = StringHelper.getEmptyIfNull(passwordMinimalLength.getValidationMessage());
				actualPasswordLength.put(settingKey, getPasswordLength(settingKey));

				//noinspection unchecked
				List<Charset> allowedCharsets = ((CharsetGroup)(passwordDescriptor.getAllowedCharsets().get(0))).getAllowedCharset();
				charsetsByKey.put(settingKey, allowedCharsets);

				//noinspection unchecked
				List<Parameter> settingParameters = settingDescriptor.getSettingParameters().getParameters();
				parametersByKey.put(settingKey, getActualValueParameters(settingParameters));

				//noinspection unchecked
				List<PasswordValidator> validatorDescriptors = strategy.getValidators();
				List<FieldValidator> validators = new ArrayList<FieldValidator>();

				for (PasswordValidator validatorDescriptor : validatorDescriptors)
					validators.add(createValidator(validatorDescriptor));

				Charset prefCharset = getPrefCharset(settingKey, passwordDescriptor);
				useCharset.put(settingKey, prefCharset.getCharset());

				String message = StringHelper.getEmptyIfNull(prefCharset.getValidationMessage());
				LengthFieldValidator lengthFieldValidator = new LengthFieldValidator();
				lengthFieldValidator.setParameter("minlength", BigInteger.valueOf(actualPasswordLength.get(settingKey)));
				lengthFieldValidator.setMessage(minimalLengthMessage);
				validators.add(lengthFieldValidator);
				validators.add(new RegexpFieldValidator("["+useCharset.get(settingKey)+"]{" + actualPasswordLength.get(settingKey) + ",}", message));

				for (Charset charset : allowedCharsets)
				{
					if (charset.getCharset().equals(prefCharset) && charset.getSpecificValidators() != null)
					{
						//noinspection unchecked
						List<PasswordValidator> passwordValidators = charset.getSpecificValidators().getValidator();
						for (PasswordValidator validatorDescriptor : passwordValidators)
						{
							validators.add(createValidator(validatorDescriptor));
						}
						break;
					}
				}

				if (strategyByKey.containsKey(settingKey))
					throw new RuntimeException("Дублирующиеся ключи : " + settingKey);

				strategyByKey.put(settingKey, new ChainValidationStrategy(validators));
			}
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Ошибка чтения конфигурации.", e);
		}
		catch (ClassNotFoundException e)
		{
			throw new ConfigurationException("Ошибка чтения не найден класс.", e);
		}
		catch (IllegalAccessException e)
		{
			throw new ConfigurationException("Нет доступа.", e);
		}
		catch (InstantiationException e)
		{
			throw new ConfigurationException("Нет доступа.", e);
		}
	}

	private Integer getPasswordLength(String settingKey)
	{
		if (settingKey.equals(CLIENT_ONE_TIME_SETTING_KEY))
		{
			return ConfigFactory.getConfig(SmsPasswordConfig.class, application).getSmsPasswordLength();
		}
		else if(CSA_PASSWORD_SETTING_KEY.equals(settingKey) || CSA_LOGIN_SETTING_KEY.equals(settingKey))
		{
			return minimalLength.get(settingKey);
		}
		else
		{
			return hasProperty(PASSWORD_GENERATOR_LENGTH_KEY) ? getIntProperty(PASSWORD_GENERATOR_LENGTH_KEY) : minimalLength.get(settingKey);
		}
	}

	private Charset getPrefCharset(String settingKey, PasswordDescriptor passwordDescriptor)
	{
		String prefCharset;
		if (settingKey.equals(CLIENT_ONE_TIME_SETTING_KEY))
		{
			prefCharset = ConfigFactory.getConfig(SmsPasswordConfig.class, application).getSmsPasswordAllowedChars();
		}
		else if(CSA_PASSWORD_SETTING_KEY.equals(settingKey) || CSA_LOGIN_SETTING_KEY.equals(settingKey))
		{
			prefCharset = null;
		}
		else
		{
			prefCharset = getProperty("com.rssl.iccs.password-generator.allowed.chars");
		}

		// ищем по имени
		if (StringHelper.isEmpty(prefCharset))
		{
			String defaultName = ((CharsetGroup)passwordDescriptor.getAllowedCharsets().get(0)).getDefault();
			for (Charset charset : charsetsByKey.get(settingKey))
			{
				if (charset.getName().equals(defaultName))
					return charset;
			}

			throw new RuntimeException("Не найден charset c именем "+ defaultName + " в списке поддерживаемых наборов символов с key=" + settingKey);
		}
		// ищем по значению
		else
		{
			for (Charset charset : charsetsByKey.get(settingKey))
			{
				if (charset.getCharset().equals(prefCharset))
					return charset;
			}

			throw new RuntimeException("Не найден charset со значением " + prefCharset + " в списке поддерживаемых для набора символов с key=" + settingKey);
		}
	}

	private List<Parameter> getActualValueParameters(List<Parameter> settingParameters)
	{
		List<Parameter> realParameterValues = new ArrayList<Parameter>();
		for (Parameter parameter : settingParameters)
		{
			String value = getProperty(parameter.getName());
			if (StringHelper.isNotEmpty(value))
				parameter.setValue(value);

			realParameterValues.add(parameter);
		}

		return realParameterValues;
	}

	public int getMinimalLength(String key)
	{
		return minimalLength.get(key);
	}

	public Map<String, List<Parameter>> getParameters()
	{
		return parametersByKey;
	}

	public List<Parameter> getParameters(String key)
	{
		return parametersByKey.get(key);
	}

	public Map<String, List<Charset>> getAllowedCharsets()
	{
		return charsetsByKey;
	}

	public List<Charset> getAllowedCharsets(String key)
	{
		return charsetsByKey.get(key);
	}

	/**
	 * возвращает допустимые символы для пароля (по ключу settingDescriptor, а не стратегии)
	 * @param key
	 * @return
	 */
	public String getPreferredAllowedCharset(String key)
	{
		return useCharset.get(key);
	}

	@Override
	public int getLoginAttempts()
	{
		return getIntProperty("com.rssl.iccs.login.attempts");
	}

	@Override
	public int getBlockedTimeout()
	{
		return getIntProperty("com.rssl.iccs.login.blockedTimeout");
	}

	@Override
	public int getMinimunLoginLength()
	{
		return minimumLoginLength;
	}

	public String getProperty0(String key, String propertyName)
	{
		List<Parameter> params = parametersByKey.get(key);
		if (params == null)
		{
			return null;
		}
		for (Parameter parameter : params)
		{
			if (propertyName.equals(parameter.getName()))
			{
				return parameter.getValue();
			}
		}
		return null;
	}

	public int getIntProperty(String key, String propertyName)
	{
		String property = getProperty0(key, propertyName);
		if(StringHelper.isEmpty(property))
		{
			return 0;
		}
		return Integer.parseInt(property);
	}

	public int getActualPasswordLength(String key)
	{
		return actualPasswordLength.get(key);
	}

	private FieldValidator createValidator(PasswordValidator validatorDescriptor)
			throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		Class<Object> clazz = ClassHelper.loadClass(validatorDescriptor.getClassName());
		Object o = clazz.newInstance();
		if (!(o instanceof FieldValidator))
			throw new RuntimeException("Валидатор " + validatorDescriptor.getClassName() +
					" не реализует " + FieldValidator.class.getName());

		FieldValidator validator = (FieldValidator) o;

		//noinspection unchecked
		List<Parameter> parameters = validatorDescriptor.getParameters();
		for (Parameter parameter : parameters)
		{
			if (parameter.getName().equals("message"))
			{
				validator.setMessage(parameter.getValue());
			}
			else
			{
				validator.setParameter(parameter.getName(), parameter.getValue());
			}
		}
		return validator;
	}
}
