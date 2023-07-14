package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 19.10.2006
 * Time: 15:03:32
 * To change this template use File | Settings | File Templates.
 */
class ReceiversListParser
{
		Document xml;
		int count = -1;
	    private static int UNIQUE_LENGTH = 9;
		public ReceiversListParser(Document xml) throws BusinessException
		{
			if(xml == null)throw new BusinessException("ѕолучен пустой документ");
			this.xml = xml;
		}
		//получить количество получателей
		public int getCount() throws BusinessException
		{
			try
			{
				NodeList list = XmlHelper.selectNodeList(xml.getDocumentElement(), "/recipientList_a/Recipient");
				if(list == null)return 0;
				else return list.getLength();
			}catch(TransformerException ex)
			{
				throw new BusinessException("ѕолучен xml неизвестного формата",ex);
			}
		}
		//получить наименование получател€, использовать только в случае одного в списке
		public String getReceiverName() throws BusinessException
		{
			try
			{
				Element  reciverName = XmlHelper.selectSingleNode(xml.getDocumentElement(), "/recipientList_a/Recipient/name");
				return reciverName.getTextContent();
			}catch(TransformerException ex)
			{
				throw new BusinessException("ѕолучен xml неизвестного формата",ex);
			}
		}
		//получить уникальный номер получател€, использовать только в случае одного в списке
		public String getReceiverUniqueNumber() throws BusinessException
		{
			try
			{
				Element  number = XmlHelper.selectSingleNode(xml.getDocumentElement(), "/recipientList_a/Recipient/uniqueNumber");
				String num = number.getTextContent();
				num = StringHelper.appendLeadingZeros(num, UNIQUE_LENGTH);//тоже  самое в PaymentReceiverJListAction
				return num;
			}catch(TransformerException ex)
			{
				throw new BusinessException("ѕолучен xml неизвестного формата",ex);
			}
		}
}
