package com.rssl.phizicgate.ips;

import com.rssl.phizgate.mobilebank.GateCardHelper;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSReceiverService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.rssl.phizic.utils.MoneyHelper.formatMoney;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestIPSReceiverService extends AbstractService implements IPSReceiverService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	///////////////////////////////////////////////////////////////////////////

	TestIPSReceiverService(GateFactory factory)
	{
		super(factory);
	}

	public List<IPSCardOperationClaim> receiveCardOperationClaimResult(GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result) throws GateException
	{
		log.trace("Пришла пачка с карточными операциями. Количество заявок: " + result.size());

		int count = 0;
		for (IPSCardOperationClaim claim : result.getKeys())
		{
			IKFLException exception = result.getException(claim);
			if (exception != null) {
				log.warn("По заявке '" + formatCardOperationClaim(claim) + "' получено исключение " + exception.getMessage(), exception);
				continue;
			}

			List<IPSCardOperation> operations = result.getResult(claim);
			log.trace("Заявка " + formatCardOperationClaim(claim) + ":");
			log.trace("\tDOCNUM\tCARD\t\t\tDATE\t\tDEBIT\t\tCREDIT\t\tACC_DEBIT\t\tACC_CREDIT\t\tDESC\t\t\t\tSHOP\t\tCASH");
			for (IPSCardOperation operation : operations)
				log.trace("\t" + formatCardOperation(operation));
			log.trace(operations.size() + " операций по заявке");

			count += operations.size();
		}
		log.trace("Всего получено " + count + " операций");
		if (count == 0)
			log.trace("Это были последние данные по указанным заявкам");

		// Данные приняты без проблем
		return Collections.emptyList();
	}

	public void setTimeoutStatusClaims(List<IPSCardOperationClaim> claims) throws GateException
	{
		for (IPSCardOperationClaim claim : claims)
		{
			log.warn("По заявке '" + formatCardOperationClaim(claim) + "' получен таймаут запроса.");
		}
	}

	private String formatCardOperationClaim(IPSCardOperationClaim claim)
	{
		return GateCardHelper.hideCardNumber(claim.getCard()) + " начиная с " + DateHelper.toISO8601DateFormat(claim.getStartDate());
	}

	private String formatCardOperation(IPSCardOperation operation)
	{
		List<String> chunks = new LinkedList<String>();
		chunks.add(operation.getDocumentNumber());
		chunks.add(GateCardHelper.hideCardNumber(operation.getOperationCard()));
		chunks.add(DateHelper.toISO8601DateFormat(operation.getDate()));
		chunks.add(formatMoney(operation.getDebitSum()));
		chunks.add(formatMoney(operation.getCreditSum()));
		chunks.add(formatMoney(operation.getAccountDebitSum()));
		chunks.add(formatMoney(operation.getAccountCreditSum()));
		chunks.add(operation.getDescription());
		chunks.add(operation.getShopInfo());
		chunks.add(operation.isCash() ? "cash" : "non-cash");
		return StringUtils.join(chunks, "\t");
	}
}
