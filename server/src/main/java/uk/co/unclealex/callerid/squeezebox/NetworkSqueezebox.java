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

package uk.co.unclealex.callerid.squeezebox;

import java.io.IOException;

import uk.co.unclealex.callerid.device.LocalNetworkDevice;
import uk.co.unclealex.process.packages.PackagesRequired;

import com.google.common.base.Charsets;

/**
 * An interface for connecting with a Logitech media server using its network
 * interface.
 * 
 * @author alex
 * 
 */
@PackagesRequired("logitechmediaserver")
public class NetworkSqueezebox extends LocalNetworkDevice implements Squeezebox {

  /**
   * @param port
   */
  public NetworkSqueezebox(int port) {
    super(port, Charsets.UTF_8);
  }

  @Override
  public String execute(String command) throws IOException {
    writeLine(command);
    String result = readLine();
    if (result != null && command.endsWith("?")) {
      return result.substring(command.length() - 1);
    }
    else {
      return result;
    }
  }
}