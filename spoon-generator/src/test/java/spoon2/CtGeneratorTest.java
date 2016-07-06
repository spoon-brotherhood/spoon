package spoon2;

import org.junit.Test;
import spoon.Launcher;
import spoon2.clone.CloneVisitorGenerator;
import spoon2.equals.EqualsVisitorGenerator;
import spoon2.replace.ReplacementVisitorGenerator;
import spoon2.scanner.CtBiScannerGenerator;

public class CtGeneratorTest {
	@Test
	public void testGenerateReplacementVisitor() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().useTabulations(true);
		// interfaces.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/internal");
		// Utils.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.addInputResource("./src/main/java/spoon2/replace/");
		launcher.addProcessor(new ReplacementVisitorGenerator());
		launcher.buildModel();
	}

	@Test
	public void testGenerateCtBiScanner() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.setSourceOutputDirectory("./target/generated/");
		// interfaces.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/internal");
		// Utils.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.addInputResource("./src/main/java/spoon2/scanner/");
		launcher.addProcessor(new CtBiScannerGenerator());
		launcher.run();
	}

	@Test
	public void testGenerateEqualsVisitor() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.setSourceOutputDirectory("./target/generated/");
		// interfaces.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/internal");
		// Utils.
		launcher.addInputResource("./src/main/java/spoon2/equals/");
		launcher.addInputResource("target/generated/spoon/reflect/visitor/");
		launcher.addProcessor(new EqualsVisitorGenerator());
		launcher.run();
	}

	@Test
	public void testGenerateCloneVisitor() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.setSourceOutputDirectory("./target/generated/");
		// interfaces.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/internal");
		// Implementations.
		launcher.addInputResource("../spoon/src/main/java/spoon/support/reflect/code");
		launcher.addInputResource("../spoon/src/main/java/spoon/support/reflect/declaration");
		launcher.addInputResource("../spoon/src/main/java/spoon/support/reflect/reference");
		launcher.addInputResource("../spoon/src/main/java/spoon/support/reflect/internal");
		// Utils.
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.addInputResource("../spoon/src/main/java/spoon/reflect/visitor/CtInheritanceScanner.java");
		launcher.addInputResource("./src/main/java/spoon2/clone/");
		launcher.addProcessor(new CloneVisitorGenerator());
		launcher.run();
	}
}
