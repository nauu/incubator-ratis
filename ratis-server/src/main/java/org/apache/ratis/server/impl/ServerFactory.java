/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ratis.server.impl;

import org.apache.ratis.rpc.RpcFactory;
import org.apache.ratis.server.RaftServer;
import org.apache.ratis.server.RaftServerRpc;

/** A factory interface for creating server components. */
public interface ServerFactory extends RpcFactory {
  static ServerFactory cast(RpcFactory rpcFactory) {
    if (rpcFactory instanceof ServerFactory) {
      return (ServerFactory)rpcFactory;
    }
    throw new ClassCastException("Cannot cast " + rpcFactory.getClass()
        + " to " + ServerFactory.class
        + "; rpc type is " + rpcFactory.getRpcType());
  }

  /** Create a new {@link LogAppender}. */
  LogAppender newLogAppender(RaftServerImpl server, LeaderState state, FollowerInfo f);

  RaftServerRpc newRaftServerRpc(RaftServer server);

  abstract class BaseFactory implements ServerFactory {
    @Override
    public LogAppender newLogAppender(
        RaftServerImpl server, LeaderState state, FollowerInfo f) {
      return new LogAppender(server, state, f);
    }
  }
}
