<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>


<%--
    ����������, ��������� ������ ����� �� ������ ����� � ��������� �� ������
    ���� � ������ ���� �������, �� onClickFunctionName ������ �������� ���� �������� ��� ����������� ������ ����-���� �� ��������,
    � selectFunctionName ��������� ����� ������ �� ���� ������, ��� �������� � ��������.
    ���� � ������ ��� ��������, �� ��������� ������ onClickFunctionName � selectFunctionName ������ ���� ��������.
    ���� ��� ������� selectFunctionName �� �������, �� ������ ��� ������������ onClickFunctionName.
        title - ��������� ������
        data - ������ ��� ������ � ����������� ��������� ��������
        onClickFunctionName  - �������� js ������� ��� ����� �� ������
        onClickFunctionParameters - ��������� js �������, �������������� ����� property ���������� � ����� ������� ��� ���������,
                                    ���������� ����� ������� � ����� �� ��������, ������������� � ������� onClickFunctionName
        selectFunctionName - �������� js ������� ��� ������ ���� ������
        selectFunctionParameters - ��������� js �������, �������������� ����� property ���������� � ����� ������� ��� ���������,
                                    ���������� ����� ������� � ����� �� ��������, ������������� � ������� selectFunctionName
        titleLinkMessage - �������� ����� ����� � ����������
        titleLinkOnClick -  ������� ���������� �� ������ ����� � ����������

        navigations - ������ �� ������� �������� ������� ������
        breadTitleAll - �������� "������� ���" ��� ������� ������

        defaultSelectedValue - ��������, ��������� �� ��������� (����� ��������� ���������� � ������)
--%>

    <tiles:importAttribute/>

    <c:set var="count" value="${fn:length(data)}"/>
    <c:if test="${count > 0 }">
        <c:if test="${not empty title}"><h1>${title}</h1></c:if>
        <c:if test="${not empty titleLinkMessage and not empty titleLinkOnClick}">
            <a href="#" onclick="${titleLinkOnClick}; return false;"
                                                 class="select-all-regions">${titleLinkMessage}</a>
        </c:if>

        <%-- ��������� "������� ������" --%>
        <c:if test="${not empty navigations }">
            <div class="clear"></div>
            <a class="alphabetOnClickLink" href="#" onclick="${onClickFunctionName}(0, ''); return false;"><b>${breadTitleAll}</b></a>
            <c:forEach var="navigation" items="${navigations}" varStatus="status">
                &raquo;
                <c:choose>
                    <c:when test="${status.last}">
                        ${navigation.name}
                        <c:set var="currentRegion" value="${navigation}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="comma" value="0"/>
                        <a class="alphabetOnClickLink" href="" onclick="${onClickFunctionName} (
                            <c:forEach var="property" items="${fn:split(onClickFunctionParameters, ',')}">
                                <c:if test="${comma == 1}">
                                    ,
                                </c:if>
                                <c:if test="${comma == 0}">
                                    <c:set var="comma" value="1"/>
                                </c:if>

                                '<bean:write name="navigation" property="${property}" bundle="commonBundle"/>'
                            </c:forEach>
                        ); return false;">${navigation.name}</a><br/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <%-- ����� ���� ������� ��������� --%>
            <div class="clear"></div>
            <tiles:insert definition="roundBorder" flush="false">
                <c:set var="name" value="${csa:escapeForJS(currentRegion.name, false)}"/>
                <c:if test="${empty selectFunctionName}">
                    <c:set var="selectFunctionName" value="${onClickFunctionName}"/>
                    <c:set var="selectFunctionParameters" value="${onClickFunctionParameters}"/>
                </c:if>
                <tiles:put name="color" value="lightGreen"/>
                <tiles:put name="data">
                    <c:set var="comma" value="0"/>
                    <a class="alphabetOnClickLink" href="#" onclick="${selectFunctionName} (
                        <c:forEach var="property" items="${fn:split(selectFunctionParameters, ',')}">
                            <c:if test="${comma == 1}">
                                ,
                            </c:if>
                            <c:if test="${comma == 0}">
                                <c:set var="comma" value="1"/>
                            </c:if>

                            '<bean:write name="currentRegion" property="${property}" bundle="commonBundle"/>'
                        </c:forEach>
                    ); return false;">${currentRegion.name}</a>
                </tiles:put>
            </tiles:insert>
        </c:if>

            <%-- ����������� ��������� �������� --%>
            <c:set var="numPerColumn" value="${count/3}"/>
            <c:set var="previousLatter" value=""/>
            <c:set var="current" value="0"/>

            <%-- ������ ������ --%>
            <div class="alphabet-list">
            <div class="alphabet-list-column">

            <c:set var="id" value="id"/>

            <c:forEach items="${data}" var="element">
                <c:set var="regName" value="${fn:trim(element.name)}"/>
                <c:set var="regName" value="${csa:replace(regName,'^([�|�][.]?[ ]+)')}"/>
                <c:set var="firstLatter" value="${fn:toUpperCase(fn:substring(regName, 0, 1))}"/>
                <%-- ����� ������� --%>
                <c:if test="${ numPerColumn lt current && previousLatter != firstLatter}">
                    <%-- ��������� ����� � ������� --%>
                    </div></div>
                    <div class="alphabet-list-column">
                    <%-- ��������� ����� ������� --%>
                    <c:set var="current" value="0"/>
                </c:if>

                <%-- ��������� ����� ��������� --%>
                <c:if test="${previousLatter != firstLatter}">
                    <c:set var="previousLatter" value="${firstLatter}"/>
                    <c:if test="${current != 0}"></div></c:if> <%-- ���� �� ������ ������� ���������� ������� ���������� ����� --%>
                    <div class="letter">${firstLatter}</div>
                    <div class="alphabet-group">
                </c:if>

                <c:set var="comma" value="0"/>
                <c:set var="selectedClass" value=""/>
                <c:if test="${not empty defaultSelectedValue && defaultSelectedValue == element.name}">
                    <c:set var="selectedClass" value="defaultSelectedValue"/>
                </c:if>
                <div class="alphabetItemBlock ${selectedClass}">
                    <a class="alphabetOnClickLink" href="#" onclick="${onClickFunctionName} (
                        <c:forEach var="property" items="${fn:split(onClickFunctionParameters, ',')}">
                            <c:if test="${comma == 1}">
                                ,
                            </c:if>
                            <c:if test="${comma == 0}">
                                <c:set var="comma" value="1"/>
                            </c:if>

                            '<bean:write name="element" property="${property}" bundle="commonBundle"/>'
                        </c:forEach>
                    ); return false;">${element.name}</a>
                </div>
                <%-- ��������� ������� ������� --%>
                <c:set var="current" value="${current+1}"/>

        </c:forEach>
            </div> </div>
            <div class="clear"></div>
            </div>
    </c:if>
