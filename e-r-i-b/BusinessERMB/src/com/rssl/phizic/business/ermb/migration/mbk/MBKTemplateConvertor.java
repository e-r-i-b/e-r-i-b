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
 * ��������� �������� ��� � ������� ����.
 */
public class MBKTemplateConvertor
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @param mbkTemplateList ������ �������� ���.
	 * @return ������ �������� ����.
	 */
	public List<TemplateDocument> getTemplates(List<MobileBankTemplate> mbkTemplateList,Person person) throws BusinessException, DocumentLogicException, DocumentException, BusinessLogicException
	{
		List<TemplateDocument> templateDocuments = new ArrayList<TemplateDocument>();

		if (mbkTemplateList == null || mbkTemplateList.isEmpty())
			return templateDocuments;

		int templateIndex = 1;
		//������� ���� ��� ����� ������������ "�������" ���������� �������
		boolean isComplexName = false;
		//����� �����, <�������� ����������, ���� ����� ��� ��������
		Map<String,Map<String,List<FieldValuesSource>>> resultMap = new HashMap<String, Map<String, List<FieldValuesSource>>>();
		//����� �����, ������ ��� ���������� (������������ ��� ���������� ������� ���������� ��������)
		Map<String,List<String>> cardNumberAndSpName = new HashMap<String, List<String>>();

		for (MobileBankTemplate mbkTemplate:mbkTemplateList)
		{
			//1.)���� ���������� � ����.
			String recipient = mbkTemplate.getRecipient();
			BillingServiceProvider serviceProvider = providerService.findByMobileBankCode(recipient);
				//���������� �� ��������� �����������
			if (serviceProvider == null)
			{
				log.error("���������� � ����� " + recipient + " �� ������� ����� � ����, ������ ��������.");
				continue;
			}
			String spName = serviceProvider.getName();

			//2.)�������� ������ �������, ������������� � �������� ���� ����������, ��������� ��� ��������� � ������� �� ���� ����
			    //���� �������� ��� ���� ��� �� ����������.
			String[] payerCodes = mbkTemplate.getPayerCodes();
			if (payerCodes.length == 0)
			{
				log.error("��� ������� ��� �� �������  ������ ��������������� ����������� , �������� ������� �� ��������,  ������ �������� .");
				continue;
			}
				//���� ���� � ����������
			String keyFieldName = null;
			List<FieldDescription> keyFields = serviceProvider.getKeyFields();
			for(FieldDescription field:keyFields)
				if (field.isEditable() && field.isVisible())
					keyFieldName = field.getName();
				//���� ����������� ���� �� �������, �� ����� ���������� ������
			if (StringHelper.isEmpty(keyFieldName ))
			{
				log.error("���������� � ������ " + spName + " �� ����� �������, ������������� � �������� �����, ������ ��������.");
				continue;
			}

			String cardNumber = mbkTemplate.getCardInfo().getCardNumber();
			//3.) ��������� ������ ���������� �������
			//���� ��� ������ ��� ���������� ������� ����������, �� ����� �������� �� ��������
			if (!isComplexName)
			{
				//����� ����������� �� ������� �����
				List<String> currentSpNames = cardNumberAndSpName.get(cardNumber);
				if (currentSpNames == null)
					currentSpNames = new ArrayList<String>();
				currentSpNames.add(spName);
				cardNumberAndSpName.put(cardNumber, currentSpNames);

				for(String cNumber:cardNumberAndSpName.keySet())
				{
					//������� ����� ����������
					if (StringHelper.equals(cardNumber,cNumber))
						continue;
					List<String> spNames = cardNumberAndSpName.get(cNumber);
					//���� ���� � ���� ��� ���������� ����������� � ������ �����
					if (CollectionUtils.containsAny(currentSpNames,spNames))
					{
						isComplexName = true;
						break;

					}
				}
			}
				//����������� ���� ��� ����������/������ �����
			Map<String,List<FieldValuesSource>> spNameAndFields = resultMap.get(cardNumber);
			if (spNameAndFields == null)
				spNameAndFields = new HashMap<String,List<FieldValuesSource>>();
				//���� �������� ����������
			List<FieldValuesSource> curFields = spNameAndFields.get(spName);
			if (curFields == null)
				curFields = new ArrayList<FieldValuesSource>();
			//3.���������� ��������, � ������� � ������ �����
			for(String payerCode:payerCodes)
			{
				Map<String,String> fieldMap = new HashMap<String,String>();
				fieldMap.put(keyFieldName,payerCode);
				fieldMap.put(PaymentFieldKeys.PROVIDER_KEY,serviceProvider.getId().toString());
				FieldValuesSource valuesSource = new MapValuesSource(fieldMap);
				curFields.add(valuesSource);
			}
			//��������� ���� �� ����������
			spNameAndFields.put(spName, curFields);
			//��������� ���� ���������/���� �� ������ �����
			resultMap.put(cardNumber,spNameAndFields);
		}
		//4.���������� ����� � ������� �������.
		for(String cardNumber:resultMap.keySet())
		{
			//��������� 4 �����  ������ �����
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
	 * ��������������� ������ ��� � ���� �� �������� �������� �� ����
	 *
	 * @param person �������
	 * @param recipient ����������
	 * @param fromResource ������ ��������
	 * @param payerCodes ������ ��������������� �������
	 * @return ������ ����
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

		//������ ���������� �������� ����
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

	//� �������� ����� ������� ���� ������ ������� �������� 100 (�������� ����� ������ ���� �������������)
	//����� ������� �� ������ ��������� ����� �� ��
	private Money getTemplateAmount(BillingServiceProvider recipient) throws BusinessLogicException, BusinessException
	{
		BigDecimal defaultTemplateAmount = ConfigFactory.getConfig(FPPMigrationConfig.class).getTemplateAmount();
		SumRestrictions summRestrictions = recipient.getRestrictions();
		if (summRestrictions != null)
		{
			BigDecimal maxSumm = summRestrictions.getMaximumSum();
			BigDecimal minSumm = summRestrictions.getMinimumSum();

			String format = "����� �� ���������: %s ��������������� ������� %s ����������� �� ����������: %s";
			if(maxSumm != null && maxSumm.compareTo(defaultTemplateAmount) < 0)
			{
				throw new BusinessLogicException(String.format(format, defaultTemplateAmount, "������", maxSumm));
			}
			if(minSumm != null && minSumm.compareTo(defaultTemplateAmount) > 0)
			{
				throw new BusinessLogicException(String.format(format, defaultTemplateAmount, "������", minSumm));
			}
		}

		return new Money(defaultTemplateAmount, MoneyUtil.getNationalCurrency());
	}
}
