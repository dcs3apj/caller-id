/**
 * Copyright 2012 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 *
 * @author alex
 *
 */

package uk.co.unclealex.callerid.modem;

import java.net.UnknownHostException;

import javax.inject.Inject;

import uk.co.unclealex.callerid.device.NetworkDevice;
import uk.co.unclealex.process.packages.PackagesRequired;

import com.google.common.base.Charsets;

/**
 * A modem that is connected via a network bridge (e.g. ser2net).
 * @author alex
 *
 */
@PackagesRequired("ser2net")
public class NetworkModem extends NetworkDevice implements Modem {

  /**
   * Create a new ASCII network modem.
   * 
   * @param port
   *          the port
   * @throws UnknownHostException 
   */
  @Inject
  public NetworkModem(int port, String host) throws UnknownHostException {
    super(port, host, Charsets.US_ASCII);
  }

  /**
   * Create a new ASCII network modem.
   * 
   * @param port
   *          the port
   */
  @Inject
  public NetworkModem(int port) {
    super(port, Charsets.US_ASCII);
  }
}
