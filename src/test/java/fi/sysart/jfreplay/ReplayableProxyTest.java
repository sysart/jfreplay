package fi.sysart.jfreplay;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import org.junit.Test;

import fi.sysart.jfreplay.ReplayableProxyTest.ModificationSet.ReplayableModificationSet;

public class ReplayableProxyTest {

	public interface ModificationSet extends Serializable {

		interface ReplayableModificationSet extends ModificationSet, Replayable<ModificationSet> {}

		void updateName(String name);

		void increaseAge(int years);
	}

	public static class RealModificationSet implements ModificationSet {

		private String name;
		private int years;

		public void updateName(String name) {
			this.name = name;
		}

		public void increaseAge(int years) {
			this.years = years;
		}

		public String getName() {
			return name;
		}

		public int getYears() {
			return years;
		}
	}

	@Test
	public void fluent_usage() {
		ReplayableModificationSet ms = ReplayableProxyFactory
				.createProxy(ReplayableModificationSet.class);

		ms.updateName("Jack");
		ms.increaseAge(4);

		RealModificationSet rms = ms.replay(new RealModificationSet());
		assertEquals("Jack", rms.getName());
		assertEquals(4, rms.getYears());
	}
}
