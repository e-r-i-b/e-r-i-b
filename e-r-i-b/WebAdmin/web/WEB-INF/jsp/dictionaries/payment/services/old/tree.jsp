<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">
    var addUrlList = "${phiz:calculateActionURL(pageContext,'/dictionaries/oldPaymentService/child')}";
    /*Показывает или скрывает дочерние услуги по id родительской.*/
    function showChild(id)
    {
        var elem = document.getElementById(id);
        if (elem.style.display == "none")
        {
            var tr = document.getElementById(id).getElementsByTagName('TBODY')[0].getElementsByTagName('TR')[0];
            if (tr == undefined)
            {
                ajaxQuery("id=" + id, addUrlList, function(children){addRow(id, children);}, null, true);
            }
            elem.style.display = "";
        }
        else
        {
            elem.style.display = "none";
        }
    }

    function addRow(rowId, data)
    {
        if (trim(data) == '')
        {
            return false;
        }
        $("#" + rowId).html($(data));
        return true;
    }
</script>

<c:forEach items="${paymentServices}" var="paymentService">
    <c:if test="${empty paymentService.parent && paymentService.visibleInSystem == 'true'}">
        <tr class="tree">
            <td>
                &nbsp;
                <c:set var="children" value="${phiz:getPaymentServiceChildrensOld(paymentService)}"/>
                <input type="checkbox" name="selectedIds" value="${paymentService.id}"  style="border:none;"/>
                <input type="hidden" name="name${paymentService.id}" value="${paymentService.name}"/>
                <c:choose>
                    <c:when test="${not empty children}">
                        <a id="parent${paymentService.id}" href="javascript:showChild('${paymentService.id}')">
                            ${paymentService.name} (${paymentService.synchKey})
                        </a>
                    </c:when>
                    <c:otherwise>
                        ${paymentService.name} (${paymentService.synchKey})
                    </c:otherwise>
                </c:choose>
                <table class="noBorder" id="${paymentService.id}" style="display:none; margin-left: 2em;height:auto;">
                    <tbody>
                    </tbody>
                </table>
            </td>
        </tr>
    </c:if>
</c:forEach>