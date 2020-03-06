package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket("localhost", 12345);
		
		System.out.println("Conexão Estabelecida");		
		
		//Enviar dados do servidor
		Thread threadEnviaComando = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Pode Enviar!");

				try {
					PrintStream saida = new PrintStream(socket.getOutputStream());
					
					Scanner teclado = new Scanner(System.in);
					while(teclado.hasNextLine()) {
						String linha = teclado.nextLine();
						
						if(linha.trim().equals("")) {
							break;
						}
						
						saida.println(linha);
					}
					
					teclado.close();
					saida.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		//Recebe dados do servidor
		Thread threadRecebeResposta = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Recebendo Dados do Servidor!");
				
				try {
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					
					while(respostaServidor.hasNextLine()) {
						String linha = respostaServidor.nextLine();
						
//						saida.println(linha);
						System.out.println(linha);
					}
					respostaServidor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		threadEnviaComando.start();
		threadRecebeResposta.start();
		
		//Thread Main espera o fim da thread da que está ligada para que não tenha conflito
		threadEnviaComando.join();
		
		System.out.println("Fechando o socket do cliente");
		socket.close();
		
	}
}
