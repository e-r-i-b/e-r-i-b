package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;

import java.util.Map;

/**
 * Интерфейс фильтров активность хендлеров машины состояний
 *
 * @author khudyakov
 * @ created 13.01.2009
 * @ $Author$
 * @ $Revision$
 */
public interface HandlerFilter<SO extends StateObject> extends StateMachineFilter<SO>
{
}
