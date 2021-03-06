/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.test.wsdync;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Resource;
import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.*;
import org.redkale.test.ws.ChatMessage;
import org.redkale.test.ws.ChatService;
import org.redkale.test.ws.ChatWebSocket;

/**
 *
 * @author zhangjx
 */
//@WebServlet("/ws/chat")
public final class _DyncChatWebSocketServlet extends WebSocketServlet {

    @Resource
    private ChatService _redkale_resource_0;

    public _DyncChatWebSocketServlet() {
        super();
        this.messageTextType = _DyncChatWebSocketMessage.class;
    }

    @Override
    protected <G extends Serializable, T> WebSocket<G, T> createWebSocket() {
        return (WebSocket) new _DyncChatWebSocket(_redkale_resource_0);
    }

    @Override
    protected BiConsumer<WebSocket, Object> createRestOnMessageConsumer() {
        return new _DynRestOnMessageConsumer();
    }

    public static class _DyncChatWebSocket extends ChatWebSocket {

        public _DyncChatWebSocket(ChatService service) {
            super();
            this.service = service;
        }
    }

    public static class _DyncChatWebSocketMessage {

        public _DyncChatWebSocketMessage_sendmessagee sendmessage;

        public _DyncChatWebSocketMessage_joinroom joinroom;

        @Override
        public String toString() {
            return JsonConvert.root().convertTo(this);
        }
    }

    public static class _DyncChatWebSocketMessage_sendmessagee {

        public ChatMessage message;

        public Map<String, String> extmap;

        @Override
        public String toString() {
            return JsonConvert.root().convertTo(this);
        }
    }

    public static class _DyncChatWebSocketMessage_joinroom {

        public int roomid;

        @Override
        public String toString() {
            return JsonConvert.root().convertTo(this);
        }
    }

    public static class _DynRestOnMessageConsumer implements BiConsumer<WebSocket, Object> {

        @Override
        public void accept(WebSocket websocket0, Object message0) {
            _DyncChatWebSocket websocket = (_DyncChatWebSocket) websocket0;
            _DyncChatWebSocketMessage message = (_DyncChatWebSocketMessage) message0;
            if (message.sendmessage != null) {
                websocket.onChatMessage(message.sendmessage.message, message.sendmessage.extmap);
                return;
            }
            if (message.joinroom != null) {
                websocket.onJoinRoom(message.joinroom.roomid);
                return;
            }
        }

    }
}
