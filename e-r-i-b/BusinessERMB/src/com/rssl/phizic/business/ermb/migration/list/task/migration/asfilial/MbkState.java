package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Состояние карт-телефонов клиента в МБК в контексте миграции МБК->ЕРМБ на запросе АС ФСБ
 * @author Puzikov
 * @ created 22.04.15
 * @ $Author$
 * @ $Revision$
 */

@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
class MbkState
{
	Set<String> phones = new HashSet<String>();         //все телефоны клиента
	Set<String> conflictPhones = new HashSet<String>(); //конфликтные телефоны
	boolean infoCardConflict = false;                   //конфликт по доп. карте
}
