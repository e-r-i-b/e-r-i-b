package com.rssl.phizic.business.messages;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.ReceiverSubType;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.loans.LoansUtil;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.messaging.mail.messagemaking.MessageVariablesProvider;
import com.rssl.phizic.utils.*;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 02.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class BusinessMessageVariablesProvider implements MessageVariablesProvider
{
	private static final Map<String, TemplateModel> variables
			= new HashMap<String, TemplateModel>();

	static {
		variables.put("getCurrencySign",   new getCurrencySign());
		variables.put("getCutPhoneNumber", new GetCutPhoneNumber());
		variables.put("getCutCardNumber", new GetCutCardNumberMethod());
		variables.put("isCard", new IsCardMethod());
		variables.put("isAccount", new IsAccountMethod());
		variables.put("isIMAccount", new IsIMAccountMethod());
		variables.put("getResourceType",            new GetResourceTypeMethod());
		variables.put("getExternalResourceType",    new GetExternalResourceTypeMethod());
		variables.put("formatMoney",    new GetFormatMoneyMethod());
		variables.put("formatPhone", new GetFormatPhoneMethod());
		variables.put("formatPersonName", new GetFormatPersonNameMethod());
		variables.put("formatLoanRate", new GetFormatLoanRateMethod());
		variables.put("numeralDeclension", new NumeralDeclension());
	}

	///////////////////////////////////////////////////////////////////////////

	public Map<String, TemplateModel> getTemplateVariables()
	{
		return Collections.unmodifiableMap(variables);
	}

	/**
	 * Класс возвращает true, если тип ресурса - карта
	 */
	private static class IsCardMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			switch (ResourceType.valueOf((String)arguments.get(0))) {
				case CARD: case EXTERNAL_CARD:
					return true;
				default:
					return false;
			}
		}
	}
	/**
	 * Класс возвращает true, если тип ресурса - карта
	 */
	private static class IsAccountMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			switch (ResourceType.valueOf((String)arguments.get(0))) {
				case ACCOUNT: case EXTERNAL_ACCOUNT:
					return true;
				default:
					return false;
			}
		}
	}

	/**
	 * Класс возвращает true, если тип ресурса - металлический счет
	 */
	private static class IsIMAccountMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
			{
				throw new TemplateModelException("Wrong arguments");
			}

			return  (ResourceType.valueOf((String) arguments.get(0))) == ResourceType.IM_ACCOUNT;
		}
	}

	/**
	 * Класс возвращает маскированный номер телефона
	 * на входе - номер телефона
	 */
	private static class GetCutPhoneNumber implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");

			return PhoneNumberFormat.IKFL.translateAsHidden((String) arguments.get(0));
		}
	}

	/**
	 * Класс возвращает меру валюты по её ISO коду
	 * на входе - ISO код валюты
	 */
	private static class getCurrencySign implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
			{
				throw new TemplateModelException("Wrong arguments");
			}
			
			return CurrencyUtils.getCurrencySign((String) arguments.get(0));
		}
	}

	/**
	 * Класс возвращает маскированный номер карты
	 * на входе - номер карты
	 */
	private static class GetCutCardNumberMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			return MaskUtil.getCutCardNumberForLog((String) arguments.get(0));
		}
	}

	private static class GetResourceTypeMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
			{
				throw new TemplateModelException("Wrong arguments");
			}

			return ResourceType.fromValue((String) arguments.get(0));
		}
	}

	private static class GetExternalResourceTypeMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
			{
				throw new TemplateModelException("Wrong arguments");
			}

			switch (ReceiverSubType.fromValue((String) arguments.get(0)))
			{
				case INDIVIDUAL_OUR_CARD:
				case INDIVIDUAL_OUR_PHONE:
				case INDIVIDUAL_OUR_CARD_BY_CONTACT:
				{
					return ResourceType.CARD;
				}
				case INDIVIDUAL_EXTERNAL_VISA_CARD:
				case INDIVIDUAL_EXTERNAL_MASTER_CARD:
				case INDIVIDUAL_EXTERNAL_CARD_BY_CONTACT:
				{
					return ResourceType.EXTERNAL_CARD;
				}
				case INDIVIDUAL_OUR_ACCOUNT:
				{
					return ResourceType.ACCOUNT;
				}
				case INDIVIDUAL_EXTERNAL_ACCOUNT:
				case JURIDICAL_EXTERNAL_ACCOUNT:
				case YANDEX_WALLET:
				case YANDEX_WALLET_BY_PHONE:
				case YANDEX_WALLET_OUR_CONTACT:
				{
					return ResourceType.EXTERNAL_ACCOUNT;
				}
				default: throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Класс возвращает денежку в строковом виде
	 * на входе - денежка
	 */
	private static class GetFormatMoneyMethod implements TemplateMethodModelEx
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			StringModel arg0 = (StringModel) arguments.get(0);
			Money money = (Money) arg0.getAdaptedObject(Money.class);
			return MoneyHelper.formatMoneyWithCurrencySign(money);
		}
	}

	/**
	 * Класс возвращает отформатированный номер телефона
	 * на входе - телефон
	 */
	private static class GetFormatPhoneMethod implements TemplateMethodModelEx
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			Object phone =  arguments.get(0);
			Object phoneNumber = ((StringModel) phone).getAdaptedObject(PhoneNumber.class);
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.format((PhoneNumber) phoneNumber);
		}
	}

	/**
	 * Класс возвращает фио в формате "Имя Отчество Ф."
	 * на входе: первый параметр - имя, второй - фамилия, третий - отчество
	 */
	private static class GetFormatPersonNameMethod implements TemplateMethodModel
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 3)
				throw new TemplateModelException("Wrong arguments");
			return PersonHelper.getFormattedPersonName( (String) arguments.get(0),(String) arguments.get(1), (String) arguments.get(2));
		}
	}

	private static class GetFormatLoanRateMethod implements TemplateMethodModelEx
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 1)
				throw new TemplateModelException("Wrong arguments");
			Object loanRate =  arguments.get(0);
			Object loanRateObject = ((StringModel) loanRate).getAdaptedObject(LoanRate.class);
			return LoansUtil.formatLoanRate((LoanRate) loanRateObject);
		}
	}

	/**
	 * Склонение существительных/прилагательных после числительных
	 * @see DeclensionUtils#numeral
	 */
	private static class NumeralDeclension implements TemplateMethodModelEx
	{
		public Object exec(List arguments) throws TemplateModelException
		{
			if (arguments.size() != 5)
				throw new TemplateModelException("Wrong arguments");
			Number number = ((TemplateNumberModel) arguments.get(0)).getAsNumber();
			String word = ((TemplateScalarModel) arguments.get(1)).getAsString();
			String endings1 = ((TemplateScalarModel) arguments.get(2)).getAsString();
			String endings2 = ((TemplateScalarModel) arguments.get(3)).getAsString();
			String endings3 = ((TemplateScalarModel) arguments.get(4)).getAsString();
			return DeclensionUtils.numeral(number.intValue(), word, endings1, endings2, endings3);
		}
	}
}
