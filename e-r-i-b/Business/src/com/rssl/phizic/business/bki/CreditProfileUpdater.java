package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.BKIResponse;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreditReportPayment;
import com.rssl.phizic.gate.bki.BKIRequestType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.bki.CreditResponseParser;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;

import java.util.Calendar;
import javax.xml.bind.JAXBException;

/**
 * ���������� ���������� ������� ������� �� ������ �� ���
 * @author Puzikov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CreditProfileUpdater
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final CreditProfileService creditProfileService = new CreditProfileService();
	private final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	/**
	 * �������� ��������� ������� �������
	 * @param response ����� ���
	 */
	public void update(BKIResponse response)
	{
		//1. ����� ���������� �������
		PersonCreditProfile creditProfile = getPersonCreditProfile(response.getPersonId());
		if (creditProfile == null)
		{
			log.error("�� ������ ��������� ������� �� ��������� �� ��� (������ ������������� ���������)");
			return;
		}

		//2. ���������� ������� � ������� �� ������
		updateData(creditProfile, response);
	}

	private void updateData(PersonCreditProfile creditProfile, BKIResponse response)
	{
		String responseXml = response.getResponseXml();
		Long paymentId = response.getPaymentId();
		boolean isGetResponse = response.getRequestType() == BKIRequestType.BKIGetCreditHistory;

		try
		{
			CreditResponseParser parser = new CreditResponseParser(false, true);
			EnquiryResponseERIB responseBKI = (EnquiryResponseERIB) parser.parse(responseXml);

			validateResponse(responseBKI);

			boolean connected = hasCreditHistory(responseBKI);

			creditProfile.setConnected(connected);
			creditProfile.setReport(isGetResponse ? responseXml : null);
			creditProfile.setLastReport(isGetResponse ? Calendar.getInstance() : null);

			if (isGetResponse)
				updatePaymentDocument(paymentId, CreditReportStatus.RECEIVED);
		}
		catch (Exception e)
		{
			log.error("�� ������� ��������� ��������� ������� �� ������ �� ���", e);
			if (isGetResponse)
			{
				creditProfile.setLastGetError(Calendar.getInstance());
				try
				{
					updatePaymentDocument(paymentId, CreditReportStatus.ERROR);
				}
				catch (BusinessException ex)
				{
					log.error("�� ������� �������� ��������� �������� c id" + paymentId +  " �� ��������� �� ���", ex);
				}
			}
		}
		finally
		{
			try
			{
				creditProfileService.save(creditProfile);
			}
			catch (BusinessException e)
			{
				log.error("�� ������� ��������� ��������� ������� �������", e);
			}
		}
	}

	private PersonCreditProfile getPersonCreditProfile(long personId)
	{
		try
		{
			return creditProfileService.findByPersonId(personId);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	//������� ���� CONNECTED, ���� � ������ ���� ���� CAISRecordsOwner � ������������� ����������� ������.
	private boolean hasCreditHistory(EnquiryResponseERIB responseBKI) throws JAXBException
	{
		//��� ����������� �������������� ������� � �������
		long accCount = responseBKI.getConsumers().getSS().get(0).getSummary().getCAISRecordsOwner();
		return accCount > 0;
	}

	//������ �������� GET ������ �� ��������, � ������ ���� �� ������� - ������ ��� ������� � ���������� �����
	private void validateResponse(EnquiryResponseERIB responseBKI)
	{
		EnquiryResponseERIB.Consumers consumers = responseBKI.getConsumers();
		if (consumers == null)
		{
			throw new IllegalArgumentException("�������� ������ ����� �� ��� : ������� �� ������� ���");
		}
		if (responseBKI.getErrorCode() != 0)
		{
			throw new IllegalArgumentException("��������� ������ �� ��� " + responseBKI.getErrorDescription());
		}
	}

	private void updatePaymentDocument(Long paymentId, CreditReportStatus status) throws BusinessException
	{
		BusinessDocument document = businessDocumentService.findById(paymentId);
		if (document == null)
			throw new NotFoundException("�� ������ �������� � id=" + paymentId);
		CreditReportPayment creditReportPayment = (CreditReportPayment) document;
		creditReportPayment.setCreditReportStatus(status.toString());
		businessDocumentService.addOrUpdate(document);
	}
}
