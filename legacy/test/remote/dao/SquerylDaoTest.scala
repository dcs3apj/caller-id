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
 * @author unclealex72
 *
 */

package legacy.remote.dao

import org.scalatest.Args
import org.scalatest.Finders
import org.scalatest.FunSuite
import org.scalatest.GivenWhenThen
import org.scalatest.Status
import org.scalatest.matchers.ShouldMatchers
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import org.squeryl.dsl.QueryDsl
import legacy.remote.model.CallerIdSchema._
import legacy.remote.model.CallerIdSchema

/**
 * @author alex
 *
 */
abstract class SquerylDaoTest extends FunSuite with ShouldMatchers with GivenWhenThen {

  Class forName "org.h2.Driver"

  protected override def runTest(testName: String, args: Args): Status = {
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:mem:", "", ""),
        new H2Adapter))
    inTransaction {
      CallerIdSchema.create
      super.runTest(testName, args)
    }
  }
}