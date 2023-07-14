package com.rssl.phizic.business.dictionaries;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductSBRF;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ProductUploadMode;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.EmptyResolver;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.common.types.MinMax;
import org.apache.commons.io.IOUtils;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

/**
 * Сорс по отбору и преобразованию описания депозитных продуктов из справочника ЦАС НСИ
 @author Pankin
 @ created 17.02.2011
 @ $Author$
 @ $Revision$
 */
public class DepositProductReplicaSource implements ReplicaSource
{
	// Путь к файлу шаблонов XSL преобразований
	private static final String TEMPLATES_PATH = "com/rssl/phizic/business/ext/sbrf/deposits/load/templates/Transform.xsl";
	// Настройка XMLReader
	private static final String NAMESPACE_FEATURE = "http://xml.org/sax/features/namespaces";
	private static final String PRODUCT_OPTIONS_PATH = "/product/data/options";
	private static final String SUCCESS_SUBTYPE_MARKER = "|OK";
	private static final String FAIL_SUBTYPE_MARKER = "|FAIL";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final DepositProductService depositProductService = new DepositProductService();

	private List<DepositProduct> depositProducts;
	private boolean isFilled = false;

	public void initialize(GateFactory factory) throws GateException
	{
	}

	/**
	 * Преобразование внешнего справочника к внутреннему
	 * @param reader xml reader
	 * @param input поток внешнего справочника.
	 * @return список ошибок
	 */
	@SuppressWarnings({"OverlyLongMethod"})
	public SynchronizeResult convert(XMLReader reader, InputStream input) throws GateException
	{
		isFilled = true;
		depositProducts = new ArrayList<DepositProduct>();

		SynchronizeResultImpl synchronizeResult = new SynchronizeResultImpl();
		synchronizeResult.setDictionaryType(DictionaryType.DEPOSIT);

		XmlConverter converter = null;
		try
		{
			reader.setFeature(NAMESPACE_FEATURE, true);
			converter = new CommonXmlConverter(getTemplates());
		}
		catch (Exception e)
		{
			log.error("Ошибки настройки XMLReader и XML конвертера", e);
			throw new GateException(e);
		}

		DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);

		// режим загрузки
		ProductUploadMode uploadMode = ProductUploadMode.valueOf(depositConfig.getUploadMode());

		// Получение списка номеров доступных для загрузки видов вкладов.
		Map<Long, List<Long>> allowedDepositProducts = getUploadingDepositProductsList(reader, input, uploadMode);

		// Получаем номера уже загруженных в систему видов вкладов. Для них при преобразовании справочника
		// не нужно отсеивать те подвиды, у которых истек срок приема.
		List<Long> savedProducts = null;
		try
		{
			savedProducts = depositProductService.getAllDepositProductIds();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}

		// Передаем в преобразование текущую дату как параметр.
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		converter.setParameter("curDate", df.format((new GregorianCalendar()).getTime()));
		converter.setParameter("tariff1AgreementCode", depositConfig.getTariff1AgreementCode());
		converter.setParameter("tariff2AgreementCode", depositConfig.getTariff2AgreementCode());
		converter.setParameter("tariff3AgreementCode", depositConfig.getTariff3AgreementCode());
		converter.setParameter("type4Kind_61",         depositConfig.getType4Kind61());
		converter.setParameter("code4Kind_61",         depositConfig.getCode4Kind61());

		MinMax<Long> allowedCardKinds = null;

		try
		{
			allowedCardKinds = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
		}
		catch(BusinessException e)
		{
			throw new GateException(e);
		}


		// Заполняем описание для разрешенных депозитных продуктов
		for (Map.Entry<Long, List<Long>> entry : allowedDepositProducts.entrySet())
		{
			Long productId = entry.getKey();
			List<Long> productSubKinds = entry.getValue();
			// если данный продукт принадлежит карточному, то пропускаем
			if (allowedCardKinds.getMin() <= productId &&  productId <=  allowedCardKinds.getMax())
			{
				if (uploadMode == ProductUploadMode.OPTIONALLY)
				{
					SynchronizeResultRecord resultRecord = new SynchronizeResultRecord();
					resultRecord.setStatus(SynchronizeResultStatus.FAIL);
					resultRecord.setSynchKey(productId);
					List<String> errorList = new ArrayList<String>(1);
					errorList.add("CRD_PDCT");
					resultRecord.setErrorDescriptions(errorList);
					synchronizeResult.addResultRecord(resultRecord);
				}
				continue;
			}

			Source source = new SAXSource(reader, new InputSource(input));
			converter.setData(source);
			converter.setParameter("productId", productId);
			converter.setParameter("isUpdate", savedProducts.contains(productId));

			DepositProductSBRF depositProduct = new DepositProductSBRF();
			depositProduct.setAllowedDepartments(new ArrayList<String>());
			// Получение описания депозитного продукта
			String depositDescription = converter.convert().toString();
			log.debug("[DepositProductReplicaSource.convert start] uploadMode = " + uploadMode + "depositDescription.length() = " + StringHelper.getEmptyIfNull(depositDescription).length());

			SynchronizeResultRecord resultRecord = new SynchronizeResultRecord();
			resultRecord.setSynchKey(productId);

			Document internalDictionary;
			try
			{
				// Парсим полученное описание депозитного продукта для получения названия вклада и ошибок
				internalDictionary = XmlHelper.parse(depositDescription);
			}
			catch (Exception e)
			{
				log.error("Ошибка при разборе полученного описания вклада с номером " + productId, e);
				throw new GateException(e);
			}

			// Проверяем, получены ли ошибки в результате разбора справочника по данному виду и сохраняем
			// результат
			boolean isError = isError(internalDictionary, resultRecord);

			// Если загрузка выборочная и указаны номера подвидов, надо отфильтровать результат
			if (!isError && uploadMode == ProductUploadMode.OPTIONALLY && !CollectionUtils.isEmpty(productSubKinds))
			{
				filterSubKinds(productSubKinds, productId, internalDictionary, resultRecord);
				try
				{
					// уточняем название вклада на основе отфильтрованных данных
					DepositProductHelper.updateProductName(internalDictionary.getDocumentElement());
					// переопределяем depositDescription, чтобы сохранить отфильтрованный результат
					depositDescription = XmlHelper.convertDomToText(internalDictionary, "windows-1251");
				}
				catch (TransformerException e)
				{
					throw new GateException(e);
				}
			}

			// Добавляем результат синхронизации для записи
			synchronizeResult.addResultRecord(resultRecord);
			// Сбрасываем input для повторного использования
			try
			{
				input.reset();
			}
			catch (IOException e)
			{
				log.error("Ошибка при попытке сброса входного потока.", e);
				throw new GateException(e);
			}

			if (isError)
				continue;

			// Если ошибок нет, заполняем депозитный продукт и добавляем в список
			depositProduct.setDescription(depositDescription);
			log.debug("[DepositProductReplicaSource.convert end] uploadMode = " + uploadMode + "depositDescription.length() = " + StringHelper.getEmptyIfNull(depositDescription).length());
			depositProduct.setName(getName(internalDictionary, productId));
			depositProduct.setProductId(productId);
			depositProducts.add(depositProduct);
		}
		return synchronizeResult;
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		// Если не вызывался метод convert, в качестве справочника-источника берем стандартный
		if (!isFilled)
			defaultConvert();

		Collections.sort(depositProducts, new DepositProductComparator());
		return depositProducts.iterator();
	}

	private void defaultConvert() throws GateException, GateLogicException
	{
		try
		{
			XMLReader reader = XmlHelper.newXMLReader();
			reader.setEntityResolver(new EmptyResolver());
			convert(reader, new ByteArrayInputStream(IOUtils.toByteArray(getDefaultStream())));
		}
		catch (SAXException e)
		{
			log.error("Ошибка при получении XMLReader", e);
			throw new GateException(e);
		}
		catch (ParserConfigurationException e)
		{
			log.error("Ошибка при получении XMLReader", e);
			throw new GateException(e);
		}
		catch (IOException e)
		{
			log.error("Ошибка при чтении стандартного файла справочника", e);
			throw new GateException(e);
		}
	}

	private InputStream getDefaultStream()
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(com.rssl.phizic.business.Constants.DEFAULT_FILE_NAME);
	}

	/**
	 * Получение шаблонов XSL преобразований
	 * @return Templates
	 * @throws javax.xml.transform.TransformerConfigurationException
	 */
	private Templates getTemplates() throws TransformerConfigurationException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setURIResolver(new StaticResolver());

		InputStream transformStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATES_PATH);

		return transformerFactory.newTemplates(new StreamSource(transformStream));
	}

	/**
	 * Получение названия вклада по внутреннему описанию депозитного продукта
	 * @param internalDictionary описание депозитного продукта
	 * @param productId номер вида вклада
	 * @return название вклада
	 */
	private String getName(Document internalDictionary, Long productId) throws GateException
	{
		String depositName = "";
		try
		{
			Node nameNode = XmlHelper.selectSingleNode(internalDictionary.getDocumentElement(), DepositProductHelper.NAME_XPATH);
			depositName = nameNode.getTextContent().trim();
		}
		catch (TransformerException e)
		{
			log.error("Ошибка при разборе полученного описания вклада с номером " + productId, e);
			throw new GateException(e);
		}

		return depositName;
	}

	public void close()
	{
	}

	private Map<Long, List<Long>> getUploadingDepositProductsList(XMLReader reader, InputStream input, ProductUploadMode uploadMode) throws GateException
	{
		if (uploadMode == ProductUploadMode.ALL)
		{
			SaxFilter saxFilter = new SaxFilter(reader);
			try
			{
				saxFilter.parse(new InputSource(input));
			}
			catch (IOException e)
			{
				log.error("Ошибка при разборе справочника депозитных продуктов для получения всех номеров видов вкладов.", e);
				throw new GateException(e);
			}
			catch (StopSAXException ignored)
			{
			}
			catch (SAXException e)
			{
				log.error("Ошибка при разборе справочника депозитных продуктов для получения всех номеров видов вкладов.", e);
				throw new GateException(e);
			}

			try
			{
				input.reset();
			}
			catch (IOException e)
			{
				log.error("Ошибка при попытке сброса входного потока.", e);
				throw new GateException(e);
			}

			Map<Long, List<Long>> result = new HashMap<Long, List<Long>>();
			for (Long productId : saxFilter.getDepositProductsKinds())
				result.put(productId, Collections.<Long>emptyList());
			return result;
		}
		else
		{
			return SynchronizeSettingsUtil.readAllowedDepositProductMap(ConfigFactory.getConfig(DepositConfig.class).getUploadingDepositProductsList());
		}
	}

	private boolean isError(Document internalDictionary, final SynchronizeResultRecord resultRecord) throws GateException
	{
		try
		{
			Element errors = XmlHelper.selectSingleNode(internalDictionary.getDocumentElement(), "/product/errors");
			if (errors == null)
			{
				resultRecord.setStatus(SynchronizeResultStatus.SUCCESS);
				log.info("Описание вклада с номером " + resultRecord.getSynchKey() + " при репликации " +
						"успешно получено.");
				return false;
			}
			else
			{
				final List<String> errorList = new ArrayList<String>();
				XmlHelper.foreach(errors, "error", new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						errorList.add(element.getTextContent());
						log.error("Ошибка при репликации справочника депозитных продуктов. " +
								"Описание по вкладу с номером " + resultRecord.getSynchKey() + " не будет " +
								"добавлено/обновлено. Не хватает данных в таблице " + element.getTextContent());
					}
				});
				resultRecord.setStatus(SynchronizeResultStatus.FAIL);
				resultRecord.setErrorDescriptions(errorList);
				return true;
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при разборе полученного описания вклада с номером " + resultRecord.getSynchKey(), e);
			throw new GateException(e);
		}
	}

	/*
	 Фильтр для получения всех номеров видов вкладов, описанных в справочнике ЦАС НСИ
	 */
	private class SaxFilter extends XMLFilterImpl
	{
		private Set<Long> depositProductsKinds = new HashSet<Long>();
		private boolean innerQVB = false;

		SaxFilter(XMLReader reader)
		{
			super(reader);
			this.setEntityResolver(reader.getEntityResolver());
		}

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && atts.getValue("name").equalsIgnoreCase("qvb"))
			{
				innerQVB = true;
			}
			else if (innerQVB && qName.equalsIgnoreCase("field") && atts.getValue("name").equalsIgnoreCase("QDTN1"))
			{
				depositProductsKinds.add(Long.parseLong(atts.getValue("value")));
			}
			if (!innerQVB)
			{
				super.startElement(uri, localName, qName, atts);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (innerQVB && qName.equalsIgnoreCase("table"))
			{
				// Можно закончить разбор файла
				throw new StopSAXException();
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

		List<Long> getDepositProductsKinds()
		{
			return new ArrayList<Long>(depositProductsKinds);
		}
	}

	// Вспомогательное исключение для принудительного завершения разбора файла.
	private class StopSAXException extends SAXException
	{
	}

	private void filterSubKinds(final List<Long> productSubKinds, Long productId, final Document internalDictionary, SynchronizeResultRecord resultRecord) throws GateException
	{
		Element internalDictionaryElement = internalDictionary.getDocumentElement();
		// Получаем все загруженные номера подвидов
		final Set<Long> allSubKinds = new HashSet<Long>();
		try
		{
			XmlHelper.foreach(internalDictionaryElement,
					DepositProductHelper.ELEMENT_FOREACH_PATH, new ForeachElementAction()
					{

						public void execute(Element element) throws Exception
						{
							allSubKinds.add(Long.parseLong(XmlHelper.getSimpleElementValue(element, "id")));
						}
					});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		// Проверяем, загружены ли требуемые подвиды, вносим в результат загрузки
		List<String> subKindsResult = new ArrayList<String>();
		for (Long subKind : productSubKinds)
		{
			if (allSubKinds.contains(subKind))
				subKindsResult.add(subKind + SUCCESS_SUBTYPE_MARKER);
			else
				subKindsResult.add(subKind + FAIL_SUBTYPE_MARKER);
		}
		if (!CollectionUtils.isEmpty(subKindsResult))
				resultRecord.setErrorDescriptions(subKindsResult);

		// Удаляем из списка всех подвидов те, которые требуется загрузить
		allSubKinds.removeAll(productSubKinds);
		// Удаляем "ненужные" подвиды
		try
		{
			DepositProductHelper.removeSubKinds(internalDictionaryElement, new ArrayList<Long>(allSubKinds));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		// Теперь надо добавить в описание информацию по уже загруженным подвидам, если такие есть
		try
		{
			DepositProduct depositProduct = depositProductService.findByProductId(productId);
			if (depositProduct != null)
			{
				Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
				Element oldOptions = XmlHelper.selectSingleNode(description, PRODUCT_OPTIONS_PATH);
				final Element newOptions = XmlHelper.selectSingleNode(internalDictionaryElement, PRODUCT_OPTIONS_PATH);
				XmlHelper.foreach(oldOptions, "element", new ForeachElementAction()
				{

					public void execute(Element element) throws Exception
					{
						Long subKind = Long.parseLong(XmlHelper.getSimpleElementValue(element, "id"));
						if (!productSubKinds.contains(subKind))
						{
							newOptions.appendChild(internalDictionary.importNode(element, true));
						}
					}
				});
			}
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
