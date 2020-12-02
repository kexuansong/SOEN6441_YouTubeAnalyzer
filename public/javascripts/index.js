(function() {
    var parseTweets;
    var idSet = [];
    $(function() {

        //give you a jQuery object representing that node.
        if($("#search").length === 1) {
            var ws;
            console.log("Waiting for WebSocket");
            ws = new WebSocket($("body").data("ws-url"));
            ws.onmessage = function (event) {
                var message;
                message = JSON.parse(event.data);
                //idSet.add(message.videoId);
                idSet.push(message.videoTitle);

                var unique = idSet.filter(onlyUnique);
                // switch (message.type) {
                //     case "Videos":
                 //console.log(message);
                 //console.log(idSet)
                //console.log(unique);

                return parseTweets(unique);
                //     default:
                //         return console.log(message);
                // }

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

    function onlyUnique(value, index, self) {
        return self.indexOf(value) === index;
    }

// usage example:
//     var a = ['a', 1, 'a', 2, '1'];

    parseTweets = function(input) {
        // var query = message.query.replace(/ /g,'');
        // videosListQuery = $("#videosList"+query);
        //
        //
        // $("#videos").prepend('<div class="results"><p>' + idSet + '</p><ul id="videosList' + query + '"></ul></div>');

        // videosListQuery.prepend('<li><a href="http://localhost:9000/profile/'+message.user.name+'">'
        //     +message.user.name+'</a> wrote: '+message.user+'</li>');


        for (let i in input){
            var today  = new Date();
            var time = today.getMinutes() + ":" + today.getSeconds();
            $("#videos").prepend('<div class="results"><p>' + input[i] +'<br>' +time + '</p><br>' + '</div>');
        }

}}).call(this);