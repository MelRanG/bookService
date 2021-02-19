function formChange(obj){
    obj.submit();
}

$(function(){
    // Enabling Popover Example 2 - JS (hidden content and title capturing)
    $("#popoverExampleTwo").popover({
        container: 'body',
        html: true,
        placement: 'bottom',
        sanitize: false,

        content: function() {
            return $('#popoverHiddenContent').html();
        },
        title: function() {
            return $('#popoverHiddenTitle').html();
        },

    }).click(AjaxCall)
});

function AjaxCall() {
    $.ajax({
        type: 'GET',
        url : 'message.do',
        error : function(error){
            console.log("err" + error);
        },
        success : function(data) {
            popoverInsert(data)
        }
    });
}

function popoverInsert(data){
    data.forEach(function (v) {
        let table = document.getElementById('popoverTable');
        let tr = document.createElement('tr');
        let m_td = document.createElement('td');
        let d_td = document.createElement('td');

        m_td.innerHTML = v.message;
        d_td.innerHTML = v.sendDate;
        tr.appendChild(m_td);
        tr.appendChild(d_td);
        let tbody = table.children[1];
        tbody.appendChild(tr);
    })

}




