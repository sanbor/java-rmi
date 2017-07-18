import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Compute{
	static boolean listening = false;
	static int port = 1099;
	static String addres = "//localhost/Compute";

	public static int getPort() {
		return port;
	}

	public static String getAddress() {
		return addres;
	}

	public static ComputeEngine instance;

	public ComputeEngine() {
		super();
	}

	public static ComputeEngine start() throws RemoteException, AlreadyBoundException, MalformedURLException {
		if (instance != null) {
			return instance;
		}

		instance = new ComputeEngine();
		Compute stub = (Compute) UnicastRemoteObject.exportObject(instance, 0);
		LocateRegistry.createRegistry(port);
        Naming.rebind (addres, stub);
		listening = true;
		System.out.println("ComputeEngine bound");

		return instance;
	}
	
	public static boolean isListening() {
		return ComputeEngine.listening;
	}

	public static void main(String[] args) {
		try {
			start();
		} catch(Exception e) {
			System.out.println("ComputeEngine exception:");
			e.printStackTrace();
		}
	}

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		return t.execute();
	}

}
