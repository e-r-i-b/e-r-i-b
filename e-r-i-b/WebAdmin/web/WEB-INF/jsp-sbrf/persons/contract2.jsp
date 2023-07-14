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
    <!-- ������������ ��� ������ -->
    <tr>
        <td height="20mm">&nbsp;</td>
    </tr>

    <!--������� �����-->
    <tr>
        <td>
            <%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %>
        </td>
    </tr>
    <tr id="insertBr" style="display:none;">
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td align='center'><b>�������������� ����������</b></td>
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
                            <nobr>� �������� � ������</nobr>
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
                        <td width="1%">��</td>
                        <td width="6%">
                            <nobr>&ldquo;<input value='<bean:write name="openDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:70%">&rdquo;</nobr>
                        </td>
                        <td width="19%"><input id="monthStr0<%=lineNumber%>" value='' type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="1%">20</td>
                        <td width="6%">
                            <nobr><input value='<bean:write name="openDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:97%;text-align:left;"></nobr>
                        </td>
                        <td>����&nbsp;�</td>
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
                <td width="25%"><nobr>�.&nbsp;<input value='${department.city}' type="Text" class="insertInput" style="width: 92%"></nobr></td>
                <td width="43%">&nbsp;</td>
                <td width="7%"><nobr>&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td width="25%"><nobr><input id='monthStr103' value='' type="Text" class="insertInput" style="width: 67%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 12%">�.</nobr></td>
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
            �������� ����������� �������� ��������� ������, ��������� � ���������� &ldquo;����&rdquo;, � ����� �������, �
        </td>
    </tr>
    <tr>
        <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:99%;">,</td>
    </tr>
    <tr>
        <td>
            ��������� � ���������� &ldquo;��������&rdquo;, � ������ �������, ��������� ��������� &ldquo;�������&rdquo;, ��������� ��������� �������������� ���������� � �������������:
        </td>
    </tr>
    <tr>
        <td>
            <% if (lineNumber < 5) {%><br><%}%>
            1.&nbsp;&nbsp;��������� ������ &ldquo;������ �������&rdquo;
        </td>
    </tr>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>�������� � ������</nobr>
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
                <td>��</td>
                <td width="30%">
                    <nobr>&ldquo;<input value='<bean:write name="openDate" format="dd.MM.yyyy"/>' type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
                <td>�</td>
                <td width="70%">
                    <nobr>&ldquo;<input value="${accountLink.account.number}" type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <tr>
        <td>������� <i>(��������)</i> ���������� ����������:</td>
    </tr>
    <tr>
        <td><% if (lineNumber < 5) {%><br><%}%>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &ldquo;�������� �������� �� ������ � ������� �� ������������� � �������������� ������� &ldquo;����������� ���������&rdquo; ��������������� ��������� � &ldquo;�������� � �������������� ����� �
            �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            � ������������ � �������� 3.13 � 4.3.2  (3.10 � 4.3.1.) &ldquo;�������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%"> ���� ������ ��������� � ������������ ������� ��� �������������� ������������ ���������
        </td>
    </tr>
    <% if (lineNumber < 6) {%>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <tr>
        <td>
            <table width="60%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>�� ����� �� ������ �</nobr></td>
                <td width="100%"><input value="${accountLink.account.number}" type="Text" readonly="true" class="insertInput" style="width:98%">,</td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <%}%>
    <% if (lineNumber < 4) {%>
    <tr>
        <td>����� �� ������ �����, ��������������� &ldquo;��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����, � ����� �� ������������� ������ � �� ��������� �������� ��� ���������� � ����� ����������� ������� ����� ��-�� ��������������� ������� �� ������".</td>
    </tr>
    <%}%>
    <% if (lineNumber < 3) {%>
    <tr>
        <td class="italic">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &ldquo;� ������������ � ������� 4.3.2 (4.3.1.) &ldquo;�������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%"> ���� ������ ��������� � ������������ ������� ��� �������������� ������������
            ��������� �� ����� �� ������ �
            <font style="text-decoration:underline;">
                <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
		        <c:if test="${(entry.paymentAbility == 'true')}">
                    ${accountLink.account.number}&nbsp;
                </c:if>
                </logic:iterate>
            </font> &rdquo; �������� �������� � ����������� ������,
            � ����������� �� �������� �� ����� � �� ��������, ������������� ������ ��� ���������� ����������� ������������� �������� �� ���� ���������� ��������,
        </td>
    </tr>
    <%}%>
    <% if (lineNumber < 2) {%>
    <tr>
        <td class="italic">��� ��������� ����� �� ������ �����, ��������������� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����.&rdquo;.</td>
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
                    <td><nobr>�� ����� �����</nobr></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:80%"></td>
                    <td>������</td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:80%"></td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
     <br style="page-break-after:always;">

     <!---------------------------------- ��������2----------------------------------------->
    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
    <% if (lineNumber > 5) {%>
    <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
    <c:set var="accountLink" value="${entry.value}"/>
    <tr>
        <td>
            <table width="60%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>�� ����� �� ������ �</nobr></td>
                <td width="100%"><input value="${accountLink.number}" type="Text" readonly="true" class="insertInput" style="width:98%">,</td>
            </tr>
            </table>
        </td>
    </tr>
    </logic:iterate>
    <%}%>
    <% if (lineNumber > 3) {%>
    <tr>
        <td>����� �� ������ �����, ��������������� &ldquo;��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����, � ����� �� ������������� ������ � �� ��������� �������� ��� ���������� � ����� ����������� ������� ����� ��-�� ��������������� ������� �� ������".</td>
    </tr>
    <%}%>
    <% if (lineNumber > 2) {%>
    <tr>
        <td class="italic">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &ldquo;� ������������ � ������� 4.3.2 (4.3.1.) &ldquo;�������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%"> ���� ������ ��������� � ������������ ������� ��� �������������� ������������
            ��������� �� ����� �� ������ �
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
            </font> &rdquo; �������� �������� � ����������� ������,
              � ����������� �� �������� �� ����� � �� ��������, ������������� ������ ��� ���������� ����������� ������������� �������� �� ���� ���������� ��������,
        </td>
    </tr>
    <%}%>
    <% if (lineNumber > 1) {%>
    <tr>
        <td class="italic">��� ��������� ����� �� ������ �����, ��������������� "��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 26%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����.&rdquo;.</td>
    </tr>
    <%}%>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
               <td valign="top">2.&nbsp;&nbsp;</td>
               <td>��������� �������������� ���������� �������� � ���� � ������� ��� ���������� ������ ���������.</td>
            </tr>
            <tr>
                <td valign="top">3.&nbsp;&nbsp;</td>
                <td>
                    ��������� �������������� ���������� ����� ���� ����������� �� ���������� ����� �� ������, ������� ������ ��������� ������������� �� ���� ������ ������� �� �����, ��� �� ���� ����� �� ���� ����������� ��������������� ����������. � ������� ����������� ���������� ��������������� ���������� �� ���������� ����� �� ������ ������������� ���������� ���� �������� &ldquo;������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 26%">, ����������� ��������� &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 5%">&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%"> 20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 4%">����.
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
                    ��������� �������������� ���������� ���������� � ����, ������� ������ ����, �����������, ���� ��������� ��� ���������, ���� - �����.
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
        <td><b><br>��������:</b></td>
    </tr>
    <tr>
        <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>����� �����������</nobr>
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
                    <nobr>����� ������������ ����������</nobr>
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
                    <nobr>���. (���.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.homePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>���. (���.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.jobPhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>���. (���.)</nobr>
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
                    <nobr>���� ��������</nobr>
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
                        <input value="��������������� ������� ��" type="Text" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'MILITARY_IDCARD')}">
                        <input value="������������� �������� ���������������" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                        <input value="������� ������" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'RESIDEN��_PERMIT_RF')}">
                        <input value="��� �� ���������� ��" readonly="true" class="insertInput" style="width:97%">
                    </c:if>
                    <c:if test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                        <input value="����������� ������� ��" readonly="true" class="insertInput" style="width:97%">
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
                    <nobr>���, ���, ����� �����</nobr>
                </td>
                <td width="20%">
                    <input value='' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td style="font-size:8pt;">(���, ���������, ��������������� �������� ) (�����, ����� - ��� �������)</td>
    </tr>
    <tr>
        <td align="center"><input value='123 ${document.documentIssueBy}, <bean:write name="person" property="passportIssueDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
    </tr>
    <tr>
        <td><b>����:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>���������������</td>
                <td width="100%">
                    <nobr><input value="${department.location}" type="Text" readonly="true" class="insertInput" style="width:100%"></nobr>
                </td>
            </tr>
            <tr>
                <td>
                    <nobr>�������� �����</nobr>
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
                    <nobr>�������</nobr>
                </td>
                <td width="50%">
                    <input value="${department.phone}" type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>����</nobr>
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
                <td width="50%">�� ����� �����:</td>
                <td>��������:</td>
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
                        <td colspan="2">�.�.</td>
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
                    <td colspan="4" class="font10" align="center">�������������� ���������� �<input value="1" type="Text" readonly="true" class="insertInput" style="width:10%"> �� &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo; <input id="monthStr102" type="Text" readonly="true" class="insertInput" style="width:15%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:4%"> �.</td>
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
