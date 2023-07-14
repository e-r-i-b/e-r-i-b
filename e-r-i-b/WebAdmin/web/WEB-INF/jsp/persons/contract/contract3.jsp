<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insert definition="personsContract3">
<tiles:put name="data" type="string">
    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <table cellpadding="0" cellspacing="0" width="172mm" style="font-size:8pt;font-family:Arial;margin-left:15mm;margin-right:10mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
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
        <td align="center"><b>�������������� ����������</b></td>
    </tr>
    <tr>
        <td align="center">� �������� � ����� &quot;<input type="Text" readonly="true" class="insertInput" style="width:30%">&quot;</td>
    </tr>
    <tr>
        <td align="center">�� &quot;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&quot;
        <input id='monthStr31' value=''  type="Text" readonly="true" class="insertInput" style="width:15%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" readonly="true" class="insertInput" style="width:4%">
        ���� �<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" readonly="true" class="insertInput" style="width:35%">
        </td>
        <script>
            document.getElementById('monthStr31').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
        </script>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="25%"><nobr>�.<input type="Text" class="insertInput" style="width: 92%"></nobr></td>
                <td width="43%">&nbsp;</td>
                <td width="7%"><nobr>&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td width="25%"><nobr><input id='monthStr32' value='' type="Text" class="insertInput" style="width: 52%">20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 16%">����</nobr></td>
            </tr>
                <script>
                    document.getElementById('monthStr32').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
                </script>
            </table>
        </td>
    </tr>

    <!-- �������� ����� �������� -->
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <span class="tabSpan">&nbsp;</span>�������� ����������� �������� ��������� ������, ��������� � ���������� "����", � ����� �������, �
            </td>
        </tr>
        <tr>
            <td><input value='<bean:write name='person' property="surName"/> <bean:write name='person' property="firstName"/> <bean:write name='person' property="patrName"/>' type="Text" class="insertInput" style="width: 99%">,</td>
        </tr>
        <tr>
            <td>
                ��������� � ���������� "������", � ������ �������, ��������� ��������� "�������", ��������� ��������� �������������� ���������� � �������������:
            </td>
        </tr>
        <tr>
            <td>
                1.&nbsp;&nbsp;��������� ������ "������� �������� � ������� �����" �������� ��
                <input value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 16%"> �<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 17%"> ������� ���������� ����������:
            </td>
        </tr>
        <tr>
            <td>
                <span class="tabSpan">&nbsp;</span>&ldquo;�������� �������� �� ����� � ������� �� ������������� � �������������� ������� &ldquo;����������� ���������&rdquo; ��������������� ��������� � &ldquo;�������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; �<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 15%"> �� <input  value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 19%">.
            </td>
        </tr>
        <tr>
            <td>
                <span class="tabSpan">&nbsp;</span>� ������������ � �������� 3.13 � 4.3.2  (3.10 � 4.3.1.)  "�������� � �������������� ����� � �������������� ������� "����������� ���������" �<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 15%"> ��  <input  value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 14%"> ���� ������ ��������� � ������������ ������� ��� �������������� ������������ ������� �� ����� �
            </td>
        </tr>
        <tr>
            <td>
                <input type="Text" class="insertInput" style="width: 42%"> ����� �� ������ �����, ��������������� &ldquo;��������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;
            </td>
        </tr>
        <tr>
            <td>
                �<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width:15%"> ��  <input  value='<bean:write name="agreementDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 18%">, � ������������ � �������� �����, � ����� �� ������������� ������ � �� ��������� �������� ��� ���������� � ����� ����������� ������� ����� ��-�� ��������������� ������� �� �����&rdquo;.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td valign="top" width="4%">
                2.
            </td>
            <td>
                ��������� �������������� ���������� �������� � ���� � ������� ��� ���������� ������ ���������.
            </td>
        </tr>
        <tr>
            <td valign="top">
                3.
            </td>
            <td>
                ��������� �������������� ���������� ����� ���� ����������� �� ���������� ����� �� ������, ������� ������ ��������� ������������� �� ���� ������ ������� �� �����, ��� �� ���� ����� �� ���� ����������� ��������������� ����������. � ������� ����������� ���������� ��������������� ���������� �� ���������� ����� �� ������ ������������� ���������� ���� �������� "������� � �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;
            </td>
        </tr>
        </table>
            </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td valign="top" width="4%">
                &nbsp;&nbsp;&nbsp;
            </td>
            <td>
                � <input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput" style="width: 15%">, ����������� ��������� &ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 5%">&rdquo;
                <input id='monthStr33' value='' type="Text" class="insertInput" style="width: 21%">
                20<input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 5%">����.
            </td>
        </tr>
        <script>
           document.getElementById('monthStr33').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
        </script>
        <tr>
            <td valign="top">
                4.
            </td>
            <td>
                ��������� �������������� ���������� ���������� � ����, ������� ������ ����, �����������, ���� ��������� ��� �������, ���� &ndash; �����.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
            <br>
            <br>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table class="textDoc" style="width:100%;">
                <tr>
                    <td><nobr>�� ����� �����<nobr></td>
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
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
      <td> <br>&nbsp;<br><b>������: </b>  </td>
    </tr>
    <tr>
        <td><input value='<bean:write name='person' property="surName"/> <bean:write name='person' property="firstName"/> <bean:write name='person' property="patrName"/>'type="Text" class="insertInput" style="width: 100%"></td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><nobr>�����&nbsp;�����������</nobr></td>
            <td width="100%"><nobr><input value='<bean:write name='person' property="registrationAddressString"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><nobr>�����&nbsp;������������&nbsp;����������</nobr></td>
            <td width="100%"><nobr><input value='<bean:write name='person' property="residenceAddressString"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="14%">���. (���.)</td>
            <td width="16%"><nobr><input value='<bean:write name='person' property="homePhone"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
            <td width="14%">���. (���.) </td>
            <td width="16%"><nobr><input value='<bean:write name='person' property="jobPhone"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
            <td width="14%">���. (���.)</td>
            <td width="26%"><nobr><input value='<bean:write name='person' property="mobilePhone"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="20%">���� ��������</td>
            <td width="16%"><nobr><input value='<bean:write name="person" property="birthDay" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
            <td width="12%">E-mail</td>
            <td width="52%"><nobr><input value='<bean:write name='person' property="email"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <c:if test="${(person.identityType == 'REGULAR_PASSPORT_RF')}">
                <td width="42%"><nobr><input value="��������������� ������� ��" type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <c:if test="${(person.identityType == 'MILITARY_IDCARD')}">
                <td width="42%"><nobr><input value="������������� �������� ���������������" type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <c:if test="${(person.identityType == 'SEAMEN_PASSPORT')}">
                <td width="42%"><nobr><input value="������� ������" type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <c:if test="${(person.identityType == 'RESIDEN��_PERMIT_RF')}">
                <td width="42%"><nobr><input value="��� �� ���������� ��" type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <c:if test="${(person.identityType == 'FOREIGN_PASSPORT_RF')}">
                <td width="42%"><nobr><input value="����������� ������� ��" type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <c:if test="${(person.identityType == 'OTHER')}">
                <td width="42%"><nobr><input value='<bean:write name='person' property="identityTypeName"/>' type="Text" class="insertInput" style="width: 100%"></nobr></td>
            </c:if>
            <td width="28%">���, ���, ����� �����</td>
            <td width="30%"><nobr><input  type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" style="font-size:8pt;">(���, ���������, ��������������� �������� ) (�����, ����� - ��� �������)</td>
    </tr>
    <tr>
        <td><input value='<bean:write name='person' property="passportIssueBy"/> <bean:write name='person' property="passportIssueDate" format="dd.MM.yyyy"/>' type="Text" class="insertInput" style="width: 100%"></td>
    </tr>
    <tr>
      <td> <br>&nbsp;<br><b>����: </b>  </td>
    </tr>
    <tr>
        <td><input type="Text" class="insertInput" style="width: 100%"></td>
    </tr>
    <tr>
        <td>����� ����������:</td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><nobr>��������&nbsp;�����</nobr></td>
            <td width="100%"><nobr><input type="Text" class="insertInput" style="width: 100%"></nobr></td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="12%">�������</td>
            <td width="18%"><nobr><input type="Text" class="insertInput" style="width: 100%"></nobr></td>
            <td width="11%">&nbsp;����</td>
            <td width="19%"><nobr><input type="Text" class="insertInput" style="width: 100%"></nobr></td>
            <td width="40%">&nbsp;</td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
          &nbsp;<br>
          &nbsp;<br>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="25%">�� ����� �����:</td>
            <td width="25%">&nbsp;</td>
            <td width="25%">������:</td>
            <td width="25%">&nbsp;</td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
          &nbsp;<br>
          &nbsp;<br>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="50%"><nobr><input type="Text" class="insertInput" style="width: 80%"></nobr></td>
            <td width="50%"><nobr><input type="Text" class="insertInput" style="width: 48%"></nobr>(<nobr><input type="Text" class="insertInput" style="width: 48%"></nobr>)</td>
        </tr>
        <tr>
            <td width="50%"><nobr><input type="Text" class="insertInput" style="width: 80%"></nobr></td>
        </tr>
        <tr>
            <td width="50%"><nobr><input type="Text" class="insertInput" style="width: 48%"></nobr>(<nobr><input type="Text" class="insertInput" style="width: 48%"></nobr>)</td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
          &nbsp;<br>
          &nbsp;<br>�.�.
        </td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            &nbsp;
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
                    <td colspan="4" class="font10" align="center">�������������� ���������� �<input type="Text" readonly="true" class="insertInput" style="width:10%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:15%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
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
