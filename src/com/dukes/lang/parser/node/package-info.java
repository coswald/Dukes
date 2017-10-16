/*
 * Copyright (C) 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  
 */

/**
 * Stores all the necessary classes to implement an abstract syntax tree. This
 * is shown in the {@code AbstractSyntaxNode} class, but more subtly shown in 
 * {@code NullNode} and {@code ExpressionNode}. More detail is described within
 * these classes; however, a quick description is as follows:
 * {@code ExpressionNode}s are only allowed to have {@code ExpressionNode}s as
 * chilren. {@code NullNode}s are an implementation of the Null Object paradigm
 * shown <a href="https://en.wikipedia.org/wiki/Null_object_pattern">here</a>.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
package com.dukes.lang.parser.node;
