package com.rssl.auth.csa.back.servises.nodes;

/**
 * @author akrenev
 * @ created 18.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Признак создания привязки профиля к блоку
 */

public enum CreateProfileNodeMode
{
	CREATION_DENIED,                  // Создание запрещено
	CREATION_ALLOWED_FOR_MAIN_NODE,   // Создание для основного блока
	CREATION_ALLOWED_FOR_ALL_NODES    // Создание для любого блока
}
