package com.rssl.phizic.test.way4u;

import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.way4u.UserInfoImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.Random;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */

class SuccessResponseBuilder extends ResponseBuilderBase
{
	private static SuccessResponseBuilder instance= new SuccessResponseBuilder();

	static SuccessResponseBuilder getInstance()
	{
		return instance;
	}

	String build(Document document, UserInfo userInfo) throws Exception
	{
		Element root = document.getDocumentElement();
		Element dataRs = XmlHelper.appendSimpleElement(XmlHelper.selectSingleNode(root, "/UFXMsg/MsgData/Information"), "DataRs");

        addContract(dataRs, userInfo, false);
		if(root.getElementsByTagName("Filter").getLength() == 0)
		{
			addContract(dataRs, userInfo, true);
		}

		setResponseCodes(root, SUCCESS_CODE);

		return XmlHelper.convertDomToText(root);

	}

    /**
     * Записывает информацию о контракте клиентиа в ранее определённый узел
     * @param dataRs место записи
     * @param userInfo данные пользователя
     * @param generateInfo флаг генерации номера карты клиента и идентифкатора ipas
     * @throws TransformerException
     */
    private void addContract(Element dataRs, UserInfo userInfo, boolean generateInfo) throws TransformerException
    {
	    Element contract = XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(dataRs, "ContractRs"),"Contract");
		XmlHelper.appendSimpleElement(contract, "ClientType", "PR");
		Element contractIDT = XmlHelper.appendSimpleElement(contract, "ContractIDT");

	    String cardNumber = generateInfo ? generateCardNumber() : userInfo.getCardNumber();
	    XmlHelper.appendSimpleElement(contractIDT, "ContractNumber", cardNumber);
		Element client = XmlHelper.appendSimpleElement(contractIDT, "Client");
		XmlHelper.appendSimpleElement(client, "ClientType", "PR");

		Element clientInfo = XmlHelper.appendSimpleElement(client, "ClientInfo");
		XmlHelper.appendSimpleElement(clientInfo, "RegNumber", userInfo.getPassport());
		XmlHelper.appendSimpleElement(clientInfo, "ShortName", "WB");
		XmlHelper.appendSimpleElement(clientInfo, "FirstName", userInfo.getFirstname());
		XmlHelper.appendSimpleElement(clientInfo, "LastName", userInfo.getSurname());
		XmlHelper.appendSimpleElement(clientInfo, "MiddleName", userInfo.getPatrname());
		XmlHelper.appendSimpleElement(clientInfo, "BirthDate", new SimpleDateFormat("yyyy-MM-dd").format(userInfo.getBirthdate().getTime()));

		XmlHelper.appendSimpleElement(client, "DateOpen", "2013-07-15");
		XmlHelper.appendSimpleElement(contract, "Currency", "RUR");
		XmlHelper.appendSimpleElement(contract, "ContractName", "WB");
		Element parm = XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(contract, "Product"), "AddInfo"), "Parm");
		XmlHelper.appendSimpleElement(parm, "ParmCode", "ContractCategory");
		XmlHelper.appendSimpleElement(parm, "Value", "Card");

		Element plasticInfo = XmlHelper.appendSimpleElement(contract, "PlasticInfo");
		XmlHelper.appendSimpleElement(plasticInfo, "Title", "MR");
		XmlHelper.appendSimpleElement(plasticInfo, "FirstName", userInfo.getFirstname());
		XmlHelper.appendSimpleElement(plasticInfo, "LastName", userInfo.getSurname());

		XmlHelper.appendSimpleElement(contract, "DateOpen", "2013-05-04");
		XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(contract, "ProductionParms"), "CardExpiry", "1705");
		String extraRS = String.format("ADD_CARD=%d;CB_CODE=%s;AUTH_IDT=%s;ContractStatusID=%d",
				userInfo.isMainCard() ? 0 : 1,
				userInfo.getCbCode(),
				generateInfo? cardNumber.substring(6) : userInfo.getUserId(),
				userInfo.isActiveCard() ? (new Random().nextBoolean() ? 239 : 14) : 231
		);
		XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(contract, "AddContractInfo"), "ExtraRs", extraRS);
        XmlHelper.appendSimpleElement(contract,"Info");
    }

    private String generateCardNumber()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%04d", (int)(Math.random()*10000)));
        sb.append(String.format("%04d", (int) (Math.random() * 10000)));
        sb.append(String.format("%04d", (int)(Math.random()*10000)));
        sb.append(String.format("%04d", (int) (Math.random() * 10000)));
        return sb.toString();
    }

	String buildErmbConnectedInfoResponse(Document document, UserInfo userInfo) throws Exception
	{
		Element root = document.getDocumentElement();
		Element client = XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(XmlHelper.selectSingleNode(root, "/UFXMsg/MsgData/Information"), "DataRs"), "ClientRs"),"Client");
		XmlHelper.appendSimpleElement(client, "ClientType", "PR");
		Element clientInfo = XmlHelper.appendSimpleElement(client, "ClientInfo");
		XmlHelper.appendSimpleElement(clientInfo, "RegNumber", userInfo.getPassport());
		XmlHelper.appendSimpleElement(clientInfo, "ShortName", "WB");
		XmlHelper.appendSimpleElement(clientInfo, "Title", "MR");
		XmlHelper.appendSimpleElement(clientInfo, "FirstName", userInfo.getFirstname());
		XmlHelper.appendSimpleElement(clientInfo, "LastName", userInfo.getSurname());
		XmlHelper.appendSimpleElement(clientInfo, "Gender", "Male");

		Element plasticInfo = XmlHelper.appendSimpleElement(client, "PlasticInfo");
		XmlHelper.appendSimpleElement(plasticInfo, "Title", "MR");
		XmlHelper.appendSimpleElement(plasticInfo, "FirstName", userInfo.getFirstname());
		XmlHelper.appendSimpleElement(plasticInfo, "LastName", userInfo.getSurname());

		XmlHelper.appendSimpleElement(client, "DateOpen", "2013-05-04");

		String addInfo = String.format("ADD_CARD=%d;CB_CODE=%s;AUTH_IDT=%s;ContractStatusID=%d",
						userInfo.isMainCard() ? 0 : 1,
						userInfo.getCbCode(),
						userInfo.getUserId(),
						userInfo.isActiveCard() ? (new Random().nextBoolean() ? 239 : 14) : 231
				);
		XmlHelper.appendSimpleElement(XmlHelper.appendSimpleElement(client, "AddInfo"), "AddInfo01", addInfo);
		XmlHelper.appendSimpleElement(XmlHelper.selectSingleNode(root, "/UFXMsg/MsgData/Information/DataRs/ClientRs"),"Info");

		setResponseCodes(root, SUCCESS_CODE);

		return XmlHelper.convertDomToText(root);
	}
}