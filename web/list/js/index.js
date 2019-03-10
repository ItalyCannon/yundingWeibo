function praise() {
    $.ajax({
        url: '/ListServlet?type=praise',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
        },
        async: true
    });
}

function repost() {
    $.ajax({
        url: '/ListServlet?type=repost',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
        },
        async: true
    });
}

window.onload = praise();