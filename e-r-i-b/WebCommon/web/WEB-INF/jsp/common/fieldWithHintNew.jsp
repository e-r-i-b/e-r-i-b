<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>

<div class="form-row-addition form-row-new" id="${externalId}Row" onclick="payInput.onClick(this)">
    <div class="paymentLabelNew">
        <span class="paymentTextLabel">${fieldName}</span><c:if test="${required}"><span class="asterisk" id="asterisk_${externalId}">*</span></c:if>
    </div>
    <div class="paymentValue paymentValueNew">
        <div class="paymentInputDiv autoInputWidth" onkeyup="${onKeyUp}">
            ${data}

            <div style="display: none" class="description ${descriptionClass}">
                ${description}
            </div>
                <script type="text/javascript">
                    $(document).ready(function(){
                        $('.errorRed').hover(function(){
                            $(this).find('.errorDiv').addClass('autoHeight');
                            $(this).find('.showFullText').hide();
                        }, function() {
                            $(this).find('.errorDiv').removeClass('autoHeight');
                            $(this).find('.showFullText').show();
                        });
                    });
                </script>
                <div class="errorDivBase errorDivPmnt">
                    <div class="errorRed">
                        <div class="errMsg">
                            <div class="errorDiv"></div>
                            <div class="showFullText">
                                <div class="pointers"></div>
                            </div>
                        </div>
                        <div class="errorRedTriangle"></div>
                    </div>
                </div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript">
    if (document.getElementsByName("${externalId}").length == 1)
        document.getElementsByName("${externalId}")[0].onfocus = function() {
            payInput.onFocus(this); }
</script>