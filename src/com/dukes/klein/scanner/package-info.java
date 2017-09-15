/*
 * Copyright (C) 2017 Coved W Oswald
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
 * <p>Describes how to not only allow a scanner object to be created for Klien,
 * but also allows for an abstract interpretation of any scanner. This is
 * clear in classes such as {@code AbstractScanner} and {@code AbstractToken}.
 * This also allows for different implementations to have complete control over
 * how the scanning process is done. This is shown in the {@code Inputter}
 * class, as it doesn't know how to read characters in a stream, but provides
 * the framework to do so. This is done so that we can reason about the
 * theoretics without the direct implementation</p>
 * <p>Exceptions provided include {@code IllegalTokenTypeException}, 
 * {@code LexicalScanningException}, and {@code LexicalAnalysisException}. Each
 * is explained in detail within their class. The main Scanner for the Klein
 * programming language is {@code KleinScanner}.</p>
 * @since 1.0
 * @author Coved W Oswald
 * @version 1.0
 */
package com.dukes.klein.scanner;
