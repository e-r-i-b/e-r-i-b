<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%-- field - бин типа Field --%>
<tiles:importAttribute ignore="true"/>

<c:set var="readonlyFlag" value="${(not field.visible or field.editable) ? '':'readonly'}"/> <%--невидимые поля редактируем всегда--%>

<tr class="${tdClass}">
    <td><c:out value="${field.name}"/></td>

    <td><c:out value="${field.type}"/></td>

    <td><c:out value="${field.title}"/></td>

    <td>
    <c:choose>
        <c:when test="${field.type eq 'list'}">
            <select name="${field.name}" onchange="${onChange}">
                <c:if test="${not empty field.listType.availableValues}">
                    <c:forEach var="valueItem" items="${field.listType.availableValues.valueItem}">
                        <c:set var="selectedFlag" value="${valueItem.selected ? 'selected':''}"/>
                        <option value="${valueItem.value}" ${selectedFlag}><c:out value="${valueItem.title}"/></option>
                    </c:forEach>
                </c:if>
            </select>
        </c:when>

        <c:when test="${field.type eq 'set'}">
            <c:if test="${not empty field.setType.availableValues}">
                <c:forEach var="valueItem" items="${field.setType.availableValues.valueItem}">
                    <c:set var="selectedFlag" value="${valueItem.selected ? 'checked':''}"/>
                    <input name="${field.name}" type="checkbox" value="${valueItem.value}" ${selectedFlag} ${readonlyFlag}><c:out value="${valueItem.title}"/>
                    <br/>
                </c:forEach>
            </c:if>
        </c:when>

        <c:when test="${field.type eq 'date'}">
            <c:set var="value"><c:out value="${field.dateType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type eq 'number'}">
            <input name="${field.name}" type="text" value="${not empty field.numberType.value ? field.numberType.value : ''}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type == 'string'}">
            <c:set var="value"><c:out value="${field.stringType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type eq 'money'}">
            <c:set var="value"><c:out value="${field.moneyType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type eq 'calendar'}">
            <c:set var="value"><c:out value="${field.calendarType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type eq 'integer'}">
            <input name="${field.name}" type="text" value="${not empty field.integerType.value ? field.integerType.value : ''}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type eq 'resource'}">
            <select name="${field.name}">
                <c:if test="${not empty field.resourceType.availableValues}">
                    <c:forEach var="valueItem" items="${field.resourceType.availableValues.valueItem}">
                        <c:set var="selectedFlag" value="${valueItem.selected ? 'selected':''}"/>
                        <option value="${valueItem.value}" ${selectedFlag}><c:out value="${valueItem.value} ${empty valueItem.displayedValue ? '' : valueItem.displayedValue} ${empty valueItem.currency ? '' : valueItem.currency}"/></option>
                    </c:forEach>
                </c:if>
            </select>
        </c:when>

        <c:when test="${field.type == 'boolean' or field.type == 'choice'}">
            <c:set var="value"><c:out value="${field.booleanType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type == 'link'}">
            <c:set var="value"><c:out value="${field.linkType.url}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>
    </c:choose>
    </td>

    <td align="center">${field.required ? 'x':''}</td>
    <td align="center">${field.editable ? 'x':''}</td>
    <td align="center">${field.visible ? 'x':''}</td>
    <td align="center">${field.isSum ? 'x':''}</td>
    <td align="center">${field.changed ? 'x':''}</td>
    <td align="center">${not empty field.minLength ? field.minLength : ''}</td>
    <td align="center">${not empty field.maxLength ? field.maxLength : ''}</td>
    <td><small><c:out value="${field.description}"/></small></td>
    <td><small><c:out value="${field.hint}"/></small></td>
</tr>
