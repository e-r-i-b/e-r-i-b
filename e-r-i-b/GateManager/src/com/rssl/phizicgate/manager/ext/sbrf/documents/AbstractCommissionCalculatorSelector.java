package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Constructor;

/**
 * @author krenev
 * @ created 06.12.2010
 * @ $Author$
 * @ $Revision$
 * јбстрактный селектор-кaлькул€тор.
 * всегда дергает делегатов. правила получени€ делегата по документу определ€ют наследники.
 */
public abstract class AbstractCommissionCalculatorSelector extends AbstractService implements CommissionCalculator
{
	public AbstractCommissionCalculatorSelector(GateFactory factory) {super(factory);}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		getDelegate(transfer).calcCommission(transfer);
	}

	protected abstract CommissionCalculator getDelegate(GateDocument document) throws GateException, GateLogicException;

	protected CommissionCalculator loadCalculator(String className)
	{
		try
		{
			Class<CommissionCalculator> senderClass = ClassHelper.loadClass(className);
			Constructor<CommissionCalculator> constructor = senderClass.getConstructor(GateFactory.class);
			return constructor.newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException("ќшибка при инстанциации CommissionCalculator:"+className, e);
		}
	}
}
