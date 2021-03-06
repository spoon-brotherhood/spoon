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
package spoon.reflect.code;

/**
 * This code element defines an write access to an array.
 *
 * In Java, it is a usage of a array inside an assignment.
 *
 * For example:
 * <pre>
 *     array[0] = "new value";
 *     array[0] += "";
 * </pre>
 *
 * If you process this element, keep in mind that you won't process array[0]++.
 *
 * @param <T>
 * 		type of the array
 */
public interface CtArrayWrite<T> extends CtArrayAccess<T, CtExpression<?>> {
	@Override
	CtArrayWrite<T> clone();
}
