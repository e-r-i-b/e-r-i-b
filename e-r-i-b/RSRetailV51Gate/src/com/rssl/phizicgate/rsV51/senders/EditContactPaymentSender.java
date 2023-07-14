package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Krenev
 * @ created 20.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditContactPaymentSender extends AbstractDocumentSender
{
	/*

	 public void editContactPayment(ContactPayment parent)  throws DocumentException
	 {
		DocumentRetailService documentService = GateSingleton.getFactory().service(DocumentRetailService.class);
		ContactDocument contactDocument = documentService.createContactDocument();
 // нужно найти в логе платеж по appKey
		MessagingLogEntry messagingLogEntry = null;
		 Query query = new BeanQuery("com.rssl.phizic.logging.findDocumentById");
		 query.setParameter("documentId", parent.getId());

		 try
		 {
			List<MessagingLogEntry> list = query.executeList();
			messagingLogEntry = list.get(list.size()-1);
		 }
		 catch (DataAccessException ex)
		 {
			 throw new  DocumentException(ex.getMessage());
		 }

		 contactDocument.setApplicationKey(messagingLogEntry.getMessageRequestId());
		 contactDocument.setReceiverFirstName(parent.getAttribute("receiver-first-name") != null ? parent.getAttribute("receiver-first-name").getStringValue() : "");
		 contactDocument.setReceiverSecondName(parent.getAttribute("receiver-patr-name") != null ? parent.getAttribute("receiver-patr-name").getStringValue() : "");
		 contactDocument.setReceiverLastName(parent.getAttribute("receiver-sur-name") != null ? parent.getAttribute("receiver-sur-name").getStringValue() : "");
 //	       contactDocument.setBornDateReceiv(contactPayment.getAttribute("currency").getStringValue()));
		 contactDocument.setAdditionalIinformation(parent.getAttribute("add-information") != null ? parent.getAttribute("add-information").getStringValue() : "");
		 contactDocument.setTransferenceDate(parent.getDocumentDate());

		 try
		 {
			 //  далее ищем этот документ в Retail и обновл€ем его
			documentService.sendContactEditDocument(contactDocument, parent.getId(), XmlHelper.convertDomToText(parent.convertToDom()));
		  }
		  catch (TransformerException e)
		  {
			  throw new DocumentException(e);
		  }
		 catch (GateException e)
		  {
			  throw new DocumentException(e);
		  }
	 }

 */
	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//TODO реализовать com.rssl.phizicgate.rsV51.senders.EditContactPaymentSender.send
		throw new UnsupportedOperationException("Ќе реализован метод void com.rssl.phizicgate.rsV51.senders.EditContactPaymentSender.send");
/*
		ExtendedAttribute parentIdAttr = ((DerivedContactPayment)document).getAttribute("parent-id");
		if ( parentIdAttr == null )
			throw new DocumentException("Ќе задан parentId");

		PaymentService service = new PaymentService();
		ContactPayment parent = null;
		try
		{
			parent = (ContactPayment) service.findById(Long.parseLong(parentIdAttr.getStringValue()));
            // мен€ем получател€
			DerivedContactPayment paymentNew = (DerivedContactPayment)document;
			parent.setDocumentDate(paymentNew.getDocumentDate());
			Map<String, ExtendedAttribute> atributes = parent.getAttributes();
			atributes.put("receiver-first-name", paymentNew.getAttribute("receiver-first-name"));
			atributes.put("receiver-patr-name", paymentNew.getAttribute("receiver-patr-name"));
			atributes.put("receiver-sur-name", paymentNew.getAttribute("receiver-sur-name"));
			atributes.put("add-information", paymentNew.getAttribute("add-information"));
			parent.addAttributes(atributes.values());
		}
		catch (BusinessException e)
		{
			throw new DocumentException("при отправке документа редактировани€ не найден исходный платеж id=" + parentIdAttr.getStringValue(), e);
		}
		parent.setState(ContactState.MODIFICATION);
		try
		{
			service.update(parent);
			editContactPayment(parent);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("ошибка установлени€ статуса '»зменение'",e);
		}
*/
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}
}
