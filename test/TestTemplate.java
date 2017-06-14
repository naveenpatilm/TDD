import static org.junit.Assert.*;

import java.util.MissingResourceException;

import org.junit.Before;
import org.junit.Test;

public class TestTemplate {
	private Template template;
	
	@Before
	public void setUp() {
		template = new Template("${one}, ${two}, ${three}");
		template.set("one", "1");
		template.set("two", "2");
		template.set("three", "3");
	}
	
	@Test
	public void multipleVariables() throws Exception {
		assertTemplateEvaluatesTo("1, 2, 3");
	}
	
	@Test
	public void unknownVariablesAreIgnored() throws Exception {
		template.set("doesnotexist", "hi");
		assertTemplateEvaluatesTo("1, 2, 3");
	}
	
	@Test
	public void missingValueRaisesException() {
		try {
			new Template("${foo}").evaluate();
			fail("evaluate should throw exception if no value was specified");
		} catch(MissingValueException expected) {
			assertEquals("No value for ${foo}", expected.getMessage());
		}
	}
	
	@Test
    public void variablesGetProcessedJustOnce() throws Exception {
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
}
	
	private void assertTemplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
}

}
