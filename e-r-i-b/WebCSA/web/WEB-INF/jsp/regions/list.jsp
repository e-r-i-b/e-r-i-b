<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="frm" value="${ShowRegionListForm}"/>
<c:if test="${frm.saved}">OK</c:if>
<c:if test="${fn:length(frm.regions) gt 0 and not frm.saved}">
    <c:set var="personRegionName" value="${sessionScope.session_region.name}"/>
    <c:set var="defaultSelectedValue" value=""/>
    <div class="regionsAlphabetList">
        <div class="currentRegion">
            <div class="float"><span>�� ������ �����:</span></div>
            <div class="currentRegionName">
                <span>
                    <c:choose>
                        <c:when test="${personRegionName == null || personRegionName == '��� �������'}">
                            ������ �� ������
                        </c:when>
                        <c:otherwise>
                            ${personRegionName}
                            <c:if test="${sessionScope.$$show_default_region == 'true'}">
                                <c:set var="defaultSelectedValue" value="${personRegionName}"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </span>
            </div>
            <div class="clear"></div>
        </div>

        <div class="selectRegionHint">
            �������� ���� ������, � ��� ������ �������� ������� ���������� ������������ �����, ������� ����� � ������ ������.
        </div>

        <tiles:insert definition="alphabet" flush="false">
            <tiles:put name="onClickFunctionName" value="clickRegion.click"/>
            <tiles:put name="onClickFunctionParameters" value="id,name"/>
            <tiles:put name="selectFunctionName" value="clickRegion.choose"/>
            <tiles:put name="selectFunctionParameters" value="id,name"/>
            <tiles:put name="data" beanName="frm" beanProperty="regions"/>
            <tiles:put name="defaultSelectedValue" value="${defaultSelectedValue}"/>
        </tiles:insert>

        <div class="checkAllRegionsBlock">
            ���� ��� ������ ��� ���������� ���������� �� ���� ��������, ��������
            <a href="#" onclick="clickRegion.choose(-1, '��� �������'); return false;">��� �������</a>.
        </div>
    </div>
</c:if>
