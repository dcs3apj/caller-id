/**
 * Copyright 2013 Alex Jones
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
package uk.co.unclealex.callerid.remote.google

import com.google.gdata.client.GoogleService
import uk.co.unclealex.callerid.remote.model.User

/**
 * An interface for retrieving OAuth access tokens from Google.
 */
trait GoogleTokenService {

  /**
   * Add user credentials to a Google service.
   * @param googleService The service that will be used to speak to google.
   * @param scope The scope of the request being made to Google.
   * @param user The user who's information is being retrieved.
   */
  def secureForUser(googleService: GoogleService, scope: String, user: User): Unit

  /**
   * Get the access token for a user, refreshing it if neccessary.
   * @param user The user who is requesting an access token.
   * @return An OAuth access token that will allow a user to access their Google contacts.
   */
  implicit def accessToken(user: User): String

  /**
   * Install a Google success code for a user.
   * @param The user who made the request for a success code.
   * @param The success code supplied by Google.
   */
  def installSuccessCode(user: User, successCode: String): Unit
}