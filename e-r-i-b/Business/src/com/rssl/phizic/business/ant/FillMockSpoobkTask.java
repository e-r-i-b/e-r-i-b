package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.xml.EmptyResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Таск для создания заглушечной таблицы справочника СПООБК2
 * @author Pankin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class FillMockSpoobkTask extends SafeTaskBase
{
	private final Set<ExtendedCodeImpl> oldCodes = new HashSet<ExtendedCodeImpl>();
	private String file;

	public void safeExecute() throws Exception
	{
		if (StringHelper.isEmpty(file))
			throw new Exception("Файл не может быть пустым");

		final String spoobkTableName = ConfigFactory.getConfig(BusinessSettingsConfig.class).getSpoobkTableName();
		if (StringHelper.isEmpty(spoobkTableName))
			throw new Exception("Не задано название таблицы с инофрмацией о подразделениях СПООБК.");
		log(String.format("Данные будут добавлены в таблицу \"%S\"",spoobkTableName));
		log("Начато чтение кодов подразделений");

		XMLFilter parser = new SaxFilter(XmlHelper.newXMLReader());
		InputStream stream = null;
		try
		{
			stream = new FileInputStream(file);
			if (stream != null)
			{
				parser.setEntityResolver(new EmptyResolver());
				parser.parse(new InputSource(stream));
			}
		}
		finally
		{
			if (stream != null)
				stream.close();
		}

		log("Прочитано " + oldCodes.size() + " кодов подразделений");

		for (final ExtendedCodeImpl code : oldCodes)
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(),
					"com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.addSpoobkMockRecord");
			query.setParameter("terbank",   code.getRegion());
			query.setParameter("branch",    code.getBranch());
			query.setParameter("subbranch", code.getOffice());
			query.setParameter("despatch",  RandomHelper.rand(10, RandomHelper.DIGITS));
			query.setParameter("date_suc", null);
			query.setParameter("table_name", spoobkTableName);

			query.executeUpdate();
		}

		log("Завершено добавление кодов подразделений");
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private final static String ELEMENT_TABLE = "table";
		private final static String ELEMENT_FIELD = "field";
		private final static String ELEMENT_RECORD = "record";

		private final static String ATTRIBUTE_NAME = "name";
		private final static String ATTRIBUTE_VALUE = "value";

		private boolean successor;
		private ExtendedCodeImpl code;

		private SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (atts.getValue(ATTRIBUTE_NAME).equals("SUCCESSOR"))
				{
					successor = true;
				}
			}

			if (successor)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					code = new ExtendedCodeImpl();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = atts.getValue(ATTRIBUTE_NAME);
					String fieldValue = StringHelper.getEmptyIfNull( atts.getValue(ATTRIBUTE_VALUE) ).trim();

					if (fieldName.equals("TERBANK_CL"))
					{
						fieldValue = StringHelper.removeLeadingZeros(fieldValue);
						code.setRegion(StringHelper.isEmpty(fieldValue) ? fieldValue : StringHelper.addLeadingZeros(fieldValue, 2));
					}
					else if (fieldName.equals("BRANCH_CL"))
					{
						code.setBranch(StringHelper.isEmpty(fieldValue) ? fieldValue : StringHelper.addLeadingZeros(fieldValue, 4));
					}
					else if (fieldName.equals("SUBBRANCH_CL"))
					{
						code.setOffice(StringHelper.isEmpty(fieldValue) ? fieldValue : StringHelper.addLeadingZeros(fieldValue, 5));
					}
				}
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (successor)
			{
				if (ELEMENT_TABLE.equals(qName))
				{
					successor = false;
				}

				if (ELEMENT_RECORD.equals(qName))
				{
					oldCodes.add(code);
				}
			}
		}
	}
}
