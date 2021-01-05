/*
 * Copyright (c) 2012-2021 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.runtime;

public class AmbiguousFunctionReferenceException extends ReflectiveOperationException {

  public AmbiguousFunctionReferenceException(String message) {
    super(message);
  }

  public AmbiguousFunctionReferenceException(String message, Throwable cause) {
    super(message, cause);
  }

  public AmbiguousFunctionReferenceException(Throwable cause) {
    super(cause);
  }
}
