<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">

    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/dictionary/regions/child')}"/>

    //Показывает или скрывает дочерние регионы по id родительского.
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
                             url: '${url}',
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

    //вставляем узел. 
    function addRow(rowId, children)
    {
        for (var i = 0; i < children.length; i = i + 1)
        {
            var tbody = document.getElementById(rowId).getElementsByTagName('TBODY')[0];
            var row = document.createElement("TR");
            tbody.appendChild(row);
            var td = document.createElement("TD");
            row.appendChild(td);
            if(children[i].hasChild == '1')
            {
                td.innerHTML = "<input type='checkbox' name='selectedIds' value='"+children[i].id+"' style='border:none;'>"+
                               "<a href = \"\" onclick=\"showChild('" +children[i].id+ "'); return false;\">" + children[i].name + "</a>"+
                               "<table style='display:none; margin-left: 2em;height:auto;' id ='"+ children[i].id+"'><tbody></tbody></table>"+
                               "<input type='hidden' name='name" + children[i].id + "' value='" + children[i].name + "'/>"+
                               "<input type='hidden' name='parent" + children[i].id + "' value='" + children[i].parent + "'/>";
            }
            else
            {
                td.innerHTML = "<input type='checkbox' name='selectedIds' value='"+children[i].id+"' style='border:none;'>" + children[i].name +
                               "<table style='display:none; margin-left: 2em;height:auto;' id ='"+ children[i].id+"'><tbody></tbody></table>"+
                               "<input type='hidden' name='name" + children[i].id + "' value='" + children[i].name + "'/>"+
                               "<input type='hidden' name='parent" + children[i].id + "' value='" + children[i].parent + "'/>";
            }
        }
    }
</script>

    <c:forEach items="${regions}" var="region">
        <c:choose>
            <c:when test="${empty region.parent}">
            <tr class="tree">
                <td>
                    &nbsp;
                    <input type="checkbox" name="selectedIds" value="${region.id}" style="border:none;"/>
                    <input type="hidden" name="name${region.id}" value="${region.name}"/>
                    <c:set var="children" value="${phiz:getRegionChildren(region)}"/>
                    <c:choose>
                        <c:when test="${not empty children}">
                            <a id="parent${region.id}" href="" onclick="showChild('${region.id}'); return false;">
                                ${region.name}
                            </a>
                        </c:when>
                        <c:otherwise>
                            ${region.name}
                        </c:otherwise>
                    </c:choose>
                    <table id="${region.id}" style="display:none; margin-left: 2em;height:auto;">
                        <tbody>
                        </tbody>
                    </table>
                </td>
            </tr>
            </c:when>
            <c:otherwise>
                <input type="hidden" id="parent${region.id}" value="${region.parent.name}"/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
