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

import org.squeryl.KeyedEntity

/**
 * A basic trait for storing and retrieving legacy.model classes from a persitence store
 * @author alex
 *
 */
trait BasicDao[M <: KeyedEntity[_]] {

  /**
   * Store a legacy.model.
   * @param model The legacy.model to store.
   */
  def store(model: M): Unit

  /**
   * Store models.
   * @param models The legacy.model to store.
   */
  def storeAll(models: List[M]): Unit

  /**
   * Get all stored models.
   * @return All models in the persistence store.
   */
  def getAll: List[M]
}