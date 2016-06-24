package spoon2;

import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.ReferenceFilter;

import java.util.List;

public final class RefactoringHelper {
	public static void renameReferences(final CtType<?> fromType, String toName, CtPackageReference toPackage, final String oldLocalisation) {
		final List<CtTypeReference> references = fromType.getReferences(new ReferenceFilter<CtTypeReference>() {
			@Override
			public boolean matches(CtTypeReference reference) {
				return reference != null && oldLocalisation.equals(reference.getQualifiedName());
			}

			@Override
			public Class<CtTypeReference> getType() {
				return CtTypeReference.class;
			}
		});
		for (CtTypeReference reference : references) {
			reference.setSimpleName(toName);
			reference.setPackage(toPackage);
		}
	}

	public static void renameReferences(final Factory fromFactory, String toName, CtPackageReference toPackage, final String oldLocalisation) {
		final List<CtTypeReference> references = Query.getReferences(fromFactory, new ReferenceFilter<CtTypeReference>() {
			@Override
			public boolean matches(CtTypeReference reference) {
				return reference != null && oldLocalisation.equals(reference.getQualifiedName());
			}

			@Override
			public Class<CtTypeReference> getType() {
				return CtTypeReference.class;
			}
		});
		for (CtTypeReference reference : references) {
			reference.setSimpleName(toName);
			reference.setPackage(toPackage);
		}
	}

	private RefactoringHelper() {
		throw new AssertionError("No instance.");
	}
}
