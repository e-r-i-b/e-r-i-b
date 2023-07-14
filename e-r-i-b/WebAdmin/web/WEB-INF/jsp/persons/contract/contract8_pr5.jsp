<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="personsContract8_pr5">
<tiles:put name="data" type="string">
    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>

    <table cellpadding="0" cellspacing="0" width="268mm" style="margin-left:10mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:268mm">
    <tr>
    <td  valign="top">

    <table style="width:268mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc font11">

    <%@include file="footer.jsp"%>

	<tbody>

    <!-- Шапка документа -->
    <tr>
        <td align="right">
            <b>Приложение 5</b><br><br>&nbsp;
            <span class="font11">к Договору о предоставлении<br>
               услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo;<br>
            </span>
            №<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" readonly="true" class="insertInput font11" style="width:21%">от&nbsp;&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
             <input id='monthStr51' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
        </td>
    </tr>
    <script>
        document.getElementById('monthStr51').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td>
            <br>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
        </td>
    </tr>
    <tr>
        <td>
            <table cellspacing="0" class="textDoc docTableBorder font12" width="100%" style="border:2px solid #000000; border-bottom:0px">
            <tr>
                <td rowspan="2" class="docTdBorder" align="center" valign="top" style="border-bottom:2px solid #000000;width:12mm;"><b>№ п/п</b></td>
                <td class="docTdBorder" align="center" valign="top" width="19%"><b>Условное обозначение получателя</b></td>
                <td class="docTdBorder" align="center" valign="top" width="19%"><b>ИНН</b></td>
                <td class="docTdBorder" align="center" valign="top" width="19%"><b>Номер счета получателя</b></td>
                <td class="docTdBorder" align="center" valign="top" width="19%"><b>БИК</b></td>
                <td class="docTdBorder" align="center" valign="top" width="19%"><b>Корсчет банка получателя</b></td>
            </tr>
	        <tr>
		        <td class="docTdBorder" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;"><b>Идентификатор плательщика</b></td>
		        <td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;"><b>Наименование получателя</b></td>
		        <td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;"><b>Наименование банка получателя</b></td>
	        </tr>
            <c:set var="lineNumber" value= "0"/>
            <logic:iterate id="entry" name="form" property="receivers">
	            <c:if test="${entry.kind == 'P'}">
					<c:set var="lineNumber" value="${lineNumber+1}"/>
					<tr>
						<td rowspan="2" class="docTdBorder" align="center" style="border-bottom:2px solid #000000;">&nbsp;<c:out value="${lineNumber}"/></td>
						<td class="docTdBorder" align="center">&nbsp;${entry.alias}</td>
						<td class="docTdBorder" align="center">&nbsp;</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.account}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.bankCode}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.correspondentAccount}</td>
					</tr>
		            <tr style="border-bottom:2px solid #000000;">
						<td class="docTdBorder" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${person.login.userId}</td>
						<td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${entry.name}</td>
						<td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${entry.bankName}</td>
					</tr>
	            </c:if>
            </logic:iterate>
            <logic:iterate id="entry" name="form" property="receivers">
	            <c:if test="${entry.kind == 'J'}">
                    <c:set var="lineNumber" value="${lineNumber+1}"/>
					<tr>
						<td rowspan="2" class="docTdBorder" align="center" style="border-bottom:2px solid #000000;">&nbsp;<c:out value="${lineNumber}"/></td>
						<td class="docTdBorder" align="center">&nbsp;${entry.alias}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.INN}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.account}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.bankCode}</td>
						<td class="docTdBorder" align="center">&nbsp;${entry.correspondentAccount}</td>
					</tr>
		            <tr style="border-bottom:2px solid #000000;">
						<td class="docTdBorder" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${person.login.userId}</td>
						<td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${entry.name}</td>
						<td class="docTdBorder font11" align="center" valign="top" colspan="2" style="border-bottom:2px solid #000000;">&nbsp;${entry.bankName}</td>
					</tr>
	            </c:if>
            </logic:iterate>
	            <c:if test="${not empty PrintPersonForm.gorodCardNumber}">
		            <c:set var="lineNumber" value="${lineNumber+1}"/>
		             <tr>
			              <td class="docTdBorder" align="center" style="border-bottom:2px solid #000000;">&nbsp;<c:out value="${lineNumber}"/></td>
			             <td colspan="6" class="docTdBorder" align="center" style="border-bottom:2px solid #000000;">
				             Все получатели, привязанные  к карте №&nbsp;<c:out value="${PrintPersonForm.gorodCardNumber}"/>&nbsp;ИСППН «Город».
			             </td>
		             </tr>
	             </c:if>
            </table>
        </td>
    </tr>
    <tr>
        <td><br><br>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td width="50%"><b>от Банка:</b></td>
                <td><b>Клиент:</b></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
                    <tr>
                        <td width="20%" style="padding-left:20mm"><input type="Text" class="insertInput font11" style="width: 100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" class="insertInput font11" style="width: 96%; border:0px">)</nobr></td>
                        <td width="25%" style="padding-left:30mm;"><input type="Text" class="insertInput font11" style="width: 100%"></td>
                        <td width="25%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" class="insertInput font11" style="width: 95%; border:0px">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td  colspan="2" style="padding-left:110mm"><b>М.П.</b></td>
            </tr>
            </table>
        </td>
    </tr>
    </tbody>
    </table>


    </td>
    </tr>
    </table>

    </html:form>
</tiles:put>
</tiles:insert>
