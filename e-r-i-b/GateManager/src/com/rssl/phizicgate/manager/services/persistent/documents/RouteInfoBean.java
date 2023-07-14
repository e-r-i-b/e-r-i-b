package com.rssl.phizicgate.manager.services.persistent.documents;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.claims.DepositClosingClaim;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * информация о маршрутизации
 * @author gladishev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class RouteInfoBean
{
	private String externalOwnerId;
	private String externalId;
	private String autoPayServiceCode;
	private String externalDepositId;
	private String receiverPointCode;
	private String withdrawExternalId;
	private Office office;
	private String uid;
	private String routeInfo;

	public String getExternalOwnerId()
	{
		return externalOwnerId;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		this.externalOwnerId = externalOwnerId;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getAutoPayServiceCode()
	{
		return autoPayServiceCode;
	}

	public void setAutoPayServiceCode(String autoPayServiceCode)
	{
		this.autoPayServiceCode = autoPayServiceCode;
	}

	public String getExternalDepositId()
	{
		return externalDepositId;
	}

	public void setExternalDepositId(String externalDepositId)
	{
		this.externalDepositId = externalDepositId;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public String getWithdrawExternalId()
	{
		return withdrawExternalId;
	}

	public void setWithdrawExternalId(String withdrawExternalId)
	{
		this.withdrawExternalId = withdrawExternalId;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getRouteInfo()
	{
		return routeInfo;
	}

	public void setRouteInfo(String routeInfo)
	{
		this.routeInfo = routeInfo;
	}

	/**
	 * преобразуем к гейте, нового документа не создаем
	 * @param document документ.
	 * @return преобразованный документ.
	 */
	public GateDocument removeRouteInfo(GateDocument document) throws GateException
	{
		office = document.getOffice();
		document.setOffice(new OfficeWithoutRouteInfo(office));
		externalOwnerId = document.getExternalOwnerId();
		document.setExternalOwnerId(IDHelper.restoreOriginalId(document.getExternalOwnerId()));
		if ((document instanceof SynchronizableDocument) && ((SynchronizableDocument) document).getExternalId() != null)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			externalId = synchronizableDocument.getExternalId();
			synchronizableDocument.setExternalId(IDHelper.restoreOriginalId(synchronizableDocument.getExternalId()));
		}
		if (document.getType() == DepositClosingClaim.class)
		{
			removeRouteInfo((DepositClosingClaim) document);
		}
		else if(AutoPayment.class.isAssignableFrom(document.getType()))
		{
			removeRouteInfo((AutoPayment) document);
		}
		else if (AbstractPaymentSystemPayment.class.isAssignableFrom(document.getType()))
		{
			removeRouteInfo((AbstractPaymentSystemPayment) document);
		}
		else if(WithdrawDocument.class.equals(document.getType()))
		{
			removeRouteInfo((WithdrawDocument) document);
		}

		return document;
	}

	/**
	 * удаляем информацию о маршрутизации для DepositOpeningClaim
	 */
	private void removeRouteInfo(DepositClosingClaim claim)
	{
		externalDepositId = claim.getExternalDepositId();
		claim.setExternalDepositId(IDHelper.restoreOriginalId(externalDepositId));
	}

	private void removeRouteInfo(AutoPayment autoPayment)
	{
		autoPayServiceCode = autoPayment.getCodeService();
		String withoutRoutInfo = IDHelper.restoreOriginalId(autoPayServiceCode);
		autoPayment.setCodeService(IDHelper.restoreOriginalIdWithAdditionalInfo(withoutRoutInfo));
	}

	private void removeRouteInfo(WithdrawDocument withdrawDocument) throws GateException
	{
		withdrawExternalId = withdrawDocument.getWithdrawExternalId();
		withdrawDocument.setWithdrawExternalId(IDHelper.restoreOriginalId(withdrawExternalId));
	}

	/**
	 * удаляем информацию о маршрутизации для PaymentSystemPayment
	 */
	private void removeRouteInfo(AbstractPaymentSystemPayment systemPayment)
	{
		receiverPointCode = systemPayment.getReceiverPointCode();
		systemPayment.setReceiverPointCode(IDHelper.restoreOriginalIdWithAdditionalInfo(IDHelper.restoreOriginalId(receiverPointCode)));
	}

	/**
	 * Восстанавливает объект из гейта, к предыдущему состоянию
	 * @param document
	 * @return
	 */
	public GateDocument storeRouteInfo(GateDocument document) throws GateException
	{
		document.setOffice(office);
		document.setExternalOwnerId(externalOwnerId);
		if ((document instanceof SynchronizableDocument) && ((SynchronizableDocument) document).getExternalId() != null)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId((externalId == null) ?// Предыдущее значение было налом.. пришло не нал.. значит ктото его установил используетм новое и добавляем информацию омаршрутизации
					IDHelper.storeRouteInfo(synchronizableDocument.getExternalId(), uid) :
					externalId
			);
		}
		if (document.getType() == DepositClosingClaim.class)
		{
			storeRouteInfo((DepositClosingClaim) document);
		}
		else if(AutoPayment.class.isAssignableFrom(document.getType()))
		{
			storeRouteInfo((AutoPayment) document);
		}
		else if (AbstractPaymentSystemPayment.class.isAssignableFrom(document.getType()))
		{
			storeRouteInfo((AbstractPaymentSystemPayment) document);
		}
		else if(WithdrawDocument.class.equals(document.getType()))
		{
			storeRouteInfo((WithdrawDocument) document);
		}

		return document;
	}

	/**
	 * сохряняем информацию о маршрутизации для DepositOpeningClaim
	 */
	private void storeRouteInfo(DepositClosingClaim claim)
	{
		claim.setExternalDepositId(externalDepositId);
	}

	private void storeRouteInfo(AutoPayment autoPayment)
	{
		autoPayment.setCodeService(autoPayServiceCode);
	}

	private void storeRouteInfo(WithdrawDocument withdrawDocument) throws GateException
	{
		withdrawDocument.setWithdrawExternalId(withdrawExternalId);
	}

	/**
	 * сохряняем информацию о маршрутизации для PaymentSystemPayment
	 */
	private void storeRouteInfo(AbstractPaymentSystemPayment claim)
	{
		claim.setReceiverPointCode(receiverPointCode);
	}
}
