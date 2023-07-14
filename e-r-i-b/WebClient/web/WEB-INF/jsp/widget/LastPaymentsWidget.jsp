<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.LastPaymentsWidget"/>
    <tiles:put name="cssClassname" value="LastPaymentsWidget"/>
    <tiles:put name="borderColor" value="greenTop"/>
    <tiles:put name="sizeable" value="true"/>
    <tiles:put name="editable" value="true"/>

    <tiles:put name="viewPanel">
        <div id="popularPayments">
            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popularPaymentsData.jsp" flush="false">
                <tiles:put name="isWidget" value="true"/>
            </tiles:insert>
        </div>
    </tiles:put>

    <tiles:put name="editPanel">
        <div id="popularPayments">
            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popularPaymentsData.jsp" flush="false">
                <tiles:put name="isWidget" value="true"/>
            </tiles:insert>
        </div>
    </tiles:put>
    <tiles:put name="additionalSetting">
        <div class="pagination">
            <h2>Количество записей:</h2>
            <table cellspacing="0" cellpadding="0" class="tblNumRec EventWidget">
                <tr>
                    <td><div button="numberOfItems5"><span class="paginationSize">5</span></div></td>
                    <td><div button="numberOfItems10"><span class="paginationSize">10</span></div></td>
                    <td><div button="numberOfItems20"><span class="paginationSize">20</span></div></td>
                </tr>
            </table>
        </div>
    </tiles:put>
</tiles:insert>