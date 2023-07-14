<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:insert definition="personsContract2">
<tiles:put name="data" type="string">
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

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
    <td>
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">

	<tfoot>
		  <th>
			 <table class="textDoc font11" cellpadding="0" cellspacing="0" width="100%">
			 <tr>
				<td valign="bottom" style="height:12mm;padding-top:7mm">
					<table class="textDoc font11" style="width:100%;">
					<tr>
						<td><nobr>��&nbsp;�����&nbsp;�����</nobr></td>
						<td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
						<td>��������</td>
						<td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		 </th>
	</tfoot>
    <tbody>

    <!-- ������������ ��� ������ -->
    <tr>
        <td align="right">��� 014180001/1</td>
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
                        <td width="100%" class="tdUnderlinedItalic">
                            <nobr>&ldquo;${accountLink.account.fullDescription}&rdquo;</nobr>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr class="textDoc">
                        <td width="1%">��</td>
                        <td width="6%" class="tdUnderlinedItalic">
                            <nobr>&ldquo;<bean:write name="openDate" format="dd"/>&rdquo;</nobr>
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
            <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="70%"><nobr>�. <input value='${department.city}' type="Text" class="insertInput" style="width: 50%"></nobr></td>
                <td>&nbsp;</td>
                <td><nobr>&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td><input id='monthStr103' value="" type="Text" class="insertInput" style="width: 100px"></td>
	            <td>20</td>
	            <td><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 20px">�.</td>
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
        <td class="tdUnderlinedItalic">${person.fullName},</td>
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
            <tr class="textDoc">
                <td>
                    <nobr>�������� � ������</nobr>
                </td>
                <td width="100%" class="tdUnderlinedItalic">
                    <nobr>&ldquo;${accountLink.account.fullDescription}&rdquo;</nobr>
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
                <td width="30%" class="tdUnderlinedItalic">
                    <nobr>&ldquo;<bean:write name="openDate" format="dd.MM.yyyy"/>&rdquo;</nobr>
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
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
	<% int accountsForPayWroteDown = 0; // ����� ���������� ��� ������ ��� �������� �����%>
    <%if ( accountsForPayWroteDown < 3 ) { %>
	<c:if test="${(accountLink.paymentAbility == 'true')}">
	<tr>
        <td>
            <table width="60%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>�� ����� �� ������ �</nobr></td>
		            <% accountsForPayWroteDown++;%>
                    <td width="100%"><input value="${accountLink.account.number}" type="Text" readonly="true" class="insertInput" style="width:98%">,</td>
            </tr>
            </table>
        </td>
    </tr>
	</c:if>
	<%}%>
    </logic:iterate>
    <tr>
        <td>����� �� ������ �����, ��������������� &ldquo;��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����, � ����� �� ������������� ������ � �� ��������� �������� ��� ���������� � ����� ����������� ������� ����� ��-�� ��������������� ������� �� ������".</td>
    </tr>
    <c:if test="${not empty PrintPersonForm.foreignAccounts}">
    <tr>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &ldquo;� ������������ � ������� 4.3.2 (4.3.1.) &ldquo;�������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%"> ���� ������ ��������� � ������������ ������� ��� �������������� ������������
            ��������� �� ����� �� ������
	        <logic:iterate id="entry" name="PrintPersonForm" property="foreignAccounts">
	             �<input value="${entry.number}" type="Text" readonly="true" class="insertInput" style="width:180px">,
	        </logic:iterate> �������� �������� � ����������� ������,
            � ����������� �� �������� �� ����� � �� ��������, ������������� ������ ��� ���������� ����������� ������������� �������� �� ���� ���������� ��������,
        </td>
    </tr>

    <tr>
        <td>��� ��������� ����� �� ������ �����, ��������������� &ldquo;��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name='agreementDate' format="dd.MM.yyyy"/>'  type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����.&rdquo;.</td>
    </tr>
    </c:if>
    <tr><td><br></td></tr>

     <!---------------------------------- ��������2----------------------------------------->
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
                    ��������� �������������� ���������� ����� ���� ����������� �� ���������� ����� �� ������, ������� ������ ��������� ������������� �� ���� ������ ������� �� �����, ��� �� ���� ����� �� ���� ����������� ��������������� ����������. � ������� ����������� ���������� ��������������� ���������� �� ���������� ����� �� ������ ������������� ���������� ���� �������� &ldquo;������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; � <bean:write name='person' property="agreementNumber"/>, ����������� ���������
	                <c:choose><c:when test="${empty agreementDate}">&ldquo;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%">20&nbsp;&nbsp;&nbsp;����.</c:when><c:otherwise>&ldquo;<bean:write name='agreementDate' format="dd"/>&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%"> 20<bean:write name='agreementDate' format="y"/>����.</c:otherwise></c:choose>
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
    <!--<tr>-->
        <!--<td>-->
            <!--<br>-->
            <!--&nbsp;-->
        <!--</td>-->
    <!--</tr>-->
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

				<script type="text/javascript">
					var str = '${person.registrationAddress}';
					var address = breakString(str, 60);
					document.write("<td class='tdUnderlinedItalic' style='width:70%'><nobr>"+address+"</nobr></td>");
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
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>����� ������������ ����������</nobr>
                </td>
 				<script type="text/javascript">
					var str = '${person.residenceAddress}';
					var address = breakString(str, 40);
					document.write("<td class='tdUnderlinedItalic' style='width:60%'><nobr>"+address+"</nobr></td>");
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
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>���� ��������</nobr>
                </td>
                <td width="40%">
                    <input value='<bean:write name="PrintPersonForm" property="activePerson.birthDay" format="dd.MM.yyyy"/>' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>E-Mail</nobr>
                </td>
                <td width="60%">
                    <input value='${person.email}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
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
        </td>
    </tr>
    <tr>
        <td style="font-size:8pt;">(���, ���������, ��������������� �������� ) (�����, ����� - ��� �������)</td>
    </tr>
    <%--<c:if test="${not empty document.documentIssueBy or not empty document.documentIssueDate}">--%>
		<tr>
			<td class="tdUnderlinedItalic">
				<c:if test="${not empty document.documentIssueBy}">${document.documentIssueBy}</c:if>, <c:if test="${not empty document.documentIssueDate}"><bean:write name="document" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:if>
			</td>
		</tr>
    <%--</c:if>    --%>
    <tr>
        <td><b>����:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>���������������</td>
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
                    <nobr>�������� �����</nobr>
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
                    <nobr>�������</nobr>
                </td>
                <td width="50%">
                    <input value="${department.telephone}" type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>����</nobr>
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
    </tbody>

    </table>
    </td>
    </tr>
    </table>

    </html:form>
</tiles:put>
</tiles:insert>
