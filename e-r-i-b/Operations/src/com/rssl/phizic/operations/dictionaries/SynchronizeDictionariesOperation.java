package com.rssl.phizic.operations.dictionaries;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.dictionaries.*;
import com.rssl.phizic.business.dictionaries.defCodes.DefCodeReplicaSource;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecodingReplicaSource;
import com.rssl.phizic.business.dictionaries.mnp.MNPPhoneReplicaSource;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.xslt.lists.cache.event.XmlDictionaryCacheClearEvent;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.xml.EmptyResolver;
import com.rssl.phizic.utils.xml.StopSAXParseException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kosyakov
 * @ created 19.09.2006
 * @ $Author: puzikov $
 * @ $Revision: 30668 $
 */
public class SynchronizeDictionariesOperation extends OperationBase implements ViewEntityOperation
{
	private static final char DELIMITER = ';';

	private DictionaryConfig dictionaryConfig = ConfigFactory.getConfig(DictionaryConfig.class);
  	private DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Collection<DictionaryDescriptor> getEntity()
	{
        return dictionaryConfig.getDescriptors();
	}

	@Transactional
	public void synchronizeAll () throws BusinessException, GateException, BusinessLogicException, GateLogicException
	{
		new DictionariesSynchronizer(getGate().getFactory()).synchronizeAll();
	}

	@Transactional
	public List<SynchronizeResult> synchronizeXml(List<String> names, InputStream stream, String fileName, boolean temporary)
			throws GateException, BusinessLogicException, IOException, GateLogicException
	{
		List<SynchronizeResult> results = new ArrayList<SynchronizeResult>();

		XMLReader reader = null;
		try
		{
			reader = XmlHelper.newXMLReader();
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка при инициализации SAX-парсера", e);
		}

		List<String> dictionaryNames = new ArrayList<String>();
		for (String name : names)
		{
			List<String> groupDescriptorNames = dictionaryConfig.getGroupDescriptorNames(name);
			if (!groupDescriptorNames.isEmpty())
			{
				dictionaryNames.addAll(groupDescriptorNames);
			}
			else
				dictionaryNames.add(name);
		}

		Map<String, DictionaryDescriptor> dictionaryDescriptors = new HashMap<String, DictionaryDescriptor>();
		for (String name : dictionaryNames)
		{
			DictionaryDescriptor descriptor = dictionaryConfig.getDescriptor(name);
			if (descriptor.getSource() instanceof MNPPhoneReplicaSource)
			{
				MNPPhoneReplicaSource source = (MNPPhoneReplicaSource) descriptor.getSource();
				source.setFileName(fileName);
			}
			dictionaryDescriptors.put(name, descriptor);
		}

		if (!temporary)
		{
			try
			{
				for (String name : dictionaryNames)
				{
					DictionaryDescriptor descriptor = dictionaryDescriptors.get(name);

					if (descriptor.isXmlSource() && !temporary)
					{
						XmlReplicaSource source = (XmlReplicaSource) descriptor.getSource();
						reader = source.chainXMLReader(reader);
					}
					else if (descriptor.getSource() instanceof DepositProductReplicaSource)
					{
						DepositProductReplicaSource source = (DepositProductReplicaSource) descriptor.getSource();
						reader.setEntityResolver(new EmptyResolver());
						results.add(source.convert(reader, stream));
						stream.reset();
					}
					else
					{
						SynchronizeResult result = findResultByDictionaryType(DictionaryType.OTHER, results);
						String errorText = descriptor.getDescription() + " не загружен: неверный тип файла для справочника.";
						result.addError(errorText);
						dictionaryNames.remove(name);
					}
				}

				reader.setEntityResolver(new EmptyResolver());
				reader.parse(new InputSource(stream));
			}
			/*
			 * Игнорируется поскольку намеренно прервали чтение справочника.
			 */
			catch (StopSAXParseException ignored) {}
			catch (Exception e)
			{
				throw new IOException("Ошибка при разборе XML-файла");
			}
		}

		for (String name : dictionaryNames)
		{
			DictionaryDescriptor descriptor = dictionaryDescriptors.get(name);
			ReplicaSource source = descriptor.getSource();

			DictionaryType dictionaryType;
			if (source instanceof DepositProductReplicaSource)
				dictionaryType = DictionaryType.DEPOSIT;
			else if(source instanceof CASNSICardProductReplicaSource)
			{
				dictionaryType = DictionaryType.CARD;
				results.add(((CASNSICardProductReplicaSource) source).getSynchronizeResult());
			}
			else if (source instanceof IMAProductReplicaSource)
			{
				dictionaryType = DictionaryType.IMA;
				results.add(((IMAProductReplicaSource) source).getSynchronizeResult());
			}
			else if (source instanceof DepartmentsRecodingReplicaSource)
			{
				dictionaryType = DictionaryType.SPOOBK2;
				results.add(((DepartmentsRecodingReplicaSource) source).getSynchronizeResult());
			}
			else if (source instanceof DefCodeReplicaSource)
			{
				dictionaryType = DictionaryType.DEF_CODES;
				results.add(((DefCodeReplicaSource) source).getSynchronizeResult());
			}
			else if (source instanceof MNPPhoneReplicaSource)
			{
				dictionaryType = DictionaryType.MNP_PHONES;
				results.add(((MNPPhoneReplicaSource) source).getSynchronizeResult());
			}
			else
			{
				dictionaryType = DictionaryType.OTHER;
			}

			SynchronizeResult result = findResultByDictionaryType(dictionaryType, results);

			if (!source.iterator().hasNext())
			{
				result.addMessage(name + " не загружен. В исходном файле нет записей для данного справочника.");
				continue;
			}

			try
			{
				if (temporary)
				{
					new DictionariesSynchronizer(getGate().getFactory()).synchronizeFromTemporary(descriptor);
				}
				else
				{
                    new DictionariesSynchronizer(getGate().getFactory()).synchronize(descriptor);
				}

				result.addMessage(descriptor.getDescription() + " загружен.");
			}
			catch (BusinessException e)
			{
				String errorText = "Ошибка при синхронизаци справочника: " + descriptor.getDescription();
				PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error(errorText, e);
				result.addError(errorText);
			}

			if(source instanceof DepositProductReplicaSource)
			{
				//чистим кеш по депозиту
				try
				{
					EventSender.getInstance().sendEvent(new XmlDictionaryCacheClearEvent(null, DepositProduct.class));
				}
				catch (Exception e)
				{
					throw new GateException(e);
				}
			}

			if (source instanceof CASNSICardProductReplicaSource)
			{
				// обновление максимальной даты закрытия вида карточного продукта
				CardProductService cardProductService = new CardProductService();
				try
				{
					cardProductService.updateStopOpenDateAllCardProducts();
				}
				catch(BusinessException e)
				{
					String errorText = "Ошибка обновления карточного продукта: " + descriptor.getDescription();
					PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error(errorText, e);
				}
			}
		}
		MultiBlockModeDictionaryHelper.updateDictionary(MultiBlockDictionaryRecord.class);
		Collections.sort(results, new SynchronizeResultsComparator());
		return results;
	}

	/**
	 * Синхронизация справочников, которые загружаются из csv-файла
	 * @param names - выбранные для загрузки справочники
	 * @param stream - данные файла
	 * @return результаты синхроницации
	 * @throws GateException
	 * @throws BusinessLogicException
	 * @throws IOException
	 */
	@Transactional
	public List<SynchronizeResult> synchronizeCsv(List<String> names, InputStream stream) throws GateException, BusinessLogicException, IOException
	{
		List<SynchronizeResult> results = new ArrayList<SynchronizeResult>();

		CsvReader reader = new CsvReader(stream, DELIMITER, new StandardCharsets().charsetForName("windows-1251"));;

		for (String name : names)
		{
			DictionaryDescriptor descriptor = dictionaryConfig.getDescriptor(name);
			SynchronizeResult result = findResultByDictionaryType(DictionaryType.OTHER, results);

			if (descriptor.isCsvSource())
			{
				CsvReplicaSource source = (CsvReplicaSource) descriptor.getSource();
				source.setReader(reader);

				try
				{
					new DictionariesSynchronizer(getGate().getFactory()).synchronize(descriptor);
					result.addMessage(descriptor.getDescription() + " загружен.");
				}
				catch (BusinessException e)
				{
					String errorText = "Ошибка при синхронизаци справочника: " + descriptor.getDescription();
					PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error(errorText, e);
					result.addError(errorText);
				}
				catch (Exception e)
				{
					String errorText = "Ошибка при разборе CSV-файла";
					PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error(errorText, e);
					throw new IOException(errorText);
				}

				if(source instanceof DepositProductReplicaSource)
				{
					//чистим кеш по депозиту
					try
					{
						EventSender.getInstance().sendEvent(new XmlDictionaryCacheClearEvent(null, DepositProduct.class));
					}
					catch (Exception e)
					{
						throw new GateException(e);
					}
				}
			}
			else
			{
				String errorText = descriptor.getDescription() + " не загружен: неверный тип файла для справочника.";
				result.addError(errorText);
			}
		}
		MultiBlockModeDictionaryHelper.updateDictionary(MultiBlockDictionaryRecord.class);
		return results;
	}

	@Transactional
	public List<String> synchronize(DictionaryDescriptor descriptor) throws BusinessException, BusinessLogicException, GateException, GateLogicException
	{
		DictionariesSynchronizer dictionariesSynchronizer = new DictionariesSynchronizer(getGate().getFactory());
		dictionariesSynchronizer.synchronize(descriptor);
		return dictionariesSynchronizer.getErrors();
	}

	public void  setUpdateDate(int i, DictionaryDescriptor descriptor)
	{
		Date date = Calendar.getInstance().getTime();
		String dateStr = df.format(Calendar.getInstance().getTime());
		descriptor.setLastUpdateDate(date);
		DbPropertyService.updateProperty("com.rssl.iccs.dictionaries.name." + i + ".lastUpdate", dateStr);
	}

	protected Gate getGate() throws BusinessException, BusinessLogicException
	{
		return GateSingleton.get();
	}

	private SynchronizeResult findResultByDictionaryType(DictionaryType type, List<SynchronizeResult> results)
	{
		for (SynchronizeResult result : results)
		{
			if (type == result.getDictionaryType())
				return result;
		}

		SynchronizeResultImpl newResult = new SynchronizeResultImpl();
		newResult.setDictionaryType(type);
		results.add(newResult);

		return newResult;
	}

}
