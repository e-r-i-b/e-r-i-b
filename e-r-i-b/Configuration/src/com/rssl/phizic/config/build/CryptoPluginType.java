package com.rssl.phizic.config.build;

/**
 * @author Omeliyanchuk
 * @ created 07.04.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип подлюченного крипто плагина.
 */
public enum CryptoPluginType
{
	sbrf,//плагин без использования внешних бибилиотек.
	rs//плагин с использованием plugin_jni.jar
}
