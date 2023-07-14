package com.rssl.phizic.gate.multinodes;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс для обозначения объектов которые в
 * многоблочном режиме получаются на основе данных из нескольких блков
 */
public interface JoinMultiNodeEntity<T> extends Comparable<T>, Serializable
{
	/**
	 * Соединить объекты
	 * @param anotherObject объект с которым необходимо соеденить текущий
	 */
	public void join(T anotherObject);
}
