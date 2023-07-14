<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<bean:define id="paymentInfo" name="PrintMemForm" property="payment"/>
<bean:define id="personInfo" name="PrintMemForm" property="person"/>

<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body>
   <tiles:insert definition="googleTagManager"/>
   <table cellspacing="0" cellpadding="0" border="0"
    style="margin-left:10mm;margin-top:10mm;table-layout:fixed;width:180mm;border-collapse:collapse;">
  <col  style="width:20mm"/>
  <col  style="width:15mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:15mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:3mm"/>
  <col  style="width:7mm"/>

	<tr style="height:5mm">
      <td colspan="2" align="center" class=ul valign="bottom">

      </td>
      <td colspan="2"></td>
      <td colspan="3" align="center" class=ul valign="bottom">

      </td>
      <td colspan="11"></td>
      <td colspan="3" align="center"  valign="middle" class=bd></td>
    </tr>
    <tr style="height:6mm">
      <td colspan="2"  align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Поступ. в банк плат.</td>
      <td colspan="2"></td>
      <td colspan="3" align="center" valign="top" style="font-size:8pt;border-top:1 solid black;">Списано со сч. плат.</td>
      <td colspan="14"></td>
    </tr>
    <tr style="height:7mm">
      <td colspan="5" >ПЛАТЕЖНОЕ ПОРУЧЕНИЕ</td>
      <td colspan="2" ><span  >N <bean:write name="paymentInfo" property="id"/></span>

      </td>
      <td colspan="5" align="center" class=ul valign="bottom">
		<bean:write name="PrintMemForm" property="day"/>.<bean:write name="PrintMemForm" property="month"/>.<bean:write name="PrintMemForm" property="year"/>
      </td>
      <td></td>
      <td colspan="4" align="center" class=ul valign="bottom">
          Электронно
      </td>
      <td colspan="3"></td>
      <td class=bd align="center"  valign="middle">

      </td>
    </tr>
    <tr style="height:7mm">
      <td colspan="7"></td>
      <td colspan="5" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Дата</td>
      <td></td>
      <td colspan="4" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Вид платежа</td>
      <td colspan="4"></td>
    </tr>
    <tr style="height:15mm">
       <td valign="top" style="border-right:1px solid black">Сумма прописью</td>
       <td colspan="20" valign="top">&nbsp;
			<bean:write name="PrintMemForm" property="summa"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="4" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
			<bean:write name="personInfo" property="inn"/>
       </td>
       <td colspan="5" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;

       </td>
       <td colspan="2" style="border-top:1px solid black;border-right:1px solid black">&nbsp;Сумма</td>
       <td colspan="10" style="border-top:1px solid black;">&nbsp;
			<bean:write name="paymentInfo" property="amount.decimal"/>
       </td>
    </tr>
    <tr style="height:10mm">
       <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
	   		<bean:write name="paymentInfo" property="payerName"/>
       </td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:10mm">
       <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
       <td colspan="10" style="border-top:1px solid black;" valign="top">&nbsp;
		   <bean:write name="paymentInfo" property="payerAccount"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Плательщик</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
			<bean:write name="PrintMemForm" property="payerBankName"/>
       </td>
       <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
       <td colspan="10" >&nbsp;
	   		<bean:write name="PrintMemForm" property="payerBIC"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
       <td colspan="10" >&nbsp;
			<bean:write name="PrintMemForm" property="payerBankAccount"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк плательщика</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10" class=ul></td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
		   <bean:write name="PrintMemForm" property="receiverBankName"/>
       </td>
       <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
       <td colspan="10" style="border-top:1px solid black;">&nbsp;
		    <bean:write name="PrintMemForm" property="receiverBIC"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
       <td colspan="10" >&nbsp;
			<bean:write name="PrintMemForm" property="receiverBankAccount"/>		              
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк получателя</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:5mm">
       <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
		  <bean:write name="PrintMemForm" property="receiverINN"/>
       </td>
       <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
		  <bean:write name="PrintMemForm" property="receiverKPP"/>
       </td>
       <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
       <td colspan="10">&nbsp;
		   <bean:write name="paymentInfo" property="receiverAccount"/>
       </td>
    </tr>
    <tr style="height:10mm">
      <td colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
		   <bean:write name="paymentInfo" property="receiverName"/>
      </td>
      <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
      <td colspan="10" class=ul></td>
    </tr>
    <tr style="height:5mm">
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Вид оп.</td>
        <td colspan="3" style="border-top:1px solid black; border-right:1px solid black">&nbsp;     </td>
        <td colspan="2" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">Срок плат.</td>
        <td colspan="5" style="border-top:1px solid black;">&nbsp;

        </td>
    </tr>
    <tr style="height:5mm">
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Наз. пл.</td>
        <td colspan="3" style="border-right:1px solid black">&nbsp;</td>
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Очер.плат.</td>
        <td colspan="5">&nbsp; </td>
    </tr>
    <tr style="height:5mm">
        <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
        <td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;</td>
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td>
        <td colspan="5" style="border-bottom:1px solid black">&nbsp;</td>
    </tr>
    <tr style="height:5mm" >
        <td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td style="border-bottom:1px solid black;border-right:1px solid black" align="center">

        </td>
        <td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td style="border-bottom:1px solid black;border-right:1px solid black" colspan="5" align="center">

        </td>
        <td style="border-bottom:1px solid black;border-right:1px solid black" colspan="4" align="center">

        </td>
        <td style="border-bottom:1px solid black;" colspan="2" align="center">

        </td>
    </tr>
    <tr style="height:25mm">
        <td colspan="21" valign="top">
          <bean:write name="paymentInfo" property="ground"/>
            <br>
            <br>
            <br>

        </td>
   </tr>
   <tr style="height:5mm">
         <td colspan="21" style="border-bottom:1px solid black;">Назначение платежа</td>
   </tr>
   <tr style="height:15mm">
        <td colspan="5">&nbsp;</td>
        <td colspan="7" align="center" valign="top" style="border-bottom:1px solid black">Подписи</td>
        <td colspan="9" align="center" valign="top">Отметки банка</td>
   </tr>
   <tr style="height:15mm">
        <td colspan="2">&nbsp;</td>
        <td align="center" valign="top">М.П.</td>
        <td colspan="2">&nbsp;</td>
        <td colspan="7" style="border-bottom:1px solid black">&nbsp;</td>
        <td colspan="9" align="center"></td>
   </tr>
   </table>
    <div style="position:absolute;left:152mm;top:172mm;display:none">
       <img src="/RSPortal/images/stamp.gif" width="125px" height="100px" border="0"/>
    </div>
</body>
</html>