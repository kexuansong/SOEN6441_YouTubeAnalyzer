(function() {
    var parseTweets;
    $(function() {
        //give you a jQuery object representing that node.
        if($("#search").length === 1) {
            var ws;
            console.log("Waiting for WebSocket");
            ws = new WebSocket($("body").data("ws-url"));
            ws.onmessage = function (event) {
                var message;
                message = JSON.parse(event.data);
                switch (message.type) {
                    case "Videos":
                        return parseTweets(message);
                    default:
                        return console.log(message);
                }
            };
            return $("#searchForm").submit(function (event) {
                event.preventDefault();
                if ($("#searchkey").val() !== '') {
                    console.log("Sending WS with value " + $("#searchkey").val());
                    ws.send(JSON.stringify({
                        query: $("#searchkey").val()
                    }));
                    return $("#searchkey").val("");
                }
            });
        }
    });

    parseTweets = function(message) {
        var query = message.query.replace(/ /g,'');
       videosListQuery = $("#videosList"+query);
        if (videosListQuery.length === 0) {
            $("#videos").prepend('<div class="results"><p>Tweets for '+message.query+'</p><ul id="videosList'+query+'"></ul></div>');
        }
        videosListQuery.prepend('<li><a href="http://localhost:9000/profile/'+message.user.name+'">'
            +message.user.name+'</a> wrote: '+message.user+'</li>');

    }

}).call(this);
