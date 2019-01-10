package main;

import static spark.Spark.init;
import static spark.Spark.webSocket;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.Session;

import com.google.gson.JsonObject;

import handler.ConvertJSON;
import handler.SocrativeWebSocketHandler;
import mongoDB.InsertData;

public class MainWSSocrative {

	public static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
	public static AtomicInteger userNumber = new AtomicInteger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub				
		webSocket("/profesor", SocrativeWebSocketHandler.class);
		init();
	}

	public static void broadcastMessage(String sender, String message) {
		ConvertJSON cjson = new ConvertJSON();		
		//cjson.convert(message);
		userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
			try {
				//cjson.convert(message);
				System.out.println(message);
				new InsertData().insert(message);
				//System.out.println(message);
				//session.getRemote().sendString(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
