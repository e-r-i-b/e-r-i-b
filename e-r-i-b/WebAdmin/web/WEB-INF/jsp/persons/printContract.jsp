<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insert definition="printContract">
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <body onLoad="showMessage();" Language="JavaScript">
    <html:form action="/persons/print">
      <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
      <!--------------------------------- ���������� �8 ----------------------------------------->


     <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
    <td>
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <!-- ������������ ��� ������ -->
    <tr>
        <td height="20mm">&nbsp;</td>
    </tr>

    <!-- ������� �����-->
    <tr>
        <td><%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %></td>
    </tr>

    <tr>
        <td align="center"><b>������� � <input type="Text" readonly="true" class="insertInput" style="width:10%"></b></td>
    </tr>
    <tr>
        <td align="center"><b>� �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;</b><br>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="25%"><nobr>�. <input value="������" type="Text" class="insertInput" style="width: 92%"></nobr></td>
                <td width="43%">&nbsp;</td>
                <td width="7%"><nobr>&ldquo;<input type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td width="25%"><nobr><input type="Text" class="insertInput" style="width: 67%">200<input type="Text" class="insertInput" style="width: 12%">�.</nobr></td>
            </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������� ����������� �������� ��������� ������, ��������� � ���������� "����", � ����
            <input type="Text" readonly="true" class="insertInput" style="width:30%">
        </td>
    </tr>
    <tr>
        <td><input type="Text" readonly="true" class="insertInput" style="width:82%">, ������������</td>
    </tr>
    <tr>
        <td>�� ���������<input type="Text" readonly="true" class="insertInput" style="width:65%">, � ����� �������</td>
    </tr>
    <tr>
        <td>�<input value='${person.fullName}' type="Text" readonly="true" class="insertInput" style="width:98%"></td>
    </tr>
    <tr>
        <td><input type="Text" readonly="true" class="insertInput" style="width:99%">,</td>
    </tr>
    <tr>
        <td>���������(��) � ���������� "������", � ������ �������, ��������� ��������� "�������", ��������� ��������� ������� � �������������.</td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center" class="titleMargin">
                    <span class="titleDoc"><br>1.&nbsp;&nbsp;����� ���������</span>
                </td>
            </tr>
            <tr>
                <td class="font9">&nbsp;</td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.����������� �������, ����������� � ������ ���������� ��������, ������������ � ��������� ��������:
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.1.&quot;����������� ��������&quot; &ndash; ����������� ����� ��������� (���������� ��� �����), �������������� � �������, ������������ ������������ ���������� �������� ���������.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;����������� ��������� ��������&quot; &ndash; ����������� ��������, ���������� ���������� ��� ���������� ������ �������� �� ����� �������.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;����������� ��������� ��������&quot; &ndash; ����������� ��������, �������������� ����� ����������� ����� ���������, �� ��������� � ����������� �������� �� ����� �������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.2.&ldquo;������������������ ������� &ldquo;����������� ���������&rdquo; (����� &ldquo;�������&rdquo;) &ndash; ������������������ ������������� �������������� ������� &ldquo;����������� ���������&rdquo;, �������������� ������ ������� &ldquo;������-����&rdquo;. ������������� ��� �������������� �������� ���������� ��� ������ �� ������ �������, ��������� � ��������� ������, ����� ���������� ���� ��������. ��������� ������������ ����������� �����, �������������� � �������� ��������� � �������������� ���������� � ������������������ ���������� ������� ��������� ������, � ����� ����������� ��������� �� ���� ������������������ ���������� ������ �������� ���������� �� �������������� ������ ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.3.&ldquo;���� �������&rdquo; &ndash; ���������� ���� �� ������ ���������-����������� ���� � �����������-����������� ���� � ������ ���������� ��������� ��� ����������� ������, ������� ���� ����������� ����-��������� � ������ ���������� ���������, ���� ����������� ����-����������� � ������ ���������� ���������, ���� ���������� ����� ����������� ���� � ������ ���������� ��������� ��� ����������� ������, ����������������� ���������� ��������� ��������, �� ��������� � �������������� ������������������� ������������. �������� ������ ����������� �������� � ���������� 1 �  ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.4.&ldquo;������������� �������&rdquo; &ndash; ���������� ����, �������������� �������� ������������� ��������� ����������, ������������ �� ��� �����(��), ����������� �� ����� ������� � �������� ����������, ������������ � ������������, ������������ � ������������ � ������������ ������������ ���������������� � ��������� ���������� ��������. ��� ���������� �������� � ������� ������������� ������� �������� ���������
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
     <br style="page-break-after:always;">

     <!---------------------------------- ��������2----------------------------------------->


    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
           ����������� � ������� ����� ��, ��� ������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.5.&quot;���-�������&quot; &ndash; ���������������� ����������� (�������) ����������� ����� ��� ����� ������ ������������� ������� � ���� ������ �������������� �������, ���������� ��������� ��������������� ����������; ������������ ��� ������������ ������� ���������������� ������� ������������ ���������.
        </td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.6.&quot;����� �� ����������� ���������� ������������ � �������������� ������� &ldquo;����������� ���������&rdquo;&quot; &ndash; �������������� �����, ��������� ������ � ������������ � ��������� ���������� �������� � �������� ����� � ���� ���������� �������� � ������ ���������� �������� � ���� ������-��������, � ������� ���������� ���� ��������� �������� �� ����� ���-�������� � ������� ��� ������� � �������. ������ ����� � ����� �� ������� ��������� ����� � ���� ���������� �������������� ������� � ������ ��� ������-��������, � ������� ���������� ���� ��������� �������������� ������� �� ����� ���-�������� � ������� ��� ������� � ������� � ������������ � �������� �����.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.7.&quot;����������� ����� �� �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;&quot; &ndash; ����������� �����, ��������� ������ � ������������ � ��������� ���������� �������� � �������� �����, �� ��������� ������� ������, ������������ � ���� ���������� ��������� ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.8.&quot;����� �� �������������� ����������� ������� � ������� &quot;����������� ���������&quot; � ������ ������������� ������  �������� (�������������� �������)&quot; &ndash;  ��������� ������ ������������� � ���� ������ ������� (������������� �������) ������ ���-�������� � ������� ��� ������� � ������� &quot;����������� ���������&quot; � ������������ � �������� ����� � ������ ��� ������������� �������� (�������������� �������).
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">

            <tr>
                <td align="center">
                    <span class="titleDoc"><br>2.&nbsp;&nbsp;������� ��������</span>
                </td>
            </tr>
            <tr>
                <td class="font9">&nbsp;</td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1.���� �� ������� ������ � � ������ ������� ������������ ����� �������, ��������������� ����������� ����� ������ � �������� ��������� ����������� ������ (�����), ������������� ������� ������ � �������������� �������, ��������� � ���������� 3 � ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2.������ ����� ������������ ������������� ������������� ��������� ����������, ������������ �� ��� ������, ��������� � ���������� 1 � ���������� ��������. ��� ����, ������������� ��������� �� ����� ������� � �������� ����������, ������������ � ������������, ������������ � ������������ � ������������ ������������ ���������������� �  ��������� ���������� ��������, � ����� ����� �� ���������� � ������� ��������, �������� ������� �������� � ���������� 2 � ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3.��� ��������������� ��������� ��������� �������� ����������� � ������� ��� ������� ���������� �� � ������ ������������ � ������������ � ������������ ������������ ���������������� � ��������� ���������� ��������, ��� ������� �� ����� ������� �������� ������� � ������, ����������� ��� ���������� �������� � �������� �� �� ������ �����.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.4. ����������� ��������� ���������� � ����������� � �������������� ������� ��� �� ������������ ������������� �� �������� ��������, ����� �������, ����������� � �.3.2 ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.5.������ (������������� �������) ����� ������������ ����� Web-���� ����� �� ������������� ����������-���������� �������, ����������� ��� ������ � ��������.
                </td>
            </tr>
            </table>
            <table class="textDoc" cellpadding="0" cellspacing="0">

            <tr>
                <td>
                    <table class="textDoc" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="font9">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="center">
                            <span class="titleDoc">3.&nbsp;&nbsp;������� �������������� � ������ �����</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="font9">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.������� ������� ��:<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;����������� � ���������� �������, ��������������� ����������� �����, ������������� � ��������������� �������� �� ���� ����;<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;���������� ������� �����, ��������������� ������ �� ���� ����;<br>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������3----------------------------------------->

    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;����������� ������� �������, ��������������� � ��������������� ������ �� ���������� ��������� ������� � ����� � �������������� ��������������� ���������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2.��� ������ �� ����� ���������� ��� ����������� ������� ������� ��� �� ���������, � ����� � ���� ������� ������������� �������������� ����� �� ��������, ���� � ������� ����� ��������� �� WEB-����� ����� ��������� � ���������� � ������ ������������� �������������� �����. �� ������ ��������������� ������������� ������� ���������� �������� (�������������� �������) �������� �� ������ ������� �������������� �� �������� ��������� ��� � �������������� ����, ��������������� ��������� ����������� ������ (�����) ������ � ������� �����.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.3.� �������� ������������� ������������ ����������� ���� ������������ ���������� ������������ ���������� ���������, ������ (������������� �������) ����������� ���� ������ ������� � �������. �������� ���������������� ������� ������������ ���������� ��������� ������� (������������� �������) ��������  �������� ���-�������, ����������� �� ���� ������������ ���������� ������������ ���������� ��������� � ������ ������� (������������� �������).
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.4.������� ��������, ��� ������������ ��������� � ������������ � ��������� ��������� ������� ������ ����������, ����������� �� ������������� ����������� ���������� ������������ ��������-����������, ���������, �� �� ��������� �����, ��������� � ���������� ������������������ � ����������� ���������� ��� �� �������� ����� ���� ��������, ������ ���������� ��� ����������� ������������ ������� ������� ������ �� ����� ������� ��� ����� ����������� ��������, ���������� �������� � ��������, ����������� ��������, ��������� � ���������� 5 � ���������� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.5.������� ���������� ������� ������� ���������������, ���:<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash; ��� ������������� ��� ���������� �� ������������� ������ ������� (������������� �������) (�� ���� ��� ������������ ��� ���������� �� ������������ ����������������� ���� � �������, � ����� ��� ������������������� ������������� ��� ���������� �� ������������������� ������������� ������) ���� ���������� �������� (�������������� �������) ��������� ������������ ���������� ��������� �� ������� ��� ���� ��������� �������� � ����������� �������� ������� ������.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash; ����, ������� �� ������� ��������� � ������������� ������, ��������� �������������� ������ ��� ������� (������������� �������). ��� ������������� �������������� ������ ������ (������������� �������) ������ ������� � ���� ��� ������ ��������� � ������������� �������������� ������ � ��������� ���-�������� � ����� ������� ��� ������� � �������.
                </td>
            </tr>
            <tr>
                <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ (������������� �������) �������������� �������� ����������� - ����������, �������������� ������ � ���� ��������, � ������������ ����������� � ���� �������� �� ���� ����������� �������. ��� �������, ��������� � ������������ � ���� ��������, ������������� � ������� ������� �� ������� ����� ���� �������� �������������� �������� �� ���� ����������� �������.<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ ��������� ����� ��� �����, ��������� � ������������ ��� �������������� ������� � �������������� ������� ������������� ������� � ���� ��������. ������ (������������� �������) �������������� ������������ ������ ����������� �������������� ������� �� �������������������� ������� � �������� ���� �� ���� ��������.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.������� �������� � �������� ������ ����� ������� ��� ������ � ������� <input type="Text" class="insertInput" style="width: 22%"> �����. ����������� �������� ����� ��������� ����� ���������� ������� �����.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.������� ����������, ��� �������� ��������� ������������ ��������� ������ � ������� �������� ������� ����� �� ��������� ����� ����� � ������ ��������� ���������� � ����� �������� ���������.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.���� ��������� ����������� �� ������� (������������� �������) � ��������
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������4----------------------------------------->

    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    ������������� ������� ����� ����������� ��������� ���������, �� ���������� �������� �� ������ �������, ��������� � ���������� 1 � ���������� ��������, ������� ������� ����, � ����������� � ����������������� ����� &ndash; ��������� ������� ����.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� ���� ���������� ���������� �������� ������ ����������� ��������� ������������ �����: � ������� ��� &ndash; � <input type="Text" class="insertInput" style="width: 5%"> �. <input type="Text" class="insertInput" style="width: 5%"> ���. �� <input type="Text" class="insertInput" style="width: 5%"> �. <input type="Text" class="insertInput" style="width: 5%"> ���., � ������������ � ��������������� ��� &ndash; � <input type="Text" class="insertInput" style="width: 5%"> �. <input type="Text" class="insertInput" style="width: 5%"> ���. �� <input type="Text" class="insertInput" style="width: 5%"> �. <input type="Text" class="insertInput" style="width: 5%"> ���.
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
        <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.9.����� ������������ ����������� ����� �������� (�������������� �������) � ������ �������� ������������� - ������ (������������� �������) ���������� � ���� �� ���������� ����������� ��������� ��������� �� ������ �������. ��������������� � ��� �� ������ ����� ������� (������������� �������) ���������� � ���������� ������ ��������� � ������, � ��������� ���� ��� ���������� ������ (&quot;������� ������� ����&quot; ��� &quot;�� ��������� ������� ����&quot;). ������ ��������� ����� �������������� �������� � �� �������� ������������ �����������. �������� �� ����������� ������ ����������� ��������� ���������� �� ������ ������� �������������� �������� (�������������� �������) �������������� ����� ��������� � ������� ��������� ���� ������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.10.�� ������ ����� �� ���������� �������� ������ ��������� � ������� � ������������ ������� � ������ ���������� ��������� ����� �������� ������������ ������� �� ����� �������, ��������� � ������ ���������� ���������, � <input type="Text" class="insertInput" style="width: 25%">  ��������� �����:
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <ul type="disc">
                <li>����� �� ����������� ���������� ������������ � �������������� ������� &ldquo;����������� ���������&rdquo; &ndash;  ��������� ������ � ������������ � �������� ����� ������������� � ���� ���������� �������� � ������ ���������� �������� � ���� ������-��������, � ������� ���������� ���� ��������� �������� �� ����� ���-�������� � ������� ��� ������� � �������. ������ ����� � ����� �� ������� ��������� ����� � ���� ���������� �������������� ������� � ������ ��� ������-��������, � ������� ���������� ���� ��������� �������������� ������� �� ����� ���-�������� � ������� ��� ������� � ������� � ������������ � �������� �����;
                <li>����������� ����� �� �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo; &ndash; ��������� ������ ���������� �� ��������� ������� ������,������������ � ���� ���������� ��������� ���������� �������� � ������������ � �������� �����;
                <li>����� �� �������������� ������ ��� ������� � ������� &quot;����������� ���������&quot; � ������ ��� ������������� �������� (�������������� �������) &ndash; ��������� ������ ������������� � ���� ������ ������� (������������� �������) ������ ���-�������� � ������� ��� ������� � ������� &quot;����������� ���������&quot; � ������ ��� ������������� �������� (�������������� �������) � ������������ � �������� �����.
        </ul>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.11.��� ���������� � ������� �������� �� ������ �������, � �������� ������� ����� �� ������, �������� ������� �������� � ���������� 3 ���������� ��������, ����������� ��������� ������ �����, ������������� ��� �������� ���� ������ ��������, ����������� ����������� �������� ��� ������ ���� � ����.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.12.� ������ ���������� �� ����� �������, ���������� �� ��� �������� � ������������ ������� ����� �� ������ �����, �������� ������� � �������, ����������� ��� �� ��������, ���� ����� ����� ������� �� � ������� ����� �������, ��������� � ������ ���������� ���������, ���������� � ������� ������� ������ ������� (���������� 1 � ���������� ��������).
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.13.��� ���������� �� ������ �������, ��������� �� � ������� ������� ������ ������� (���������� 1 � ��������), �������� �������, � �������, ����������� ��� �������� ������ ����������� ����� �� �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;, �������������� ������� (������������� �������) ����� � ������� ������������������.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������5----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��� ���� ���������� ����������� ����� �� �������������� ����� � �������������� ������� �������������� ���������� �� ���������� ����� � ������������ ������ ����� ����������� ��������.
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center">
                <span class="titleDoc"><br>4.&nbsp;&nbsp;����� � ����������� ������</span>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.1.���� ���������:</b>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.1.������������ ������� � ��� �������������� �� ����� �������� ���������� �������� ���-�������� � ������� ��� ������� � �������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.2.���������� ����������� ������� (������������� �������) � �������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.3.��������� � ���������� ����������� �� ������� (������������� �������) ����������� ��������� ���������, ����������� � ���������� � ���� � ������������ � ��������� ���������� ��������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.4.�� ��������� ����������� �� ������� (������������� �������) ����������� ��������� ���������, ����������� � ���������� ���������� ������������ ���������������� ���������� ���������, ����������� ���������� ����� ������ � ������� ���������� �������� � ��������� � ����� (������) �������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.5.�� ���������� � �� ���������� ������� ����� (���������� ������������������) ����������, ��������� � �������������� ������� �������� (�������������� �������), �� ����������� �������, ��������������� ����������� ����������������� ���������� ��������� � ��������� ���������� ��������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.6.������������ �� ��������� � ����������� ������ ��������� ������� � ������� ���� ���������� � �������� ����������� ���������� �� ���������� ��������� ������� � ������� �� ����� �������� ����������� ���� ����� �� ���������, � � ������ ������������� ������ - �� �� ����������. ���������� ����������� ������� ���������� � �������� ������, � ����� ��������� �������� � ������� �����, �������������� ����������� �����������������.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.7.� ������ ���������� ����� ������ ����������� ���������� ���������� �� ������������� �������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.2.���� ����� �����:</b>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.1.� ������������� ������� �������� ����������� ��������� ������� � ������ ��������� �������� (�������������� �������)  ����� ������������, �������� �� ���������� ��������, �������� ������� �� �������, ��� �� _____________ ����������� ���� �� ���� �����������.<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����������� ���������� �������� �� ������ �� ����� ����������� ������������ ������� �� ���������� ����� ����������� ����� �� ����������� ���������� ������������ � �������������� ������� "����������� ���������", ��������� �� ���� ����������� ���������� ��������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.2.��������� � ������������ ������� �� ������ �������, ��������� � ���������� 1 � ���������� ��������, ����� ����� �� ������ �����.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.3.����������� ������� �������������� � ������� ����� � ���������� ��������, ��������������� ��������� ���������, � ������������ ������� ���������������� �����������, � ������ ��������������� ������� �� ����� ������� �� ������ �������� �����.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.4.��� ������������ ����������� ��������� ������������ �������, ��������� ������ ��� ��������� ����� ���������, ���������� ������������� ������������� ������� � ���������� ������� � ��� �������������� � �������, ��������������� � �. 3.2 ���������� ��������.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.5.� ������������� ������� ������� �����, �������� � ��������� ����������� ������ �� ������ �����, ������� � ����� �������� �����. ������ ���������� ����������� �� WEB-����� ����� � ������� "�������", �� ���� ���������� � ���� ��������������� ���������.<br>
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������6----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��� ���� ���������� ����������� ����� �� �������������� ����� � �������������� ������� �������������� ���������� �� ���������� ����� � ������������ ������ ����� ����������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� ���� ���������� ���������� �������� ������ ����������� ������ � ������������ � ����������� 4 � ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.6.������� ����������� ��� ��������� ���������� �������������� ������� � ��� �������������� ����� �� ���������� �������� � ������ ��������� ������� ������������, ���� �������������� �������� �����, � ����� ���� ��������� ��������� ������������ � �������������� ����������-���������� ������� ������� (������������� �������) �� ������ �� ����������� �������� ��������� ���������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.3.������ ���������:</b>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1.���������� ����� ����� �� �������������� ����� � ������������ � ��������, ������������ �� ������ �������������� �����. ������ ������������� ����� ����� ��������� ��� ������������ ������� � ������������ ������� � ��� ������ ������� � ����� � ������������ � ��������. ��� ���������� � ����� ����������� ��������� �������� ������� � �������� ����� �� �������� �� ����� ������������ � � ����������� � ����� � ���������������� �������� ������� �� �����, ������� ����������� ������� (������������� �������)  ��������������� ����������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.2.������������ � ����������� ���������� �������� ��������� � ������ ������������ �� �������������� ���������� � ��������� ����������� ������ �/��� ��������� ����������� �����, �� ������� ����� ��������������� ������ � ����������� ����� ����� �� ������ �����, ��������������� ��������� ���������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.3.��-�� ����������� ������������� ��� ������ � �������� ������ ������� (������������� �������), ������ ��������� �������������� ������������� ��������� � ����������������� ������������ �������� �� �������� �������������������� ������������ ������� � �� �����.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.4.������ ����� �����:</b>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.1.��������� � ������� ��������, � ����� �������� ����������� ������� ������, ��������������� ����������� 3 � ���������� ��������. ��� ���� ������ (������������� �������) ����� ����� ����������� �������� �������� �� ������ �������, �������� � ������ ���������� ��������� � ��������� � ���������� 1 � ���������� ��������, ������ � ����� ����������������� �������� ������� ����������� ��������, ���������� � ���������� 5 � ���������� ��������, ���� ����������� (����������) �������� �������� �� ������ �������, ��������� � ���������� 1 � ���������� ��������, �� �����, �������� �� ��� �������-��������� �����. ��� ������������ ������������ ���������� ��������� �� ����� ������� � ����� ���������� ������� ������ (������������� �������) ��������� ������ ����� � ����� ����� ��� ����� (������������) ���������� ������� � ������������ � �������� �����������, ��������� � ���������� 5 � ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.2.������ ������ �������� �������� ������������� � ������� ������ �������, ��������� � ���������� 1 � ���������� ��������, �������� ����� ��������������, ��������� � ���������� 2 � ���������� ��������, � ����� �������� ����������� ��������, ��������� � ���������� 5 � ���������� ��������, � ����� ������� �������� ����������� �������� �������� �� ����� ������, �������� � ������ ���������� ���������, ����� ���������� ��������������� ���������� � ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.3.� ������ ������������� � ������� ���������, ��������� � ��������� ��� ����������� �/��� ����������� ��� ������������� ������������ ���������, ��������� � ���� �������������� ��������� � �����������.
        </td>
    </tr>
    <tr>
        <td align="center">
            <span class="titleDoc">5.&nbsp;&nbsp;��������������� ������</span>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1.�� ������������ ��� ������������ ���������� ������������ �� ���������� �������� �������� ������� ����� ��������������� � ������������ � �����������
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������7----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            ����������������� ��.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.2.� ������ ������������� ������������� ������������� ����, � ������� ��������� ��������� ��������, ������, ������, �������� ����������, ����������, ���������, ������� ��������, �������������� �������� ������� ���, ���������� � ���� ��������������� �����, ����������������� ������������� � ������������ ��������������� �������, ����� ��� �������� ����������� ��� �������������� ������������� ��������� ����� ������� �� ��������, � ���� �������������, �� ��������� �� �������������� ������, ������� �� ���������� �������� ������������� �� ��������������� �� ������������ ��� ������������ ���������� ������ �� ���� ������������.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��� ����������� ������������� ������������� ���� ������� ������ ��� ����������� ��������� � ��� ������ ������� � �������, ��������������� �.3.2 ���������� ��������. ��������� ������ ��������� ������ � ��������� �������������, � ����� ������ �� ������� �� ����������� ���������� �������� ������������ �� ���������� ��������.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� ����������� ��������� ���� ������������� ������� ������ ��� ����������� ��������� �� ���� ������ ������� � �������, ��������������� �. 3.2 ���������� ��������. � ��������� ������ ���� ������ ����, � ������� �������� �������������� ��������� ������������� �� ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.3.� ������ �������� ����� ��������������� ����� (� ��� ����� �� ������� ���������� ����������� ����� ������� �� �����(��) ������� ��� ������������� �������� ������� � ����� � ���������������� ��������� �������� �� �����(��) ��� ���������� ������ �� �������� ��������, ����������� �� �����(��), ���� ����� ����� ������������� �� ���� �� ������ ������ �������������� ����� �� ���������� ��������, � � ������ �������� �� ��������� ���������� ����� - ����������� ������� � ������������� ������� ��� ���������� �����, �������������� ��������� ���������. ��� ���� ������� ������������ ���������� ����������� � ����������� ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.4.������ ����� ��������������� �� ���������� ����������� ����������, ���������� � ���� � �������������� ����������� ������ ������� (������������� �������), � �� ������������ ���������� � ���������� ����������� ����������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.5.���� �� ����� ��������������� �� �����, ��������� ���������� ����������� �������� (��������������� �������) ������������ ������, ��� ������ ��� ��� ��������, ��� ����������� �� ������, ���������������� �����.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���� �� ����� ��������������� �� ����������� ���������� ������������ ���������� ��������� ������� (������������� �������), ����������� � ���� � �������������� ����������� ������ ������� (������������� �������), � ��� ����� � ������ ������������� ������ � ����������-���������� ������� ���������� ����� ������� ���������������� �����.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.6.���� �� ����� ��������������� �� ������������������� ������������ � ����������� ������� ������� ���, ��������� �� ����� ������������� ������� ������� (������������� �������) � ���������� ����� ������� � ��������� � ���������� �������� � ������������� �������� ������� (������������� �������).
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.7.������ ��������� ����� ����� ��������� � ������������ ��� �������������� ������� � �������������� ������� �������������� ������� � ���� ��������. ���� �� ����� ������� ���������������, � ��� ����� ����������, � ������ ����������� (� ������ ��� ��������� ������) ���������� �� �������������� ��������� � � ������ ������ �� ����� ����� �������������� ������� ������� (������������� �������), ������������ � ���� �������� ��� ����������� �������������� ����� �� ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.8.���� �� ����� ��������������� �� �������������� ��������� ������� � ����������������� ������������ �������� �� �������� �������������������� ������������ �������� ������� �� ����� � �������������� ������ ������� �/��� ��� ��������������.
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

     <!---------------------------------- ��������8----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td align="center">
            <span class="titleDoc">6.&nbsp;&nbsp;���� �������� ��������</span>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.1.��������� ������� �������� � ���� � ������� ��� ���������� ��������� � ��������� � ������� 1 ����.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.2.��������� ������� ��������� ���������������� �� ������ ��������� ����, ���� �� ���� �� ������ �� ����������� ������ ������� � ����������� ������������� ������� �� �����, ��� �� 30 ���� �� ���� ��������� ����� �������� ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.3.��������� ������� ����� ���� ���������� �������� ����� �� ������ � ������������� ������� � ��������������� ���������� ������������ ������ ������� �� �����, ��� �� 30 ���� �� ���� ����������� ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.4.��������� ������� ��������� ������������, � ������������� ������ ������������� � ���� ����������� �/��� ����������� ������������ ������ �� ��������� � �������������� ����������� � ��� �� ������ �������, ��������� � ���������� 1 � ���������� ��������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.5.��������� ������� ����� ���� ������� ��� �������� ������ �� ��������� �������� ������, �� ����������� ������, ���������������� ������� 4.2.3 ���������� ��������. ��������� ��������� � ���������� ����������� � ���� �������������� ���������� � ���������� ��������, ���������� ��� ������������ ������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.6.��� ����������, �� ������� ���� ������ � ������ ���������� ��������, �������� ��� ������������� �������.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.7.��������� ������� ��������� � ����, ������� ������ ����, �����������, ���� ��������� ��� �������, ���� ��� �����.
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td align="center">
            <span class="titleDoc">7.&nbsp;&nbsp;��������� ������</span>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <b><nobr>������:</nobr></b>
                </td>
                <td width="100%">&nbsp;</td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>����� �����������</nobr>
                </td>
                <td width="100%">
                    <input value='${person.registrationAddress}' type="Text" readonly="true" class="insertInput" style="width:100%">
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
                    <nobr>����� ������������ ���������� (��� �������� �����������)</nobr>
                </td>
                <td width="100%">
                    <input value='${person.residenceAddress}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><input type="Text" class="insertInput" style="width:100%"></td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td style="font-size:12pt;">
                    <nobr>�����&nbsp;�����������&nbsp;�����&nbsp;(E-mail)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.email}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>�������&nbsp;(���.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.homePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>(���.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.jobPhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
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
                    <nobr>(���.)</nobr>
                </td>
                <td width="50%">
                    <input value='${person.mobilePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>��������� ��������</nobr>
                </td>
                <td width="50%">
                    <input value='${person.mobileOperator}' type="Text" readonly="true" class="insertInput" style="width:100%">
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
                    <nobr>���</nobr>
                </td>
                <td width="30%">
                    <input value='${person.inn}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>�����������</nobr>
                </td>
                <td width="70%">
                    <input value='${person.citizenship}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center" valign="top" style="font-size:8pt;">(��� ��� �������)</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>���&nbsp;���������,&nbsp;���������������&nbsp;��������,</nobr>
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
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
                    <nobr>�����&nbsp;(�����)</nobr>
                </td>
                <td width="30%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>���,&nbsp;���,&nbsp;�����&nbsp;�����</nobr>
                </td>
                <td width="70%">
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
                <td>
                    <nobr>���&nbsp;�������������&nbsp;(���&nbsp;�������)</nobr>
                </td>
                <td width="100%">
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
                <td>
                    <nobr>����&nbsp;�&nbsp;�����&nbsp;��������</nobr>
                </td>
                <td width="100%">
                    <input value='${person.birthDayString}, ${person.birthPlace}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td><b>����:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    ������
                </td>
                <td width="50%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>�����������&nbsp;�������������&nbsp;�</nobr>
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
                <td>
                    <nobr>��������&nbsp;�����</nobr>
                </td>
                <td width="100%">
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
                <td>
                    ���������������
                </td>
                <td width="100%">
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
                <td>
                    �������
                </td>
                <td width="100%" align="left">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
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
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
    <br style="page-break-after:always;">

    <!---------------------------------- ��������9----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td width="50%" align="center"><b>�� �����:</b></td>
                <td align="center"><b>������:</b></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td><span class="tabSpan">&nbsp;</span><b>�.�.</b></td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                </tr>
                <tr>
                    <td><b><nobr>�� �����</nobr></b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                    <td><b>������</b></td>
                    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                    <td>&nbsp;</td>
                    <td align="center" valign="top"  class="font10">(������� �������)</td>
                </tr>
            </table>
        </td>
    </tr>
    </table>

    </td>
    </tr>
    </table>

     <br style="page-break-after:always;">
     <!--------------------------------- ���������� �8_1 ---------------------------------------->

      <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:5mm;margin-right:5mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
      <col style="width:172mm">
      <tr>
      <td>

      <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc" style="vertical-align:100%">
      <!-- ����� ��������� -->
      <tr>
          <td align="right">
              <b>���������� 1</b><br><br>&nbsp;
              <span class="italic font10">� �������� � ��������������<br>
                 ����� � �������������� �������<br>
                 &ldquo;����������� ���������&rdquo;<br>
              </span>
              �<input type="Text" readonly="true" class="insertInput" style="width:8%">��&nbsp;&ldquo;<input type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
               <input type="Text" readonly="true" class="insertInput" style="width:13%">20<input type="Text" readonly="true" class="insertInput" style="width:3%">�.
          </td>
      </tr>

      <tr>
          <td align="center">
              <br><br>
              <b>��������<br>
              ������� ������ �������
              </b><br>
          </td>
      </tr>

      <tr>
          <td><br><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="10%"><b>�/�</b></td>
                  <td class="docTdBorder" align="center"><b>������ ����� �������, �� ������� ��������������� ������,<br>
                                                  ��������������� ��������� ���������
                                          </b>
                  </td>
              </tr>
      <% int lineNumber = 0;%>
      <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
		  <bean:define id="accountLink" name="entry" property="value"/>
		     <%lineNumber++;%>
              <tr>
                  <td class="docTdBorder" align="center">&nbsp;<%=lineNumber%>&nbsp;</td>
                  <td class="docTdBorder">&nbsp;${accountLink.number}&nbsp;</td>
              </tr>
              </table>
          </td>
      </tr>
      </logic:iterate>
      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" width="50%">�����(�) �����(��) �������, � ��������(��) � ������������ ������� ������ ������������ �������� ����� �� ������, ��������������� ��������� ���������</td>
                  <td class="docTdBorder">
                      <table cellspacing="0" cellpadding="0" width="100%">
                      <tr>


                      </tr>
                      </table>
                  </td>


              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br><b>������ �������� ������ ������������� �� ���������� �������� �� ����� �������:</b></td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;SMS-���������</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;SMS-���������</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;�� ������������� �����������</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br><br>
              <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
              <tr>
                  <td width="50%" align="center"><b>�� �����:</b></td>
                  <td align="center"><b>������:</b></td>
              </tr>
              <tr>
                  <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                  <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
              </tr>
              <tr>
                  <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                  <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
              </tr>
              <tr>
                  <td colspan="2">
                      <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                      <tr>
                          <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                          <td width="25%" style="padding-right:8;"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                          <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                          <td width="25%"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                      </tr>
                      </table>
                  </td>
              </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td><span class="tabSpan">&nbsp;</span><b>�.�.</b></td>
      </tr>
      <tr>
          <td valign="bottom" style="height:100%;">
              <table style="width:100%;height:12mm;">
                  <tr>
                      <td colspan="4" class="font10" align="center">������� �<input type="Text" readonly="true" class="insertInput" style="width:18%"> �� "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> �.</td>
                  </tr>
                  <tr>
                      <td><b><nobr>�� �����</nobr></b></td>
                      <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
                      <td><b>������</b></td>
                      <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                  </tr>
                  <tr>
                      <td>&nbsp;</td>
                      <td align="center" valign="top"  class="font10">(������� ��������� �����)</td>
                      <td>&nbsp;</td>
                      <td align="center" valign="top"  class="font10">(������� �������)</td>
                  </tr>
              </table>
          </td>
      </tr>
      </table>

      </td>
      </tr>
      </table>

      <br style="page-break-after:always;">
      <!--------------------------------- ���������� �8_2 ---------------------------------------->



      <br style="page-break-after:always;">
      <!--------------------------------- ���������� �8_3 ---------------------------------------->



      <br style="page-break-after:always;">
      <!--------------------------------- ���������� �8_4 ---------------------------------------->



 </html:form>
</body>
</tiles:put>
</tiles:insert>
