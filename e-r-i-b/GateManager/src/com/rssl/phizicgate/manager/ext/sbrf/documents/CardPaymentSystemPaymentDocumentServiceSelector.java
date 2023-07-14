package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.routable.documents.AbstractRoutableDocumentService;
import com.rssl.phizicgate.manager.services.selectors.way.AbstractWaySelector;
import com.rssl.phizicgate.manager.services.selectors.way.WaySelectorHelper;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author krenev
 * @ created 07.12.2010
 * @ $Author$
 * @ $Revision$
 * ������ ������ ������ ������������� �������� ����� iqw � �����.
 * �������: ��������� iqw ������ ���� � iqw, ��������� � ����������� �� ���������������� ��.
 * ������ ��� ������ � ������� ������������ ���������� ������ ���������� � ��������� esb-erib-document-service.
 */
public class CardPaymentSystemPaymentDocumentServiceSelector extends AbstractRoutableDocumentService
{
	private static final String MQ_DELEGATE_PARAMETER_NAME  = "mq-delegate";

	private DocumentService esbDocumentService;
	private static final String ESB_ERIB_DOCUMENT_SERVICE_PARAMETER_NAME = "esb-erib-document-service";

	private DocumentService mqDelegate;

	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public CardPaymentSystemPaymentDocumentServiceSelector(GateFactory factory)
	{
		super(factory);
	}

	protected DocumentService getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		boolean isWithdraw = WithdrawDocument.class == document.getType();
		Class<? extends GateDocument> actual = document.getType();
		if (CardPaymentSystemPayment.class != actual && CardPaymentSystemPaymentLongOffer.class != actual &&
				!(isWithdraw && ((WithdrawDocument)document).getWithdrawType() == CardPaymentSystemPayment.class))
		{
			throw new IllegalArgumentException("�������� ��� ���������. ��������� CardPaymentSystemPayment ��� CardPaymentSystemPaymentLongOffer ��� WithdrawDocument, ������� " + actual);
		}

		CardPaymentSystemPayment payment = isWithdraw ? (CardPaymentSystemPayment) ((WithdrawDocument)document).getTransferPayment()
													  : (CardPaymentSystemPayment) document;

		if (AbstractWaySelector.useMQDelegate(document, WaySelectorHelper.DOCUMENT_SENDER))
			return mqDelegate;

		String receiverPointCode = payment.getReceiverPointCode();

		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter iqwAdapter = config.getCardTransfersAdapter();
		if (iqwAdapter == null)
			throw new GateException("�� ������ ������� ������� ��� ��������� ���������");

		if (iqwAdapter.getUUID().equals(IDHelper.restoreRouteInfo(receiverPointCode)))
		{
			//��� ��������� uuid �������� ������ �������� �� ���������� ������� �������
			String uuid = ExternalSystemHelper.getCode(iqwAdapter.getUUID());
			//�������� � ������ ����������� IQW ������ ���� � IQW.
			return GateManager.getInstance().getService(uuid, DocumentService.class);
		}

		//������ ������� �� ���������������� ����.
		if (ESBHelper.isESBSupported(document))
		{
			return esbDocumentService;
		}
		throw new GateLogicException("�� �� ������ ��������� ������ � ����� �� ���� �����������. ����������, ������� ���� ��������.");
	}

	public void setParameters(Map<String, ?> params)
	{
		String esbDocumentServiceClassName = (String) params.get(ESB_ERIB_DOCUMENT_SERVICE_PARAMETER_NAME);
		if (esbDocumentServiceClassName == null)
		{
			throw new IllegalStateException("�� ����� " + ESB_ERIB_DOCUMENT_SERVICE_PARAMETER_NAME);
		}
		esbDocumentService = loadService(esbDocumentServiceClassName);

		String mqDelegateClassName = (String) params.get(MQ_DELEGATE_PARAMETER_NAME);
		if (StringHelper.isEmpty(mqDelegateClassName))
			throw new IllegalStateException("�� ����� " + MQ_DELEGATE_PARAMETER_NAME);
		mqDelegate = loadService(mqDelegateClassName);
	}

	/**
	 * ��������� ������ �� ����� ������
	 * @param className fqn
	 * @return ������� �������.
	 */
	protected DocumentService loadService(String className)
	{
		try
		{
			Class<DocumentService> senderClass = ClassHelper.loadClass(className);
			Constructor<DocumentService> constructor = senderClass.getConstructor(GateFactory.class);
			return constructor.newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException("������ ��� ������������ DocumentService:"+className, e);
		}
	}
}

