package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

/**
 * @author Krenev
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemPaymentGroundSaveHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	/*
	* � ���������� ������� ������� ������� ����� ���������, ��� ������ ���� ������� (42308)
	* ��������� ���������� ����� (
	* ������������ ����������,
	* ��� ����� ����������,
	* ��������� ���� ����������)
	* � ��������� ������� �������
	* (�������������� ���� � ��������� ������� � ������ ���������� �����).
	* � �������� ������ ��������� � ���������� ���������� ����������� �������� ���� ����� ����� ��������������
	* ������ �|�.
	* �������� ��������������� ������� ������� � ����������� ����������� �����.
	* � �������� ���������� ������� ����������� ������������ �����, ������� ������������ �� ����� ��������,
	* � �� �������� ����� �������������� ������, �������� � ����������� ����������� �����
	* (� ������� ���� ������������ ������ �@�).
	* ������ ���������� �������:
	*         6837|��� �����|042007681|40702810813020002487|�����@89037776655|���@���������.
	*/
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof GateExecutableDocument))
		{
			throw new DocumentException("O�������� GateExecutableDocument");
		}
		GateExecutableDocument executableDocument = (GateExecutableDocument) document;
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(executableDocument.getType()))
			throw new DocumentException("�������� ��� ������� - ��������� ��������� AbstractPaymentSystemPayment");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) executableDocument.asGateDocument();
		try
		{

			String pointCode = payment.getReceiverPointCode();
			ServiceProviderShort serviceProvider = serviceProviderService.findShortProviderBySynchKey(pointCode);
			if (serviceProvider == null)
			{
				return;// ���� ���������� � �� ���(������� ����������) - ��������� �������� ��� ������� ��� ��� ������� � ����.
			}
			String attrDelimiter = serviceProvider.getAttrDelimiter();
			String attrValuesDelimiter = serviceProvider.getAttrValuesDelimiter();
			if (!serviceProvider.isGround())
			{
				//����� �������, ��� ����������� ���������� �� �����.
				return;
			}

			BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
			Office office = backRefBankrollService.getAccountOffice(payment.getInternalOwnerId(), payment.getChargeOffAccount());

			StringBuilder builder = new StringBuilder();
			builder.append(new SBRFOfficeCodeAdapter(office.getCode()).getBranch()).append(attrDelimiter);
			builder.append(serviceProvider.getName()).append(attrDelimiter);
			builder.append(payment.getReceiverBank().getBIC()).append(attrDelimiter);
			builder.append(payment.getReceiverAccount()).append(attrDelimiter);
			for (Field field : payment.getExtendedFields())
			{
				if (field.isVisible())
				{
					builder.append(field.getName()).append(attrValuesDelimiter).
							append(field.getValue()).append(attrDelimiter);
				}
			}
			String ground = builder.substring(0, builder.length() > 210 ? 210 : builder.length() - 1);//�������� ��������� �����������
			payment.setGround(ground);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
