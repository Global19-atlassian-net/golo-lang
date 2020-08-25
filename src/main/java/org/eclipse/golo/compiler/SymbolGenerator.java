/*
 * Copyright (c) 2012-2020 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.compiler;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Name generator for synthetic objects.
 * <p>
 * The generated name follows the patter {@code __$$_<name>_<counter>}.
 * The default name is {@code symbol}.
 * <p>
 * The generator maintains a stack of scope names, to generate hierarchical names.
 * <pre class="listing"><code class="lang-java" data-lang="java">
 * SymbolGenerator sym = new SymbolGenerator("closure");
 * sym.current(); // __$$_closure_0
 * sym.next(); // __$$_closure_1
 * sym.current(); // __$$_closure_1
 * sym.enter("scope");
 * sym.next(); // __$$_closure_scope_2
 * sym.enter("subscope");
 * sym.next(); // __$$_closure_scope_subscope_3
 * sym.exit().exit();
 * sym.next(); // __$$_closure_4
 * </code></pre>
 * <p>
 * Since the counter maintains uniqueness, the name and scopes only purpose is to give
 * somewhat readable names to help debugging.
 * <p>
 * Be warned that the uniqueness is only preserved in the context of a single generator.
 * Two independent generators with the same name (and scope) can produce identical names.
 * <p>
 * A counter is used instead of e.g. the generation timestamp or a random number to guarantee stability across
 * compilations to ease debugging.
 * <p>If a true uniqueness is require, or if the somewhat predictability of the symbol is a concern, one can use
 * {@link #getFor(String)} or even {@link #next(String)} in conjunction with {@code System.nanoTime()} or
 * {@code Random.nextLong()} (for instance <code class="lang-java">sym.next(String.valueOf(System.nanoTime()))</code>
 */
public final class SymbolGenerator {
  public static final String PREFIX = "__$$_";
  public static final String DEFAULT_NAME = "symbol";
  public static final String ESCAPE_MANGLE = "$";
  public static final String JOIN = "_";
  private final AtomicLong counter = new AtomicLong();
  private final Deque<String> prefixes = new LinkedList<>();

  public SymbolGenerator(String name) {
    this.prefixes.addLast(name == null ? DEFAULT_NAME : name.replace('.', '$'));
  }

  public SymbolGenerator() {
    this.prefixes.addLast(DEFAULT_NAME);
  }

  private String name(String localName, long idx) {
    return name(
        (localName == null || "".equals(localName)
          ? ""
          : (localName + JOIN))
        + idx);
  }

  private String name(String localName) {
    String name = PREFIX + String.join(JOIN, prefixes);
    if (localName != null && !"".equals(localName)) {
      name += JOIN + localName;
    }
    return name;
  }

  /**
   * Generates the next name for the current context.
   * <p>
   * Increments the counter and returns the name generated by the current scope and counter.
   *
   * @return the next name
   */
  public String next() {
    return next(null);
  }

  /**
   * Generates the next name for the current context and given simple name.
   * <p>
   * Increments the counter and returns the name generated by the given name and the current scope and counter, and increment
   * it.
   *
   * <p><strong>Warning</strong>: no check is made that the given name will produce a valid language symbol.
   * @param name the simple name to derive the unique name from
   * @return the corresponding next name
   */
  public String next(String name) {
    return name(name, counter.incrementAndGet());
  }

  /**
   * Mangles the given name without using the counter.
   *
   * <p>This can be used in macros to provide hygiene by mangling the local variable names. Mangling is escaped for
   * names beginning by {@code ESCAPE_MANGLE}.
   *
   * <p>For instance:
   * <pre class="listing"><code class="lang-golo" data-lang="golo">
   * let symb = SymbolGenerator("foo")
   * symb: getFor("bar")  # __$$_foo_bar
   * symb: getFor("$bar") # bar
   * </code></pre>
   * <p><strong>Warning</strong>: no check is made that the given name will produce a valid language symbol.
   */
  public String getFor(String localName) {
    if (localName.startsWith(ESCAPE_MANGLE)) {
      return localName.substring(ESCAPE_MANGLE.length());
    }
    return name(localName);
  }

  /**
   * Generate the name for the current context and given simple name.
   * <p>
   * Returns the name generated by the given name and the current scope and counter, without
   * incrementing it.
   * <p><strong>Warning</strong>: no check is made that the given name will produce a valid language symbol.
   *
   * @param name the simple name to derive the unique name from
   * @return the corresponding generated name
   */
  public String current(String name) {
    return name(name, counter.get());
  }

  /**
   * Generate the name for the current context.
   * <p>
   * Returns the name generated by the current scope and counter, without
   * incrementing it.
   *
   * @return the generated name
   */
  public String current() {
    return name(null, counter.get());
  }

  /**
   * Exit from a scope.
   */
  public SymbolGenerator exit() {
    if (this.prefixes.size() > 1) {
      this.prefixes.removeLast();
    }
    return this;
  }

  /**
   * Enter a hierarchical scope.
   *
   * <p><strong>Warning</strong>: no check is made that the given name will produce a valid language symbol.
   * @param scopeName the name of the scope.
   */
  public SymbolGenerator enter(String scopeName) {
    if (scopeName == null || scopeName.isEmpty()) {
      return this;
    }
    this.prefixes.addLast(scopeName.replace('.', '$'));
    return this;
  }

}