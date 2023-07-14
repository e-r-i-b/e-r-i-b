package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract.BalancesTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract.OperationTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос выписки по продукту
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "operation", "balances"})
@XmlRootElement(name = "message")
public class ProductAbstractResponse extends Response
{
	private List<OperationTag> operation;
	private BalancesTag balances;

	@XmlElement
	public List<OperationTag> getOperation()
	{
		return operation;
	}

	public void setOperation(List<OperationTag> operation)
	{
		this.operation = operation;
	}

	@XmlElement(name = "balances", required = false)
	public BalancesTag getBalances()
	{
		return balances;
	}

	public void setBalances(BalancesTag balances)
	{
		this.balances = balances;
	}
}
