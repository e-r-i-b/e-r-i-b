package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Gainanov
 * @ created 24.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReqXObjectQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
//���� � ������� ������������ AddService ������������ ������ �����
		Element service = XmlHelper.selectSingleNode(message.getDocumentElement(), "/RSASMsg/ReqXObject/XObject");
		String id = service.getAttribute("id");
		if (id.contains("AddService"))
		{
			return XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsXObject_AddService.xml");
		}
		service = XmlHelper.selectSingleNode(message.getDocumentElement(), "/RSASMsg/ReqXObject/ListOfMoreInfo/moreInfo");
		//���� ������ ����� ���� �� ������
		if (service.getAttribute("xpRef").equals("/RSASMsg/AnsXObject/XObject/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/Val"))
		{
			return XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsXObjectByServiceId.xml");
		}
		//����� ������ ����� ���� �� abonentId
		return XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsXObjectByAbonentId.xml");
	}
}
