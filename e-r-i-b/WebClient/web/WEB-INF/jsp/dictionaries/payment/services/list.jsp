<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">
    var addUrl = "${phiz:calculateActionURL(pageContext,'/dictionaries/paymentService/edit')}";
    function doEdit()
    {
        checkIfOneItem("selectedIds");
        if (!checkSelection("selectedIds", '������� ���� ������') || (!checkOneSelection("selectedIds", '������� ������ ���� ������')))
            return;
        var id = getRadioValue(document.getElementsByName("selectedIds"));
        window.location = addUrl + "?id=" + encodeURIComponent(id);
    }

    function doAdd()
    {
        if (!checkOneSelectionOrNothing('selectedIds', '�������� �� ����� ����� ������'))
            return;
        var id = getRadioValue(document.getElementsByName("selectedIds"));
        if (!document.getElementById("child" + id))
        {
            window.location = addUrl + "?parentId=" + encodeURIComponent(id);
        }
        else
        {
            alert("��������� ����������� ��������� ������� �������� - 2.");
        }
    }
    function doRemove()
    {
        checkIfOneItem("selectedIds");
        if (checkSelection('selectedIds', '������� ���� �� ���� ������'))
        {
            var id = getRadioValue(document.getElementsByName("selectedIds"));
            if (!document.getElementById("parent" + id))
            {
                return confirm("�� ������������� ������ ������� " + getSelectedQnt('selectedIds') + " ������� ����� �� �����������?");
            }
            else
            {
                alert("������� ������ ��� ����������� ���������.");
            }
        }
        return false;
    }
</script>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="paymentServicesMain"/>
	</c:otherwise>
</c:choose>

<html:form action="/private/dictionaries/paymentService/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="PaymentServices"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="paymentServiceBundle" key="listpage.title"/></tiles:put>

        <!--����-->
        <tiles:put name="menu" type="string">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditPaymentServiceOperation">
                        <tiles:put name="commandTextKey" value="button.add"/>
                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                        <tiles:put name="bundle" value="paymentServiceBundle"/>
                        <tiles:put name="onclick" value="doAdd();"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"         value="paymentServiceBundle"/>
                        <tiles:put name="onclick"        value="javascript:window.close()"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>

        </tiles:put>
        <!-- ������ -->
        <tiles:put name="data" type="string">
            <c:set var="paymentServices" value="${form.data}"/>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="PaymentServiceList"/>
                <tiles:put name="text" type="string"><bean:message bundle="paymentServiceBundle" key="table.title"/></tiles:put>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${standalone}">
                            <tiles:insert definition="clientButton" flush="false" operation="EditPaymentServiceOperation">
                                <tiles:put name="commandTextKey" value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                <tiles:put name="bundle" value="paymentServiceBundle"/>
                                <tiles:put name="image" value="iconSm_edit.gif"/>
                                <tiles:put name="onclick" value="doEdit();"/>
                            </tiles:insert>

                            <tiles:insert definition="commandButton" flush="false" operation="RemovePaymentServiceOperation">
                                <tiles:put name="commandKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="image" value="iconSm_delete.gif"/>
                                <tiles:put name="bundle" value="paymentServiceBundle"/>
                                <tiles:put name="validationFunction">
                                    doRemove()
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <script type="text/javascript">
                                function sendPaymentServiceData()
                                {
                                    checkIfOneItem("selectedIds");
                                    if (!checkOneSelection("selectedIds", "�������� ���� ������!"))
                                       return;
                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var res = new Array(2);
                                            var elemName = ensureElementByName("name"+ids.item(i).value);
                                            res['name'] = trim(elemName.value);
                                            res['id'] = ids.item(i).value;
                                            window.opener.setPaymentServiceInfo(res);
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("�������� ������!");
                                }

                            </script>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.send.paymentService"/>
                                <tiles:put name="commandHelpKey" value="button.send.paymentService.help"/>
                                <tiles:put name="bundle" value="paymentServiceBundle"/>
                                <tiles:put name="image" value="iconSm_select.gif"/>
                                <tiles:put name="onclick">sendPaymentServiceData()</tiles:put>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
                <tiles:put name="data">
                    <%@ include file="tree.jsp"%>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty paymentServices}"/>
                <tiles:put name="emptyMessage" type="string"><bean:message bundle="paymentServiceBundle" key="empty.message"/></tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>

</html:form>
