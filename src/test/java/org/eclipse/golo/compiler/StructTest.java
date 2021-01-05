/*
 * Copyright (c) 2012-2021 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.compiler;

import gololang.*;
import org.eclipse.golo.internal.testing.GoloTest;

import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.fail;
import static org.eclipse.golo.internal.testing.TestUtils.compileAndLoadGoloModule;

public class StructTest extends GoloTest {

  @Override
  public String srcDir() { return "for-execution/"; }

  private static final String SRC = "src/test/resources/for-execution/";

  @Test
  public void struct_outside() throws Throwable {
    load("structs");
    run("structs-outside");
  }

  @Test
  public void structs() throws Throwable {
    run("structs");
  }
}
