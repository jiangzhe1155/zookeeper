/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zookeeper;

/**
 * This interface specifies the public interface an event handler class must
 * implement. A ZooKeeper client will get various events from the ZooKeepr
 * server it connects to. An application using such a client handles these
 * events by registering a callback object with the client. The callback object
 * is expected to be an instance of a class that implements Watcher interface.
 *
 */
public interface Watcher {

    /**
     * This interface defines the possible states an Event may represent
     *
     * zookeeper 客户端连接 服务端所产生的各种状态
     */
    public interface Event {
        /**
         * Enumeration of states the ZooKeeper may be at the event
         * 连接的状态
         */
        public enum KeeperState {
            Unknown(-1),
            Disconnected(0),
            NoSyncConnected(1),
            SyncConnected(3),
            Expired(-112);

            private final int intValue;     // Integer representation of value
            // for sending over wire

            KeeperState(int intValue) {
                this.intValue = intValue;
            }

            public int getIntValue() {
                return intValue;
            }

            public static KeeperState fromInt(int intValue) {
                switch (intValue) {
                    case -1:
                        return KeeperState.Unknown;
                    case 0:
                        return KeeperState.Disconnected;
                    case 1:
                        return KeeperState.NoSyncConnected;
                    case 3:
                        return KeeperState.SyncConnected;
                    case -112:
                        return KeeperState.Expired;

                    default:
                        throw new RuntimeException("Invalid integer value for conversion to KeeperState");
                }
            }
        }

        /**
         * Enumeration of types of events that may occur on the ZooKeeper
         * 事件的动作
         */
        public enum EventType {
            None(-1),
            NodeCreated(1),
            NodeDeleted(2),
            NodeDataChanged(3),
            NodeChildrenChanged(4);

            private final int intValue;     // Integer representation of value
            // for sending over wire

            EventType(int intValue) {
                this.intValue = intValue;
            }

            public int getIntValue() {
                return intValue;
            }

            public static EventType fromInt(int intValue) {
                switch (intValue) {
                    case -1:
                        return EventType.None;
                    case 1:
                        return EventType.NodeCreated;
                    case 2:
                        return EventType.NodeDeleted;
                    case 3:
                        return EventType.NodeDataChanged;
                    case 4:
                        return EventType.NodeChildrenChanged;

                    default:
                        throw new RuntimeException("Invalid integer value for conversion to EventType");
                }
            }
        }
    }

    // 当有事件到来时，就会执行这个回调函数,根据不同的观察者 来选择对应的处理逻辑
    abstract public void process(WatchedEvent event);
}
