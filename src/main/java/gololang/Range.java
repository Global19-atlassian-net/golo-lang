/*
 * Copyright (c) 2012-2020 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package gololang;

import java.util.Collection;

/**
 * Represents a generic value range.
 * <p>
 * A range represent a set of values between to bounds, optionally with a step (or increment) from
 * one value to the next.
 */
public interface Range<T> extends Collection<T>, HeadTail<T> {

  /**
   * Gets the lower bound of the range.
   *
   * @return the starting value of the range.
   */
  T from();

  /**
   * Gets the upper bound of the range.
   *
   * @return the excluded ending value of the range.
   */
  T to();

  /**
   * Gets the increment of the range.
   *
   * @return the number of value to between two elements in the range.
   */
  int increment();

  /**
   * Sets the increment of the range.
   *
   * @param value the new increment.
   * @return the range itself.
   */
  Range<T> incrementBy(int value);

  /**
   * Sets the negative increment of the range.
   *<p>
   * this is equivalent to:
   * <pre class="listing"><code class="lang-java" data-lang="java">
   * range.incrementBy(-value)
   * </code></pre>
   *
   * @param value the new increment.
   * @return the range itself.
   */
  Range<T> decrementBy(int value);

  /**
   * Checks if the range encloses the value.
   * <p>
   * i.e. if {@code from() <= value} and {@code value < to()} (for positive increments, resp. for
   * negative ones), regardless the increment value.
   * <p>
   * For instance a range between 0 and 5 with an increment of 2 encloses 1 but don't contains it.
   *
   * @param value the value to check.
   */
  boolean encloses(T value);

  /**
   * Creates a new reversed range from this range.
   * <p>
   * i.e. swaps the {@code from()} and {@code to()} values and sets the increment to
   * {@code -increment()}.
   */
  Range<T> reversed();
}
