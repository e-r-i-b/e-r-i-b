package com.rssl.phizic.business.dictionaries.defCodes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Парсер справочника Def-кодов
 * @author Rtischeva
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DefCodeReplicaSource extends XmlReplicaSourceBase
{
	public static final String DEFAULT_FILE_NAME = "defCodes.xml";
	private List<DefCode> defCodes = new ArrayList<DefCode>();
	private SynchronizeResultImpl results;

	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final BillingService billingService = new BillingService();

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		return defCodes.iterator();
	}

	@Override
	protected void clearData()
	{
		defCodes.clear();
		results = new SynchronizeResultImpl();
	}

	@Override
	protected InputStream getDefaultStream()
	{
		return getResourceReader(DEFAULT_FILE_NAME);
	}

	@Override
	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SAXFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SAXFilter(super.chainXMLReader(xmlReader));
	}

	/**
	 * @return результат синхронизации
	 */
	public SynchronizeResult getSynchronizeResult()
	{
		return results;
	}

	private class SAXFilter extends XMLFilterImpl
	{
		private static final String BODY = "body";
		private static final String NUMBER_FROM = "NUMBERFROM";
		private static final String NUMBER_TO = "NUMBERFTO";
		private static final String SERVICE_ID = "SERVICE_ID";
		private static final String MNC = "MNC";

		private DefCode defCode;
		private StringBuilder tagValue = new StringBuilder();
		private boolean readValue;

		private Billing billing;

		private SAXFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase(BODY))
			{
				defCode = new DefCode();
			}
			else if (qName.equals(NUMBER_FROM))
			{
				readValue = true;
			}
			else if (qName.equals(NUMBER_TO))
			{
				readValue = true;
			}
			else if (qName.equals(SERVICE_ID))
			{
				readValue = true;
			}
			else if (qName.equals(MNC))
			{
				readValue = true;
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			try
			{
				if (qName.equalsIgnoreCase(NUMBER_FROM))
				{
					defCode.setDefCodeFrom(tagValue.toString());
					readValue = false;
				}
				else if (qName.equalsIgnoreCase(NUMBER_TO))
				{
					defCode.setDefCodeTo(tagValue.toString());
					readValue = false;
				}
				else if (qName.equals(SERVICE_ID))
				{
					if (StringHelper.isEmpty(tagValue.toString()))
						saveResults(SynchronizeResultStatus.FAIL, "EMPTY_SERVICE_ID", "Для указанного диапазона def-кодов в справочнике не указан SERVICE_ID: "+ defCode.getSynchKey());
					else
					{
						String providerCode = tagValue.toString();
						findMobileProvider(providerCode);
					}
					readValue = false;
				}
				else if (qName.equalsIgnoreCase(BODY))
				{
					if (StringHelper.isNotEmpty(defCode.getProviderCode()))
					{
						defCodes.add(defCode);
						saveResults(SynchronizeResultStatus.SUCCESS, "SUCCESS", "Для диапазона def-кодов: "+ defCode.getSynchKey() + " добавлена запись в справочник");
					}
					readValue = false;
				}
				else if (qName.equalsIgnoreCase(MNC))
				{
					defCode.setMnc(Long.valueOf(tagValue.toString()));
					readValue = false;
				}
			}
			finally
			{
				tagValue = new StringBuilder();
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (readValue)
				tagValue.append(ch, start, length);
		}

		/**
		 * Сохранение результатов и логирование
		 * @param resultStatus  - статус сообщения: ошибка или нет
		 * @param messageCode - код сообщения
		 * @param logMessage - сообщение для логирования
		 */
		private void saveResults(SynchronizeResultStatus resultStatus, String messageCode, String logMessage)
		{
			SynchronizeResultRecord record = new SynchronizeResultRecord();

			record.setStatus(resultStatus);
			List<String> errorDescriptions = new ArrayList<String>();
			errorDescriptions.add(messageCode);
			record.setErrorDescriptions(errorDescriptions);
			record.setSynchKey(defCode.getSynchKey());

			if (SynchronizeResultStatus.FAIL.equals(resultStatus))
			{
				LOG.error(logMessage);
			}
			else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
			{
				LOG.info(logMessage);
			}

			results.setDictionaryType(DictionaryType.DEF_CODES);
			results.addResultRecord(record);
		}

		private void findMobileProvider(String providerCode) throws SAXException
		{
			if (billing == null)
				findBilling();

			try
			{
				List<BillingServiceProvider> providerList = providerService.find(providerCode, billing);
				int providerListSize = providerList.size();
				if (providerListSize == 0)
					saveResults(SynchronizeResultStatus.FAIL, "PROVIDER_NOT_FOUND", "Для диапазона def-кодов "+ defCode.getSynchKey() + " не найден поставщик услуг c SERVICE_ID " + providerCode);
				else if (providerListSize > 1)
				{
					saveResults(SynchronizeResultStatus.FAIL, "PROVIDER_MORE_THAN_ONE", "Для диапазона def-кодов "+ defCode.getSynchKey() + " найдено больше одного поставщика услуг c SERVICE_ID " + providerCode);
				}
				else
					defCode.setProviderCode(providerCode);
			}
			catch (BusinessException e)
			{
				throw new SAXException("Ошибка получения поставщика услуг", e);
			}
		}

		private void findBilling() throws SAXException
		{
			try
			{
				Adapter cardTransferAdapter = ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter();
				billing = billingService.getByAdapterUUID(cardTransferAdapter.getUUID());
			}
			catch (BusinessException e)
			{
				throw new SAXException("Ошибка получения биллинга iqwave", e);
			}
		}
	}

}
