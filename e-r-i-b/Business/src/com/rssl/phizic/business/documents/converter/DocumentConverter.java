package com.rssl.phizic.business.documents.converter;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.extendedattributes.Attributable;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author khudyakov
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class DocumentConverter
{
	private static final String QUERY_NAME = NotConvertedDocuments.class.getName() + ".list";

	private static final String ROSTELECOM_RECEIVER_CODE    = "125";        //��� ���������� ����������
	private static final String GKH_RECEIVER_CODE   = "10";                 //��� ���������� ���

	private static final String DEBT_FIELD_NAME = "DebtsAccNumber";         //�������������
	private static final String DEBT_ROW_FIELD_NAME = "DebtsCaseNumber";    //�������� ������

	private static final ServiceProviderService  providerService = new ServiceProviderService();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������������� ����������� ��������
	 * @param document ������
	 */
	public static void conver(AbstractPaymentSystemPayment document) throws BusinessException
	{
		try
		{
			ServiceProviderBase provider = providerService.findBySynchKey(document.getReceiverPointCode());
			if (provider == null)
				throw new BusinessException("��������������� ������������ ������� id = " + document.getId() + " �����������, � �.�. ����������� ��������� ����� synchKey = " + document.getReceiverPointCode());

			if (!(provider instanceof BillingServiceProvider))
				throw new BusinessException("��������������� ������������ ������� id = " + document.getId() + " �����������, ������������ ������������ ��� ���������� �����, ��� ���������� = " + document.getReceiverPointCode());

			BillingServiceProvider billingServiceProvider = (BillingServiceProvider) provider;

			PaymentRecipientGateService paymentRecipientGateService = GateSingleton.getFactory().service(PaymentRecipientGateService.class);
			List<Field> keyFields = paymentRecipientGateService.getRecipientKeyFields(billingServiceProvider);
			setFieldsValue(keyFields, document);

			List<Field> extendedFields = paymentRecipientGateService.getRecipientFields(billingServiceProvider, keyFields);
			setFieldsValue(extendedFields, document);

			//��������� ���. ���� �����
			if (getMainSumField(extendedFields) == null)
			{
				extendedFields.add(createAmountField());
			}

			AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
			Adapter iqwAdapter = config.getCardTransfersAdapter();

			//��� �������� IQWave
			if (billingServiceProvider.getBilling().restoreRouteInfo().equals(iqwAdapter.getUUID()))
			{
				String providerCode = provider.getCode();
				//��� ������������� ������� ���������� �������� ���� ������� ������
				if (GKH_RECEIVER_CODE.equals(providerCode))
				{
					extendedFields.add(createDebtRowField());
				}
				//��� ������������� ������� ���������� �������� ���� �������������
				if (ROSTELECOM_RECEIVER_CODE.equals(providerCode))
				{
					extendedFields.add(createDebtField());
				}
			}

			document.setExtendedFields(extendedFields);
			simpleService.addOrUpdate(document);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private static void setFieldsValue(List<Field> extendedFields, AbstractPaymentSystemPayment document)
	{
		for (Field field : extendedFields)
		{
			field.setValue(getFieldValue(field, document));
		}
	}

	private static Object getFieldValue(Field field, AbstractPaymentSystemPayment document)
	{
		ExtendedAttribute extendedAttribute = ((Attributable) document).getAttribute(field.getExternalId());
		if (extendedAttribute == null)
			return "";

		return extendedAttribute.getValue();
	}

	private static Field getMainSumField(List<Field> extendedFields)
	{
		if (extendedFields != null)
		{
			for (Field field : extendedFields)
			{
				if (field.isMainSum())
				{
					return field;
				}
			}
		}

		return null;
	}

	private static Field createAmountField()
	{
		FieldImpl field = new FieldImpl();
		field.setType(FieldDataType.money);
		field.setExternalId("amount");
		field.setName("�����");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(true);

		return field;
	}

	/**
	 * �������� ���� "�������� ������"
	 * @return ���� "�������� ������"
	 */
	static Field createDebtRowField()
	{
		FieldImpl field = new FieldImpl();
		field.setType(FieldDataType.string);
		field.setExternalId(DEBT_ROW_FIELD_NAME);
		field.setName("�������� ������");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(false);

		return field;
	}

	/**
	 * �������� ���� "�������������"
	 * @return ���� "�������������"
	 */
	static Field createDebtField()
	{
		FieldImpl field = new FieldImpl();
		field.setType(FieldDataType.money);
		field.setExternalId(DEBT_FIELD_NAME);
		field.setName("�������������");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(false);

		return field;
	}

	/**
	 * �������� ������ ��������������� ���������� ��� ����������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public static List<Long> getNotConvertedDocuments() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
		    {
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_NAME);

					//noinspection unchecked
					return (List<Long>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
