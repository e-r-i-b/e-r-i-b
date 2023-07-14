<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- field - бин типа Field --%>
<tiles:importAttribute ignore="true"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
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
                <c:set var="replacedName" value="${fn:replace(field.name, '-', '')}"/>
                <c:set var="replacedName" value="${fn:replace(replacedName, '(', '')}"/>
                <c:set var="replacedName" value="${fn:replace(replacedName, ')', '')}"/>
                <c:forEach var="valueItem" items="${field.setType.availableValues.valueItem}" varStatus="status">
                    <c:set var="selectedFlag" value="${valueItem.selected ? 'checked':''}"/>
                    <input class="setFieldView${replacedName}" type="checkbox" value="${valueItem.value}" ${selectedFlag} ${readonlyFlag} onchange="setCheckboxValues${replacedName}();"><c:out value="${valueItem.title}"/>
                    <br/>
                </c:forEach>
                <input name="${field.name}" id="setFieldSend${replacedName}" type="hidden"/> <%--значение, передаваемое на сервер--%>
                <script type="text/javascript">
                    require(["dojo/domReady!"], function(){
                        setCheckboxValues${replacedName}();
                    });
                    function setCheckboxValues${replacedName}()
                    {
                        require(["dojo/query", "dojo/dom-attr"], function(query, domAttr) {
                            var checkboxes = query(".setFieldView${replacedName}");
                            var values = new Array();
                            for (var i = 0; i < checkboxes.length; i++) {
                                if (checkboxes[i].checked) {
                                    values.push(checkboxes[i].value);
                                }
                            }
                            domAttr.set("setFieldSend${replacedName}", "value", values.join("@"));
                        });
                    }
                </script>
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

        <c:when test="${field.type == 'boolean'}">
            <c:set var="value"><c:out value="${field.booleanType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type == 'agreement'}">
            <c:set var="value"><c:out value="${field.agreementType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
            <span><c:out value="${field.agreementType.agreementId}"/></span>
        </c:when>

        <c:when test="${field.type == 'places'}">
            <c:set var="value"><c:out value="${field.placesType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>

        <c:when test="${field.type == 'dict'}">
            <c:set var="value"><c:out value="${field.dictType.value}"/></c:set>
            <input name="${field.name}" type="text" value="${value}" size="40" ${readonlyFlag}>
        </c:when>
    </c:choose>
    </td>

    <td align="center">${field.required ? 'x':''}</td>
    <td align="center">${field.editable ? 'x':''}</td>
    <td align="center">${field.visible ? 'x':''}</td>
    <td align="center">${not empty field.isSum and field.isSum ? 'x':''}</td>
    <td align="center">${field.changed ? 'x':''}</td>
    <td align="center">${not empty field.minLength ? field.minLength : ''}</td>
    <td align="center">${not empty field.maxLength ? field.maxLength : ''}</td>
    <td><small><c:out value="${field.description}"/></small></td>
    <td><small><c:out value="${field.hint}"/></small></td>
</tr>
