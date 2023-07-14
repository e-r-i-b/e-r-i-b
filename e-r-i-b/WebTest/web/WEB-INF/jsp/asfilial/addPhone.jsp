<script type="text/javascript">
   var dojoConfig = {
                async: true,
                parseOnLoad: true,
                baseUrl: "${initParam.resourcesRealPath}/scripts/",     // корень со всеми js
                packages: [
                    { name: "dojo", location: "dojo" },                 // здесь лежит dojo

                ]
            };
</script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dojo/dojo.js"></script>
<script type="text/javascript">
    require(["dojo/ready", "dojo/on", "dojo/query","dojo/dom-construct"], function(ready, on, query,dom)
    {
        function addPhoneField(onClieckElement, phoneNumberNInputName, mobilePhoneOperatorInputName, confirmCodeInputName, toElementName, position)
        {
            on(onClieckElement, "click", function()
            {
                var numbers = query("input[name=" + phoneNumberNInputName + "]");
                if (!((numbers.length != 0) && (numbers[numbers.length - 1].value == "")))
                {

                    dom.place('<input name="' + phoneNumberNInputName + '" maxlength="11" size="11"/><input name="' + mobilePhoneOperatorInputName + '" maxlength="100" size="10"/>',
                            toElementName, position);

                    if (confirmCodeInputName != null)
                    {

                        dom.place('<input name="' + confirmCodeInputName + '" maxlength="10" size="10"/>',
                                toElementName, position);
                    }
                    dom.place('</p>', toElementName, position);
                }

            });

        }
