
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

function CommentReceive() {
    $.ajax({
        url: '/MyCommentServlet?type=receive',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="collect" id="' + 'collect' + i + '">' +
                    '<img src="' + data[i].profilePicture + '" alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data[i].nickname + '</p>' +
                    '<p class="date">' + data[i].formatCommentTime + '</p>' +
                    '</div>' +
                    '<p class="text">回复<span>@' + user.nickname + '</span>：' + data[i].commentContent + '</p>' +
                    '<div class="reply">' +
                    '<p class="reply_text">' + user.nickname + ':' + data[i].weiboContent + '</p>' +
                    '</div>' +
                    '<div class="bottom">' +
                    '<p><i class="iconfont">&#xe643;</i> 回复</p>' +
                    '<p class="last"><i class="iconfont">&#xe60c;</i> ' + data[i].commentPraise + '</p>' +
                    '</div>' +
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
    CommentReceive();
};
