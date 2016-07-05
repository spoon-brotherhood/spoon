package spoon.processing;

import org.junit.Test;
import spoon.Launcher;
import spoon.generating.CloneVisitorGenerator;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Filter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spoon.testing.Assert.assertThat;
import static spoon.testing.utils.ModelUtils.build;

public class CtGenerationTest {
	@Test
	public void testGenerateCloneVisitor() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.setSourceOutputDirectory("./target/generated/");
		// interfaces.
		launcher.addInputResource("./src/main/java/spoon/reflect/code");
		launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("./src/main/java/spoon/reflect/reference");
		launcher.addInputResource("./src/main/java/spoon/reflect/internal");
		// Implementations.
		launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
		launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
		launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
		launcher.addInputResource("./src/main/java/spoon/support/reflect/internal");
		// Utils.
		launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtInheritanceScanner.java");
		launcher.addInputResource("./src/main/java/spoon/generating/clone/");
		launcher.addProcessor(new CloneVisitorGenerator());
		launcher.setOutputFilter(new RegexFilter("spoon.support.visitor.clone.*"));
		launcher.run();

		assertThat(build(new File("./src/main/java/spoon/support/visitor/clone/")).Package().get("spoon.support.visitor.clone"))
				.isEqualTo(build(new File("./target/generated/spoon/support/visitor/clone/")).Package().get("spoon.support.visitor.clone"));
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
