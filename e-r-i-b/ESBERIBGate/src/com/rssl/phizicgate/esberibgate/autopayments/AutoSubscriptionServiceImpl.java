package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;

import java.util.Calendar;
import java.util.List;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionServiceImpl extends AbstractService implements AutoSubscriptionService
{
	private AutoSubscriptionsRequestHelper autoSubscriptionsRequestHelper = new AutoSubscriptionsRequestHelper(getFactory());
	private AutoSubscriptionsResponseSerializer autoSubscriptionsResponseSerializer = new AutoSubscriptionsResponseSerializer();

	protected AutoSubscriptionServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public List<AutoSubscription> getAutoSubscriptions(List<Card> cards, AutoPayStatusType ... types) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = autoSubscriptionsRequestHelper.createAutoSubscriptionListRq(cards, types);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		return autoSubscriptionsResponseSerializer.getAutoSubscriptions(ifxRq, ifxRs, cards);
	}

	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscriptionInfo(AutoSubscription ... autoSubscriptions) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = autoSubscriptionsRequestHelper.createAutoSubscriptionDetailInfoRq(autoSubscriptions);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		return autoSubscriptionsResponseSerializer.getAutoSubscriptionInfo(ifxRq, ifxRs, autoSubscriptions);
	}

	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscription(String... externald) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = autoSubscriptionsRequestHelper.createAutoSubscriptionDetailInfoRq(externald);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		return autoSubscriptionsResponseSerializer.getAutoSubscriptionInfo(ifxRq, ifxRs, externald);
	}

	public List<ScheduleItem> getSheduleReport(AutoSubscription autoSubscription, Calendar begin, Calendar end) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = autoSubscriptionsRequestHelper.createAutoPaymentListRq(autoSubscription, begin, end);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		return autoSubscriptionsResponseSerializer.getAutoPaymentList(ifxRq, ifxRs, autoSubscription);
	}

	public GroupResult<Long, AutoSubscriptionDetailInfo> getSubscriptionPayments(AutoSubscription autoSubscription, Long ... ids) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = autoSubscriptionsRequestHelper.createGetAutoPaymentDetailInfoRq(autoSubscription, ids);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		return autoSubscriptionsResponseSerializer.getAutoPaymentDetailInfo(ifxRq, ifxRs);
	}
}
