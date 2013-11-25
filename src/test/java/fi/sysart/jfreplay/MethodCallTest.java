package fi.sysart.jfreplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MethodCallTest {

	@Test
	public void equal_when_all_content_is_equal() {
		MethodCall m1 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "bar" });
		MethodCall m2 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "bar" });
		assertEquals(m1, m2);
		assertEquals(m1.hashCode(), m2.hashCode());
	}

	@Test
	public void not_equal_when_name_is_different() {
		MethodCall m1 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "bar" });
		MethodCall m2 = new MethodCall("bar", new Class[] { String.class }, new Object[] { "bar" });
		assertFalse(m1.equals(m2));
	}

	@Test
	public void not_equal_when_arg_value_is_different() {
		MethodCall m1 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "bar" });
		MethodCall m2 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "xyz" });
		assertFalse(m1.equals(m2));

	}

	@Test
	public void not_equal_when_argtype_is_different() {
		MethodCall m1 = new MethodCall("foo", new Class[] { String.class }, new Object[] { "bar" });
		MethodCall m2 = new MethodCall("foo", new Class[] { Integer.class }, new Object[] { "bar" });
		assertFalse(m1.equals(m2));
	}

}
