package com.rssl.phizic.business.ermb.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.business.ermb.sms.config.CommandDefinition;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collection;
import java.util.List;

/**
 * ¬алидатор, провер€ющий, не совпадает ли название (смс-идентификатора, шаблона операции, etc.) c ключевыми словами смс-команд
 *
 * @author EgorovaA
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class IdenticalToSmsCommandValidator extends FieldValidatorBase
{
	private String message;

	public IdenticalToSmsCommandValidator(String message)
	{
		if (StringHelper.isNotEmpty(message))
			this.message = message;
	}

	public IdenticalToSmsCommandValidator()
	{
		this.message = "SMS-идентификатор совпадает с названием SMS-команды";
	}

	public boolean validate(String alias) throws TemporalDocumentException
	{
		if( isValueEmpty(alias) )
			return true;
		
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		Collection<CommandDefinition> commandDefinitions = smsConfig.getAllCommandDefinitions();
		for (CommandDefinition commandDefinition : commandDefinitions)
		{
			List<String> words = commandDefinition.getKeywords().getWords();
			for (String word : words)
			{
				if (StringHelper.equalsNullIgnore(word.toUpperCase(),alias.toUpperCase()))
				{
					setMessage(message);
					return false;
				}
			}
		}
		return true;
	}
}
