package com.rssl.phizic.business.documents;

/**
 * Отмена оплаты заказа из интернет-магазина
 *
 * @author gladishev
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class RollbackOrderClaim extends RecallDocument
{
	private static final String EXTERNAL_DOCUMENT_ID_FIELD = "external-document-id";
	private static final String REFUND_UUID = "refund-uuid";

	/**
	 * @return id документа отмены в интернет-магазине
	 */
	public String getExternalDocumentId()
	{
		return getNullSaveAttributeStringValue(EXTERNAL_DOCUMENT_ID_FIELD);
	}

	public String getRefundUuid()
	{
		return getNullSaveAttributeStringValue(REFUND_UUID);
	}
}