/*
 *������� ��� ������ � ��
 */
function RSAObject()
{
    this.domDataCollection = new DomDataCollection();
    this.domDataCollection.startInspection();

    if (window.addEventListener)
    {
        window.addEventListener('load', UIEventCollector.initEventCollection, false);
    }
    else if (window.attachEvent)
    {
        window.attachEvent('onload', UIEventCollector.initEventCollection);
    }
    else
    {
        window.onload = UIEventCollector.initEventCollection;
    }
}

/**
 * ����������� ��������� ������ � ������� ����
 */
RSAObject.prototype.toHiddenParameters = function()
{
    addFieldWithCheck('hidden', 'deviceprint', add_deviceprint());

    var htmlinjection = this.domDataCollection.domDataAsJSON();
    addFieldWithCheck('hidden', 'htmlinjection', htmlinjection);
    addFieldWithCheck('hidden', 'domElements',   htmlinjection);

    var manvsmachinedetection = UIEventCollector.serialize();
    addFieldWithCheck('hidden', 'manvsmachinedetection',    manvsmachinedetection);
    addFieldWithCheck('hidden', 'jsEvents',                 manvsmachinedetection);
}

/**
 * ����������� ��������� ������ � �����
 * @param form �����
 */
RSAObject.prototype.toSubmitFormParameters = function(form)
{
    this.toHiddenParameters();

    form.appendChild(getElement('deviceprint'));
    form.appendChild(getElement('manvsmachinedetection'));
    form.appendChild(getElement('htmlinjection'));
    form.appendChild(getElement('domElements'));
    form.appendChild(getElement('jsEvents'));
}

/**
 * ����������� ��������� ������ ��� ���������� �������
 * @returns {string}
 */
RSAObject.prototype.toRequestParameters = function()
{
    var jsEvents    = encodeURIComponent(UIEventCollector.serialize());
    var domElements = encodeURIComponent(this.domDataCollection.domDataAsJSON());
    var deviceprint = encodeURIComponent(add_deviceprint());
    return "&deviceprint=" + deviceprint + "&jsEvents=" + jsEvents + "&domElements=" + domElements;
}