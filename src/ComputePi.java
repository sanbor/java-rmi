import java.rmi.Naming;

public class ComputePi { 
	public static Compute getStub(String address) throws Exception {
        return (Compute) Naming.lookup(address);   
	}
}
