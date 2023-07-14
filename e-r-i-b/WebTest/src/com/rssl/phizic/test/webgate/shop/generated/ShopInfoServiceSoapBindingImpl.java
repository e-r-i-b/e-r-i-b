/**
 * ShopInfoServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.shop.generated;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.test.webgate.shop.generated.mock.Document;
import com.rssl.phizic.test.webgate.shop.generated.mock.Status;
import com.rssl.phizic.test.wsgateclient.shop.ShopSystemName;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Calendar;

public class ShopInfoServiceSoapBindingImpl implements com.rssl.phizic.test.webgate.shop.generated.ShopInfoService{

	/**
	 * Создание полной структуры документов и их возврат на клиентскую часть
	 * @param parameters - сообщение с кодами документов
	 * @return - сообщение со списком документов
	 * @throws java.rmi.RemoteException
	 */
	public com.rssl.phizic.test.webgate.shop.generated.DocInfoRsType docInfo(com.rssl.phizic.test.webgate.shop.generated.DocInfoRqType parameters) throws java.rmi.RemoteException {

		init();

	    DocInfoRsType docInfo = new DocInfoRsType();

	    docInfo.setRqUID(new RandomGUID().getStringValue());

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();
	    docInfo.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));

	    InfoType[] infoTypes = new InfoType[parameters.getDocuments().length];

		Document mockDocument = new Document();
		Status mockStatus = new Status();

		String spName = "OZON";
		for (int i = 0; i < parameters.getDocuments().length; i++)
	    {
		    InfoType infoType = new InfoType();

		    try
		    {
			    RegRqDocumentType document = mockDocument.getDocument(parameters.getDocuments()[i]);

			    if (document == null)
			        continue;
			    spName = document.getRecipientName();

			    if (ShopSystemName.valueOf(spName)== ShopSystemName.YANDEX)
				    document.setRecipientName("ООО «Интернет магазин.РУ»");

			    infoType.setDocument(document);
			    infoType.setStatus(mockStatus.getStatus());

			    infoTypes[i] = infoType;
		    }
		    catch (BusinessException e)
		    {
			    continue;
		    }
	    }

	    docInfo.setSPName(spName);
		
	    docInfo.setDocuments(infoTypes);

	    return docInfo;
    }


	/**
	 * Прием и отправка сообщения на получение статуса заказа.
	 * @param parameters - запрос на получение информации по статусу документа
	 * @return коды статуса
	 * @throws java.rmi.RemoteException
	 */
	public com.rssl.phizic.test.webgate.shop.generated.DocInfoStatRsType docInfoStat(com.rssl.phizic.test.webgate.shop.generated.DocInfoStatRqType parameters) throws java.rmi.RemoteException
	{
		DocInfoStatRsType docInfoStatRsType = new DocInfoStatRsType();

	    docInfoStatRsType.setRqUID(new RandomGUID().getStringValue());
	    docInfoStatRsType.setSystemId(parameters.getSystemId());

	    DateFormat simpleDateFormat = ShopConstants.getDateFormat();
	    docInfoStatRsType.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));

	    Status mockStatus = new Status();

		ResultType resultType = new ResultType();

		resultType.setERIBUID(parameters.getDocuments().getERIBUID());
		resultType.setStatus(mockStatus.getStatus());

	    docInfoStatRsType.setDocuments(new DocInfoStatRsDocumentsType(resultType));

	    return docInfoStatRsType;
	}

	/**
	 * Прием и отправка сообщения о том, что документы успешно (или не успешно) оплачены
	 * @param parameters - оповещение о статусе оплаты документов
	 * @return коды успешного или не успешного завершения
	 * @throws java.rmi.RemoteException
	 */
	public com.rssl.phizic.test.webgate.shop.generated.DocStatNotRsType docStatNot(com.rssl.phizic.test.webgate.shop.generated.DocStatNotRqType parameters) throws java.rmi.RemoteException {

	    DocStatNotRsType docStatNot = new DocStatNotRsType();

	    docStatNot.setRqUID(new RandomGUID().getStringValue());

	    DateFormat simpleDateFormat = ShopConstants.getDateFormat();
	    docStatNot.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));

		ResultType[] resultTypes = new ResultType[parameters.getDocuments().length];

	    Status mockStatus = new Status();

	    for (int i = 0; i < parameters.getDocuments().length; i++)
	    {
		    ResultType resultType = new ResultType();

		    resultType.setERIBUID(parameters.getDocuments()[i].getERIBUID());
		    resultType.setStatus(mockStatus.getStatus());

		    resultTypes[i] = resultType;
	    }

	    docStatNot.setSystemId(parameters.getSystemId());
	    docStatNot.setDocuments(resultTypes);

	    return docStatNot;
    }

	/**
	 * Инициализация конфигов для их использования
	 */
	private void init() throws RemoteException
	{
		XmlHelper.initXmlEnvironment();
	}
}
