package com.rssl.phizic.operations.dictionaries.loanclaim;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.SynchronizeResultImpl;
import com.rssl.phizic.business.dictionaries.loanclaim.*;
import com.rssl.phizic.business.dictionaries.loanclaim.validators.*;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.DictionaryType;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import com.rssl.phizic.gate.loanclaim.dictionary.MultiWordDictionaryEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция для загрузки справочников адрессной информации
 */
public class LoadAddressDictionariesOperation extends OperationBase
{
	private final LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public SynchronizeResult load(final String dictionary, InputStream stream)
	{

		final CsvReader reader = new CsvReader(stream, loanClaimConfig.getLoanClaimDictionaryCSVDelimiter().charAt(0), new StandardCharsets().charsetForName("windows-1251"));
		try
		{
			final AbstractAddressDictionaryReader csvReader = getReaderByName(dictionary, reader);
			final AddressDictionaryValidator validator = getValidatorByName(dictionary);
			return HibernateExecutor.getInstance().execute(new HibernateActionStateless<SynchronizeResult>()
			{
				public SynchronizeResult run(org.hibernate.StatelessSession session) throws IOException
				{
					int success = 0;
					int numberRow = 0;
					SynchronizeResultImpl result = new SynchronizeResultImpl();
					Query query = session.getNamedQuery("LoanClaimDictionaryService.deleteAll"+dictionary);
					query.executeUpdate();
					List<Integer> errorRows = new ArrayList<Integer>();
					StringBuilder errorText = new StringBuilder("Не загруженные строки: ");

					readNext:
					while (csvReader.hasNext())
					{
						numberRow++;
						try
						{
							for (MultiWordDictionaryEntry entry : csvReader.readRecordWithPostfixes())
							{
								if (validator.validate(entry)){
									session.insert(entry);
								} else
								{
									errorRows.add(numberRow);
									continue readNext;
								}
							}
							success++;
						}
						catch (ConstraintViolationException e)
						{
							numberRow++;
							errorRows.add(numberRow);
							log.error("Ошибка в записи № - " + (numberRow));
						}
						catch (NonUniqueObjectException e)
						{
							numberRow++;
							errorRows.add(numberRow);
							log.error("Ошибка в записи № - " + (numberRow));
						}
					}
					log.error("Ошибка в поле - тип объекта, записи № - "+StringUtils.join(errorRows,";"));
					errorText.append(StringUtils.join(errorRows, "; "));
					result.addError(errorText.toString());
					result.addMessage("Успешно загруженные строки:" + success);
					result.setDictionaryType(DictionaryType.OTHER);
					return result;
				}
			});
		}
		catch (Exception e)
		{
			throw new InternalErrorException(e);
		}
	}

	private AbstractAddressDictionaryReader getReaderByName(String dictionaryName, CsvReader reader) throws IllegalArgumentException, IOException
	{
		String searchWordSeparators = loanClaimConfig.getDictionarySearchWordSeparators();
		if ("Area".equals(dictionaryName))
			return new AreaCSVReader(reader, searchWordSeparators);
		else if ("City".equals(dictionaryName))
			return new CityCSVReader(reader, searchWordSeparators);
		else if ("Settlement".equals(dictionaryName))
			return new SettlementCSVReader(reader, searchWordSeparators);
		else if ("Street".equals(dictionaryName))
			return new StreetCSVReader(reader, searchWordSeparators);
		else throw new UnsupportedOperationException("Неожиданный справочник: " + dictionaryName);
	}

	private AddressDictionaryValidator getValidatorByName(String dictionaryName) throws BusinessException
	{
		if ("Area".equals(dictionaryName))
			return new DictionaryAreaValidator();
		else if ("City".equals(dictionaryName))
			return new DictionaryCityValidator();
		else if ("Settlement".equals(dictionaryName))
			return new DictionarySettlementValidator();
		else if ("Street".equals(dictionaryName))
			return new DictionaryStreetValidator();
		else throw new UnsupportedOperationException("Неожиданный справочник: "+dictionaryName);
	}
}
