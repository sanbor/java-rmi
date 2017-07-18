import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ComputeEngineTest {
	static ComputeEngine engine;
	static Compute compute;

	@BeforeClass
	public static void instantiateServer() throws Exception {
		engine = ComputeEngine.start();
		new ComputePi();
		compute = ComputePi.getStub(ComputeEngine.getAddress());
	}

	@Test
	public void serverShouldListen() throws Exception {
		assertEquals(ComputeEngine.isListening(), true);
	}

	@Test
	public void shouldHaveStub() throws Exception {
		assertEquals(compute instanceof Compute, true);
	}
	
	@Test
	public void shouldComputePiWithTwoDigits() throws Exception {
		Task<BigDecimal> task = new Pi(2);
		assertTrue(3.14f == compute.executeTask(task).floatValue());
	}
	
	@Test
	public void shouldComputePiWithTeneDigits() throws Exception {
		Task<BigDecimal> task = new Pi(10);
		assertEquals(new BigDecimal("3.1415926536"), compute.executeTask(task));
	}
}
