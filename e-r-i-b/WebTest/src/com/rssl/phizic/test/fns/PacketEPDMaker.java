package com.rssl.phizic.test.fns;

import com.rssl.phizic.security.crypto.*;
import com.rssl.phizic.utils.RandomGUID;

/**
 * @author gulov
 * @ created 24.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Создание сообщения packetEPD для ФНС в виде xml
 */
public class PacketEPDMaker
{

	public PacketEPDMaker()
	{
	}

	/**
	 * Создать сообщение (для ФНС)
	 * @return строка xml
	 * @throws Exception
	 */
	public String makeXmlFNS(String countPayment) throws Exception
	{
		XmlWriter xmlWriter = new XmlWriter();
		MockPacket mockPacket = new MockPacket(Integer.parseInt(countPayment));

		xmlWriter.writeEntity("PacketEPD")
			.writeAttribute("xmlns", "urn:cbr-ru:ed:v2.0")
			.writeAttribute("xmlns:dsig", "urn:cbr-ru:dsig:v1.1")
			.writeAttribute("EDNo", mockPacket.getHeader().getNumber())
			.writeAttribute("EDDate", mockPacket.getHeader().getDate())
			.writeAttribute("EDAuthor", mockPacket.getHeader().getAuthor())
			.writeAttribute("EDQuantity", mockPacket.getHeader().getQuantity())
			.writeAttribute("Sum", mockPacket.getHeader().getSum())
			.writeAttribute("SystemCode", mockPacket.getHeader().getSystemCode());

		CryptoProviderFactory cryptoProviderFactory = CryptoProviderHelper.getDefaultFactory();
		CryptoProvider cryptoProvider = cryptoProviderFactory.getProvider();

		for (PacketItem packetItem : mockPacket.getItemList())
		{
			XmlWriter xmlWriterItem = new XmlWriter();

			xmlWriterItem.writeEntity("ED101")
				.writeAttribute("TransKind", packetItem.getTransKind())
				.writeAttribute("Priority", packetItem.getPriority())
				.writeAttribute("ReceiptDate", packetItem.getReceiptDate())
				.writeAttribute("PaytKind", packetItem.getPaymentKind())
				.writeAttribute("Sum", packetItem.getSum())
				.writeEntity("Purpose")
				.writeText(packetItem.getPurpose())
				.endEntity()
				.writeEntity("DepartmentalInfo")
				.writeAttribute("DrawerStatus", packetItem.getDrawerStatus())
				.writeAttribute("CBC", packetItem.getKbk())
				.writeAttribute("OKATO", packetItem.getOkato())
				.writeAttribute("PaytReason", packetItem.getPaymentReason())
				.writeAttribute("TaxPeriod", packetItem.getTaxPeriod())
				.writeAttribute("DocNo", packetItem.getDocumentNumber())
				.writeAttribute("DocDate", packetItem.getDocumentDate())
				.writeAttribute("TaxPaytKind", packetItem.getTaxPaymentKind())
				.endEntity()
				.writeEntity("Payer")
				.writeAttribute("INN", packetItem.getPayerINN())
				.endEntity()
				.writeEntity("Payee")
				.writeAttribute("PersonalAcc", packetItem.getPersonalAccount())
				.writeAttribute("INN", packetItem.getPayeeINN())
				.writeAttribute("KPP", packetItem.getPayeeKPP())
				.writeEntity("Name")
				.writeText(packetItem.getPayeeTitle())
				.endEntity()
				.writeEntity("Bank")
				.writeAttribute("BIC", packetItem.getPayeeBIC())
				.writeAttribute("CorrespAcc", packetItem.getPayeeCorrespondentAccount())
				.endEntity()
				.endEntity()
			    .endEntity();

			Signature signature = cryptoProvider.makeSignature(CryptoConstants.FNS_JCP_PRIVATE_SERTIFICATE_ID, xmlWriterItem.toString());

			xmlWriter.writeXml(insertSign(xmlWriterItem.toString(), signature.toBase64()));
		}

		xmlWriter.endEntity();

		return xmlWriter.toString();
	}

	/**
	 * Создать сообщение (для УЭК)
	 * @return строка xml
	 * @throws Exception
	 */
	public String makeXmlUEC() throws Exception
	{
		XmlWriter xmlWriter = new XmlWriter();
		MockPacket mockPacket = new MockPacket(1);
		PacketItem packetItem = mockPacket.getItemList().get(0);

		CryptoProviderFactory cryptoProviderFactory = CryptoProviderHelper.getDefaultFactory();
		CryptoProvider cryptoProvider = cryptoProviderFactory.getProvider();

		xmlWriter.writeEntity("PacketEPD")
			.writeAttribute("xmlns", "urn:cbr-ru:ed:v2.0")
			.writeAttribute("xmlns:dsig", "urn:cbr-ru:dsig:v1.1")
			.writeAttribute("EDNo", mockPacket.getHeader().getNumber())
			.writeAttribute("EDDate", mockPacket.getHeader().getDate())
			.writeAttribute("EDAuthor", mockPacket.getHeader().getAuthor())
			.writeAttribute("EDQuantity", mockPacket.getHeader().getQuantity())
			.writeAttribute("Sum", mockPacket.getHeader().getSum())
			.writeAttribute("SystemCode", mockPacket.getHeader().getSystemCode());

		XmlWriter xmlWriterItem = new XmlWriter();
			xmlWriterItem.writeEntity("ED101")
			.writeAttribute("TransKind", packetItem.getTransKind())
			.writeAttribute("Priority", packetItem.getPriority())
			.writeAttribute("PaytKind", packetItem.getPaymentKind())
			.writeAttribute("Sum", packetItem.getSum())
			.writeAttribute("DocUID", new RandomGUID().getStringValue())
			.writeEntity("Purpose")
			.writeText(packetItem.getPurpose())
			.endEntity()
			.writeEntity("Payer")
			.writeAttribute("INN", packetItem.getPayerINN())
			.endEntity()
			.writeEntity("Payee")
			.writeAttribute("PersonalAcc", packetItem.getPersonalAccount())
			.writeAttribute("INN", packetItem.getPayeeINN())
			.writeEntity("Name")
			.writeText(packetItem.getPayeeTitle())
			.endEntity()
			.writeEntity("Bank")
			.writeAttribute("BIC", packetItem.getPayeeBIC())
			.endEntity()
			.endEntity()
			.endEntity();

		Signature signature = cryptoProvider.makeSignature(CryptoConstants.UEC_JCP_PRIVATE_SERTIFICATE_ID, xmlWriterItem.toString());
		xmlWriter.writeXml(insertSign(xmlWriterItem.toString(), signature.toBase64()));

		xmlWriter.endEntity();

		return xmlWriter.toString();
	}

	private String insertSign(String xmlText, String sign)
	{
		String signTag = "<dsig:SigValue>" + sign + "</dsig:SigValue>";
        int pos = xmlText.indexOf("<", 1);

		return xmlText.substring(0, pos) + signTag + xmlText.substring(pos);
	}
}
