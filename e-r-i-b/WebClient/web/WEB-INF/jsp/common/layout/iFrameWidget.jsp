<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
    iframe-������
    digitClassname - ��� ������-������
    cssClassname - ��� CSS-������
    sizeable - ������ "������ ����� ������ ������"
    editPanel - html-�������� ��� ������ �������������� �������
    borderColor - ���� �����
    url - ����� ������������� �����
    cssClassFrame - css ����� ������
--%>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="${digitClassname}"/>
    <tiles:put name="cssClassname" value="${cssClassname}"/>
    <tiles:put name="borderColor" value="${borderColor}"/>
    <tiles:put name="sizeable" value="${sizeable}"/>
    <tiles:put name="editable" value="${editable}"/>

    <tiles:put name="viewPanel">
        <iframe src="${url}" class="${cssClassFrame}"></iframe>
    </tiles:put>

    <tiles:put name="editPanel">
        ${editPanel}
    </tiles:put>
</tiles:insert>
