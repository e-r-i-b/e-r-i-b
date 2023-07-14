package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * ѕоле с графическим представлением выбора значений
 * @author niculichev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface GraphicSetField extends ListField
{
	/**
	 * @return им€ графического шаблона, по которому строитс€ представление
	 */
	String getGraphicTemplateName();
}
