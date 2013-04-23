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
package uk.co.unclealex.callerid.remote.numbers

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.GivenWhenThen

class JsonResourceCityDaoTest extends FunSuite with ShouldMatchers with GivenWhenThen {

  val jsonResourceCityDao = new JsonResourceCityDao

  test("Extracting the international code") {
    jsonResourceCityDao.extractInternationalDiallingCode("441256118118") should equal("44")
  }

  test("Extracting UK cities") {
    Map("1697500500" -> "Brampton", "1697300300" -> "Wigton", "1697400400" -> "Raughton Head").foreach {
      case (number, expectedCityName) =>
        When(s"Trying to find the city for ${number}")
        Then(s"The city name should be ${expectedCityName}")
        jsonResourceCityDao.extractCity(number, "44").get.name should equal(expectedCityName)
    }
  }

  test("Get country for city") {
    val basingstoke = City(name = "Basingstoke", stdCode = "1256")
    jsonResourceCityDao.countryOf(basingstoke).name should equal("United Kingdom")
  }

  test("Get countries") {
    jsonResourceCityDao.countries("44").map(_.name) should equal(List("United Kingdom", "Guernsey", "Isle of Man", "Jersey"))
  }
}
