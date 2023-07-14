package com.rssl.phizic.business.dictionaries.cellNumberArray;

import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.gate.dictionaries.DictionaryType;
import com.rssl.phizic.gate.dictionaries.SynchronizeResultRecord;
import com.rssl.phizic.gate.dictionaries.SynchronizeResultStatus;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shapin
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 */
public class NumberArrayXmlHandler extends DefaultHandler
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private SynchronizeResultImpl results = new SynchronizeResultImpl();

	private static final String UPDATE_SECTION = "UPDATE";
	private static final String BODY = "body";
	private static final String NUMBERFROM = "NUMBERFROM";
	private static final String NUMBERTO = "NUMBERTO";
	private static final String OWNERID = "OWNERID";
	private static final String ORGNAME = "ORGNAME";
	private static final String MNC = "MNC";
	private static final String REGIONCODE = "REGIONCODE";
	private static final String SERVICE_ID = "SERVICE_ID";
	private static final String SERVICE_CODE = "SERVICE_CODE";
	private static final String CASH_SERVICE_ID = "CASH_SERVICE_ID";
	private static final String MBOPERATOR_ID = "MBOPERATOR_ID";
	private static final String MBOPERATOR_NUMBER = "MBOPERATOR_NUMBER";
	private static final String PROVIDER_ID = "PROVIDER_ID";


	private String fileName;
	private NumberArray tmpNumberArray;

	private StringBuilder tagValue = new StringBuilder();
	private boolean readValue;
	private boolean updateSection;

	private List<NumberArray> numberArrays = new ArrayList<NumberArray>();

	public NumberArrayXmlHandler(String fileName)
	{
		this.fileName = fileName;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equalsIgnoreCase(UPDATE_SECTION))
		{
			updateSection = true;
		}
		if (qName.equalsIgnoreCase(BODY) && updateSection)
		{
			tmpNumberArray = new NumberArray();
		}
		else if (qName.equals(NUMBERFROM) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(NUMBERTO) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(OWNERID) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(ORGNAME) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(MNC) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(REGIONCODE) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(SERVICE_ID) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(SERVICE_CODE) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(CASH_SERVICE_ID) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(MBOPERATOR_ID) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(MBOPERATOR_NUMBER) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equals(PROVIDER_ID) && updateSection)
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
			else if (qName.equalsIgnoreCase(NUMBERFROM) && updateSection)
			{
				tmpNumberArray.setNumberFrom(Long.parseLong(tagValue.toString()));
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(NUMBERTO) && updateSection)
			{
				tmpNumberArray.setNumberTo(Long.parseLong(tagValue.toString()));
				readValue = false;
			}
			else if (qName.equals(OWNERID) && updateSection)
			{
				tmpNumberArray.setOwnerId(tagValue.toString());
				readValue = false;
			}
			else if (qName.equals(ORGNAME) && updateSection)
			{
				tmpNumberArray.setOrgName(tagValue.toString());
				readValue = false;
			}
			else if (qName.equals(MNC) && updateSection)
			{
				tmpNumberArray.setMnc(tagValue.toString());
				readValue = false;
			}
			else if (qName.equals(REGIONCODE) && updateSection)
			{
				tmpNumberArray.setRegionCode(tagValue.toString());
				readValue = false;
			}
			else if (qName.equals(SERVICE_ID) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setServiceId(tagValue.toString());
			}
			else if (qName.equals(SERVICE_CODE) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setServiceCode(tagValue.toString());
			}
			else if (qName.equals(CASH_SERVICE_ID) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setCashServiceId(tagValue.toString());
			}
			else if (qName.equals(MBOPERATOR_ID) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setMbOperatorId(tagValue.toString());
			}
			else if (qName.equals(MBOPERATOR_NUMBER) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setMbOperatorNumber(tagValue.toString());
			}
			else if (qName.equals(PROVIDER_ID) && updateSection)
			{
				readValue = false;
				tmpNumberArray.setProviderId(tagValue.toString());
			}
			else if (qName.equalsIgnoreCase(BODY) && updateSection)
			{
				//Условие уникальности
				if (tmpNumberArray.getNumberFrom() != 0 && tmpNumberArray.getNumberTo() != 0 && tmpNumberArray.getOwnerId() != null &&
						tmpNumberArray.getMnc() != null && tmpNumberArray.getRegionCode() != null)
				{
					numberArrays.add(tmpNumberArray);
					saveResults(SynchronizeResultStatus.SUCCESS, "SUCCESS", "Запись" + tmpNumberArray.getSynchKey() + "добавлена в справочник номерных емкостей.");
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
		record.setSynchKey(tmpNumberArray.getSynchKey());

		if (SynchronizeResultStatus.FAIL.equals(resultStatus))
		{
			LOG.error(logMessage);
		}
		else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
		{
			LOG.info(logMessage);
		}

		results.setDictionaryType(DictionaryType.NUMBER_ARRAYS);
		results.addResultRecord(record);
	}

	public SynchronizeResultImpl getResults()
	{
		return results;
	}

	public List<NumberArray> getNumberArrays()
	{
		return numberArrays;
	}

}
