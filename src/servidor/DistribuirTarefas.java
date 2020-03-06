package servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	
	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("Distribuindo as tarefas para o cliente: " + socket);

		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while(entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("Comando:" + comando);
				
				switch (comando) {
				case "Olá":
					saidaCliente.println("Mundo");
					break;
				case "Hello":
					saidaCliente.println("World");
					break;
				default:
					saidaCliente.println("Comando não encontrado!");
				}
			}
			entradaCliente.close();
			saidaCliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
