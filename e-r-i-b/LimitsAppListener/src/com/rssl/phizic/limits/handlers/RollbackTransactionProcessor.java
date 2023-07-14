package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.common.types.limits.OperationType;
import com.rssl.phizic.limits.exceptions.TransactionNotFoundException;
import com.rssl.phizic.limits.servises.DocumentTransaction;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для операции отката
 */
public class RollbackTransactionProcessor extends TransactionProcessorBase
{
	private static final String XSD_SCHEME_PATH = "com/rssl/phizic/limits/handlers/schemes/rollbackTransactionRq.xsd";

	/**
	 * ctor
	 */
	public RollbackTransactionProcessor()
	{
		super(XSD_SCHEME_PATH);
	}

	protected void doProcess(Element root) throws Exception
	{
		String documentExternalId = XmlHelper.getSimpleElementValue(root, Constants.DOCUMENT_EXTERNAL_ID_TAG);
		String externalID = XmlHelper.getSimpleElementValue(root, Constants.EXTERNAL_ID_TAG);
		String operationDateValue = XmlHelper.getSimpleElementValue(root, Constants.OPERATION_DATE_TAG);
		Calendar operationDate = XMLDatatypeHelper.parseDateTime(operationDateValue);

		DocumentTransaction transaction = DocumentTransaction.find(documentExternalId, operationDate);

		if (transaction == null)
		{
			throw new TransactionNotFoundException("Транзакция для documentExternalId=" + documentExternalId +
					" и operationDate=" + DateHelper.toISO8601DateFormat(operationDate) + " не найдена.");
		}

		DocumentTransaction rollback = new DocumentTransaction(transaction);
		rollback.setExternalId(externalID);
		rollback.setOperationType(OperationType.ROLL_BACK);
		rollback.add();
	}
}
