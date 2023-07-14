<%--
    ��������� ��� �������� ��������� ����
    id - �� ���� (������������ ��� �������� �������� � ��������� ������)
    data - ������ ����
    loadAjaxUrl - ����� �������� ������� ���� ���������� ���������.
                    ���� �������� ����������� �� ������� ��������� �� �����.
                    ������ ����������� ������ ��� ������ �������� ����.
    closeCallback - js ������� ������� ����������� ����� ��������� ����.
                    ����� �������� �������� ���� ���������� ����� ������
                    ������� ��������� false (�������������� ��������)
    styleClass - ����� ���� ��� ����������� ��������� �������� ���� (�������������� ��������)
                 ����� ����, ���� � ����������� �������� �� ������ ���� ������� ������������.
    notCloseWindow - ����, ������� �� ���� ���������, ��� �������� ����� ����.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="input" uri="http://jakarta.apache.org/struts/tags-html" %>


<tiles:importAttribute/>
<div class="window ${styleClass} farAway" id="${id}Win">
    <input type="hidden" id="hiddenAjaxUrl${id}" value="${loadAjaxUrl}"/>
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="${color}"/>
        <tiles:put name="data">
            <div id="${id}">
                    ${data}
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
    <c:if test="${empty notShowCloseButton or not notShowCloseButton}">
        <div class="closeImg" title="�������" onclick="win.close('${id}');"></div>
    </c:if>
</div>

<c:if test="${closeCallback != ''}">

    <script type="text/javascript">
        $(document).ready(
                function()
                {
                    win.aditionalData('${id}', {closeCallback: ${closeCallback}});
                }
        );
    </script>
</c:if>

<c:if test="${loadAjaxUrl != ''}">

    <script type="text/javascript">
        $(document).ready(
                function()
                {
                    win.aditionalData('${id}', {onOpen: function (id)
                    {
                        var element = document.getElementById(id);

                        if (element.dataWasLoaded == true) return true;

                        var ajaxUrl = document.getElementById('hiddenAjaxUrl${id}');
                        win.loadAjaxData('${id}', ajaxUrl.value,
                                function ()
                                {
                                    element.dataWasLoaded = true;
                                    win.open('${id}', true
                                            <c:if test="${not empty notCloseWindow}">, "${notCloseWindow}"</c:if>
                                    );
                                    if (typeof payInput !== "undefined")
                                        payInput.setEvents(payInput);
                                    if (typeof onAfterLoad == "function")
                                        onAfterLoad();
                                }
                        );

                        return false;
                    }
                    });
                }
        );
    </script>
</c:if>