package com.rssl.phizic.business.receptiontimes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.xml.XmlExplorer;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConsiderReceptionTimeDocumentsLoader
{
	private static final String LIST_PAYMENT_TYPES_FILE_NAME = "considerReceptionTimePaymentTypes.xml";

	/**
	 * @return Список пар <форма платежа, описание> в которых учитывается время приема
	 */
	public static List<Pair<String, String>> loadConsiderReceptionTimePaymentTypes() throws BusinessException
	{
		try
		{
			List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
			XmlExplorer xmlExplorer = new XmlExplorer();
			Document doc = XmlHelper.loadDocumentFromResource(LIST_PAYMENT_TYPES_FILE_NAME);
			NodeList nodeList = xmlExplorer.selectNodeList(doc.getDocumentElement(), "//considerReceptionTimePaymentTypes/payment");
			for (int i=0; i< nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				String paymentTypeName = xmlExplorer.selectSingleNode(node, "paymentTypeName").getTextContent();
				String description = xmlExplorer.selectSingleNode(node, "description").getTextContent();
				result.add(new Pair<String, String>(paymentTypeName, description));
			}
			return result;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка обработки документа " + LIST_PAYMENT_TYPES_FILE_NAME, e);
		}
	}
}
