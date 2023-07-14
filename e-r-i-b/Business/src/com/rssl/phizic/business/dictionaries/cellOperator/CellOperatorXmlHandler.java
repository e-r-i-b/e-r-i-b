package com.rssl.phizic.business.dictionaries.cellOperator;

import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.gate.dictionaries.DictionaryType;
import com.rssl.phizic.gate.dictionaries.SynchronizeResultRecord;
import com.rssl.phizic.gate.dictionaries.SynchronizeResultStatus;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shapin
 * @ created 27.05.15
 * @ $Author$
 * @ $Revision$
 */
public class CellOperatorXmlHandler extends DefaultHandler
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private SynchronizeResultImpl results = new SynchronizeResultImpl();

	private static final String UPDATE_SECTION = "UPDATE";
	private static final String BODY = "body";
	private static final String ORGNAME = "ORGNAME";
	private static final String ORGCODE = "ORGCODE";
	private static final String MNC = "MNC";
	private static final String TIN = "TIN";
	private static final String MBOPERATOR_NUMBER = "MBOPERATOR_Number";
	private static final String FL_AUTO = "FL_AUTO";
	private static final String BALANCE = "BALANCE";
	private static final String MIN_SUMM = "MIN_SUMM";
	private static final String MAX_SUMM = "MAX_SUMM";



	private String fileName;
	private CellOperator tmpCellOperator;

	private StringBuilder tagValue = new StringBuilder();
	private boolean readValue;
	private boolean updateSection;

	private List<CellOperator> cellOperators = new ArrayList<CellOperator>();

	public CellOperatorXmlHandler(String fileName)
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
			tmpCellOperator = new CellOperator();
		}
		else if (qName.equalsIgnoreCase(ORGNAME) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(ORGCODE) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(MNC) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(TIN) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(MBOPERATOR_NUMBER) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(FL_AUTO) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(BALANCE) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(MIN_SUMM) && updateSection)
		{
			readValue = true;
		}
		else if (qName.equalsIgnoreCase(MAX_SUMM) && updateSection)
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
			else if (qName.equalsIgnoreCase(ORGNAME) && updateSection)
			{
				tmpCellOperator.setOrgName(tagValue.toString());
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(ORGCODE) && updateSection)
			{
				tmpCellOperator.setOrgCode(tagValue.toString());
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(MNC) && updateSection)
			{
				tmpCellOperator.setMnc(tagValue.toString());
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(TIN) && updateSection)
			{
				tmpCellOperator.setTin(tagValue != null ? tagValue.toString() : null);
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(MBOPERATOR_NUMBER) && updateSection)
			{
				tmpCellOperator.setMbOperatorNumber(Long.parseLong(StringHelper.isNotEmpty(tagValue.toString()) ? tagValue.toString() : "0"));
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(FL_AUTO) && updateSection)
			{
				tmpCellOperator.setFlAuto(tagValue != null ? tagValue.toString() : null);
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(BALANCE) && updateSection)
			{
				tmpCellOperator.setBalance(Long.parseLong(StringHelper.isNotEmpty(tagValue.toString()) ? tagValue.toString() : "0"));
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(MIN_SUMM) && updateSection)
			{
				tmpCellOperator.setMinSumm(Long.parseLong(StringHelper.isNotEmpty(tagValue.toString()) ? tagValue.toString() : "0"));
				readValue = false;
			}
			else if (qName.equalsIgnoreCase(MAX_SUMM) && updateSection)
			{
				tmpCellOperator.setMaxSumm(Long.parseLong(StringHelper.isNotEmpty(tagValue.toString()) ? tagValue.toString() : "0"));
				readValue = false;
			}

			else if (qName.equalsIgnoreCase(BODY) && updateSection)
			{
				//”словие уникальности
				if (tmpCellOperator.getOrgCode() != null && tmpCellOperator.getMnc() != null)
				{
					cellOperators.add(tmpCellOperator);
					saveResults(SynchronizeResultStatus.SUCCESS, "SUCCESS", "«апись" + tmpCellOperator.getSynchKey() + "добавлена в справочник операторов сотовой св€зи.");
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
	 * —охранение результатов и логирование
	 * @param resultStatus  - статус сообщени€: ошибка или нет
	 * @param messageCode - код сообщени€
	 * @param logMessage - сообщение дл€ логировани€
	 */
	private void saveResults(SynchronizeResultStatus resultStatus, String messageCode, String logMessage)
	{
		SynchronizeResultRecord record = new SynchronizeResultRecord();

		record.setStatus(resultStatus);
		List<String> errorDescriptions = new ArrayList<String>();
		errorDescriptions.add(messageCode);
		record.setErrorDescriptions(errorDescriptions);
		record.setSynchKey(tmpCellOperator.getSynchKey());

		if (SynchronizeResultStatus.FAIL.equals(resultStatus))
		{
			LOG.error(logMessage);
		}
		else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
		{
			LOG.info(logMessage);
		}

		results.setDictionaryType(DictionaryType.CELL_OPERATORS);
		results.addResultRecord(record);
	}

	public SynchronizeResultImpl getResults()
	{
		return results;
	}

	public List<CellOperator> getCellOperators()
	{
		return cellOperators;
	}

}
