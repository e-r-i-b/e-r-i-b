package com.rssl.common.forms.processing;

import com.rssl.common.forms.*;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 02.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */
public class FormProcessor<E, EC extends ErrorsCollector<E>>
{
	private static final String VALIDATION_LOG_PREFIX = "[ќшибка валидации(форм-процессор)]: ";
	private ApplicationConfig applicationConfig = ApplicationConfig.getIt();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private Form               form;
    private FieldValuesSource  source;

	private Map<String,String> input;
    private Map<String,Object> result;
	private EC                 errorsCollector;
	private ValidationStrategy validationStrategy;

	private Set<String>        changedFields = new HashSet<String>();

	public FormProcessor(FieldValuesSource source, Form form, EC errorsCollector, ValidationStrategy validationStrategy)
    {
        this.source = source;
        this.form = form;
	    this.errorsCollector = errorsCollector;
	    this.validationStrategy = validationStrategy;
    }

	public FormProcessor(FieldValuesSource source, Form form, EC errorsCollector, ValidationStrategy validationStrategy, List<String> changedFields)
	{
		this(source, form, errorsCollector, validationStrategy);
		this.changedFields.addAll(changedFields);
	}

	public EC getErrorsCollector()
	{
		return errorsCollector;
	}

	public void setErrorsCollector(EC errorsCollector)
	{
		this.errorsCollector = errorsCollector;
	}

	public E getErrors()
	{
		return errorsCollector.errors();
	}

    /** ѕреобразовани€ + проверка правильности заполнени€ формы
     * ≈сли метод возвращает true то обработанные данные доступны в getResult
     * если метод возвращает false описание ошибок обработки доступно в getErrors
     * @return признак успешной обработки
     * */
    public boolean process()
    {
	    input = null;
        result = null;

	    // —начала сохран€ем оригинальные (невалидированные) значени€ полей
	    Map<String, String> fieldsInputs = new HashMap<String, String>();
	    for (Field field : form.getFields())
	    {
	        //получаем значение
		    String value = source.getValue(field.getName());
		    if (value != null)
			    fieldsInputs.put(field.getName(), value);
	    }
	    input = fieldsInputs;

	    //валидировать только изменившиес€ пол€ формы.
	    boolean validateOnlyChanged = !CollectionUtils.isEmpty(changedFields);

	    // «атем валидируем пол€
	    Map<String, Object> fieldsValues = new HashMap<String, Object>();
	    for (Field field : form.getFields())
	    {
			//провер€ем включено поле или нет.
		    boolean disabled = !isFieldEnabledOnApi(field)||!isFieldEnabled(field, fieldsValues);
		    if (disabled)
		    {
			    continue;
		    }

		    if (validationStrategy.accepted(field))
		    {
		        boolean invalid = !validateField(field, fieldsValues, validateOnlyChanged);
			    if (invalid)
			    {
				    return false;
			    }
		    }

		    //получаем значение
		    Object value = getFieldValue(field, fieldsValues);
		    fieldsValues.put(field.getName(), value);
	    }

        if ( !validateFormValidators(fieldsValues) )
        {
            return false;
        }

        result = fieldsValues;
        return true;
    }

	private boolean isFieldEnabledOnApi(Field field)
	{
		VersionNumber toApi = field.getToApi();
		VersionNumber fromApi = field.getFromApi();
		if(applicationConfig.getApplicationInfo().isMobileApi()&&(toApi != null || fromApi != null))
		{
			Integer apiVersion = applicationConfig.getApplicationInfo().getApiMajorVersion();
			VersionNumber versionNumber = new VersionNumber(apiVersion,0);

			if (toApi != null && fromApi == null)
			{
				return versionNumber.le(toApi);
			}
			else if (toApi != null && fromApi != null)
			{
				throw  new FormException("ќшибка при преобразовании пол€ " + field.getName() + " :значение fromApi и toApi не должны указыватьс€ совместно ");
			}
			else if (toApi == null || fromApi != null)
			{
				return versionNumber.ge(fromApi);
			}
		}

		//≈сли указанно  toApi то считаем что поле в основном приложении выключенно.
		//≈сли же fromApi, или не указанно ни чего, то включено.
	    if (toApi!=null)
			return  false;
		else
		    return true;
	}

	private boolean isFieldEnabled(Field field, Map<String, Object> fieldsValues)
	{
		Object enabled = field.getEnabledExpression().evaluate(fieldsValues);
		if (!(enabled instanceof Boolean))
		{
			throw new FormException("ќшибка при преобразовании пол€ " + field.getName() + " :значение enabledexpression должно иметь тип Boolean");
		}
		return (Boolean) enabled;
	}

	private Object getFieldValue(Field field, Map<String, Object> fieldsValues)
	{
		Expression valueExpression = field.getValueExpression();
		if (valueExpression != null)
		{
			Map<String, Object> copyMap = new HashMap<String, Object>(fieldsValues);

			String strValue = source.getValue(field.getName());
			copyMap.put("$this", strValue != null ? strValue.trim() : null);

			Object value = valueExpression.evaluate(copyMap);
			return (value instanceof String)  ? ((String) value).trim() : value;
		}
		String name = field.getName();
		FieldValueParser parser = field.getParser();
		try
		{
			return parser.parse(source.getValue(name) != null ? source.getValue(name).trim() : source.getValue(name));
		}
		catch (Exception e)
		{
			throw new FormException("ќшибка при преобразовании пол€ " + name, e);
		}
	}

	/**
	 * @return оригинальные (невалидированные) значени€ полей
	 */
	public Map<String, String> getInput()
	{
		if(input == null)
			throw new RuntimeException("input not available");
		//noinspection ReturnOfCollectionOrArrayField
		return input;
	}

	/** –езультат обработки формы. */
	public Map<String, Object> getResult()
	{
		if(result == null)
			throw new RuntimeException("result not available");
		//noinspection ReturnOfCollectionOrArrayField
		return result;
	}

	private boolean validateFormValidators(Map<String,Object> fieldsValues)
    {
        List<MultiFieldsValidator> formValidators = form.getFormValidators();

        for (MultiFieldsValidator validator:formValidators)
        {
            if (!validationStrategy.accepted(validator))
            {
                continue;
            }
	        if ( !isValidatorEnabled(validator, fieldsValues) )
	        {
		        continue;
	        }
	        boolean res = false;

	        try
	        {
				res = validator.validate(fieldsValues);
	        }
	        catch (TemporalDocumentException e)
	        {
		        log.error(e.getMessage(), e);
		        errorsCollector.setTemporalUnAccesible();
		        res = true;//чтоб не выводилась ошибка самого валидатора
	        }

	        if(!res)
            {
	            if (log.isInfoEnabled())
			        log.info(VALIDATION_LOG_PREFIX + validator.getMessage() + ". " +
					        "‘орма: " + form.getName() + ". " +
					        "MultiFieldsValidator: " + validator.getClass().getName());
	            errorsCollector.add(fieldsValues, validator.getErrorFieldNames(), validator);
            }
        }

        return errorsCollector.count() == 0;
    }

	private boolean isValidatorEnabled(MultiFieldsValidator validator, Map<String, Object> fieldsValues)
	{
		Object enabled = validator.getEnabledExpression().evaluate(fieldsValues);
		if (!(enabled instanceof Boolean))
		{
			throw new FormException("ќшибка при преобразовании пол€ " + validator.getClass().getName() + " :значение enabledexpression должно иметь тип Boolean");
		}
		return (Boolean) enabled;
	}

	private boolean isValidatorEnabled(FieldValidator validator, Map<String, Object> fieldsValues)
	{
		Object enabled = validator.getEnabledExpression().evaluate(fieldsValues);
		if (enabled instanceof String){
			return Boolean.parseBoolean(enabled.toString());
		}
		if (!(enabled instanceof Boolean))
		{
			throw new FormException("ќшибка при преобразовании пол€ " + validator.getClass().getName() + " :значение enabledexpression должно иметь тип Boolean");
		}
		return (Boolean) enabled;
	}

	private boolean validateField(Field field, Map<String, Object> fieldsValues, boolean validateOnlyChanged)
	{
		//≈сли есть €вно заданные пол€, то валидируем только их, остальные пол€ пропускаютс€.
		if (validateOnlyChanged)
		{
			if (!changedFields.contains(field.getName()))
			{
				return true;
			}
		}

		String fieldValue = source.getValue(field.getName());
		fieldValue = fieldValue != null ? fieldValue.trim() : fieldValue;
		List<FieldValidator> validators = field.getValidators();

		boolean isFieldValid = true;
		for (FieldValidator validator: validators)
		{
			if (!validationStrategy.accepted(validator, fieldValue))
			{
			    continue;
			}

			if (!isValidatorEnabled(validator, fieldsValues))
	        {
		        continue;
	        }

			boolean valid = false;
			try
			{
				valid = validator.validate(fieldValue);
			}
			catch (TemporalDocumentException ex)
			{
				log.error(ex);
				errorsCollector.setTemporalUnAccesible();
				valid = true;//чтоб не выводилась ошибка самого валидатора
			}

			if (!valid)
		    {
			    if (log.isInfoEnabled())
					log.info(VALIDATION_LOG_PREFIX + validator.getMessage() + ". " +
							"‘орма: " + form.getName() + ". " +
							"ѕоле: " + field.getName() + ". " +
							"FieldValidator: " + validator.getClass().getName() + ". " +
							"«начение: " + fieldValue);
			    errorsCollector.add(fieldValue, field, validator);
			    isFieldValid = false;
			    break;
		    }
		}

		return isFieldValid;
	}

	/**
	 *
	 * ”становить изменившиес€ пол€. ≈сли changedFields > 0, тогда валидаци€ осуществл€етс€ только дл€
	 * изменившихс€ полей, в противном случае валидаци€ идет дл€ всех полей.
	 *
	 * @param changedFields изменившиес€ пол€
	 */
	public void setChangedFields(Set changedFields)
	{
		this.changedFields = changedFields;
	}
}
