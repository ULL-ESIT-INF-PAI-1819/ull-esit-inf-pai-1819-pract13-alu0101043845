
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Clase que permite realizar pruebas sobre la clase Trayectorias
 */
public class TestDisparos {
	
	/**
	 * Test para comprobar los constructores
	 */
	@Test
	void comprobarConstructores() {
		Disparos tr = new Disparos();
		assertNotNull(tr);
	}


	/**
	 * Test para comprobar Funcion
	 */
	@Test
	void comprobarFunction() {
		Disparos tr = new Disparos();
		tr.ActualizarCanon(new Point(100,100));
		assertEquals(tr.getAngle(), 45);
	}
}
