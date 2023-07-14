<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 23.04.2007
  Time: 9:15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html locale="true">
 <SCRIPT LANGUAGE="JavaScript" TYPE="text/JavaScript">

var workLayer;

function updateLayerPosition()
{
  workLayer.style.visibility = 'visible';
  workLayer.style.left = (document.body.clientWidth - workLayer.scrollWidth) / 2;
  workLayer.style.top = 20;
}

function onLoadFunc()
{
  divEnglishSettings.style.visibility = 'hidden';
  divRussianSettings.style.visibility = 'hidden';

  if ( navigator.browserLanguage == 'ru' ) workLayer = divRussianSettings;
  else 					   workLayer = divEnglishSettings;

  updateLayerPosition();
}

function CheckSettings(lang)
{
   if (document.getElementById("CheckSettings"+lang).checked)
      document.getElementById("ButtonsTD"+lang).innerHTML="<nobr><a href='javascript:window.close()'>Отказ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:loaded();\">Установить</a></nobr>";
   else document.getElementById("ButtonsTD"+lang).innerHTML="<nobr><a href='javascript:window.close()'>Отказ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font CLASS='not_a'>Установка</font></nobr>";
}
function loaded()
{
	window.opener.SettingsChanged();
	window.close();
}
</SCRIPT>
<BODY onLoad="onLoadFunc()" onResize="updateLayerPosition()">
<div ID="divEnglishSettings" STYLE="visibility:visible; position: absolute;">
<table class="win" width="500" align="center">
 <tr>
  <td class=head>Установка программного обеспечения.</td>
 </tr>
 <tr>
  <td>
     <br>
     <p CLASS="attention"><b>Вам необходимо изменить настройки Internet Explorer!</b></p>
     <p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Для этого необходимо:</b></p>
     <ol>
     <li>Выбрать в меню Internet Explorer пункт <SPAN CLASS="menuTopic">Tools</SPAN> - <SPAN CLASS="menuTopic">Internet Options</SPAN>, перейти на вкладку <SPAN CLASS="menuTopic">Security</SPAN>.</li>
     <li>Выделить зону <SPAN CLASS="menuTopic">Trusted Sites</SPAN>, нажать кнопку <SPAN CLASS="menuTopic">Sites</SPAN> и добавить сайт банка в список (Например, <font style="color:#00008B;">http://www.mybank.com)</font></li>
     <li>На закладке <SPAN CLASS="menuTopic">Trusted Sites</SPAN>, нажать <SPAN CLASS="menuTopic">Custom Level</SPAN>, и установить в настройках <SPAN CLASS="menuTopic">Activex controls and plugins</SPAN> значения: <SPAN CLASS="menuTopic">Enable</SPAN></li>
     </ol>
  </td>
 </tr>
 <tr>
     <td align="left">
         <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="CheckBox" id="CheckSettingsEn" onClick="CheckSettings('En')">&nbsp;Настройки изменены.<br>&nbsp;
     </td>
 </tr>
 <tr>
   <td align=center id="ButtonsTDEn">
      <nobr><a href="javascript:window.close()">Отказ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font CLASS="not_a">Установка</font></nobr>
   </td>
 </tr>
</table>
</div>
<div ID="divRussianSettings" STYLE="visibility:visible; position: absolute;">
<table class="win" width="500" align="center">
 <tr>
  <td class=head>Установка программного обеспечения.</td>
 </tr>
 <tr>
  <td>
     <br>
     <p CLASS="attention"><b>Вам необходимо изменить настройки Internet Explorer!</b></p>
     <p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Для этого необходимо:</b></p>
     <ol>
     <li>Выбрать в меню Internet Explorer пункт <SPAN CLASS="menuTopic">Сервис - Свойства обозревателя...</SPAN>, перейти на вкладку <SPAN CLASS="menuTopic">Безопасность</SPAN>.</li>
     <li>Выделить зону <SPAN CLASS="menuTopic">Надежные узлы</SPAN>, нажать кнопку <SPAN CLASS="menuTopic">Узлы</SPAN> и добавить сайт банка в список (Например, <font style="color:#00008B;">http://www.mybank.com)</font></li>
     <li>На закладке <SPAN CLASS="menuTopic">Надежные узлы</SPAN>, нажать <SPAN CLASS="menuTopic">Другой...</SPAN>, и установить в настройках <SPAN CLASS="menuTopic">Элементы ActiveX и модули подключения</SPAN> значения: <SPAN CLASS="menuTopic">Разрешить</SPAN></li>
     </ol>
  </td>
 </tr>
 <tr>
     <td align="left">
         <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="CheckBox" id="CheckSettingsRu" onClick="CheckSettings('Ru')">&nbsp;Настройки изменены.<br>&nbsp;
     </td>
 </tr>
 <tr>
   <td align=center id="ButtonsTDRu">
      <nobr><a href="javascript:window.close()">Отказ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font CLASS="not_a">Установка</font></nobr>
   </td>
 </tr>
</table>
</div>
  </body>
</html:html>
