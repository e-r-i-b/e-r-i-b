package com.rssl.phizic.operations.virtualcards.unload;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.VirtualCardClaim;
import com.rssl.phizic.common.types.Encodings;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.operations.loanOffer.ClaimUnloadExceptoin;
import com.rssl.phizic.config.loan.UnloadConfig;
import com.rssl.phizic.operations.tasks.UnloadPereodicalTaskOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author Dorzhinov
 * @ created 13.10.2011
 * @ $Author$
 * @ $Revision$
 */
//public class UnloadVirtualCardClaimOperation extends OperationBase implements ListEntitiesOperation
public class UnloadVirtualCardClaimOperation extends UnloadPereodicalTaskOperationBase
{
	private static final CounterService counterService= new CounterService();
	//Константы в имени файла
	private static final String FILE_NAME_PREFIX = "9900";
	private static final String FILE_NAME_MIDST = "o";
	private static final String FILE_NAME_EXTENSION = ".xml";

	private static final String ENCODING = "windows-1251";

	//Константы в значениях тегов
	private static final String BIC = "BIC123456";
	private static final String SBOL = "СБОЛ";
	private static final String NPP = "1";
	private static final String DEPARTMENT = "0002";
	private static final String FILIAL = "0001";
	private static final String CATEGORY = "0";

	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public UnloadVirtualCardClaimOperation() throws BusinessException
	{
		super();
	}

	/**
	 * Инициализация списка заявок на вирт. карты за указанный период.
	 * @throws BusinessException
	 */
	public List<? extends AbstractPaymentDocument> getDataPack(Integer maxResults)
	{
		try
		{
			return  businessDocumentService.getVirtualCardClaims(startDate, endDate, maxResults);
		}
		catch (BusinessException e)
		{
			addReportError(GET_PART_ERROR,e);
		}
		return new ArrayList<AbstractPaymentDocument>();
	}
	
	/**
	 * Составляет имя файла выгрузки заявок на виртуальные карты.
	 * Формат: 9900JJJov.xml, где
	 * 9900 - константа
	 * JJJ - юлианская дата
	 * o - константа (латинская буква "o")
	 * v - порядковый номер файла за день
	 * @return Имя файла выгрузки заявок на виртуальные карты
	 */
	public String getFileName() throws BusinessException
	{
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append(FILE_NAME_PREFIX);
			sb.append(DateHelper.getJulianDate(Calendar.getInstance()));
			sb.append(FILE_NAME_MIDST);
			sb.append(counterService.getNext(Counters.UNLOAD_VIRTUAL_CARD_CLAIM));
			sb.append(FILE_NAME_EXTENSION);
			return sb.toString();
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	protected String additionalFileUpdate(Pair<FileOutputStream,File> file)
	{
		return file.getSecond().getName();
	}

	protected  void finalFileAction(Pair<FileOutputStream,File> tempFilePair)
	{
	}

	/**
	 * Создает xml-документ выгрузки, заполняя его данными.
	 * Дополнительно запоминаются id выгружаемых заявок.
	 * Используется в ручной выгрузке.
	 * @param docList выгружаемые документы
	 * @return xml в виде строки, и id документов выгруженные в данную xml
	 */
	public Pair<String,Set<Long>> getFullDocument(List<? extends AbstractPaymentDocument> docList) throws BusinessException
	{
		Set<Long> docNumber = new HashSet<Long>();
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("СчетаПК");
		root.setAttribute("БИК", BIC);
		root.setAttribute("ДатаФормирования", DateHelper.formatDateToString(Calendar.getInstance()));
		root.setAttribute("НаименованиеОрганизации", SBOL);
		root.setAttribute("xmlns", "create.claims.sbrf.ru");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "create.claims.sbrf.ru OpenClaims.xsd");
		document.appendChild(root);

		for (VirtualCardClaim claim :  (List<VirtualCardClaim>)docList)
		{
			Element entry = document.createElement("Сотрудник");
			entry.setAttribute("Нпп", NPP);

			XmlHelper.appendSimpleElement(entry, "Фамилия", claim.getSurName());
			XmlHelper.appendSimpleElement(entry, "Имя", claim.getFirstName());
			XmlHelper.appendSimpleElement(entry, "Отчество", claim.getPatrName());
			XmlHelper.appendSimpleElement(entry, "ОтделениеБанка", DEPARTMENT);
			XmlHelper.appendSimpleElement(entry, "ФилиалОтделенияБанка", FILIAL);

			Element deposit = XmlHelper.appendSimpleElement(entry, "ВидВклада", claim.getNameCardProduct());
			deposit.setAttribute("КодВидаВклада", claim.getKindCardProduct().toString());
			deposit.setAttribute("КодПодвидаВклада", claim.getSubKindCardProduct().toString());
			deposit.setAttribute("КодВалюты", "643".equals(claim.getCurrencyCodeCardProduct()) ? "810" : claim.getCurrencyCodeCardProduct());

			XmlHelper.appendSimpleElement(entry, "МобильныйТелефон", claim.getMobilePhone());
			XmlHelper.appendSimpleElement(entry, "ОператорСвязи", claim.getCodeMobileOperator());
			XmlHelper.appendSimpleElement(entry, "МобильныйБанк", claim.getTariffMobileOperator());
			XmlHelper.appendSimpleElement(entry, "КатегорияНаселения", CATEGORY);
			XmlHelper.appendSimpleElement(entry, "НомерКартыСуществующий", claim.getLastCardNumber());
			root.appendChild(entry);
			getResult().successRecordProcessed();
			docNumber.add(claim.getId());
		}

		//Преобразуем в строку, предварительно установив нужную кодировку.
		try {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(OutputKeys.ENCODING, ENCODING);
			properties.put(OutputKeys.INDENT, "yes");
			return new Pair(XmlHelper.setPropsAndConvertDomToText(document, properties),docNumber);
		} catch (TransformerConfigurationException e) {
			getResult().setSuccesslResultCount(0L);
			throw new BusinessException("It is not possible to create a Transformer instance.", e);
		} catch (TransformerException e) {
			getResult().setSuccesslResultCount(0L);
			throw new BusinessException("An unrecoverable error occurs during the course of the transformation.", e);
		}
	}

	/**
	 * Используется в автовыгрузке,
	 * т.к. в job-е выгрузка осуществляется порциями по 20 документов с последующим flush-ем выходного потока.
	 * @return Шапка xml-документа в виде строки.
	 */
	public String getStringHeader()
	{
		return new StringBuffer()
				.append("<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n")
				.append("<СчетаПК БИК=\"BIC123456\"")
				.append(" ДатаФормирования=\"")
				.append(DateHelper.formatDateToString(Calendar.getInstance()))
				.append("\" НаименованиеОрганизации=\"СБОЛ\"")
				.append(" xmlns=\"create.claims.sbrf.ru\"")
				.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
				.append(" xsi:schemaLocation=\"create.claims.sbrf.ru OpenClaims.xsd\">\n")
				.toString();
	}

	/**
	 * Используется в автовыгрузке.
	 * @return Закрывающий тэг xml-документ в виде строки.
	 */
	public String getStringFooter()
	{
		return new StringBuffer()
				.append("</СчетаПК>")
				.toString();
	}

//	/**
//	 * Используется в автовыгрузке.
//	 * Возвращает строку, содержащую данные всех заявок из списка virtualCardClaims.
//	 * Дополнительно запоминаются id выгружаемых заявок.
//	 * @param partList список входных бизнес сущьностиц
//	 * @return Заявки в виде строки.
//	 */
//	public String getNextDataPortion(List<? extends AbstractPaymentDocument> partList)
//	{
//		StringBuffer sb = new StringBuffer();
//		for (VirtualCardClaim claim :  (List<VirtualCardClaim>)partList)
//		{
//			try
//			{
//				sb.append(getUnloadedDataString(claim));
////				addClaimId(claim.getId());
//			}
//			catch(ClaimUnloadExceptoin e)
//			{
//				addReportError(e.getMessage(),e);
//			}
//		}
//		return sb.toString();
//	}

	public String getUnloadedDataString(GateExecutableDocument document) throws ClaimUnloadExceptoin
	{
		VirtualCardClaim claim = (VirtualCardClaim) document;
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("<Сотрудник Нпп=\"").append(NPP).append("\">\n");
			sb.append("<Фамилия>").append(claim.getSurName()).append("</Фамилия>\n");
			sb.append("<Имя>").append(claim.getFirstName()).append("</Имя>\n");
			sb.append("<Отчество>").append(claim.getPatrName()).append("</Отчество>\n");
			sb.append("<ОтделениеБанка>").append(DEPARTMENT).append("</ОтделениеБанка>\n");
			sb.append("<ФилиалОтделенияБанка>").append(FILIAL).append("</ФилиалОтделенияБанка>\n");
			sb.append("<ВидВклада КодВидаВклада=\"").append(claim.getKindCardProduct())
					.append("\" КодПодвидаВклада=\"").append(claim.getSubKindCardProduct())
					.append("\" КодВалюты=\"").append(claim.getCurrencyCodeCardProduct())
					.append("\">").append(claim.getNameCardProduct()).append("</ВидВклада>\n");
			sb.append("<МобильныйТелефон>").append(claim.getMobilePhone()).append("</МобильныйТелефон>\n");
			sb.append("<ОператорСвязи>").append(claim.getCodeMobileOperator()).append("</ОператорСвязи>\n");
			sb.append("<МобильныйБанк>").append(claim.getTariffMobileOperator()).append("</МобильныйБанк>\n");
			sb.append("<КатегорияНаселения>").append(CATEGORY).append("</КатегорияНаселения>\n");
			sb.append("<НомерКартыСуществующий>").append(claim.getLastCardNumber()).append("</НомерКартыСуществующий>\n");
			sb.append("</Сотрудник>\n");
		}
		catch(Exception e)
		{
			String errText = "Заявка с Id = " + claim.getId() + " не выгружена, ошибка формирования ";
			throw new ClaimUnloadExceptoin(errText,e);

		}
		return sb.toString();
	}

	public int getRepeatInterval()
	{
		return ConfigFactory.getConfig(UnloadConfig.class).getVirtualCardUnloadRepeatInterval();
	}

	protected String additionalResultStringUpdate(String str)
	{
		StringBuilder sb = new StringBuilder();
		/*добавляем хеддер и футтер*/
		sb.append(getStringHeader()).append(str).append(getStringFooter());
		return str;
	}

	public String getEncoding()
	{
		return Encodings.CP_1251;
	}

}
