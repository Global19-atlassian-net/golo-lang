/*
 * Copyright (c) 2012-2021 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.compiler.parser;

public class ASTForLoop extends GoloASTNode {

  public ASTForLoop(int id) {
    super(id);
  }

  public ASTForLoop(GoloParser p, int id) {
    super(p, id);
  }

  @Override
  public String toString() {
    return "ASTForLoop{}";
  }

  @Override
  public Object jjtAccept(GoloParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
