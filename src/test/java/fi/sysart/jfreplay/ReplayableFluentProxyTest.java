package fi.sysart.jfreplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;

import fi.sysart.jfreplay.ReplayableFluentProxyTest.ModificationSet.ReplayableModificationSet;

public class ReplayableFluentProxyTest {

	public interface ModificationSet<T extends ModificationSet> extends Serializable {

		interface ReplayableModificationSet extends ModificationSet<ReplayableModificationSet>,
				Replayable<ModificationSet> {}

		T updateName(String name);

		T increaseAge(int years);
	}

	public static class RealModificationSet implements ModificationSet, Cloneable {

		String name;
		private int years;

		public ModificationSet updateName(String name) {
			RealModificationSet clone = clone();
			clone.name = name;
			return clone;
		}

		public ModificationSet increaseAge(int years) {
			RealModificationSet clone = clone();
			clone.years = years;
			return clone;
		}

		public String getName() {
			return name;
		}

		public int getYears() {
			return years;
		}

		protected RealModificationSet clone() {
			try {
				return (RealModificationSet) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Test
	public void fluent_usage() {
		ReplayableModificationSet ms = ReplayableFluentProxyFactory
				.createProxy(ReplayableModificationSet.class);

		ms = ms.updateName("Jack").increaseAge(4);

		RealModificationSet rms = ms.replay(new RealModificationSet());
		assertEquals("Jack", rms.getName());
		assertEquals(4, rms.getYears());
	}

	@Test
	public void fluent_returns_new_instance_for_each_record_call() {
		ReplayableModificationSet ms = ReplayableFluentProxyFactory
				.createProxy(ReplayableModificationSet.class);

		ModificationSet ms2 = ms.updateName("Jack");
		assertNotSame(ms, ms2);
	}

	@Test
	public void hashCode_is_not_proxyied() {
		ReplayableModificationSet ms = ReplayableFluentProxyFactory
				.createProxy(ReplayableModificationSet.class);
		int hash = ms.hashCode();
		assertTrue(hash > 0);
	}

	@Test
	public void equal_proxies_are_equal() {
		ReplayableModificationSet ms = ReplayableFluentProxyFactory
				.createProxy(ReplayableModificationSet.class);

		ReplayableModificationSet ms1 = ms.updateName("A");
		ReplayableModificationSet ms2 = ms.updateName("A");

		assertEquals(ms1, ms2);
	}

}
