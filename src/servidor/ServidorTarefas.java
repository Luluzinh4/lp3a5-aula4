package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	public static void main(String[] args) throws IOException {
		System.out.println(" ------- Iniciando Servidor ------- ");
		
		@SuppressWarnings("resource")
		ServerSocket servidor = new ServerSocket(12345);
		
//		ExecutorService poolThread = Executors.newFixedThreadPool(2);
		//Conforme a demanda, ele vai criando mais threads
		ExecutorService poolThread = Executors.newCachedThreadPool();
		
		while(true) {
			// Aceitando Requisição
			Socket socket = servidor.accept();
			System.out.println("Aceitando novo cliente em nova porta: " + socket.getPort());
		
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			
			poolThread.execute(distribuirTarefas);
			
//			new Thread(distribuirTarefas).start();
		}

	}
	
}
