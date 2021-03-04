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
            console.log("err",error);
        },
        success : function(data) {
            console.log("Ajax Success")
            popoverInsert(data);
        }
    });
}

function popoverInsert(data){
    let tbody = document.getElementById('messageBody');

    //데이터가 비어있지 않다면
    if(tbody.children[0] != null) {
        $('#messageBody').empty();
        data.slice().reverse().forEach(function (v) {
            const newRow = tbody.insertRow(0);
            const mCell = newRow.insertCell(0);
            const dCell = newRow.insertCell(1);
            mCell.innerHTML = v.message;
            dCell.innerHTML = v.sendDate;
        })
        // if(date_td[0].innerHTML !== data[0].sendDate){
        //     let tr = document.createElement('tr');
        //     let m_td = document.createElement('td');
        //     let d_td = document.createElement('td');
        //     m_td.innerHTML = data[0].message;
        //     d_td.innerHTML = data[0].sendDate;
        //     tr.appendChild(m_td);
        //     tr.appendChild(d_td);
        //     tbody.prepend(tr);
        // }
    }
}




