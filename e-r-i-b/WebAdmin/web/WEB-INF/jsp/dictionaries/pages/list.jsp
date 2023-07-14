<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean"%>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/dictionaries/pages/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string"><bean:message key="page.title" bundle="pagesBundle"/></tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="pagesBundle"/>
                <tiles:put name="onclick"        value="javascript:window.close()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.name"/>
                <tiles:put name="bundle" value="pagesBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="size" value="100"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="PagesList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.select"/>
                        <tiles:put name="commandHelpKey" value="button.select.help"/>
                        <tiles:put name="bundle" value="pagesBundle"/>
                        <tiles:put name="onclick">sendPageData()</tiles:put>
                    </tiles:insert>
                    <script type="text/javascript">

                        function addRows(id, data)
                        {
                            if (trim(data) == '')
                            {
                                return false;
                            }
                            $("#" + id).html($(data));
                            return true;
                        }

                        function showChild(id)
                        {
                            var getChildForGroupActionUrl = "${phiz:calculateActionURL(pageContext,'/dictionaries/pages/child')}";
                            var elem = document.getElementById(id);
                            var img = document.getElementById('img'+id);
                            if (elem.style.display == "none")
                            {
                                var tr = document.getElementById(id).getElementsByTagName('TBODY')[0].getElementsByTagName('TR')[0];
                                if (tr == undefined)
                                {
                                    ajaxQuery("filter(parentId)=" + id, getChildForGroupActionUrl, function(data){addRows(id, data);openList(id);}, null, true);
                                }
                                elem.style.display = "";
                                img.src = "${imagePath}/minus.gif";
                                openList(id);
                            }
                            else
                            {
                                elem.style.display = "none";
                                img.src = "${imagePath}/plus.gif";
                            }
                        }

                        //�������� ��� �������� ��� ������ ��� ������� �� "�������� ���".
                        //������� ��������� �� ���� ������� ��� ������ ��� ������� �� "��������".
                        // select - ���������� ����������: true - �������� ���, false - �������� ���
                        function checkAllChilds(id, select)
                        {
                            var elem = document.getElementById('id'+id);
                            elem.checked = select;

                            var elements = document.getElementById(id).getElementsByTagName('input');
                            for (var i = elements.length-1; i >= 0; i--)
                            {
                                var input = elements.item(i);
                                if (input.type == 'checkbox')
                                    input.checked = select;
                                else if (input.name == 'selectedIds' && input.type == 'hidden')
                                {
                                    input.checked = select;
                                    setSelUnsel(input.value, select);
                                }
                            }
                            setSelUnsel(id, select);
                        }

                        // �������� ������. ���� ��� �������� => ������ �������� ��� ������/�������� � ���.
                        function openList(id)
                        {
                            var elem = document.getElementById('id'+id);
                            var elements = document.getElementById(id).getElementsByTagName('input');
                            if (elem.checked)
                            {
                                checkAllChilds(id, true);    
                            }
                        }

                        function setSelUnsel(id, selectAll)
                        {
                            var unselectChild = document.getElementById('unselectChild'+id);
                            var selectChild = document.getElementById('selectChild'+id);

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

                        // ������/������� ��������� �������.
                        function selectPage(id)
                        {
                            var elem = document.getElementById('id' + id);

                            // ���� ��������/����� ��������� � ���� ������
                            if (elem.name == 'selectedIds' && elem.type == 'hidden')
                            {
                                var selectChild = document.getElementById('selectChild'+id);
                                if (selectChild.style.display == '')
                                    checkAllChilds(id, true);
                                else
                                    checkAllChilds(id, false);
                            }

                            // ����� ��������� � �������� =>
                            // ���� ���� �������� ��� ������, �� ������ ����� ��������� � ������ (������������)
                            if (!elem.checked)
                            {
                                var parent = findParentByClassName(elem, 'pageTree');
                                while (parent != undefined && parent != document) {
                                    // ������ ��������/������ ��������� � TABLE � ��������� className='pageTree'.
                                    // � TABLE ���� id, �� �������� ����� ����� ���������������
                                    // input, � �������� ������� ���������.
                                    var input = document.getElementById('id' + parent.id);
                                    input.checked = false;
                                    setSelUnsel(parent.id, false);
                                    parent = findParentByClassName(parent.parentNode, 'pageTree');
                                }
                            }
                            // ��������� ��������� � ������/�������� =>
                            // ���� ���� �������� ��� ������, �� ������ ��������� ��������� � ������ (������������)
                            if (elem.checked)
                            {
                                var parent = findParentByClassName(elem, 'pageTree');
                                while (parent != undefined && parent != document) {
                                    var allChecked = true;
                                    var inputs = parent.getElementsByTagName('input');

                                    // ���������, ��� �� �������� � ������ ��������.
                                    for (var i = inputs.length - 1; i >= 0; i--)
                                        if (inputs.item(i).type == 'checkbox' && !inputs.item(i).checked)
                                            allChecked = false;

                                    // ���� �������� ��� ��������, �� ������ ��������� ��������� � ������������ ������.
                                    if (allChecked)
                                    {
                                        var parentInput = document.getElementById('id' + parent.id);
                                        parentInput.checked = true;
                                        setSelUnsel(parent.id, true);
                                    }
                                    parent = findParentByClassName(parent.parentNode, 'pageTree');
                                }
                            }
                        }
                        function sendPageData()
                        {
                            if (!checkSelection("selectedIds", "<bean:message key="error.selection" bundle="pagesBundle"/>"))
                               return false;
                            var ids = document.getElementsByName("selectedIds");

                            var results = [];
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids.item(i).checked)
                                {
                                    var elemName = ensureElementByName("name" + ids.item(i).value);
                                    results.push({'name': trim(elemName.value), 'id': ids.item(i).value});
                                }
                            }
                            window.opener.setPageInfo(results);
                            window.close();
                            return true;
                        }

                    </script>
                </tiles:put>

                <tiles:put name="data">
                    <c:set var="form" value="${ListPagesForm}"/>
                    <jsp:include page="child.jsp">
                        <jsp:param name="nonbsp" value="true"/>
                    </jsp:include>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="message.empty" bundle="pagesBundle"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
