<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 23.06.2009
  Time: 14:06:09
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 16.06.2009
  Time: 18:47:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insert definition="personsContract2">
<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="form" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <c:set var="document" value="${form.activeDocument}"/>

    <c:set var="count" value="0"/>
    <logic:iterate id="currentAccountLink" name="form" property="currentAccountLinks">
        <c:if test="${count > 0}">
            <br style="page-break-before:always;">            
        </c:if>
        <c:set var="count" value="${count + 1}"/>
        
        <c:set var="currentAccountLink" value="${currentAccountLink}"/>

        <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;text-align:justify; vertical-align:text-top;">
        <col style="width:172mm">
            <tr>
            <td>
            <table style="width:172mm;" cellpadding="0" cellspacing="0" class="textDoc" border="0">
            <%@include file="footer_curDop.jsp"%>
            <tbody>

            <!-- Пространство над шапкой -->
            <tr>
                <td align="right">Код 014180002/2</td>
            </tr>

            <!--Логотип Сбера-->
            <tr>
                <td>
                    <%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %>
                </td>
            </tr>
            <tr id="insertBr" style="display:none;">
                <td><br>&nbsp;</td>
            </tr>
            <tr>
                <td align='center'><b>Дополнительное соглашение</b><br/>к Договору о текущем счете</td>
            </tr>
            <c:set var="openDate" value="${currentAccountLink.account.openDate.time}"/>
            <tr>
                <td align="center">
                    <table cellpadding="0" cellspacing="0" width="75%">
                    <tr>
                        <td>
                            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                            <tr class="textDoc">
                                <td width="1%">от</td>
                                <td width="6%" class="tdUnderlinedItalic">
                                    <nobr>&ldquo;<bean:write name="openDate" format="dd"/>&rdquo;</nobr>
                                </td>
                                <td width="19%"><input id="monthStr${count}" value='' type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                                <td width="1%">20</td>
                                <td width="6%">
                                    <nobr><input value='<bean:write name="openDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:97%;text-align:left;"></nobr>
                                </td>
                                <td>года&nbsp;№</td>
                                <td width="65%">
                                    <input value="${currentAccountLink.account.number}"  type="Text" readonly="true" class="insertInput" style="width:98%">
                                </td>
                            </tr>
                            </table>
                            <script type="text/javascript">document.getElementById('monthStr${count}').value = monthToStringOnly('<bean:write name="openDate" format="dd.MM.yyyy"/>');</script>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td style="padding-top:10mm">
                    <table cellpadding="0" cellspacing="0" width="100%"  class="textDoc">
                    <tr>
                        <td width="70%"><nobr><input value='${department.city}' type="Text" class="insertInput" style="width: 50%"></nobr></td>
                        <td>&nbsp;</td>
                        <td><nobr>&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                        <td><input id='monthStr103${count}' value="" type="Text" class="insertInput" style="width: 100px"></td>
                        <td>20</td>
                        <td><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 20px">года</td>
                    </tr>
                    </table>
                </td>
            </tr>
            <script>
                document.getElementById('monthStr103${count}').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
            </script>
            <tr>
                <td>
                    <script type="text/javascript">
                        document.getElementById("insertBr").style.display = "block";
                    </script>
                    <br>
                     <span style="padding-left:10mm">Открытое</span> акционерное общество «Сбербанк России», именуемое в дальнейшем &ldquo;Банк&rdquo;, с одной стороны, и
                </td>
            </tr>
            <tr>
                <td class="tdUnderlinedItalic">${person.fullName},</td>
            </tr>
            <tr>
                <td>
                    именуемый в дальнейшем &ldquo;Владелец счета&rdquo;, с другой стороны, совместно именуемые &ldquo;Стороны&rdquo;, заключили настоящее Дополнительное соглашение о нижеследующем:
                </td>
            </tr>
            <tr>
                <td style="padding-top:5mm;">
                    <table style="width:100%" class="textDoc">
                        <tr>
                            <td><nobr>1.&nbsp;&nbsp;Дополнить раздел &ldquo;Порядок открытия и ведения счета&rdquo; Договора от</nobr></td>
                            <td style="width:15%" class="tdUnderlinedItalic"><bean:write name="openDate" format="dd.MM.yyyy"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    № <nobr><span style="text-decoration:underline">${currentAccountLink.account.number}</span></nobr>
                    пунктом следующего содержания:
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message bundle="commonBundle" key="text.additional.agreement.operations.list"/>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    В соответствии с Заявлением № <span style="text-decoration:underline"><bean:write name='person' property="agreementNumber"/></span> от <span style="text-decoration:underline"><bean:write name="agreementDate" format="dd.MM.yyyy"/></span> и Условиями Банк вправе списывать в безакцептном порядке без дополнительных распоряжений Владельца счета
                    со счета № <nobr><span style="text-decoration:underline">${currentAccountLink.account.number}</span></nobr> плату за услуги Банка, предусмотренную Заявлением № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%"> в соответствии с тарифами Банка.
                </td>
            </tr>
            <tr>
                <td style="padding-top:5mm;">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                       <td valign="top">2.&nbsp;&nbsp;</td>
                       <td>Настоящее Дополнительное соглашение вступает в силу с момента его подписания обеими Сторонами.</td>
                    </tr>
                    <tr>
                        <td valign="top">3.&nbsp;&nbsp;</td>
                        <td>
                            <bean:message bundle="commonBundle" key="text.additional.agreement.stop"/> <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от
                            <c:choose>
                                <c:when test="${empty agreementDate}">
                                    &ldquo;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rdquo;<input id='monthStr101${count}' value='' type="Text" class="insertInput" style="width: 17%">
                                </c:when>
                                <c:otherwise>
                                    &ldquo;<bean:write name='agreementDate' format="dd"/>&rdquo;<input id='monthStr101${count}' value='' type="Text" class="insertInput" style="width: 17%"> 20<bean:write name='agreementDate' format="y"/>
                                    <script>
                                        document.getElementById('monthStr101${count}').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
                                    </script>
                                </c:otherwise>
                            </c:choose>
                            и Условия.
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td style="padding-top:5mm;">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td valign="top">4.&nbsp;&nbsp;</td>
                        <td>
                            Настоящее Дополнительное соглашение составлено в двух, имеющих равную силу, экземплярах, один экземпляр для Вкладчика, один - Банку.
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><b><br>Владелец счета:</b></td>
            </tr>
            <tr>
                <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
            </tr>
            <tr>
                <td><b>Банк:</b></td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td>местонахождение</td>
                        <script type="text/javascript">
                            var str = '${department.location}';
                            var address = breakString(str, 60);
                            document.write("<td class='tdUnderlinedItalic' style='width:85%'><nobr>"+address+"</nobr></td>");
                            str = str.substring(address.length, str.length);
                        </script>
                   </tr>
                    <script type="text/javascript">
                             while (str.length > 0)
                             {
                                 address = breakString(str, 70);
                                 document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
                                 str = str.substring(address.length, str.length);
                             }
                    </script>
                    <tr>
                        <td>
                            <nobr>почтовый адрес</nobr>
                        </td>
                        <script type="text/javascript">
                            var str = '${department.address}';
                            var address = breakString(str, 60);
                            document.write("<td class='tdUnderlinedItalic' style='width:85%'><nobr>"+address+"</nobr></td>");
                            str = str.substring(address.length, str.length);
                        </script>
                   </tr>
                    <script type="text/javascript">
                             while (str.length > 0)
                             {
                                 address = breakString(str, 80);
                                document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
                                str = str.substring(address.length, str.length);
                             }
                    </script>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="50%" class="textDoc" cellpadding="0" cellspacing="0">
                    <tr valign="bottom">
                        <td>
                            <nobr>телефон</nobr>
                        </td>
                        <td width="50%">
                            <input value="${department.telephone}" type="Text" readonly="true" class="insertInput" style="width:100%">
                        </td>
                        <td>
                            <nobr>факс</nobr>
                        </td>
                        <td width="50%">
                            <input type="Text" readonly="true" class="insertInput" style="width:100%">
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="50%">От имени Банка:</td>
                        <td>Вкладчик:</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                            <tr>
                                <td width="23%"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                                <td width="27%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 96%">)</nobr></td>
                                <td width="23%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                                <td width="27%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 94%">)</nobr></td>
                            </tr>
                            <tr>
                                <td colspan="2">М.П.</td>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="bottom" style="height:12mm;">
                    <table class="textDoc" style="width:100%;">
                        <tr>
                            <td>
                                <input type="Text" class="insertInput" style="width: 100%">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="font10" align="center">Дополнительное соглашение №<input value="2" type="Text" readonly="true" class="insertInput" style="width:10%"> от &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo; <input id="monthStr102${count}" type="Text" readonly="true" class="insertInput" style="width:15%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:4%"> г.</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <script>
                document.getElementById('monthStr102${count}').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
            </script>
            </tbody>

            </table>
            </td>
            </tr>
            </table>
    </logic:iterate>
    </html:form>
</tiles:put>
</tiles:insert>
