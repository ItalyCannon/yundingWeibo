var words = ['新', '热', '沸', '荐'];

function load_home1() {
    window.location.href = "/home/index.html"
}

function praise() {
    $("#like").css("border-bottom", "3px solid #EA824B");
    $("#transmit").css("border-bottom", "");
    $.ajax({
        url: '/ListServlet?type=praise',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            console.log(data);
            var $list = $("#list");
            $list.html('');
            var html;
            var count;
            var style;
            var word;
            var display = '';
            var max = 3;
            var min = 0;
            for (var i = 0; i < data.length; ++i) {
                if (i == 0) {
                    count = "top";
                    style = "part_2";
                } else {
                    count = i;
                    style = "part_1";
                }
                if (i <= 7) {

                    word = words[Math.floor(Math.random() * (max - min + 1) + min)];
                    display = '';
                } else {
                    word = '';
                    display = 'style="display:none;"';
                }

                html = '<div class=' + style + '>' +
                    '<p class="part_1_1">' + count + '</p>' +
                    '<p class="part_1_2">' + data[i].weiboContent + '&nbsp;&nbsp;<span >' + data[i].praiseNum + '</span></p>' +
                    '<div class="hot" ' + display + '>' + word + '</div>' +
                    '</div>';
                $list.append(html);
            }
        },
        async: false
    });
}

function repost() {
    $("#transmit").css("border-bottom", "3px solid #EA824B");
    $("#like").css("border-bottom", "");

    $.ajax({
        url: '/ListServlet?type=repost',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            console.log(data);
            var $list = $("#list");
            $list.html('');
            var html;
            var count;
            var style;
            var word;
            var display = '';
            var max = 3;
            var min = 0;
            for (var i = 0; i < data.length; ++i) {
                if (i == 0) {
                    count = "top";
                    style = "part_2";
                } else {
                    count = i;
                    style = "part_1";
                }
                if (i <= 7) {

                    word = words[Math.floor(Math.random() * (max - min + 1) + min)];
                    display = '';
                } else {
                    word = '';
                    display = 'style="display:none;"';
                }

                html = '<div class=' + style + '>' +
                    '<p class="part_1_1">' + count + '</p>' +
                    '<p class="part_1_2">' + data[i].weiboContent + '&nbsp;&nbsp;<span >' + data[i].praiseNum + '</span></p>' +
                    '<div class="hot" ' + display + '>' + word + '</div>' +
                    '</div>';
                $list.append(html);
            }
        },
        async: false
    });
}

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
    praise();
    baseInfo();
};
