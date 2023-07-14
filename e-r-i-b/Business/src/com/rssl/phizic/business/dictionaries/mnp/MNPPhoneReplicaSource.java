package com.rssl.phizic.business.dictionaries.mnp;

import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Rtischeva
 * @ created 21.04.15
 * @ $Author$
 * @ $Revision$
 */
public class MNPPhoneReplicaSource extends XmlReplicaSourceBase
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private List<MNPPhone> mnpPhones = new ArrayList<MNPPhone>();
	private SynchronizeResultImpl results;
	private String fileName = "empty";

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		return mnpPhones.iterator();
	}

	@Override
	protected void clearData()
	{
		mnpPhones.clear();
		results = new SynchronizeResultImpl();
	}

	@Override
	protected InputStream getDefaultStream()
	{
		return null;
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
		private static final String UPDATE_SECTION = "UPDATE";
		private static final String BODY = "body";
		private static final String PHONE_NUMBER = "PHONE_NO";
		private static final String SERVICE_ID = "SERVICE_ID";
		private static final String START_DATE = "START_DATE";
		private static final String MNC = "MNC";

		private MNPPhone mnpPhone;
		private StringBuilder tagValue = new StringBuilder();
		private boolean readValue;
		private boolean updateSection;

		private SAXFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase(UPDATE_SECTION))
			{
				updateSection = true;
			}
			if (qName.equalsIgnoreCase(BODY) && updateSection)
			{
				mnpPhone = new MNPPhone();
			}
			else if (qName.equals(PHONE_NUMBER) && updateSection)
			{
				readValue = true;
			}
			else if (qName.equals(SERVICE_ID) && updateSection)
			{
				readValue = true;
			}
			else if (qName.equals(START_DATE) && updateSection)
			{
				readValue = true;
			}
			else if (qName.equals(MNC) && updateSection)
			{
				readValue = true;
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			try
			{
				if (qName.equalsIgnoreCase(UPDATE_SECTION))
				{
					updateSection = false;
				}
				else if (qName.equalsIgnoreCase(PHONE_NUMBER) && updateSection)
				{
					String phoneNumber = tagValue.toString();
					try
					{
						PhoneNumberFormat.SIMPLE_NUMBER.parse(phoneNumber);
						mnpPhone.setPhoneNumber(tagValue.toString());
					}
					catch (NumberFormatException e)
					{
						saveResults(SynchronizeResultStatus.FAIL, "FAIL", "Некорректный формат телефона: " + phoneNumber);
					}
					readValue = false;
				}
				else if (qName.equalsIgnoreCase(SERVICE_ID) && updateSection)
				{
					mnpPhone.setProviderCode(tagValue.toString());
					readValue = false;
				}
				else if (qName.equals(START_DATE) && updateSection)
				{
					try
					{
						Calendar movingDate = DateHelper.parseCalendar(tagValue.toString(), "yyyy-MM-dd");
						if (movingDate.compareTo(DateHelper.getCurrentDate()) > 0)
							saveResults(SynchronizeResultStatus.FAIL, "FAIL", "Дата переноса не может быть будущей датой: " + tagValue.toString());
						else
							mnpPhone.setMovingDate(movingDate);
					}
					catch (ParseException e)
					{
						saveResults(SynchronizeResultStatus.FAIL, "FAIL", "Некорректный формат даты: " + tagValue.toString());
					}
					readValue = false;
				}
				else if (qName.equals(MNC) && updateSection)
				{
					mnpPhone.setMnc(Long.valueOf(tagValue.toString()));
					readValue = false;
				}
				else if (qName.equalsIgnoreCase(BODY) && updateSection)
				{
					mnpPhone.setSourceFileName(fileName);

					if (mnpPhone.getPhoneNumber() != null && mnpPhone.getMovingDate() != null)
					{
						mnpPhones.add(mnpPhone);
						saveResults(SynchronizeResultStatus.SUCCESS, "SUCCESS", "Для телефона " + mnpPhone.getPhoneNumber() + "добавлена запись в справочник mnp-телефонов");
					}
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
			record.setSynchKey(mnpPhone.getSynchKey());

			if (SynchronizeResultStatus.FAIL.equals(resultStatus))
			{
				LOG.error(logMessage);
			}
			else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
			{
				LOG.info(logMessage);
			}

			results.setDictionaryType(DictionaryType.MNP_PHONES);
			results.addResultRecord(record);
		}
	}
}
