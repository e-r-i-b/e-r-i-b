package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.SumRestrictions;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.TransferTemplateBase;
import com.rssl.phizic.business.documents.templates.source.MbkNewTemplateSource;
import com.rssl.phizic.business.documents.templates.source.NewTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.FPPMigrationConfig;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Moshenko
 * @ created 18.11.2013
 * @ $Author$
 * @ $Revision$
 * Конвертор шаблонов МБК в шаблоны ЕРИБ.
 */
public class MBKTemplateConvertor
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @param mbkTemplateList Список шаблонов МБК.
	 * @return Список шаблонов ЕРМБ.
	 */
	public List<TemplateDocument> getTemplates(List<MobileBankTemplate> mbkTemplateList,Person person) throws BusinessException, DocumentLogicException, DocumentException, BusinessLogicException
	{
		List<TemplateDocument> templateDocuments = new ArrayList<TemplateDocument>();

		if (mbkTemplateList == null || mbkTemplateList.isEmpty())
			return templateDocuments;

		int templateIndex = 1;
		//Признак того что будим использовать "сложное" именование шаблонв
		boolean isComplexName = false;
		//Номер карты, <Название поставщика, Сорс полей для шаблонов
		Map<String,Map<String,List<FieldValuesSource>>> resultMap = new HashMap<String, Map<String, List<FieldValuesSource>>>();
		//Номер карты, Список имён поставщика (используется для определена формата именования шаблонов)
		Map<String,List<String>> cardNumberAndSpName = new HashMap<String, List<String>>();

		for (MobileBankTemplate mbkTemplate:mbkTemplateList)
		{
			//1.)Ищем поставщика в ЕРИБ.
			String recipient = mbkTemplate.getRecipient();
			BillingServiceProvider serviceProvider = providerService.findByMobileBankCode(recipient);
				//пропускаем не найденных поставщиков
			if (serviceProvider == null)
			{
				log.error("Поставщика с кодом " + recipient + " не удалось найти в ЕРИБ, шаблон пропущен.");
				continue;
			}
			String spName = serviceProvider.getName();

			//2.)Получаем первое видимое, редактируемое и ключевое поле поставщика, заполняем его значением и создаем по нему сорс
			    //Если значений для поля нет то пропускаем.
			String[] payerCodes = mbkTemplate.getPayerCodes();
			if (payerCodes.length == 0)
			{
				log.error("Для шаблона МБК не передан  список идентификаторов плательщика , создание шаблона не возможно,  шаблон пропущен .");
				continue;
			}
				//Ищим поле в поставщике
			String keyFieldName = null;
			List<FieldDescription> keyFields = serviceProvider.getKeyFields();
			for(FieldDescription field:keyFields)
				if (field.isEditable() && field.isVisible())
					keyFieldName = field.getName();
				//если подходящего поля не найдено, то также пропускаем шаблон
			if (StringHelper.isEmpty(keyFieldName ))
			{
				log.error("Поставщика с именем " + spName + " не имеет видимых, редактируемых и ключевых полей, шаблон пропущен.");
				continue;
			}

			String cardNumber = mbkTemplate.getCardInfo().getCardNumber();
			//3.) Проверяем формат именования шаблона
			//если уже поняли что используем сложное именование, то более проверку не проводим
			if (!isComplexName)
			{
				//Имена поставщиков по текущей карте
				List<String> currentSpNames = cardNumberAndSpName.get(cardNumber);
				if (currentSpNames == null)
					currentSpNames = new ArrayList<String>();
				currentSpNames.add(spName);
				cardNumberAndSpName.put(cardNumber, currentSpNames);

				for(String cNumber:cardNumberAndSpName.keySet())
				{
					//Текущую карту пропускаем
					if (StringHelper.equals(cardNumber,cNumber))
						continue;
					List<String> spNames = cardNumberAndSpName.get(cNumber);
					//Если одно и тоже имя поставщика встречается у другой карты
					if (CollectionUtils.containsAny(currentSpNames,spNames))
					{
						isComplexName = true;
						break;

					}
				}
			}
				//вытаскиваем пары Имя поставщика/Список полей
			Map<String,List<FieldValuesSource>> spNameAndFields = resultMap.get(cardNumber);
			if (spNameAndFields == null)
				spNameAndFields = new HashMap<String,List<FieldValuesSource>>();
				//поля текущего поставщика
			List<FieldValuesSource> curFields = spNameAndFields.get(spName);
			if (curFields == null)
				curFields = new ArrayList<FieldValuesSource>();
			//3.Перебираем значение, и заносим в список полйе
			for(String payerCode:payerCodes)
			{
				Map<String,String> fieldMap = new HashMap<String,String>();
				fieldMap.put(keyFieldName,payerCode);
				fieldMap.put(PaymentFieldKeys.PROVIDER_KEY,serviceProvider.getId().toString());
				FieldValuesSource valuesSource = new MapValuesSource(fieldMap);
				curFields.add(valuesSource);
			}
			//обновляем поля по поставщику
			spNameAndFields.put(spName, curFields);
			//обновляем пары поставщик/поля по номеру карты
			resultMap.put(cardNumber,spNameAndFields);
		}
		//4.Гинерируем имена и создаем шаблоны.
		for(String cardNumber:resultMap.keySet())
		{
			//Последние 4 цифры  номера карты
			String cutCardNumber = StringUtils.right(cardNumber, 4);
			Map<String,List<FieldValuesSource>> spNameAndFields =  resultMap.get(cardNumber);
			for(String spName:spNameAndFields.keySet())
			{
				List<FieldValuesSource> fields = spNameAndFields.get(spName);
				for(FieldValuesSource field:fields)
				{
					String tamplateName = null;
					if (isComplexName)
						tamplateName = spName + "_" + templateIndex + "_" +cutCardNumber;
					else
						tamplateName = spName + "_" + templateIndex;
					NewTemplateSource templateSource = new NewTemplateSource(FormConstants.SERVICE_PAYMENT_FORM,field,CreationType.internet,person,tamplateName);
					templateDocuments.add(templateSource.getTemplate());
					templateIndex++;
				}
			}
		}
		return templateDocuments;
	}

	/**
	 * Сконвертировать шаблон МБК в ЕРИБ по правилам миграции на лету
	 *
	 * @param person персона
	 * @param recipient получатель
	 * @param fromResource ресурс списания
	 * @param payerCodes список идентификаторов платежа
	 * @return шаблон ЕРИБ
	 */
	public TemplateDocument getTemplate(Person person, BillingServiceProvider recipient, CardLink fromResource, List<String> payerCodes) throws BusinessLogicException, BusinessException
	{
		//noinspection deprecation
		MobileBankUtils.testServiceProviderMobilebankCompatible(recipient);
		String keyFieldName = recipient.getKeyFields().get(0).getExternalId();
		Money templateAmount = getTemplateAmount(recipient);

		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(PaymentFieldKeys.PROVIDER_KEY, recipient.getId().toString());
		fieldMap.put(PaymentFieldKeys.FROM_RESOURCE_KEY, fromResource.getCode());
		fieldMap.put("fromResourceCurrency", fromResource.getCurrency().getCode());
		fieldMap.put("fromResourceType", fromResource.getClass().getName());
		fieldMap.put("fromResourceLink", fromResource.getCode());
		fieldMap.put(PaymentFieldKeys.AMOUNT, templateAmount.getDecimal().toPlainString());
		fieldMap.put(PaymentFieldKeys.CURRENCY, templateAmount.getCurrency().getCode());

		//Первое попавшееся непустое поле
		for (String payerCode : payerCodes)
		{
			if (StringHelper.isNotEmpty(payerCode))
			{
				fieldMap.put(keyFieldName, payerCode);
				break;
			}
		}
		FieldValuesSource initialValuesSource = new MapValuesSource(fieldMap);
		TemplateDocumentSource source = new MbkNewTemplateSource(FormConstants.SERVICE_PAYMENT_FORM, initialValuesSource, person);

		try
		{
			TransferTemplateBase template = (TransferTemplateBase) source.getTemplate();
			template.setDestinationAmount(templateAmount);
			template.setChargeOffResource(fromResource.getNumber());
			template.setExtendedFields((List)recipient.getFieldDescriptions());
			return template;
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	//В качестве суммы шаблона ЕРИБ должен указать значение 100 (значение суммы должно быть настраиваемым)
	//Сумма шаблона не должна превышать лимит на ПУ
	private Money getTemplateAmount(BillingServiceProvider recipient) throws BusinessLogicException, BusinessException
	{
		BigDecimal defaultTemplateAmount = ConfigFactory.getConfig(FPPMigrationConfig.class).getTemplateAmount();
		SumRestrictions summRestrictions = recipient.getRestrictions();
		if (summRestrictions != null)
		{
			BigDecimal maxSumm = summRestrictions.getMaximumSum();
			BigDecimal minSumm = summRestrictions.getMinimumSum();

			String format = "Сумма по умолчанию: %s конвертируемого шаблона %s ограничения на поставщика: %s";
			if(maxSumm != null && maxSumm.compareTo(defaultTemplateAmount) < 0)
			{
				throw new BusinessLogicException(String.format(format, defaultTemplateAmount, "больше", maxSumm));
			}
			if(minSumm != null && minSumm.compareTo(defaultTemplateAmount) > 0)
			{
				throw new BusinessLogicException(String.format(format, defaultTemplateAmount, "меньше", minSumm));
			}
		}

		return new Money(defaultTemplateAmount, MoneyUtil.getNationalCurrency());
	}
}
