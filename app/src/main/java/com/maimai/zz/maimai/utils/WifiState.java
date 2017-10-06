/*
 * Copyright (C) 2017 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maimai.zz.maimai.utils;

import android.net.wifi.WifiManager;

public enum WifiState {
  DISABLING(WifiManager.WIFI_STATE_DISABLING, "禁用ing"),
  DISABLED(WifiManager.WIFI_STATE_DISABLED, "禁用"),
  ENABLING(WifiManager.WIFI_STATE_ENABLING, "启动ing"),
  ENABLED(WifiManager.WIFI_STATE_ENABLED, "启动"),
  UNKNOWN(WifiManager.WIFI_STATE_UNKNOWN, "小Mai不清楚你得网络");

  public final int state;
  public final String description;

  WifiState(final int state, String description) {
    this.state = state;
    this.description = description;
  }

  /**
   * Gets WifiState enum basing on integer value
   *
   * @param state as an integer
   * @return WifiState enum
   */
  public static WifiState fromState(final int state) {
    switch (state) {
      case WifiManager.WIFI_STATE_DISABLING:
        return DISABLING;
      case WifiManager.WIFI_STATE_DISABLED:
        return DISABLED;
      case WifiManager.WIFI_STATE_ENABLING:
        return ENABLING;
      case WifiManager.WIFI_STATE_ENABLED:
        return ENABLED;
      case WifiManager.WIFI_STATE_UNKNOWN:
        return UNKNOWN;
      default:
        return UNKNOWN;
    }
  }

  @Override public String toString() {
    return "WifiState{" + "state=" + state + ", description='" + description + '\'' + '}';
  }
}
