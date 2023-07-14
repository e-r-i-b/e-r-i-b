<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">
    var addUrlList = "${phiz:calculateActionURL(pageContext,'/private/dictionaries/paymentService/child')}";
    /*Показывает или скрывает дочерние услуги по id родительской.*/
    function showChild(id)
    {
        var elem = document.getElementById(id);
        if (elem.style.display == "none")
        {
            var tr = document.getElementById(id).getElementsByTagName('TBODY')[0].getElementsByTagName('TR')[0];
            if (tr == undefined)
            {
                jQuery.noConflict();
                jQuery.ajax({
                             type: "POST",
                             url: addUrlList,
                             data: ({id : id}),
                             dataType: "script"
                           });
            }
            elem.style.display = ""
        }
        else
        {
            elem.style.display = "none";
        }
    }

    function addRow(rowId, childs)
    {
        for (var i = 0; i < childs.length; i = i + 1)
        {
            var tbody = document.getElementById(rowId).getElementsByTagName('TBODY')[0];
            var row = document.createElement("TR");
            tbody.appendChild(row);
            var td = document.createElement("TD");
            row.appendChild(td);
            td.innerHTML = "<input type='checkbox' name='selectedIds' value='"+childs[i].id+"' style='border:none;'>"+ childs[i].name+
                            "<input id='child" + childs[i].id +"' type='hidden' name='name" + childs[i].id + "' value='" + childs[i].name + "'/>";
        }
    }
</script>

<c:forEach items="${paymentServices}" var="paymentService">
    <c:if test="${empty paymentService.parent}">
        <tr class="tree">
            <td>
                &nbsp;
                <input type="checkbox" name="selectedIds" value="${paymentService.id}" style="border:none;"/>
                <input type="hidden" name="name${paymentService.id}" value="${paymentService.name}"/>
                <c:set var="children" value="${phiz:getPaymentServiceChildren(paymentService)}"/>
                <c:choose>
                    <c:when test="${not empty children}">
                        <a id="parent${paymentService.id}" href="" onclick="showChild('${paymentService.id}'); return false;">
                            ${paymentService.name}
                        </a>
                    </c:when>
                    <c:otherwise>
                        ${paymentService.name}
                    </c:otherwise>
                </c:choose>
                <table  id="${paymentService.id}" style="display:none; margin-left: 2em;height:auto;">
                    <tbody>
                    </tbody>
                </table>
            </td>
        </tr>
    </c:if>
</c:forEach>
