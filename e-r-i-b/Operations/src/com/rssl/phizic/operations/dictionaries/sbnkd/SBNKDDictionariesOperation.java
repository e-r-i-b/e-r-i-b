package com.rssl.phizic.operations.dictionaries.sbnkd;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.business.dictionaries.cellNumberArray.NumberArray;
import com.rssl.phizic.business.dictionaries.cellNumberArray.NumberArrayXmlHandler;
import com.rssl.phizic.business.dictionaries.cellOperator.CellOperator;
import com.rssl.phizic.business.dictionaries.cellOperator.CellOperatorXmlHandler;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.DictionaryType;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author shapin
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDDictionariesOperation extends OperationBase
{
	public SynchronizeResult load(final InputStream stream, final String fileName, final String dictName) throws BusinessException
	{
		SynchronizeResultImpl result = null;
		if(dictName.equalsIgnoreCase("NumberArrays"))
		{
			result = (SynchronizeResultImpl) loadNumberArraysDict(stream, fileName);
		}
		if(dictName.equalsIgnoreCase("CellOperators"))
		{
			result = (SynchronizeResultImpl) loadCellOperatorsDict(stream, fileName);
		}
		return result;
	}

	public SynchronizeResult loadNumberArraysDict(final InputStream stream, final String fileName) throws BusinessException
	{

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<SynchronizeResult>()
			{
				public SynchronizeResult run(Session session) throws BusinessException
				{
					// 1.удаляем все номерные емкости
					Query query  = session.getNamedQuery("com.rssl.phizic.business.dictionaries.cellNumberArray.NumberArray.deleteAll");
					query.executeUpdate();

					// 2. Парсим справочник и складываем в базу пачками
					NumberArrayXmlHandler handler = new NumberArrayXmlHandler(fileName);

					try
					{
						SAXParserFactory factory = SAXParserFactory.newInstance();
						SAXParser saxParser = factory.newSAXParser();

						saxParser.parse(new InputSource(stream), handler);

						List<NumberArray> numberArrays = handler.getNumberArrays();

						int count = 0;
						for (NumberArray numberArray : numberArrays)
						{
							session.save(numberArray);
							count++;

							if (count >= 1000)
							{
								session.flush();
								count = 0;
							}
						}
						session.flush();
					}
					catch (IOException e)
					{
						throw new BusinessException(e);
					}
					catch (SAXException e)
					{
						throw new BusinessException(e);
					}
					catch(ParserConfigurationException e)
					{
						throw new BusinessException(e);
					}

					SynchronizeResultImpl result = handler.getResults();
					if (result.getResultRecords().isEmpty())
					{
						result.addMessage("Справочник не загружен. В исходном файле нет записей для данного справочника.");
					}
					else
					{
						result.addMessage("Справочник номерных емкостей успешно загружен.");
					}
					result.setDictionaryType(DictionaryType.OTHER);
					return result;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public SynchronizeResult loadCellOperatorsDict(final InputStream stream, final String fileName) throws BusinessException
	{

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<SynchronizeResult>()
			{
				public SynchronizeResult run(Session session) throws BusinessException
				{
					// 1.удаляем все номерные емкости
					Query query  = session.getNamedQuery("com.rssl.phizic.business.dictionaries.cellOperator.CellOperator.deleteAll");
					query.executeUpdate();

					// 2. Парсим справочник и складываем в базу пачками
					CellOperatorXmlHandler handler = new CellOperatorXmlHandler(fileName);

					try
					{
						SAXParserFactory factory = SAXParserFactory.newInstance();
						SAXParser saxParser = factory.newSAXParser();

						saxParser.parse(new InputSource(stream), handler);

						List<CellOperator> cellOperators = handler.getCellOperators();

						int count = 0;
						for (CellOperator cellOperator : cellOperators)
						{
							session.save(cellOperator);
							count++;

							if (count >= 1000)
							{
								session.flush();
								count = 0;
							}
						}
						session.flush();
					}
					catch (IOException e)
					{
						throw new BusinessException(e);
					}
					catch (SAXException e)
					{
						throw new BusinessException(e);
					}
					catch(ParserConfigurationException e)
					{
						throw new BusinessException(e);
					}

					SynchronizeResultImpl result = handler.getResults();
					if (result.getResultRecords().isEmpty())
					{
						result.addMessage("Справочник не загружен. В исходном файле нет записей для данного справочника.");
					}
					else
					{
						result.addMessage("Справочник операторов сотовой связи успешно загружен.");
					}

					result.setDictionaryType(DictionaryType.OTHER);
					return result;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


}
