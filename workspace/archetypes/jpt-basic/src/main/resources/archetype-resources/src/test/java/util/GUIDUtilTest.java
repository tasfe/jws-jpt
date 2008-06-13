package ${package}.util;

import static org.junit.Assert.*;
import ${package}.util.GUIDUtil;

import org.junit.Test;

public class GUIDUtilTest {

	@Test
	public void testBuildMd5GUID() {
		assertEquals(GUIDUtil.buildMd5GUID(false).length(), 32);
	}

}
