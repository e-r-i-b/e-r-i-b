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
	//��������� � ����� �����
	private static final String FILE_NAME_PREFIX = "9900";
	private static final String FILE_NAME_MIDST = "o";
	private static final String FILE_NAME_EXTENSION = ".xml";

	private static final String ENCODING = "windows-1251";

	//��������� � ��������� �����
	private static final String BIC = "BIC123456";
	private static final String SBOL = "����";
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
	 * ������������� ������ ������ �� ����. ����� �� ��������� ������.
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
	 * ���������� ��� ����� �������� ������ �� ����������� �����.
	 * ������: 9900JJJov.xml, ���
	 * 9900 - ���������
	 * JJJ - ��������� ����
	 * o - ��������� (��������� ����� "o")
	 * v - ���������� ����� ����� �� ����
	 * @return ��� ����� �������� ������ �� ����������� �����
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
	 * ������� xml-�������� ��������, �������� ��� �������.
	 * ������������� ������������ id ����������� ������.
	 * ������������ � ������ ��������.
	 * @param docList ����������� ���������
	 * @return xml � ���� ������, � id ���������� ����������� � ������ xml
	 */
	public Pair<String,Set<Long>> getFullDocument(List<? extends AbstractPaymentDocument> docList) throws BusinessException
	{
		Set<Long> docNumber = new HashSet<Long>();
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("�������");
		root.setAttribute("���", BIC);
		root.setAttribute("����������������", DateHelper.formatDateToString(Calendar.getInstance()));
		root.setAttribute("�����������������������", SBOL);
		root.setAttribute("xmlns", "create.claims.sbrf.ru");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "create.claims.sbrf.ru OpenClaims.xsd");
		document.appendChild(root);

		for (VirtualCardClaim claim :  (List<VirtualCardClaim>)docList)
		{
			Element entry = document.createElement("���������");
			entry.setAttribute("���", NPP);

			XmlHelper.appendSimpleElement(entry, "�������", claim.getSurName());
			XmlHelper.appendSimpleElement(entry, "���", claim.getFirstName());
			XmlHelper.appendSimpleElement(entry, "��������", claim.getPatrName());
			XmlHelper.appendSimpleElement(entry, "��������������", DEPARTMENT);
			XmlHelper.appendSimpleElement(entry, "��������������������", FILIAL);

			Element deposit = XmlHelper.appendSimpleElement(entry, "���������", claim.getNameCardProduct());
			deposit.setAttribute("�������������", claim.getKindCardProduct().toString());
			deposit.setAttribute("����������������", claim.getSubKindCardProduct().toString());
			deposit.setAttribute("���������", "643".equals(claim.getCurrencyCodeCardProduct()) ? "810" : claim.getCurrencyCodeCardProduct());

			XmlHelper.appendSimpleElement(entry, "����������������", claim.getMobilePhone());
			XmlHelper.appendSimpleElement(entry, "�������������", claim.getCodeMobileOperator());
			XmlHelper.appendSimpleElement(entry, "�������������", claim.getTariffMobileOperator());
			XmlHelper.appendSimpleElement(entry, "������������������", CATEGORY);
			XmlHelper.appendSimpleElement(entry, "����������������������", claim.getLastCardNumber());
			root.appendChild(entry);
			getResult().successRecordProcessed();
			docNumber.add(claim.getId());
		}

		//����������� � ������, �������������� ��������� ������ ���������.
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
	 * ������������ � ������������,
	 * �.�. � job-� �������� �������������� �������� �� 20 ���������� � ����������� flush-�� ��������� ������.
	 * @return ����� xml-��������� � ���� ������.
	 */
	public String getStringHeader()
	{
		return new StringBuffer()
				.append("<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n")
				.append("<������� ���=\"BIC123456\"")
				.append(" ����������������=\"")
				.append(DateHelper.formatDateToString(Calendar.getInstance()))
				.append("\" �����������������������=\"����\"")
				.append(" xmlns=\"create.claims.sbrf.ru\"")
				.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
				.append(" xsi:schemaLocation=\"create.claims.sbrf.ru OpenClaims.xsd\">\n")
				.toString();
	}

	/**
	 * ������������ � ������������.
	 * @return ����������� ��� xml-�������� � ���� ������.
	 */
	public String getStringFooter()
	{
		return new StringBuffer()
				.append("</�������>")
				.toString();
	}

//	/**
//	 * ������������ � ������������.
//	 * ���������� ������, ���������� ������ ���� ������ �� ������ virtualCardClaims.
//	 * ������������� ������������ id ����������� ������.
//	 * @param partList ������ ������� ������ ����������
//	 * @return ������ � ���� ������.
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
			sb.append("<��������� ���=\"").append(NPP).append("\">\n");
			sb.append("<�������>").append(claim.getSurName()).append("</�������>\n");
			sb.append("<���>").append(claim.getFirstName()).append("</���>\n");
			sb.append("<��������>").append(claim.getPatrName()).append("</��������>\n");
			sb.append("<��������������>").append(DEPARTMENT).append("</��������������>\n");
			sb.append("<��������������������>").append(FILIAL).append("</��������������������>\n");
			sb.append("<��������� �������������=\"").append(claim.getKindCardProduct())
					.append("\" ����������������=\"").append(claim.getSubKindCardProduct())
					.append("\" ���������=\"").append(claim.getCurrencyCodeCardProduct())
					.append("\">").append(claim.getNameCardProduct()).append("</���������>\n");
			sb.append("<����������������>").append(claim.getMobilePhone()).append("</����������������>\n");
			sb.append("<�������������>").append(claim.getCodeMobileOperator()).append("</�������������>\n");
			sb.append("<�������������>").append(claim.getTariffMobileOperator()).append("</�������������>\n");
			sb.append("<������������������>").append(CATEGORY).append("</������������������>\n");
			sb.append("<����������������������>").append(claim.getLastCardNumber()).append("</����������������������>\n");
			sb.append("</���������>\n");
		}
		catch(Exception e)
		{
			String errText = "������ � Id = " + claim.getId() + " �� ���������, ������ ������������ ";
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
		/*��������� ������ � ������*/
		sb.append(getStringHeader()).append(str).append(getStringFooter());
		return str;
	}

	public String getEncoding()
	{
		return Encodings.CP_1251;
	}

}
