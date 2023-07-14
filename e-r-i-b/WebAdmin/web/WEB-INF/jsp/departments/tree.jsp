<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 24.04.2009
  Time: 11:19:07
  To change this template use File | Settings | File Templates.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">
    var addUrlList = "${phiz:calculateActionURL(pageContext,'/departments/child')}";
    /*Показывает или скрывает дочерние подразделения по id родительского.*/
    function showChild(id)
    {
        var elem = document.getElementById(id);
        if (elem.style.display == "none")
        {
            var tr = document.getElementById(id).getElementsByTagName('TBODY')[0].getElementsByTagName('TR')[0];
            if (tr == undefined)
            {
                ajaxQuery(
                        "id=" + id  + "<c:if test='${form.level != null}'>&level=${form.level}</c:if>",
                        addUrlList,
                        function(children){addRows(id, children);showSelectLinkFirst(id);},
                        null,
                        true);
            }
            else
                showSelectLink(id);
            elem.style.display = "";
        }
        else
        {
            elem.style.display = "none";
            delSelectLink(id);
        }
    }

    <%-- Отобразить ссылку "выбрать подчиненные"/"очистить" --%>
    function showSelectLink(id)
    {
        var input = document.getElementById('input'+id);
        if (input.name == 'notAllowed')
            return;
        var selAsParent = document.getElementById('selAsParent'+id).checked;
        if (input.disabled && selAsParent)
        {
            var unselectChild = document.getElementById('unselectChild'+id);
            unselectChild.style.display = "";
        }
        else if (!input.disabled)
            if (selAsParent)
            {
                var unselectChild = document.getElementById('unselectChild'+id);
                unselectChild.style.display = "";
            }
            else
            {
                var selectChild = document.getElementById('selectChild'+id);
                selectChild.style.display = "";
            }
    }

    <%-- Отобразить ссылку "выбрать подчиненные"/"очистить" после получения подчиненных департаментов через ajax --%>
    function showSelectLinkFirst(id)
    {
        var elem = document.getElementById('input' + id);
        if (elem.name == 'notAllowed')
            return;
        if (elem.disabled)
        {
            checkAllChilds(id, true);
            delSelectLink(id);
        }
        else
        {
            var selectChild = document.getElementById('selectChild'+id);
            selectChild.style.display = '';
        }
    }

    <%-- Выбрать/очистить все подчиненные --%>
    <%-- select == true - выбрать, select == false - очистить --%>
    function checkAllChilds(id, select)
    {
        var elem = document.getElementById('input'+id);
        var parent = document.getElementById('selAsParent' + id);
        if (elem.name != 'notAllowed')
            elem.checked = select;
        if (!(select && elem.disabled))
        {
            parent.checked = select;
            var dep = document.getElementById('dep'+id);
            dep.checked = select;
        }
        setDisabled(select, elem);

        var elements = document.getElementById(id).getElementsByTagName('input');
        for (var i = elements.length-1; i >= 0; i--)
        {
            var input = elements.item(i);
            if (input.type == 'checkbox' && input.name == 'selectedIds')
            {
                if (select)
                    input.checked = false;
            }
            else if (input.type == 'checkbox')
            {
                input.checked = select;
            }
            else if (input.type == 'hidden' && input.name == 'selectedParents')
            {
                if (select)
                    input.checked = false;
                delSelectLink(input.value);
            }
            setDisabled(select, input);
        }
        setSelUnsel(id, select);
    }

    function setDisabled(disabled, input)
    {
        if (input.name == 'notAllowed')
            return;
        if (disabled)
            input.setAttribute('disabled','true');
        else
            input.removeAttribute('disabled');
    }

    <%-- Прячем ссылку "выбрать подчиненные"/"очистить" --%>
    function delSelectLink(id)
    {
        if ($('#input'+id).name == 'notAllowed')
            return;
        var unselectChild = document.getElementById('unselectChild'+id);
        unselectChild.style.display = "none";
        var selectChild = document.getElementById('selectChild'+id);
        selectChild.style.display = "none";
    }

    <%-- Отобразить ссылку "выбрать подчиненные"/"очистить" --%>
    function setSelUnsel(id, selectAll)
    {
        if ($('#input'+id).name == 'notAllowed')
            return;
        var unselectChild = document.getElementById('unselectChild'+id);
        var selectChild = document.getElementById('selectChild'+id);

        if (unselectChild != undefined && selectChild != undefined)
        {
            if (selectAll)
            {
                unselectChild.style.display = '';
                selectChild.style.display = 'none';
            }
            else
            {
                selectChild.style.display = '';
                unselectChild.style.display = 'none';
            }
        }
    }

    function checkDepartment(id)
    {
        var elem = document.getElementById('input'+id);
        var dep = document.getElementById('dep'+id);
        dep.checked = elem.checked;
    }

    function addRows(id, data)
    {
        if (trim(data) == '')
        {
            return false;
        }
        $("#" + id).html($(data));
        return true;
    }
</script>


<c:set var="form" value="${ListDepartmentsForm}"/>
<jsp:include page="child.jsp">
    <jsp:param name="nonbsp" value="true"/>
</jsp:include>
