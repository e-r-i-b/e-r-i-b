<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="customSelectId" value="${customSelectId}" scope="request"/>
<c:set var="customSelectName" value="${customSelectName}" scope="request"/>
<c:set var="customSelectOnclick" value="${customSelectOnclick}" scope="request"/>
<c:forEach var="node" items="${nodeList}">
    <c:if test="${node.selected || (onlyOneValue && node.leaf)}">
        <script type="text/javascript">
            $(document).ready(function()
            {
                checkGroupValues('${customSelectId}', '${node.id}', '${customSelectName}');
            });
        </script>
    </c:if>
    <c:choose>
        <c:when test="${node.leaf}">
            <c:set var="className" value="selectListElem"/>
            <c:choose>
                <c:when test="${level == 2}">
                    <c:set var="className" value="${className} mainTitleLevel titleAndUnit levelDivider groupTitle"/>
                </c:when>
                <c:otherwise>
                    <c:set var="className" value="${className} units"/>
                </c:otherwise>
            </c:choose>
            <c:if test="${node.selected}">
                <c:set var="className" value="${className} activeListElem"/>
            </c:if>

            <div>
                <div class="${className} " onclick="checkGroupValues('${customSelectId}', '${node.id}', '${customSelectName}'); ${customSelectOnclick};" id="${node.id}">
                    <c:out value="${node.name}"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <c:if test="${not empty node.name}">
                    <c:set var="doOnclick" value="${phiz:size(node.allIdentifiers) > 1}"/>
                    <c:set var="className" value="selectListElem groupTitle"/>
                    <c:if test="${node.selected}">
                        <c:set var="className" value="${className} activeListElem"/>
                    </c:if>
                    <c:choose>
                        <c:when test="${level == 1}">
                            <c:set var="className" value="${className} mainTitleLevel"/>
                        </c:when>
                        <c:when test="${level == 2}">
                            <c:set var="className" value="${className} mainTitleLevel levelDivider"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="className" value="${className} subTitleLevel"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="${className}" <c:if test="${doOnclick}">onclick="checkGroupValues('${customSelectId}', '${node.id}', '${customSelectName}'); ${customSelectOnclick};"</c:if> id="${node.id}">
                        <c:out value="${node.name}"/>
                    </div>
                </c:if>
                <c:set var="nodeList" value="${node.list}" scope="request"/>
                <tiles:insert page="/WEB-INF/jsp/common/layout/drawList.jsp" flush="false">
                    <tiles:put name="level" value="${level + 1}"/>
                </tiles:insert>
            </div>
        </c:otherwise>
    </c:choose>
</c:forEach>