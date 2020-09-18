package application.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;

import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class FunctionCommand extends MainCommand{

	public FunctionCommand(SocketClient client,Connection con) {
		super(client,con);
	}
	public void run() {
		
	}
}
