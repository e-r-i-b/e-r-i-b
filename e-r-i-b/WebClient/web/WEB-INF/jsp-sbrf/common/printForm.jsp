<%--
  Created by IntelliJ IDEA.
  User: lukina
  Date: 10.06.2009
  Time: 12:11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/printForm" onsubmit="return setEmptyAction(event)">

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="Info"/>
    <tiles:put name="submenu" value="PrintForm"/>
    <tiles:put name="pageTitle">������ �������</tiles:put>

    <tiles:put name="data" type="string">

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="name" value="������ �������"/>
            <tiles:put name="data" type="string">
                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    ������ ����� ��-4
                </phiz:link>
                <p>�� ������ ��������� � ����������� ��������� �������� ��-4 ��� ������ � ����� �� ������������� ��������������� �����.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="nalog"/>
                    ������ ����� ��-4�� (�����)
                </phiz:link>
                <p>�� ������ ��������� � ����������� ��������� �������� ��-4�� (�����) ��� ������ � ����� �� ������������� ��������������� �����.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="pay"/>
                    ������ ���������� ���������
                </phiz:link>
                <p>�� ������ ��������� � ����������� ��������� ��������� ����� 0401060 ��� ������������ ��� � ������������� ��������������� ����� ��� ���������� �������� �� �������� ����� ��� ������������ ����� �����������.</p>

                <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                    <phiz:param name="page" value="letter"/>
                    ������ ����������� ���������
                </phiz:link>
                 <p>�� ������ ��������� � ����������� ���������� ��������� ����� 0401071 ��� ������������ ��� � ������������� ��������������� ����� ��� ���������� �������� �� �������� ����� ��� ������������ ����� �����������.</p>
            </tiles:put>
         </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>