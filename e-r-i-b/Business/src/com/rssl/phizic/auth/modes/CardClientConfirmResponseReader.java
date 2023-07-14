package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.readers.ConfirmResponseReaderBase;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.userlog.WorkingFromPlasticCardLogger;
import com.rssl.phizic.security.config.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author basharin
 * @ created 23.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardClientConfirmResponseReader extends ConfirmResponseReaderBase
{
	private static final Form form = createForm(true);
	private static final Form formForLog = createForm(false);

	private FieldValuesSource           valuesSource;
	private CardClientConfirmResponse response;
	private List<String>                errors;

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}

	public boolean read()
	{
		FormProcessor processor = createFormProcessor(form, valuesSource);

		boolean res = processor.process();
		if(res)
		{
			response = new CardClientConfirmResponse(processor.getResult());
		}
		else
		{
			processor = createFormProcessor(formForLog, valuesSource);
			processor.process();
			response = new CardClientConfirmResponse(processor.getResult());
			errors = (List<String>) processor.getErrors();
		}

		try
		{
			writeLog(response, PersonHelper.getLastClientLogin());
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}

		return res;
	}

	/**
	 * записывает лог.
	 * @param response информация о карте с pos-терминала.
	 */
	private void writeLog(CardClientConfirmResponse response, CommonLogin login)
	{
		Map<String, String> strData = new HashMap<String, String>();
		if (response.getCardNumber() != null && response.getCardNumber().equals(login.getLastLogonCardNumber()))
			strData.put(WorkingFromPlasticCardLogger.SUCCESSFULL_FIELD, "");

		strData.put(WorkingFromPlasticCardLogger.OPERATION_FIELD, response.getFormDescription());
		strData.put(WorkingFromPlasticCardLogger.EMPLOYEE_LOGIN_ID_FIELD,  response.getEmployeeLogin());

		if (response.getCardNumber() != null)
			strData.put(WorkingFromPlasticCardLogger.CARD_NUMBER_FIELD, response.getCardNumber());
		strData.put(WorkingFromPlasticCardLogger.CARD_TYPE_FIELD, response.getCardType());
		strData.put(WorkingFromPlasticCardLogger.TRANSACTION_DATE_FIELD, response.getTransactionDate());
		strData.put(WorkingFromPlasticCardLogger.TRANSACTION_TIME_FIELD, response.getTransactionTime());
		strData.put(WorkingFromPlasticCardLogger.TERMINAL_NUMBER_FIELD, response.getTerminalNumber());

		try
		{
			WorkingFromPlasticCardLogger.writeLog(strData);
		}
		catch (SystemException ex)
		{
			throw new SecurityException(ex);
		}
	}

	public ConfirmResponse getResponse()
	{
		return response;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	private static Form createForm(boolean hasValidator)
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName(Constants.CONFIRM_PLASTIC_CARD_NUMBER_FIELD);
		fb.setDescription("Номер карты");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Номер карты клиента не был получен"));
		formBuilder.addField(fb.build());
		
		//тип карты
		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_CARD_TYPE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Тип карты");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Тип карты не был получен"));
		formBuilder.addField(fb.build());

		//номер терминала
		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_TERMINAL_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Номер терминала");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Номер терминала не был получен"));
		formBuilder.addField(fb.build());

		//дата транзакции
		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_TRANSACTION_DATE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Дата транзакции");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Дата транзакции не была получена"));
		formBuilder.addField(fb.build());

		//время транзакции
		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_TRANSACTION_TIME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Время транзакции");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Время транзакции не было получено"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_EMPLOYEE_LOGIN_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Логин сотрудника");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("Логин сотрудника не был получен"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(Constants.CONFIRM_PLASTIC_FORM_NAME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Имя формы");
		if (hasValidator)
			fb.addValidators(new RequiredFieldValidator("имя формы не было получено"));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
