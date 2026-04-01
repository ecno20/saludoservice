package unam.diplomado.kubernetes.saludo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaludoApplicationTests {
	//Se agrega TestFail para verificar la seguridad en el PipeLine
	@Test
	void testFail() {
    //	assertEquals(1, 2); // Esto causará que el pipeline se detenga
		assertEquals(2, 2); // Esto causará que pase la prueba ya que coinciden
}
	//Se Comenta prueba para efectuar TEST en pipeline y verificar las medidas de seguridad
	// @Test
	// void contextLoads() {
	// }

}
