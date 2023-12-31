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
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <c:set var="document" value="${form.activeDocument}"/>


    <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
    <td>
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">

    <%@include file="footer_dop.jsp"%>

    <tbody>

    <!-- ������������ ��� ������ -->
    <tr>
        <td align="right">��� 014180001/2</td>
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
    <c:set var="current" value="${fn:startsWith(accountLink.account.number, '40817') or fn:startsWith(accountLink.account.number, '40820')}"/>
    <c:if test="${not current}">
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
                            <c:set var="accountDescription"
                                value="${(accountLink.account.fullDescription != null && accountLink.account.fullDescription != '') ? accountLink.account.fullDescription : accountLink.description}"/>
                            <nobr>&ldquo;${accountDescription}&rdquo;</nobr>
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
                        <td width="1%">
                            <script type="text/javascript">
                                var year=<bean:write name="openDate" format="yyyy"/>;
                                year=year.toString().substring(0,2);
								document.write(year);
                            </script>
                        </td>
                        <td width="6%">
                            <nobr><input value='<bean:write name="openDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:97%;text-align:left;"></nobr>
                        </td>
                        <td>����&nbsp;�</td>
                        <td width="65%">
                            <input value="${accountLink.number}"  type="Text" readonly="true" class="insertInput" style="width:98%">
                        </td>
                    </tr>
                    </table>
                    <script type="text/javascript">document.getElementById('monthStr0<%=lineNumber%>').value = monthToStringOnly('<bean:write name="openDate" format="dd.MM.yyyy"/>');</script>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </c:if>
    </logic:iterate>
    <tr>
        <td>
	        <% if (lineNumber < 5) {%><br><%}%>
            <table cellpadding="0" cellspacing="0" width="100%" class="textDoc">
            <tr>
                <td width="70%"><nobr>�. <input value='${department.city}' type="Text" class="insertInput" style="width: 50%"></nobr></td>
                <td>&nbsp;</td>
                <td><nobr>&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td><input id='monthStr103' value="" type="Text" class="insertInput" style="width: 100px"></td>
	            <td>20</td>
	            <td><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 20px">����</td>
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
            1.&nbsp;&nbsp;��������� ������ &ldquo;������ �������&rdquo;
        </td>
    </tr>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="current" value="${fn:startsWith(accountLink.account.number, '40817') or fn:startsWith(accountLink.account.number, '40820')}"/>
    <c:if test="${not current}">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr class="textDoc">
                <td>
                    <nobr>�������� � ������</nobr>
                </td>
                <td width="100%" class="tdUnderlinedItalic">
                    <c:set var="accountDescription"
                        value="${(accountLink.account.fullDescription != null && accountLink.account.fullDescription != '') ? accountLink.account.fullDescription : accountLink.description}"/>
                    <nobr>&ldquo;${accountDescription}&rdquo;</nobr>
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
                    <nobr>&ldquo;<input value="${accountLink.number}" type="Text" readonly="true" class="insertInput" style="width:97%">&rdquo;</nobr>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </c:if>
    </logic:iterate>
    <tr>
        <td>������� <i>(��������)</i> ���������� ����������:</td>
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
            � ������������ � ����������  � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">  � ��������� ���� ������ ��������� � ������������ ������� ��� �������������� ������������ ���������
        </td>
    </tr>
    <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
    <c:set var="current" value="${fn:startsWith(accountLink.number, '40817') or fn:startsWith(accountLink.number, '40820')}"/>
    <c:if test="${not current and (accountLink.paymentAbility == 'true')}">
    <c:set var="openDate" value="${accountLink.account.openDate.time}"/>
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
    </c:if>        
    </logic:iterate>
    <tr>
        <td>����� �� ������ �����, ��������������� ����������  � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 21%"> �� <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 13%">, � ������������ � �������� �����.</td>
    </tr>
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
                    <bean:message bundle="commonBundle" key="text.additional.agreement.stop"/>&nbsp;<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 22%"> ��
	                <c:choose>
                        <c:when test="${empty agreementDate}">
                            &ldquo;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%">20&nbsp;&nbsp;&nbsp;����.
                        </c:when>
                        <c:otherwise>
                            &ldquo;<bean:write name='agreementDate' format="dd"/>&rdquo;<input id='monthStr101' value='' type="Text" class="insertInput" style="width: 17%"> 20<bean:write name='agreementDate' format="y"/>����
                        </c:otherwise>
                    </c:choose>
                    � �������.
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
    <tr>
        <td><b><br>��������:</b></td>
    </tr>
    <tr>
        <td><input value='${person.fullName}' type="Text" class="insertInput" style="text-align:center;width:100%;"></td>
    </tr>               
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
                        <td width="23%"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td width="27%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 96%">)</nobr></td>
                        <td width="23%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width: 100%"></td>
                        <td width="27%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width: 94%">)</nobr></td>
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
