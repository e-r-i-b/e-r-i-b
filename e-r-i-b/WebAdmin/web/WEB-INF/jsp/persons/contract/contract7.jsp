<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:insert definition="personsContract7">
<tiles:put name="data" type="string">
    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
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
    <tr>
        <td align="center"><bean:message bundle="commonBundle" key="text.act.software.reception.transfer"/><input value='<bean:write name='person' property="agreementNumber"/>' type="Text" readonly="true" class="insertInput" style="width:21%">
                �� &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:5%">&rdquo;
                <input id='monthStr71' value='' type="Text" readonly="true" class="insertInput" style="width:15%"> 20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">�.

       </b></td>
    </tr>
    <script>
        document.getElementById('monthStr71').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td>
            <br><br>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                    <td align="right">
                        &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:5%">&rdquo;
                        <input id='monthStr72' value=''  type="Text" readonly="true" class="insertInput" style="width:15%"> 20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">�.
                    </td>
                </tr>
                <script>
                    document.getElementById('monthStr72').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
                </script>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                    <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������� ��� ��������� � ���, ��� �������� ����������� �������� ��������� ������, ��������� � ���������� ���� � ���� <input value="${employee.surName} ${employee.firstName} ${employee.patrName}" type="Text" readonly="true" class="insertInput" style="width:85%">,
                    </td>
                </tr>
                <tr>
                    <td>
                    ������������(��) �� ��������� ������������ �������, �
                    </td>
                </tr>
                <tr>
                    <td>
                    <input value='<bean:write name='person' property="surName"/> <bean:write name='person' property="firstName"/> <bean:write name='person' property="patrName"/>' type="Text" readonly="true" class="insertInput" style="width:85%">,
                        �������
                        <c:choose>
                            <c:when test="${(person.gender == 'M')}">
                               <input value="��" type="Text" readonly="true" class="insertInput" style="width:3%">
                            </c:when>
                            <c:otherwise>
                                <input value="��" type="Text" readonly="true" class="insertInput" style="width:3%">
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>
                        � ���������� ������, ������ ���������:
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                    <td>
                         ���-������� � ������� ��� ������� ������ � �������.
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br><br><br><br><br><br>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td width="50%" align="left"><input type="Text" readonly="true" class="insertInput" style="width:95%"></td>
                <td width="50%" align="left"><input type="Text" readonly="true" class="insertInput" style="width:95%"></td>
            </tr>
            <tr>
                <td align="left">������</td>
                <td align="left">�� �����</td>
            </tr>
            <tr>
                <td align="left">&nbsp;</td>
                <td align="left">�.�.</td>
            </tr>
            </table>
        </td>
    </tr>
    </table>
    </td>
    </tr>
    </table>

    </html:form>
</tiles:put>
</tiles:insert>
