package com.rssl.phizic.business.payments.forms.meta.ima;

import com.rssl.common.forms.*;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.CommissionsListHelper;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.IMAProductService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mescheryakova
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningClaimMetadataLoader  extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final String IMA_CURRENCY_ID = "imaId";
	private static final DepartmentService departmentService = new DepartmentService();
	private static final IMAProductService imaProductService = new IMAProductService();;

	public Metadata load(Metadata metadata, BusinessDocument document) throws FormException, BusinessException, BusinessLogicException
	{
		ExtendedMetadata newMetadata = (ExtendedMetadata) load(metadata, new DocumentFieldValuesSource(metadata,document));
		Map<String, Element> dictionaries = new HashMap<String, Element>();
		Element dictionary = null;
		try
		{
			dictionary = CommissionsListHelper.getCommissionDictionary(((AbstractPaymentDocument) document));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		dictionaries.put("writeDownOperations.xml", dictionary);
		newMetadata.addAllDictionaries(dictionaries);
		return newMetadata;
	}


	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);

		String imaCurrencyId = fieldSource.getValue(IMA_CURRENCY_ID);
		if (StringHelper.isEmpty(imaCurrencyId))
			throw new BusinessException("Не задана валюта открываемого счета");

		Form form = metadata.getForm();
		FormBuilder formBuilder = new FormBuilder();

		IMAProduct imaProduct = imaProductService.findById(Long.valueOf(imaCurrencyId));
		if (imaProduct == null)
			throw new BusinessException("IMA продукт с id=" + imaCurrencyId + " не найден");

		formBuilder.setFields(updateFields(imaProduct, form));

		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.setFormValidators(form.getFormValidators());

		newMetadata.addAllDictionaries(metadata.getDictionaries());
		//добавляем справочник микроопераций списания
		Map<String, Element> dictionaries = new HashMap<String, Element>();
		try
		{
			dictionaries.put("writeDownOperations.xml", CommissionsListHelper.getEmptyCommissionDictionary());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		newMetadata.addAllDictionaries(dictionaries);
		newMetadata.setExtendedForm(formBuilder.build());

		return newMetadata;
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private List<Field> updateFields(IMAProduct imaProduct, Form form) throws BusinessException
	{
		List<Field> fields = form.getFields();
		List<Field> newFields = new ArrayList<Field>(fields);
		BigDecimal forrmatBuyRateCourse = null;
		Field premierShowMsgField = null;
		for(Field field : fields)
	    {
		    if ("buyCurrency".equals(field.getName()) || "buyAmountCurrency".equals(field.getName()))
		    {
			    Currency currency = imaProduct.getCurrency();
			    if (currency != null)
			        copyFields(newFields, field, currency.getCode());

		    }
		    else if (IMA_CURRENCY_ID.equals(field.getName()))
		    {
			    copyFields(newFields, field, imaProduct.getId());
		    }
		    else if("IMAType".equals(field.getName()))
		    {
			    copyFields(newFields, field, imaProduct.getType());
		    }
		    else if("IMASubType".equals(field.getName()))
		    {
			    copyFields(newFields, field, imaProduct.getSubType());			    
		    }
		    else if ("buyIMAProduct".equals(field.getName()))
		    {
			     copyFields(newFields, field, imaProduct.getName());
		    }
		    else if ("defaultLocaleImaName".equals(field.getName()))
		    {
			    copyFields(newFields, field, imaProduct.getDefaultLocaleName());
		    }
		    else if ("course".equals(field.getName()) && PersonContext.isAvailable())
		    {
			    forrmatBuyRateCourse = initFormatByRateField(imaProduct, newFields, field, CurrencyRateType.SALE_REMOTE, null);
			    copyFields(newFields, field, forrmatBuyRateCourse);
		    }
		    else if ("premierShowMsg".equals(field.getName()))
		    {
			    copyFields(newFields, field, "true");
		    }
		    else if ("courseSell".equals(field.getName()) && PersonContext.isAvailable())
		    {
				BigDecimal forrmatBuyRate = initFormatByRateField(imaProduct, newFields, field, CurrencyRateType.BUY_REMOTE, null);
			    copyFields(newFields, field, forrmatBuyRate);
		    }
		    else if ("openingDate".equals(field.getName()))
		    {
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				String dateString = dateFormat.format(Calendar.getInstance().getTime());
			    copyFields(newFields, field, dateString);
		    }
		    else if ("sellCurrency".equals(field.getName()))
		    {
			    try
			    {
				    CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			        Currency nationalCurrency = currencyService.getNationalCurrency();
				    copyFields(newFields, field, nationalCurrency.getCode());
			    }
			    catch(GateException e)
				{
					throw new BusinessException("Ошибка определения валюты продажи", e);
				}			    
		    }

	    }

		return newFields;
	}

	private BigDecimal initFormatByRateField(IMAProduct imaProduct, List<Field> newFields, Field field,
	                                         CurrencyRateType currencyRateType, String tarifPlanCodeType) throws BusinessException
	{
		Currency fromCurrency = imaProduct.getCurrency();
		CurrencyRate buyRate = getCourse(fromCurrency, currencyRateType, tarifPlanCodeType);
		BigDecimal forrmatBuyRate = null;
		try
		{
			forrmatBuyRate = buyRate.getToValue().divide(buyRate.getFromValue(), 4, CurrencyRate.ROUNDING_MODE);
		}
		catch(Exception e)
		{
			throw new BusinessException("Ошибка калибровки валюты ОМС", e);
		}

		return forrmatBuyRate;
	}

	private void copyFields(List<Field> toFields, Field oldField, Object value)
	{
		toFields.remove(oldField);
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(oldField.getName());
		fieldBuilder.setSource(oldField.getSource());
		fieldBuilder.setDescription(oldField.getDescription());
		List<FieldValidator> validators = oldField.getValidators();
		fieldBuilder.addValidators(validators.toArray(new FieldValidator[validators.size()]));
		fieldBuilder.setValueExpression(new ConstantExpression(value));
		fieldBuilder.setInitalValueExpression(new ConstantExpression(value));
		toFields.add(fieldBuilder.build());
	}

	private CurrencyRate getCourse(Currency fromCurrency, CurrencyRateType currencyRateType,
	                               String tarifPlanCodeType) throws BusinessException
	{
		if (fromCurrency == null)
		{
			throw new BusinessException("Валюта открытия ОМС не найдена");
		}

		Currency nationalCurrency = MoneyUtil.getNationalCurrency();
		if (nationalCurrency == null)
		{
			throw new BusinessException("Национальная валюта не найдена");
		}

		if (!PersonContext.isAvailable())
		{
			throw new BusinessException("Клиент должен быть проинициализирован.");
		}

		Department department = departmentService.findById(PersonHelper.getContextPerson().getDepartmentId());
		if (department == null)
		{
			throw new BusinessException("Не удалось найди департамент");
		}

		try
		{
			String tarifPlan = tarifPlanCodeType != null ? tarifPlanCodeType : PersonHelper.getActivePersonTarifPlanCode();
			CurrencyRateService rateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			return rateService.getRate(fromCurrency, nationalCurrency, currencyRateType, department, tarifPlan);
		}
		catch(GateLogicException e)
		{
			throw new BusinessException("Ошибка получения курса покупки металла", e);
		}
		catch(GateException e)
		{
			throw new BusinessException("Ошибка получения курса покупки металла", e);
		}
	}
}
