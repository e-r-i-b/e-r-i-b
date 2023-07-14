<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
  ������ ��� ������� ��������

  id            - id �������, ��� ����, ����� ������ � ������ ��������� ����������
  grid          - ���������� ������� (������ ���������� � ������� �����)
  emptyMessage  - ���������, ������� ��������� � ������ isEmpty = true
  isEmpty       - ������� ��������� ���������� � ������� ��� ����, ����� �� ������. �� ��������� ������������.
  hideable      - ���� true �� ������ ����� �������� (�� ��������� false)
  productType   - ��� �������� (��� ���������� ��������/���������� ��������)
  ajaxDataURL   - url ������ � ������� ������� ���������� ���������
  show          - ������� ��������/���������� ��������
--%>
<c:if test="${not empty show and not show}">
    <c:set var="style" value="display: none"/>
</c:if>

<c:if test="${hideable}">
    <c:set var="productId" value="${id}"/>
    <c:set var="id" value="${productType}_${productId}"/>
</c:if>

<%-- ������� ���������� --%>
<c:if test="${empty simpleTableCounter}">
    <c:set var="simpleTableCounter" value="0" scope="request"/>
</c:if>

<%-- ��������� ���� ����������� id. ��� ������������� ���������� --%>
<c:if test="${empty id || id == ''}">
    <c:set var="id" value="simpleTable${simpleTableCounter}"/>
     <c:set var="simpleTableCounter" value="${simpleTableCounter+1}" scope="request"/>
</c:if>

<c:set var="gridData">
    <div id="${id}" class="simpleTable" style = "${style}">
        <c:choose>
            <c:when test="${isEmpty}">
                <div class="emptyText">
                    <tiles:insert definition="roundBorderLight" flush="false">
                        <tiles:put name="color" value="greenBold"/>
                        <tiles:put name="data">
                            ${emptyMessage}
                        </tiles:put>
                    </tiles:insert>
                </div>

            </c:when>
            <c:otherwise>
                <div class="grid">
                    <c:choose>
                        <c:when test="${(empty grid || grid == '') && ajaxDataURL != ''}">
                            <img src="${imagePath}/ajaxLoader.gif" alt="Loading..." title="Loading..." class="abstractLoader"/>
                        </c:when>
                        <c:otherwise>${grid}</c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</c:set>

<%-- ������� ����� �������� ������ � ������� --%>
<c:set var = "ajaxFunction" value = ""/>
<c:if test="${not empty ajaxDataURL && ajaxDataURL != ''}">
    <c:set var = "ajaxFunction">loadAjaxDataOnce('${ajaxDataURL}', '${id}')</c:set>
</c:if>

<c:choose>
    <c:when test="${hideable}">
                ${gridData}

                    <c:set var="url" value="${csa:calculateActionURL(pageContext,'/private/async/product/display/change')}"/>
                    <c:set var="relText" value="��������"/>
                    <c:set var="mainText" value="����������"/>
                    <c:set var="aditionalClass" value=""/>
                    <c:if test="${empty show or show}">
                        <c:set var="relText" value="����������"/>
                        <c:set var="mainText" value="��������"/>
                        <c:set var="aditionalClass" value="hide"/>
                    </c:if>
                <%-- ����� hideable-element ��������� ��� ������ ��������. ������� �� ������� �������� ������� �������. --%>
                <a class="hide-text text-gray hideable-element ${aditionalClass}" onclick="showHideOperations(this, '${productId}', '${productType}', '${url}', function(){${ajaxFunction}; return false;}); cancelBubbling(event); return false;" rel="${relText}">${mainText}</a>
    </c:when>
    <c:otherwise>
        ${gridData}
    </c:otherwise>
</c:choose>

<c:if test="${ajaxFunction != '' && (!hideable || hideable && show)}">
    <script type="text/javascript">
        doOnLoad (function(){ ${ajaxFunction}});
    </script>
</c:if>