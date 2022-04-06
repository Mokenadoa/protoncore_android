/*
 * Copyright (c) 2020 Proton Technologies AG
 * This file is part of Proton Technologies AG and ProtonCore.
 *
 * ProtonCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonCore.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.proton.core.network.domain

/**
 * Persistently stored preferences for network module.
 */
interface NetworkPrefs {

    /** Base url of currently active Proton API proxy. [null] if primary API is in use */
    var activeAltBaseUrl: String?

    /** Timestamp for last primary API fail. After a defined period (see
     * [ApiClient.proxyValidityPeriodMs]) primary API will be attempted again. */
    var lastPrimaryApiFail: Long

    /** List of base urls for Proton API proxies returned in the last DoH query. */
    var alternativeBaseUrls: List<String>?

    /** Timestamp for last alternatives refresh. Will used with [DohProvider.MIN_REFRESH_INTERVAL_MS]
     * and compared to current timestamp */
    var lastAlternativesRefresh: Long
}
