package com.rssl.phizicgate.cpfl.documents;

import com.rssl.phizgate.common.routable.RecipientImpl;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends AbstractService implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ����� ����������� � �������� �� �����, ���� � ���
	 * @param account ���� ����������
	 * @param bic ��� ����� ����������
	 * @param inn ��� ����������
	 * @param billing �������, � ������� ������ ����� ����������
	 * @return ������ ����������� ��������������� ����������. ���� ��������� �� ������� - ������ ������
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		BackRefBankInfoService bankInfoService = getFactory().service(BackRefBankInfoService.class);

		GateMessage gateMessage = service.createRequest("recipientListDemand_q");
		gateMessage.addParameter("account", account);
		gateMessage.addParameter("bankBIC", bic);

		ResidentBank bank = bankInfoService.findByBIC(bic);
		if(bank == null)
			throw new GateException("���� � ��� ������ " + bic + " �� ������");
		
		if(!StringHelper.isEmpty(bank.getAccount()))
		{
			gateMessage.addParameter("bankAccount", bank.getAccount());
		}
		else if (account.startsWith("40101"))
		{
			gateMessage.addParameter("bankAccount", "0");//��� ��������� ����������� ��������� 0 ��� ������������� ���������
		}
		if (!StringHelper.isEmpty(inn))
		{
			gateMessage.addParameter("inn", inn);
		}
		Document responce = service.sendOnlineMessage(gateMessage, null);
		final List<Recipient> result = new ArrayList<Recipient>();
		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "//recipient", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String code = XmlHelper.getSimpleElementValue(element, "uniqueNumber");
					String name = XmlHelper.getSimpleElementValue(element, "name");
					RecipientImpl recipient = new RecipientImpl();
					recipient.setSynchKey(code);
					recipient.setName(name);
					result.add(recipient);
				}
			}
			);
		}
		catch (GateTimeOutException e)
		{
			throw new GateLogicException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		if (result.isEmpty())
		{
			//���� � ���� ���������� ��� - ������� ����������������, ����� ������ ������ ������������� � �������� ����.
			RecipientImpl recipient = new RecipientImpl();
			recipient.setSynchKey(CPFLBillingPaymentHelper.NOT_CONTRACTUAL_RECEIVER_CODE);
			result.add(recipient);
		}
		return result;
	}

	/**
	 * �������� ������ ������������ ����������(��������) �������
	 *
	 * @param billingClientId ������������� ������� � �������.
	 * @param billing ������
	 * @return ������ ����������� ��� ������ �������, ���� �� ���
	 */
	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ������ ��������� ������������ ����� ������������� ����������(�������)
	 * @param recipient ����������
	 * @param billingClientId ������������� ������� � ��������
	 * @return ������ �������� ����� ��� ������ �������, ���� �� ���
	 */
	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� �������������� ���������� �� ����������.
	 *
	 * @param recipient ����������
	 * @param fields ������ �������������� �����
	 * @param debtCode - ��� �������������, ����� ���� null
	 * @return �������������� ��������� �� ����������
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * ��������� ���������� ������� �� ��� ��������������.
	 * GateException - ���� ���������� �� ������.
	 *
	 * @param recipientId ������������� ����������.
	 * @return ���������� �������
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ������ ����������� ���������� � ��������
	 * @param billing �������
	 * @return ������ ����������� ��� ������ �������, ���� �� ���
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ������ ���. ����� ��� ����������
	 * @param recipient ����������
	 * @param keyFields ����
	 * @return ������ ��� �����
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ������ �������� ����� ��� ����������
	 * @param recipient ����������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @return ������ �������� �����
	 */
	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� ��������� ����� ������������ ��������
	 * @param cardId - ����� ����� ������������ ��������
	 * @param billing - �������
	 * @return �������� ����� ������������ ��������
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
