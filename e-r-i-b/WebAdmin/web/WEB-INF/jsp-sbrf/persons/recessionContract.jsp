<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 22.08.2006
  Time: 12:56:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:insert definition="recessionContract">
<tiles:put name="pageTitle" type="string" value="Заявление на прекращение предоставления услуг"/>
<tiles:put name="data" type="string">
<c:set var="form" value="${PrintRecessionForm}"/>
<c:set var="isProl" value="${form.isProlongation}"/>
<bean:define id="person" name="PrintRecessionForm" property="activePerson"/>

<c:choose>
   <c:when test="${person.agreementNumber == null}">
      <c:set var="agrNum" value=""/>
   </c:when>
   <c:when test="${person.agreementNumber != null}">
      <c:set var="agrNum" value="${person.agreementNumber}"/>
   </c:when>
</c:choose>
<c:set var="prolDate" value="${person.prolongationRejectionDate.time}"/>
<c:set var="openDate" value="${person.agreementDate.time}"/>
<c:set var="curDate" value="${form.currentDate}"/>
<c:set var="fullName" value="${person.fullName}"/>
<c:set var="cancellationCause" value="${person.contractCancellationCouse}"/>

<style type="text/css">
  .label {font-family:Times New Roman;font-size:7pt;}
  .subscript {font-family:Times New Roman;font-size:7pt;vertical-align:top;text-align:center}
   table{font-family:Times New Roman;color:#3d3d3d;font-size:10pt;}
</style>

<c:if test="${isProl}">
     <table style="width:169mm;margin-left:15mm;margin-top:15mm;">
      <tr>
         <td align="right">
            <i><u><b>в Сбербанк России</b></u></i>
         </td>
      </tr>
      <tr>
         <td align="center" valign="bottom" style="height:25mm">
            <b>
            ЗАЯВЛЕНИЕ<br/>
            на прекращение предоставления услуг с использованием системы “Электронная<br/>
            Сберкасса” и расторжение договора<br/>
            </b>
         </td>
      </tr>
      <tr>
         <td align="center">

         <table width="90%">
      <tr >
         <td align="right" valign="center" style="height:15mm;">
            <u>
               "<bean:write name="curDate" format="d"/>"
               <script type="text/javascript">document.write(monthToStringOnly('<bean:write name="curDate" format="dd.MM.yyyy"/>'));</script>
            <bean:write name="curDate" format="yyyy"/>
            </u>
         </td>
      </tr>
      <tr>
         <td align="left">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            Прошу с <nobr>"<bean:write name="prolDate" format="d"/>"
            <script type="text/javascript">document.write(monthToStringOnly('<bean:write name="prolDate" format="dd.MM.yyyy"/>'));</script>
            <bean:write name="prolDate" format="yyyy"/></nobr>
             года прекратить предоставление услуг с использованием
            системы “Электронная Сберкасса” и расторгнуть договор “О предоставлении услуг с
            использованием системы “Электронная Сберкасса” <nobr>№ <bean:write name="agrNum"/></nobr>
            от <nobr>"<bean:write name="openDate" format="d"/>"
            <script type="text/javascript">document.write(monthToStringOnly('<bean:write name="openDate" format="dd.MM.yyyy"/>'));</script>
            <bean:write name="openDate" format="yyyy"/> года.</nobr>
         </td>
      </tr>
      <tr>
         <td align="center">
            <table width="100%">
               <tr>
                  <td style="width:15mm">
                     &nbsp;
                  </td>
                  <td >
                     Причина прекращения предоставления услуги:
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr>
         <td >
            <table>
               <tr>
                  <td style="width:5mm">

                  </td>
                  <td style="width:15mm" align="left">
	                  <table><tr><td height="9px" width="11px" style="border:1px solid black;font-size:10px;text-align:center;font-family:Verdana;">
		                  <c:choose><c:when test="${cancellationCause eq 'C'}">X</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td></tr></table>
                  </td>
                  <td  align="left">
                     - инициатива Клиента.
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr>
         <td >
            <table>
               <tr>
                  <td style="width:5mm">

                  </td>
                  <td style="width:15mm" align="left">
						<table><tr><td height="9px" width="11px" style="border:1px solid black;font-size:10px;text-align:center;font-family:Verdana;">
		                  <c:choose><c:when test="${cancellationCause eq 'B'}">X</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td></tr></table>                  </td>
                  <td  align="left">
                     - инициатива Банка.
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr>
         <td >
            <table>
               <tr>
                  <td style="width:5mm">

                  </td>
                  <td style="width:15mm" align="left">
					<table><tr><td height="9px" width="11px" style="border:1px solid black;font-size:10px;text-align:center;font-family:Verdana;">
		                  <c:choose><c:when test="${cancellationCause eq 'W'}">X</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td></tr></table>                  </td>
                  <td  align="left">
                     - инициатива Банка без взимания платы.
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr style="height:25mm;">
         <td align="right" valign="bottom">
            Клиент:
         </td>
      </tr>
      <tr>
         <td align="right" align="center">
            <table width="100%">
               <tr>
                  <td>
                     &nbsp;
                  </td>
                  <td align="right" style="border-bottom:1px solid black;width:110mm;">
                     <bean:write name="fullName"/>
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr style="height:10mm;">
         <td>
         &nbsp;
         </td>
      <tr >
         <td align="right" valign="top">
            <table width="100%">
               <tr valign="top">
                  <td>
                     &nbsp
                  </td>
                  <td align="right" style="border-top:1px solid black;width:45mm" class="subscript">
                     (подпись)
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr style="height:25mm" align="bottom">
         <td align="center">
            <table style="width:155mm">
               <tr>
                  <td style="border-bottom:1px solid black">
                     &nbsp;
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr>
         <td>
            <table width="100%">
               <tr>
                  <td style="width:5mm">
                     &nbsp;
                  </td>
                  <td>
                     Заявление принято к исполнению
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr>
         <td>
            <table width="100%">
               <col  style="width:5mm"/>
               <col  style="width:67mm"/>
               <col  style="width:3mm"/>
               <col  style="width:80mm"/>
               <col/>
               <tr>
                  <td >
                     &nbsp;
                  </td>
                  <td style="border-bottom:1px solid black;">
                     &nbsp;
                  </td>
                  <td valign="bottom" align="right" >
                     /
                  </td>
                  <td style="border-bottom:1px solid black;">
                     &nbsp;
                  </td>
                  <td valign="bottom">
                     /
                  </td>
               </tr>
               <tr>
                  <td >
                     &nbsp;
                  </td>
                  <td align="center" class="subscript">
                     (подпись работника банка)
                  </td>
                  <td>
                     &nbsp;
                  </td>
                  <td align="center" class="subscript">
                     (Ф.И.О.)
                  </td>
                  <td >
                     &nbsp;
                  </td>
               </tr>
            </table>
         </td>
      </tr>
         </table>
         </td>
      </tr>
    </table>

</c:if>
<c:if test="${!isProl}">
   <script type="text/javascript">
   window.width =0;
   window.height = 0;
   alert("Для печати заявления на прекращение предоставления услуг поле \"Дата расторжения договора\" должно быть заполнено");
   window.close();
   </script>
</c:if>
</tiles:put>
</tiles:insert>
