<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 16.04.2009
  Time: 11:19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<c:set var="form" value="${ListDepartmentsForm}"/>

<%-- костыль для IE, когда делаем ajaxQuery IE хочет в начале ответа видеть &nbsp; --%>
<c:if test="${not param.nonbsp}">&nbsp;</c:if>

<c:forEach items="${form.children}" var="child">
    <c:set var="id" value="${child[0]}"/>
    <c:set var="name" value="${child[1]}"/>
    <c:set var="allowed" value="${child[2]}"/>
    <c:set var="has" value="${child[3]}"/>

     <tr class="tree">
         <td>
             &nbsp;
             <c:set var="inputName" value="selectedDeps"/>
             <c:if test="${allowed != 1}">
                 <c:set var="inputName" value="notAllowed"/>
             </c:if>
             <input id="input${id}" type="checkbox" name="${inputName}" value="${id}" style="border:none;"
                    onclick="checkDepartment(${id});" <c:if test="${allowed != 1}">disabled</c:if>>
             <input type="hidden" name="name${id}" value="${name}"/>
             <input type="hidden" name="tb${id}" value="${child[4]}"/>
             <input type="hidden" name="osb${id}" value="${child[5]}"/>
             <input type="hidden" name="vsp${id}" value="${child[6]}"/>
             <input id="dep${id}" type="checkbox" name="selectedIds" value="${id}" style="display:none;"/>

            <c:choose>
                <c:when test="${has > 0}">
                    <input id="selAsParent${id}" type="hidden" name="selectedParents" value="${id}"/>
                    <a href="javascript:showChild('${id}')">
                        <c:out value="${name}"/>
                    </a>
                    &nbsp;
                    <a class="selectChild">
                        <span id="selectChild${id}" class="page" style="display: none;" onclick="checkAllChilds(${id}, true);">выбрать подчиненные</span>
                        <span id="unselectChild${id}" class="page" style="display: none;" onclick="checkAllChilds(${id}, false);">очистить</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <c:out value="${name}"/>
                </c:otherwise>
            </c:choose>
            <table id="${id}" class="departmentTree noBorder" style="display:none;">
                <tbody>
                </tbody>
            </table>
         </td>
     </tr>
</c:forEach>

<c:if test="${empty form.children}">
    <tr>
        <td>
            <c:out value="нет доступных подразделений"/>
        </td>
    </tr>
</c:if>