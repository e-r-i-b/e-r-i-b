package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.util.Map;

/**
 * @author krenev
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 * Calculator выбирает в зависимости от офиса, в который посылаетс€ документ, необходимость его посылки
 * запроса на подсчет комисии на шину (esb-erib-calculator) или на обычный узел(default-calculator)
 */
public class ESBERIBCommissionCalculatorSelector extends AbstractCommissionCalculatorSelector implements CommissionCalculator
{
	private static final String ESB_ERIB_CALCULATOR_PARAMETER_NAME = "esb-erib-calculator";
	private static final String DEFAULT_CALCULATOR_PARAMETER_NAME = "default-calculator";
	private CommissionCalculator esbCalculator;
	private CommissionCalculator defaultCalculator;

	public ESBERIBCommissionCalculatorSelector(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		String sbPaymentSenderClassName = (String) params.get(ESB_ERIB_CALCULATOR_PARAMETER_NAME);
		if (sbPaymentSenderClassName == null)
		{
			throw new IllegalStateException("Ќе задан " + ESB_ERIB_CALCULATOR_PARAMETER_NAME);
		}
		esbCalculator = loadCalculator(sbPaymentSenderClassName);

		String simpleSenderClassName = (String) params.get(DEFAULT_CALCULATOR_PARAMETER_NAME);
		if (simpleSenderClassName == null)
		{
			throw new IllegalStateException("Ќе задан " + DEFAULT_CALCULATOR_PARAMETER_NAME);
		}
		defaultCalculator = loadCalculator(simpleSenderClassName);
	}

	public CommissionCalculator getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		if (ESBHelper.isESBSupported(document))
		{
			return esbCalculator;
		}
		return defaultCalculator;
	}
}
