/*
 * Copyright (c) 2012-2020 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.runtime;

import java.util.NoSuchElementException;
import static java.util.Arrays.copyOfRange;

public final class ArrayHelper {

  private ArrayHelper() {
    throw new UnsupportedOperationException("Don't instantiate utility classes");
  }

  public static Object head(Object[] array) {
    if (array.length == 0) {
      return null;
    }
    return array[0];
  }

  public static Object[] tail(Object[] array) {
    if (array.length >= 1) {
      return copyOfRange(array, 1, array.length);
    }
    return new Object[0];
  }

  public static Object first(Object[] array) {
    if (array.length == 0) {
      throw new NoSuchElementException("Empty array");
    }
    return array[0];
  }

  public static Object last(Object[] array) {
    if (array.length == 0) {
      throw new NoSuchElementException("Empty array");
    }
    return array[array.length - 1];
  }

  public static boolean isEmpty(Object[] array) {
    return array.length == 0;
  }
}
