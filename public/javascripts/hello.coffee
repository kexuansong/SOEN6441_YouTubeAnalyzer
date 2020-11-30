$ ->
    ws = new WebSocket $("body").data("ws-url")
    ws.onmessage = (event) ->
        message = JSON.parse event.data
        $('#searchkey').append message.time + "<br/>"