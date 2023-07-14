/**
 * Данный модуль используется для того чтобы не обвалились плагины и остальной код
 * использующий старую версию jquery(1.4.2)
 */
define(['scripts/require/lib/jquery'], function ()
{
   return jQuery.noConflict(true);
});