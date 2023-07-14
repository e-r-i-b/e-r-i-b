package com.rssl.phizic.operations.payment;

import com.rssl.phizgate.basket.BasketProxyListenerEnableClearCacheEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.businessProperties.LoanReceptionTimeHelper;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * �������������� ����������� �� ��������� ��������
 * @author gladishev
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentRestrictionsOperation extends EditPropertiesOperation<Restriction>
{
	public static final String BASKET_ENABLE_PROPERTY_KEY = "global.basket.proxy.listener.enable.state";
	public static final String BASKET_QUEUE_MODE_PROPERTY_KEY = "com.rssl.phizic.service.basket.queue.working.mode";

	private static final BusinessPropertyService service = new BusinessPropertyService();
	private static final LimitService limitService = new LimitService();
	private Map<String, String> loanProperties; //��������� ��� ��������

	private Property basketListenerProperty;//��������� ��������� �������� ��������� �� �� "AutoPay" � ������ ������� ��������.(����/���)
	private Property basketListenerModeProperty;//��������� ������ ������ ��������� ���������(����� �������� ���/ ����� ������� autopay)

	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		loanProperties = new HashMap<String, String>();
	}

	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		List<Property> list = DbPropertyService.findPropertiesLike(LoanReceptionTimeHelper.PREFIX_KEY, propertyCategory.getValue(), getDbInstance(propertyCategory));
		loanProperties = new HashMap<String, String>(list.size());
		for (Property prop : list)
			loanProperties.put(prop.getKey(), prop.getValue());
		//�������� �� �������� ����� ���������� ��������� ���������/���������� ������ ��������� ������� ��������.
		basketListenerProperty = DbPropertyService.findProperty(BASKET_ENABLE_PROPERTY_KEY, PropertyCategory.OfflineDoc.getValue(), getDbInstance(PropertyCategory.OfflineDoc));
		basketListenerModeProperty = DbPropertyService.findProperty(BASKET_QUEUE_MODE_PROPERTY_KEY, PropertyCategory.OfflineDoc.getValue(), getDbInstance(PropertyCategory.OfflineDoc));
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		super.save();
		DbPropertyService.updatePropertiesWithLikeKeys(LoanReceptionTimeHelper.PREFIX_KEY, loanProperties, getPropertyCategory(), getDbInstance(getPropertyCategory()));
		updateBasketListenerEnableProperty();
		updateBasketListenerModeProperty();
	}

	/**
	 * @return ��������� ��� ��������
	 */
	public Map<String, String> getLoanProperties()
	{
		return loanProperties;
	}

	/**
	 * @param loanProperties - ��������� ��� ��������
	 */
	public void setLoanProperties(Map<String, String> loanProperties)
	{
		this.loanProperties = loanProperties;
	}

	/**
	 * ��������� ��������� ���������/���������� ��������� �������� ������� ��������.
	 * @return ���������
	 */
	public Property getBasketListenerProperty()
	{
		return basketListenerProperty;
	}

	/**
	 * ��������� ��������� ���������/���������� ��������� �������� ������� ��������.
	 * @return ���������
	 */
	public Property getBasketListenerModeProperty()
	{
		return basketListenerModeProperty;
	}

	/**
	 * ��������� �������� ��������� ���������� ��������� ������� ��������.
	 * @param enableValue - "true"/"false"
	 */
	public void setBasketListenerProperty(String enableValue)
	{
		this.basketListenerProperty = new Property(BASKET_ENABLE_PROPERTY_KEY, enableValue, PropertyCategory.OfflineDoc.getValue());
	}

	/**
	 * ��������� �������� ��������� ������ ������ ��������� ���������(����� �������� ���/ ����� ������� autopay).
	 * @param mode - "esb"/"autopay"
	 */
	public void setBasketListenerModeProperty(String mode)
	{
		this.basketListenerModeProperty = new Property(BASKET_QUEUE_MODE_PROPERTY_KEY, mode, PropertyCategory.OfflineDoc.getValue());
	}

	private void updateBasketListenerEnableProperty() throws BusinessException
	{
		try
		{
			Property actualProperty = DbPropertyService.findProperty(BASKET_ENABLE_PROPERTY_KEY, PropertyCategory.OfflineDoc.getValue(), getDbInstance(PropertyCategory.OfflineDoc));
			//���� �������� ���������� - ���������� ���, ����� � ����.
			if(actualProperty == null)
			{
				if(basketListenerProperty.getValue().equals("false"))
					return;
			}
			else
			{
				if(actualProperty.getValue().equals(basketListenerProperty.getValue()))
					return;
			}
			DbPropertyService.updateProperty(BASKET_ENABLE_PROPERTY_KEY, basketListenerProperty.getValue(), PropertyCategory.OfflineDoc);
			EventSender.getInstance().sendEvent(new BasketProxyListenerEnableClearCacheEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void updateBasketListenerModeProperty() throws BusinessException
	{
		try
		{
			Property actualProperty = DbPropertyService.findProperty(BASKET_QUEUE_MODE_PROPERTY_KEY, PropertyCategory.OfflineDoc.getValue(), getDbInstance(PropertyCategory.OfflineDoc));
			//���� �������� ���������� - ���������� ���, ����� � ����.
			if(actualProperty == null)
			{
				// ���� ����� ���������� ��������
				if(basketListenerModeProperty.getValue().equals("autopay"))
					return;
			}
			else
			{
				if(actualProperty.getValue().equals(basketListenerModeProperty.getValue()))
					return;
			}
			DbPropertyService.updateProperty(BASKET_QUEUE_MODE_PROPERTY_KEY, basketListenerModeProperty.getValue(), PropertyCategory.OfflineDoc);
			EventSender.getInstance().sendEvent(new BasketProxyListenerEnableClearCacheEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

    public Limit getOverallLimit() throws BusinessException
    {
        List<Limit> activeLimits = limitService.findActiveOverallLimits();

        if (CollectionUtils.isEmpty(activeLimits))
        {
            return null;
        }

        if (activeLimits.size() > 1)
        {
            throw new BusinessException("���������� �������� ������� �� ������ ��������� 1.");
        }

        return activeLimits.get(0);
	}
}
