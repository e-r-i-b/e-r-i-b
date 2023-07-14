package com.rssl.phizic.business.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.business.resources.external.ActiveRurCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * ��� iqwave ��������� ���������� ����� ������� � �� ���� ��������� ��������
 */
public class ClientCardAppendDebtService extends AbstractService implements DebtService
{
	private DebtService delegate;

	public ClientCardAppendDebtService(GateFactory factory)
	{
		super(factory);
		delegate = (DebtService) getDelegate(DebtService.class.getName() + DELEGATE_KEY);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field>  keyFields) throws GateException, GateLogicException
	{
			//���������� �������� �� �������� ���������� ���������� iqwave.
			List<Field> fields = new ArrayList<Field> (keyFields);
			if (ESBHelper.isIQWavePayment(IDHelper.restoreRouteInfo((String) recipient.getSynchKey())))
			{
				//���� ��������� �����
				//����� ������ ���������� ����� �������
				CardLink link = getCurrentClinmtCardLink();
				Card card = link.getCard();
				if(MockHelper.isMockObject(card))
				{
					throw new GateLogicException("���������� �������� ���������� �� ����� "+ MaskUtil.getCutCardNumber(link.getNumber()));
				}

				FieldImpl field = new FieldImpl();
				field.setName("����� �����");
				field.setExternalId("$$CLIENT_CARD_NUMBER");
				field.setType(FieldDataType.string);
				field.setValue(link.getNumber());
				fields.add(field);

				field = new FieldImpl();
				field.setName("���� ��������� �������� �����");
				field.setExternalId("$$CLIENT_CARD_EXPIRE_DATE");
				field.setType(FieldDataType.date);
				field.setValue(card.getExpireDate());
				fields.add(field);
			}
			return delegate.getDebts(recipient, fields);
	}

	/**
	 * �������� ������ �� �������� �������� ����� �������� �������.
	 * @return ������ �� �����.
	 */
	private CardLink getCurrentClinmtCardLink() throws GateException, GateLogicException
	{
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			//��� ��� ��� ������ ������������� ������������ ������ �������� �������� �����, �� �������� ������ ��
			List<CardLink> links = data.getCards(new ActiveRurCardFilter());
			if (links.isEmpty())
			{
				throw new GateException("������ �� ����� ����");
			}
			return links.get(0);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
	}
}
