/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.rssl.phizic.messaging.mail.messagemaking.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


/**
 * An activation DataSource object that sources the data from
 * a byte[] array.
 * @version $Rev: 467553 $ $Date: 2006-10-25 00:01:51 -0400 (Wed, 25 Oct 2006) $
 */
public class ByteArrayDataSource implements DataSource {
    // the data source
    private byte[] source;
    // the content MIME type
    private String contentType;
    // the name information (defaults to a null string)
    private String name = "";

    /**
     * Create a ByteArrayDataSource directly from a byte array.
     *
     * @param data   The source byte array (not copied).
     * @param type   The content MIME-type.
     */
    public ByteArrayDataSource(byte[] data, String type) {
        source = data;
        contentType = type;
    }

    /**
     * Create an input stream for this data.  A new input stream
     * is created each time.
     *
     * @return An InputStream for reading the encapsulated data.
     * @exception IOException
     */
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(source);
    }


    /**
     * Open an output stream for the DataSource.  This is not
     * supported by this DataSource, so an IOException is always
     * throws.
     *
     * @return Nothing...an IOException is always thrown.
     * @exception IOException
     */
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Writing to a ByteArrayDataSource is not supported");
    }


    /**
     * Get the MIME content type information for this DataSource.
     *
     * @return The MIME content type string.
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Retrieve the DataSource name.  If not explicitly set, this
     * returns "".
     *
     * @return The currently set DataSource name.
     */
    public String getName() {
        return name;
    }


    /**
     * Set a new DataSource name.
     *
     * @param name   The new name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
