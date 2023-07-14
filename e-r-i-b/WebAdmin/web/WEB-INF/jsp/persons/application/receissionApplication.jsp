<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 24.06.2009
  Time: 17:47:44
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
  .label {font-family:Times New Roman;font-size:12pt;}
  .subscript {font-family:Times New Roman;font-size:12pt;vertical-align:top;text-align:center}
   table{font-family:Times New Roman;color:#3d3d3d;font-size:12pt;}
</style>

<c:if test="${isProl}">
     <table style="width:169mm;margin-left:15mm;margin-top:15mm; font-size:12pt;">
      <tr>
         <td align="right">
            <i><u><b>в Сбербанк России</b></u></i>
         </td>
      </tr>
      <tr>
         <td align="center" valign="bottom" style="height:25mm">
            <b>
            <bean:message bundle="commonBundle" key="text.declaration.stop.service"/>
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
            <bean:write name="curDate" format="yyyy"/> года
            </u>
         </td>
      </tr>
      <tr>
         <td style="text-align:justify;">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            Прошу с "<bean:write name="prolDate" format="d"/>"
            <script type="text/javascript">document.write(monthToStringOnly('<bean:write name="prolDate" format="dd.MM.yyyy"/>'));</script>
            <bean:write name="prolDate" format="yyyy"/>
             <bean:message bundle="commonBundle" key="text.declaration.service.stop.fromDate"/></td>
      </tr>
      <tr style="height:25mm;">
         <td align="right" valign="bottom">
            <i>Клиент:</i>
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
                     Заявление принято к исполнению.
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
                  <td align="center" class="subscript" style="font-style:italic;font-size:9pt;">
                     (подпись работника банка)
                  </td>
                  <td>
                     &nbsp;
                  </td>
                  <td align="center" class="subscript" style="font-style:italic;font-size:9pt;">
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