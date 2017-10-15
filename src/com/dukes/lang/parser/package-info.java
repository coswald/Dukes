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
 * <p>Describes an abstract interpretation of any programming language parser.
 * However, given the types parsers available, this currently only holds an
 * abstract implementation of a table parser. This does not disallow any
 * parser, as the {@code Parser} class is what will be passed on to the
 * semantic analyzer.</p>
 * <p>Exceptions provided include {@code ParsingException} and
 * {@code InvalidStackValueException}. Each is explain in detail within their
 * class.  
 * @author Coved W Oswald
 * @version 2.0
 * @since 0.2.0
 */
package com.dukes.lang.parser;
