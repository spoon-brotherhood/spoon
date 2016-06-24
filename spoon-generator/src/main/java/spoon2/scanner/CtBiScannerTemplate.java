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
package spoon2.scanner;

/**
 * This visitor implements a deep-search scan on the model for 2 elements.
 *
 * Ensures that all children nodes are visited once, a visit means three method
 * calls, one call to "enter", one call to "exit" and one call to biScan.
 *
 * This class is generated automatically.
 *
 * Is used by EqualsVisitor.
 */
abstract class CtBiScannerTemplate extends CtAbstractBiScanner {
}
