package spoon2.replace;

import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CtScannerTest {
	@Test
	public void testGenerateReplacementVisitor() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setGenerateJavadoc(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.setSourceOutputDirectory("./target/generated/");
		// interfaces.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/internal");
		// Utils.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.addInputResource("./src/main/java/spoon2/replace/");
		launcher.addProcessor(new ReplacementVisitorGenerator());
		launcher.setOutputFilter(new RegexFilter("spoon.support.visitor.replace.*"));
		launcher.run();
	}

	private class RegexFilter implements Filter<CtType<?>> {
		private final Pattern regex;

		private RegexFilter(String regex) {
			if (regex == null) {
				throw new IllegalArgumentException();
			}
			this.regex = Pattern.compile(regex);
		}

		public boolean matches(CtType<?> element) {
			Matcher m = regex.matcher(element.getQualifiedName());
			return m.matches();
		}

		public Class<CtElement> getType() {
			return CtElement.class;
		}
	}
}
