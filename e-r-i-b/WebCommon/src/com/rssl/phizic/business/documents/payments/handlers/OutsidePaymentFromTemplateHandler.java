package com.rssl.phizic.business.documents.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskRank;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Handler ��������� ��������� ������, ���� ������� ��� �������:
 * - ������ �������� �� light-�����
 * - ������� ����� (�������������� ���, ��� ������ handler ��������� ������ � �������� ����� � state-machine)
 * - �� ������ ������������ ��������
 * - �� ������ �������������� ����������
 * - �� ������ �������������� ���������� � ��������� "��������� �����" ��� "��������-�������" ��� ������� ��������������� ����� � ��.
 * - ������ �� �� ������� ��� ������ �� �������, �� ������������ ����������� ������ ����������� ������� (��������� ����� ������ ���������� ���������, ��������� �������� ����� � �.�.)
 * @author Dorzhinov
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class OutsidePaymentFromTemplateHandler extends BusinessDocumentHandlerBase
{
    public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
    {
        if (!(document instanceof AbstractPaymentDocument))
            throw new DocumentException("��������� " + AbstractPaymentDocument.class.getSimpleName());

	    //�� light �����
	    if (!MobileApiUtil.isLightScheme())
			return;

	    if (document instanceof JurPayment)
	    {
		    JurPayment jurPayment = (JurPayment) document;

		    //��������� ������ ������������ ��������
		    if (jurPayment.isSelfMobileNumberPayment())
				return;

		    ServiceProviderBase provider = jurPayment.getServiceProvider();
		    if (provider != null)
		    {
				GroupRisk groupRisk = provider.getGroupRisk();
			    //��������� ������� �� �� ������������� ������
				if (groupRisk != null && groupRisk.getRank() == GroupRiskRank.LOW)
					return;
				//��������� ������� �� �� �������������� ������ ���� �� � ������-��������� "��������� �����" ��� "�������� �������"
				// � ���� ���� � ���������� "������� �����" ��� "�������� �������"
				else if (groupRisk != null)
				{
					for (FieldDescription fd : provider.getFieldDescriptions())
					{
						if (fd.getBusinessSubType() != null)
							return;
					}
				}
		    }
	    }
	    if (document instanceof RurPayment)
	    {
		    //������� �������� ���� ���� �������� �� light-�����, ������� � 6.00. �� ���������� ����� ��� �������� ������ �� ����������� ����� ���������(���������� �����������).
		    RurPayment rurPayment = (RurPayment) document;
		    if (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00) && RurPayment.PHIZ_RECEIVER_TYPE_VALUE.equals(rurPayment.getReceiverType()) && rurPayment.getDictFieldId() != null)
			    return;
	    }

	    //������ �� �� ������� ��� �� �������, �� ������������� ������������� �� otp
        AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
        if (!payment.isByTemplate() || payment.isPaymentFromTemplateNeedConfirm())
            throw new DocumentLogicException("� ������ ������ ������� �� ������ ������ ��������� �������� �� ������� ��� ���������� ������ ����� ������ �������.");
    }
}
