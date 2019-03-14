
function load_home1() {
    window.location.href = "/home/index.html"
}

function load_home2() {
    window.location.href = "/collection/index.html";
}

function load_home3() {
    window.location.href = "/praise/index.html";
}

function load_home4() {
    window.location.href = "/commentReceive/index.html";
}

function show_list() {
    window.location.href = "/list/index.html";
}

function loadDetail() {
    window.location.href = "/detail/index.html"
}

function load_receive() {
    window.location.href = "/commentReceive/index.html"
}

function load_send() {
    window.location.href = "/commentSend/index.html"
}


function CommentSend() {
    $.ajax({
        url: '/MyCommentServlet?type=send',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="part_1">' +
                    '<p class="content">' + data[i].commentContent + '</p>'
                    + '<div class="text"><p>' + data[i].formatCommentTime + ' 评论了 ' + data[i].nickname + '的微博: '
                    + data[i].weiboContent + '</p></div>' +
                    '</div>';
                noApplicationRecord.innerHTML = html
            }
        },
        async: true
    });
}


var user;

function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
            $("#profilePicture").attr('src', user.profilePicture);
            $("#profile1").attr('src', user.profilePicture);
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}

window.onload = function () {
    baseInfo();
    CommentSend();
};