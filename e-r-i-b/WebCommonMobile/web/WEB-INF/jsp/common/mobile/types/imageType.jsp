<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute ignore="true"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<%--
    ���������� ��� ���� Image. ��������� ���� id ���� imageData, ���� ���� �������� url.
    ���� ������ ��� imageData �� image �� �����
    ����� �� id ����� image � ������ �������:
        ���� � ����� Image ����� url (extendImage), �� ���������� ��� � ���� staticImage;
        ����� (extendImage �� �����), �� ���������� id � ���� dbImage.
    ����� (������ url) - ���������� url � ���� staticImage.

    ���������:
    name - �������� ����
    id - id ����� � ��. ��������� ���� id, ���� imageData, ���� ���� �������� url.
    updateTime - ����� ���������� ����� � ��. ���� �� ������, �� ���� ���� �� id-�����.
    url - url �����������. ��������� ���� id, ���� imageData, ���� ���� �������� url.
    imageData - �����������. ��������� ���� id, ���� imageData, ���� ���� �������� url.
--%>

<c:if test="${(not empty id or not empty url or not empty imageData) and not empty name}">
    <${name}>
        <c:choose>
            <%--��������� � imageId--%>
            <c:when test="${not empty id or not empty imageData}">
                <c:if test="${empty imageData}">
                    <c:set var="imageData" value="${phiz:getImageById(id)}"/>
                </c:if>
                <c:set var="extendImage" value="${phiz:getFullPathToExtendImage(imageData)}"/>
                <c:choose>
                    <%--���� ����� id, �� ��������� � ������ �� ������� ��������--%>
                    <c:when test="${not empty extendImage}">
                        <staticImage>
                            <url>${extendImage}</url>
                        </staticImage>
                    </c:when>
                    <%--���� ����� �� ������� �������� �����������, �� ���������� id--%>
                    <c:otherwise>
                        <dbImage>
                            <id>${empty id ? imageData.id : id}</id>
                            <%--workaround: ������������� updateTime � ����� ����������--%>
                            <c:set var="updateTimeForward" value="${updateTime}"/>
                            <c:if test="${empty updateTimeForward}">
                                <c:set var="updateTimeForward" value="${imageData.updateTime}"/>
                            </c:if>
                            <tiles:insert definition="mobileDateTimeType" flush="false">
                                <tiles:put name="name" value="updated"/>
                                <tiles:put name="calendar" beanName="updateTimeForward"/>
                            </tiles:insert>
                        </dbImage>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <%--���� imageId �� �����, ������������ url--%>
            <c:when test="${not empty url}">
                <staticImage>
                    <url>${imagePath}${url}</url>
                </staticImage>
            </c:when>
        </c:choose>
    </${name}>
</c:if>