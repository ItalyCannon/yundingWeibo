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
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}

function load_home1() {
    window.location.href = "/home/index.html"
}

function showList() {
    window.location.href = "/list/index.html"
}

window.onload = function () {
    baseInfo();
    CommentReceive();
};
