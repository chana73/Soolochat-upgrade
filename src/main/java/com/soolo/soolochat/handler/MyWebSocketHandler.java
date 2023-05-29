package com.soolo.soolochat.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println(session+"연결");
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println(session+"연결 종료");
		sessions.remove(session);
	}

	public void closeSession(WebSocketSession session) {
		try {
			session.close();
		} catch (IOException e) {
			// 처리할 예외 처리
		}
	}
}