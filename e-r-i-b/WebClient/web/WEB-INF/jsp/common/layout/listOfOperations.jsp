<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:importAttribute ignore="true"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--
    ��������� ��� ����������� ����������� ������ � ���������� � ���������
    items - ������ ���������
    isLock - ������� ������������� ��������
    nameOfOperation - �������� ����� � ����������
    productOperation - �������������� ����� �������� � ���������
    onClickList - ��������� ������� �� ���� ������(������ ��� ����������� ����)
--%>
<c:if test="${!empty items}">
    <c:choose>
        <c:when test="${isLock && !isShowOperationButton}"/>
        <c:when test="${isLock}">
            <div class='listOfOperation' onclick="cancelBubbling(event); return false;">
                    <%-- ������ ������������ --%>
                <div class="buttonSelect lock hasLayout">
                    <div class="buttonSelectLeft"></div>
                    <div class="buttonSelectCenter">
                            ${nameOfOperation}
                            <%--
                            ���� ������� ����� ������ ��� ���� ���������� ������ �������� �������� �� ������ ���������� ����� ��� ��� ��.
                            ${items[0]}
                            --%>
                    </div>
                    <div class="buttonSelectRight"></div>
                    <div class="clear"></div>
                </div>
            </div>
        </c:when>
        <c:otherwise>


            <c:if test="${empty listOfOperationCount}">
                <c:set var="listOfOperationCount" scope="request" value="0"/>
            </c:if>
            <c:set var="itemId" value="listOfOperation${listOfOperationCount}"/>
            <div class='listOfOperation productOperation' id="${itemId}_parent"  onclick="cancelBubbling(event); <c:if test="${not empty onClickList}">${onClickList}</c:if>">
                <div id="${itemId}" class="moreListOfOperation" style="display:none;">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="hoar"/>
                        <tiles:put name="data">
                            <div>
                                <table cellpadding="0" cellspacing="0" class="productOperationList">
                                    <logic:iterate id="item" name="items">
                                        <c:if test="${empty firstItem}">
                                            <c:set var="firstItem" value="${item}"/>
                                        </c:if>
                                        <tr><td onmouseover="mouseEnter(this);" onmouseout="mouseLeave(this);">
                                            <div class="opName">${item}</div>
                                        </td></tr>
                                    </logic:iterate>
                                </table>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <%-- ������ --%>
                <c:if test="${isShowOperationButton}">
                <div class="buttonSelect <c:if test="${productOperation == true}">productListOperation</c:if>">
                    <div class="buttonSelectLeft"></div>
                    <div class="buttonSelectCenter">
                        ${nameOfOperation}
                        <%--
                            ���� ������� ����� ������ ��� ���� ���������� ������ �������� �������� �� ������ ���������� ����� ��� ��� ��.
                            ${firstItem}
                        --%>
                    </div>
                    <div class="buttonSelectRight"></div>
                    <div class="clear"></div>
                </div>
                </c:if>
            </div>

            <%-- ����������� ������--%>
            <c:set var="listOfOperationCount" scope="request" value="${listOfOperationCount+1}"/>

        </c:otherwise>
    </c:choose>
</c:if>