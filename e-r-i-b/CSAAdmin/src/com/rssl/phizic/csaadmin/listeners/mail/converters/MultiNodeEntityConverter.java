package com.rssl.phizic.csaadmin.listeners.mail.converters;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 *  Конвертер сущности из одного представления в другое.
 */
public interface MultiNodeEntityConverter<GateType,NodeType>
{

	/**
	 * Преобразовать объект к гейтовому предоставлению
	 * @param entity - объект
	 * @return гейтовое представление объекта
	 */
	public GateType convertToGate(NodeType entity);

}
