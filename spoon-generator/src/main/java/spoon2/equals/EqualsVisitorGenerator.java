/**
 * Copyright (C) 2006-2015 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/spoon/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon2.equals;

import spoon.processing.AbstractManualProcessor;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.CtScanner;

import java.lang.annotation.Annotation;

import static spoon2.RefactoringHelper.renameReferences;

public class EqualsVisitorGenerator extends AbstractManualProcessor {
	private static final String TARGET_EQUALS_PACKAGE = "spoon.support.visitor.equals";
	private static final String GENERATING_EQUALS_PACKAGE = "spoon2.equals";
	private static final String GENERATING_EQUALS = GENERATING_EQUALS_PACKAGE + ".EqualsVisitorTemplate";
	private static final String IGNORED_BY_EQUALS = "IgnoredByEquals";
	private static final String GENERATING_IGNORED_BY_EQUALS = GENERATING_EQUALS_PACKAGE + "." + IGNORED_BY_EQUALS;
	private static final String EQUALS_CHECKER = "EqualsChecker";
	private static final String GENERATING_EQUALS_CHECKER = GENERATING_EQUALS_PACKAGE + "." + EQUALS_CHECKER;

	@Override
	public void process() {
		final CtClass<Object> target = createEqualsVisitor();
		final CtType<Object> equalsCheckerType = target.getFactory().Type().get(GENERATING_EQUALS_CHECKER);
		target.getPackage().addType(target.getFactory().Type().get(GENERATING_IGNORED_BY_EQUALS));
		target.getPackage().addType(equalsCheckerType);
		renameReferences(target, IGNORED_BY_EQUALS, target.getPackage().getReference(), GENERATING_IGNORED_BY_EQUALS);
		renameReferences(target, EQUALS_CHECKER, target.getPackage().getReference(), GENERATING_EQUALS_CHECKER);
		renameReferences(equalsCheckerType, EQUALS_CHECKER, equalsCheckerType.getPackage().getReference(), GENERATING_EQUALS_CHECKER);
		renameReferences(target, "CtBiScannerDefault", target.getFactory().Package().createReference("spoon.reflect.visitor"), "spoon2.scanner.CtAbstractBiScanner");
		insertStartingPoint(target);
		new CtScanner() {
			@Override
			public <T> void visitCtMethod(CtMethod<T> element) {
				if (!element.getSimpleName().startsWith("visitCt")) {
					return;
				}

				Factory factory = element.getFactory();
				CtMethod<T> clone = factory.Core().clone(element);

				final CtAnnotation<?> ignoredAnnotation = factory.Core().createAnnotation();
				ignoredAnnotation.setAnnotationType(factory.Type().<Annotation>createReference(TARGET_EQUALS_PACKAGE + "." + IGNORED_BY_EQUALS));

				for (int i = 2; i < clone.getBody().getStatements().size() - 1; i++) {
					final CtInvocation targetInvocation = (CtInvocation) ((CtInvocation) clone.getBody().getStatement(i)).getArguments().get(0);
					final CtExecutable declaration = targetInvocation.getExecutable().getDeclaration();
					if (declaration != null && declaration.getAnnotations().contains(ignoredAnnotation)) {
						clone.getBody().getStatement(i--).delete();
						continue;
					}
					CtInvocation replace = (CtInvocation) factory.Core().clone(clone.getBody().getStatement(i));
					clone.getBody().getStatement(i).replace(replace);
				}

				target.addMethod(clone);
			}
		}.scan(getFactory().Class().get("spoon.reflect.visitor.CtBiScannerDefault"));
	}

	private void insertStartingPoint(CtClass<Object> target) {
		// !new EqualsVisitorTemplate().biScan(element, other);
		final CtMethod<?> equalsMethod = target.getMethodsByName("equals").get(0);
		final Factory factory = target.getFactory();
		final CtVariableAccess<?> elementAccess = factory.Code().createVariableRead(equalsMethod.getParameters().get(0).getReference(), false);
		final CtVariableAccess<?> otherAccess = factory.Code().createVariableRead(equalsMethod.getParameters().get(1).getReference(), false);
		final CtExecutableReference<Object> biScanRef = factory.Executable().createReference("CtAbstractBiScanner boolean#biScan()");
		final CtConstructorCall<Object> newEqualsVisitor = factory.Code().createConstructorCall(target.getReference());
		final CtReturn ctReturn = (CtReturn) equalsMethod.getBody().getStatements().get(0);
		final CtUnaryOperator<Object> unaryOperator = factory.Core().createUnaryOperator();
		unaryOperator.setKind(UnaryOperatorKind.NOT);
		unaryOperator.setOperand(factory.Code().createInvocation(newEqualsVisitor, biScanRef, elementAccess, otherAccess));
		ctReturn.getReturnedExpression().replace(unaryOperator);
	}

	private CtClass<Object> createEqualsVisitor() {
		final CtPackage aPackage = getFactory().Package().getOrCreate(TARGET_EQUALS_PACKAGE);
		final CtClass<Object> target = getFactory().Class().get(GENERATING_EQUALS);
		target.setSimpleName("EqualsVisitor");
		target.addModifier(ModifierKind.PUBLIC);
		target.removeModifier(ModifierKind.ABSTRACT);
		target.setSuperclass(getFactory().Type().createReference("spoon2.scanner.CtAbstractBiScanner"));
		aPackage.addType(target);
		renameReferences(target, target.getSimpleName(), aPackage.getReference(), GENERATING_EQUALS);
		return target;
	}
}
