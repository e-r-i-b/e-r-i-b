package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsReplicaSource extends XmlReplicaSourceBase
{
	protected final static String ELEMENT_TABLE   = "table";
	protected final static String ELEMENT_FIELD   = "field";
	protected final static String ELEMENT_RECORD  = "record";
	protected final static String ATTRIBUTE_NAME  = "name";
	protected final static String ATTRIBUTE_VALUE = "value";

	protected void clearData()
	{
	}

	protected InputStream getDefaultStream()
	{
		return null;//getResourceReader(com.rssl.phizic.business.Constants.DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return null;
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		return null;
	}

	protected Calendar parseDate(String value) throws SAXException
	{
		if (StringHelper.isEmpty(value))
			return null;

		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		try
		{
			Date date = formatter.parse(value);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		}
		catch (ParseException e)
		{
			throw new SAXException("Ошибка парсинга даты " + value, e);
		}
	}

	protected BigDecimal parseBigDecimal(String value) throws SAXException
	{
		if (StringHelper.isEmpty(value))
			return null;
		try
		{
			return NumericUtil.parseBigDecimal(value);
		}
		catch (ParseException e)
		{
			throw new SAXException("Ошибка парсинга числа с десятичной точкой " + value, e);
		}
	}

	protected Boolean parseBoolean(String value)
	{
		return "1".equals(StringHelper.getEmptyIfNull(value));
	}
}
