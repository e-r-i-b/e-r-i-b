<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:insert definition="personsContract2">
<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <body onLoad="showMessage();" Language="JavaScript">

    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <c:set var="document" value="${form.activeDocument}"/>
    <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
    <td>
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <!-- Пространство над шапкой -->
    <tr>
        <td height="20mm">&nbsp;</td>
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
        <td align='center'><b>Дополнительное соглашение</b></td>
    </tr>
    <% int lineNumber = 0;%>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
             <%lineNumber++;%>
    <tr>
        <td align="center">
            <table cellpadding="0" cellspacing="0" class="textDoc" width="90%">
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td>
                            <nobr>к Договору о вкладе</nobr>
                        </td>
                        <td width="100%">
                            <nobr>&ldquo;<input value="${accountLink.account.type}" type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="1%">от</td>
                        <td width="6%">
                            <nobr>&ldquo;<input value='<bean:write name="openDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:70%">&rdquo;</nobr>
                        </td>
                        <td width="19%"><input id="monthStr0<%=lineNumber%>" value='' type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="1%">20</td>
                        <td width="6%">
                            <nobr><input value='<bean:write name="openDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:97%;text-align:left;"></nobr>
                        </td>
                        <td>года&nbsp;№</td>
                        <td width="65%">
                            <input value="${accountLink.account.number}"  type="Text" readonly="true" class="insertInput" style="width:98%">
                        </td>
                    </tr>
                    </table>
                    <script type="text/javascript">document.getElementById('monthStr0<%=lineNumber%>').value = monthToStringOnly('<bean:write name="openDate" format="dd.MM.yyyy"/>');</script>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <tr>
        <td>
            <% if (lineNumber < 5) {%><br><%}%>
            <table  class="textDoc" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="25%"><nobr>г.&nbsp;<input value='${department.city}' type="Text" class="insertInput" style="width: 92%"></nobr></td>
                <td width="43%">&nbsp;</td>
                <td width="7%"><nobr>&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td width="25%"><nobr><input id='monthStr103' value='' type="Text" class="insertInput" style="width: 67%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 12%">г.</nobr></td>
            </tr>
            </table>
        </td>
    </tr>
    <script>
        document.getElementById('monthStr103').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td>
            <% if (lineNumber < 5) {%>
            <script type="text/javascript">
                document.getElementById("insertBr").style.display = "block";
            </script>
            <br>
            <%}%>
            Открытое акционерное общество «Сбербанк России», именуемое в дальнейшем &ldquo;Банк&rdquo;, с одной стороны, и
        </td>
    </tr>
    <tr>
        <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:99%;">,</td>
    </tr>
    <tr>
        <td>
            именуемый в дальнейшем &ldquo;Вкладчик&rdquo;, с другой стороны, совместно именуемые &ldquo;Стороны&rdquo;, заключили настоящее Дополнительное соглашение о нижеследующем:
        </td>
    </tr>
    <tr>
        <td>
            <% if (lineNumber < 5) {%><br><%}%>
            1.&nbsp;&nbsp;Дополнить раздел &ldquo;Особые условия&rdquo;
        </td>
    </tr>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>Договора о вкладе</nobr>
                </td>
                <td width="100%">
                    <nobr>&ldquo;<input value="${accountLink.account.type}" type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="90%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>от</td>
                <td width="30%">
                    <nobr>&ldquo;<input value='<bean:write name="openDate" format="dd.MM.yyyy"/>' type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
                <td>№</td>
                <td width="70%">
                    <nobr>&ldquo;<input value="${accountLink.account.number}" type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <tr>
        <td>пунктом <i>(пунктами)</i> следующего содержания:</td>
    </tr>
    <tr>
        <td><% if (lineNumber < 5) {%><br><%}%>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &ldquo;Перечень операций по вкладу и порядок их осуществления с использованием системы &ldquo;Электронная Сберкасса&rdquo; устанавливается Сторонами в &ldquo;Договоре о предоставлении услуг с
            использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            В соответствии с пунктами 3.13 и 4.3.2  (3.10 и 4.3.1.) &ldquo;Договора о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%"> Банк вправе списывать в безакцептном порядке без дополнительных распоряжений Вкладчика
        </td>
    </tr>
    <% if (lineNumber < 6) {%>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <tr>
        <td>
            <table width="60%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>со счета по вкладу №</nobr></td>
                <td width="100%"><input value="${accountLink.account.number}" type="Text" readonly="true" class="insertInput" style="width:98%">,</td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <%}%>
    <% if (lineNumber < 4) {%>
    <tr>
        <td>плату за услуги Банка, предусмотренную &ldquo;Договором о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, в соответствии с тарифами Банка, а также не предоставлять услуги и не совершать операции при отсутствии у Банка возможности списать плату из-за недостаточности средств во вкладе".</td>
    </tr>
    <%}%>
    <% if (lineNumber < 3) {%>
    <tr>
        <td class="italic">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &ldquo;В соответствии с пунктом 4.3.2 (4.3.1.) &ldquo;Договора о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%"> Банк вправе списывать в безакцептном порядке без дополнительных распоряжений
            Вкладчика со счета по вкладу №
            <font style="text-decoration:underline;">
                <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
		        <c:if test="${(entry.paymentAbility == 'true')}">
                    ${accountLink.account.number}&nbsp;
                </c:if>
                </logic:iterate>
            </font> &rdquo; денежные средства в иностранной валюте,
            с последующей их продажей по курсу и на условиях, установленных Банком для совершения безналичных конверсионных операций на дату совершения операции,
        </td>
    </tr>
    <%}%>
    <% if (lineNumber < 2) {%>
    <tr>
        <td class="italic">для погашения платы за услуги Банка, предусмотренные с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, в соответствии с тарифами Банка.&rdquo;.</td>
    </tr>
    <tr><td><br></td></tr>
    <%}%>
<%--
    <% if (lineNumber < 5) {%>
        <tr><td>
            <br>
        </td></tr>
    <%}%>
--%>
    <% if (lineNumber == 6 ) {%>
    <tr><td>
        <br>
    </td></tr>
    <%}%>
    <% if (lineNumber < 2 ) {%>
    <tr><td>
        <br>
    </td></tr>
    <%}%>
    <% if (lineNumber < 3 ) {%>
        <tr><td>
            <br>
        </td></tr>
    <%}%>
    <% if (lineNumber < 1 ) {%>
    <tr><td>
        <br><br><br><br><br><br><br><br>
    </td></tr>
    <%}%>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table  class="textDoc" style="width:100%;">
                <tr>
                    <td><nobr>От имени Банка</nobr></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:80%"></td>
                    <td>Клиент</td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:80%"></td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
     <br style="page-break-after:always;">

     <!---------------------------------- страница2----------------------------------------->
    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
    <% if (lineNumber > 5) {%>
    <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
    <c:set var="accountLink" value="${entry.value}"/>
    <tr>
        <td>
            <table width="60%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>со счета по вкладу №</nobr></td>
                <td width="100%"><input value="${accountLink.number}" type="Text" readonly="true" class="insertInput" style="width:98%">,</td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <%}%>
    <% if (lineNumber > 3) {%>
    <tr>
        <td>плату за услуги Банка, предусмотренную &ldquo;Договором о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">, в соответствии с тарифами Банка, а также не предоставлять услуги и не совершать операции при отсутствии у Банка возможности списать плату из-за недостаточности средств во вкладе".</td>
    </tr>
    <%}%>
    <% if (lineNumber > 2) {%>
    <tr>
        <td class="italic">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &ldquo;В соответствии с пунктом 4.3.2 (4.3.1.) &ldquo;Договора о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%"> Банк вправе списывать в безакцептном порядке без дополнительных распоряжений
            Вкладчика со счета по вкладу №
            <font style="text-decoration:underline;">
            <% boolean lineNumberFlag = false;%>
                <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
                <c:set var="accountLink" value="${entry.value}"/>
                <c:if test="${(entry.paymentAbility == 'true')}">
                    <% if (lineNumberFlag) {%>,&nbsp;<%}%>
                    ${accountLink.number}
                    <%lineNumberFlag=true;%>
                </c:if>
                </logic:iterate>
            </font> &rdquo; денежные средства в иностранной валюте,
              с последующей их продажей по курсу и на условиях, установленных Банком для совершения безналичных конверсионных операций на дату совершения операции,
        </td>
    </tr>
    <%}%>
    <% if (lineNumber > 1) {%>
    <tr>
        <td class="italic">для погашения платы за услуги Банка, предусмотренные "Договором о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 26%"> от <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">, в соответствии с тарифами Банка.&rdquo;.</td>
    </tr>
    <%}%>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
               <td valign="top">2.&nbsp;&nbsp;</td>
               <td>Настоящее Дополнительное соглашение вступает в силу с момента его подписания обеими Сторонами.</td>
            </tr>
            <tr>
                <td valign="top">3.&nbsp;&nbsp;</td>
                <td>
                    Настоящее Дополнительное соглашение может быть расторгнуто по инициативе одной из Сторон, которая должна письменно информировать об этом другую Сторону не менее, чем за один месяц до даты расторжения Дополнительного соглашения. С момента расторжения настоящего Дополнительного соглашения по инициативе одной из Сторон автоматически прекращает свое действие &ldquo;Договор о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; № <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 26%">, заключенный Сторонами &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 5%">&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%"> 20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 4%">года.
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <script>
        document.getElementById('monthStr101').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td>
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
<%--
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
--%>
    <tr>
        <td><b><br>Вкладчик:</b></td>
    </tr>
    <tr>
        <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>адрес регистрации</nobr>
                </td>
                <td width="100%">
                    <nobr><input value='${person.registrationAddress}' type="Text" readonly="true" class="insertInput" style="width:100%"></nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>адрес фактического проживания</nobr>
                </td>
                <td width="100%">
                    <nobr><input value='${person.residenceAddress}' type="Text" readonly="true" class="insertInput" style="width:100%"></nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>тел. (дом.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.homePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>тел. (раб.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.jobPhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>тел. (моб.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.mobilePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>дата рождения</nobr>
                </td>
                <td width="50%">
                    <input value='${person.birthDayString}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>E-Mail</nobr>
                </td>
                <td width="50%">
                    <input value='${person.email}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table style="width:170mm;height:100%;" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td width="40%">
                    <c:if test="${(document.documentType == 'REGULAR_PASSPORT_RF')}">
                        <input value="Общегражданский паспорт РФ" type="Text" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'MILITARY_IDCARD')}">
                        <input value="Удостоверение личности военнослужащего" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                        <input value="Паспорт моряка" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'RESIDENСЕ_PERMIT_RF')}">
                        <input value="Вид на жительство РФ" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                        <input value="Заграничный паспорт РФ" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'OTHER')}">
                        <input value="" readonly="true" class="insertInput" style="width:97%">
                       ${document.documentName}
                    </c:if>
                </td>
                <td width="20%">
                    <input value='${document.documentNumber} ${document.documentSeries}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td width="10%">
                    <nobr>кем, где, когда выдан</nobr>
                </td>
                <td width="20%">
                    <input value='' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td style="font-size:8pt;">(вид, документа, удостоверяющего личность ) (номер, серия - при наличии)</td>
    </tr>
    <tr>
        <td align="center"><input value='123 ${document.documentIssueBy}, <bean:write name="person" property="passportIssueDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
    </tr>
    <tr>
        <td><b>Банк:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>местонахождение</td>
                <td width="100%">
                    <nobr><input value="${department.location}" type="Text" readonly="true" class="insertInput" style="width:100%"></nobr>
                </td>
            </tr>
            <tr>
                <td>
                    <nobr>почтовый адрес</nobr>
                </td>
                <td width="100%">
                    <nobr><input value="${department.postAddress}" type="Text" readonly="true" class="insertInput" style="width:100%"></nobr>
                </td>
            </tr>
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
                    <input value="${department.phone}" type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>факс</nobr>
                </td>
                <td width="50%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
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
                        <td width="50%" colspan="2" style="padding-right:8;"><input value="${employee.surName} ${employee.firstName}" type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td width="23%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td width="27%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 94%">)</nobr></td>
                    </tr>
                    <tr>
                        <td style="padding-right:8;" colspan="2"><input value="${employee.patrName}" type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td style="padding-left:8;" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td width="23%"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td width="27%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 96%">)</nobr></td>
                        <td style="padding-left:8;" colspan="2">&nbsp;</td>
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
        <td>
            <%if (lineNumber < 6) {%><br><br><br><br><br><br><%}%>
            <%if (lineNumber < 4) {%><br><br><br><br><br><br><%}%>
            <%if (lineNumber < 3) {%><br><br><br><br><br><br><%}%>
            <%if (lineNumber < 2) {%><br><br><br><br><%}%>
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
                    <td colspan="4" class="font10" align="center">Дополнительное соглашение №<input value="1" type="Text" readonly="true" class="insertInput" style="width:10%"> от &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo; <input id="monthStr102" type="Text" readonly="true" class="insertInput" style="width:15%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:4%"> г.</td>
                </tr>
            </table>
        </td>
    </tr>
    <script>
        document.getElementById('monthStr102').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    </table>
    </td>
    </tr>
    </table>

    </html:form>
    </body>
</tiles:put>
</tiles:insert>
