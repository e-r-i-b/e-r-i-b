package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author eMakarov
 * @ created 24.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class SetGoodAndServicesDateAction extends SetBusinessDocumentDateAction
{
	private static final String CHECK_TIME = "checkTime";

	public SetGoodAndServicesDateAction()
	{
		super();
	}

	//TODO ���� �� ���� ��� ��� ����. �.�. ����� ������ ���������� �������� ��� ���� �����, � ��� ������ ����� ������������ ���� ����� �������
	protected int isWorkTime(BusinessDocument document) throws DocumentLogicException, DocumentException
	{
		//��� �� ���������! - ����� �� ������
		return 0;
	}

	//TODO ���� �� ���� ��� ��� ����
	/* �������� ������ ����������, �� �������������� ������������� ������� ������ ���������� ������ ������� � �����.
	protected Time getCheckTime(BusinessDocument document) throws DocumentLogicException
	{
		if (!(document instanceof GoodsAndServicesPayment)) {
			throw new IllegalArgumentException("�������� �� �������� ��������");
		}
		try
		{
			GoodsAndServicesPayment payment = (GoodsAndServicesPayment)document;
			String checkTimeString = dbPropertyReader.getProperty(getParameter(CHECK_TIME) + "-" + payment.getAttribute("appointment").getStringValue());
			//                                                           �����������  ^
			return Time.valueOf(checkTimeString);
		}
		catch (IllegalArgumentException e)
		{
			throw new DocumentLogicException("�������� ������ ��������� �����, ������ ���� hh:mm:ss", e);
		}
	}
	*/
}
